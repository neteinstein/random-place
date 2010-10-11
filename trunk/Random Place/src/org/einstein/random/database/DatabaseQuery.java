package org.einstein.random.database;

import java.util.ArrayList;

import org.einstein.random.entities.Place;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

//TODO This example needs to be adapted to this application

public class DatabaseQuery {
	// Variables area
	private ArrayList<String> arrayKeys = null;
	private ArrayList<String> arrayValues = null;
	private ArrayList<String> databaseColumns = null;
	private ArrayList<String> databaseColumnsType = null;
	private DBAdapter database;

	/**
	 * Initialize the ArrayList
	 * 
	 * @param context
	 *            Pass context from calling class.
	 */
	public DatabaseQuery(Context context) {
		// Create an ArrayList of keys and one of the options/parameters
		// for the keys.
		databaseColumns = new ArrayList<String>();
		databaseColumnsType = new ArrayList<String>();
		// Database columns
		databaseColumns.add("PLACE_NAME");
		databaseColumns.add("PLACE_WEIGHT");

		// Database columns types
		databaseColumnsType.add("text not null");
		databaseColumnsType.add("integer");

		// Call the database adapter to create the database
		database = new DBAdapter(context, "places", databaseColumns,
				databaseColumnsType);
		database.open();

		initializeArrays();
	}

	public void initializeArrays() {
		arrayKeys = new ArrayList<String>();
		arrayValues = new ArrayList<String>();

	}

	/**
	 * Append data to an ArrayList to then submit to the database
	 * 
	 * @param key
	 *            Key of the value being appended to the Array.
	 * @param value
	 *            Value to be appended to Array.
	 */
	public void appendData(String key, String value) {
		arrayKeys.add(key);
		arrayValues.add(value);
	}

	/**
	 * This method adds the row created by appending data to the database. The
	 * parameters constitute one row of data.
	 */
	public void addRow() {
		database.insertEntry(arrayKeys, arrayValues);
	}

	/**
	 * Get data from the table.
	 * 
	 * @param keys
	 *            List of columns to include in the result.
	 * @param selection
	 *            Return rows with the following string only. Null returns all
	 *            rows.
	 * @param selectionArgs
	 *            Arguments of the selection.
	 * @param groupBy
	 *            Group results by.
	 * @param having
	 *            A filter declare which row groups to include in the cursor.
	 * @param sortBy
	 *            Column to sort elements by.
	 * @param sortOption
	 *            ASC for ascending, DESC for descending.
	 * @return Returns an ArrayList<String> with the results of the selected
	 *         field.
	 */
	public ArrayList<Place> getData(String[] keys, String selection,
			String[] selectionArgs, String groupBy, String having,
			String sortBy, String sortOption) {

		ArrayList<Place> list = new ArrayList<Place>();
		Place place = null;

		Cursor results = database.getAllEntries(keys, selection, selectionArgs,
				groupBy, having, sortBy, sortOption);

		if (results != null) {
			results.moveToFirst();
		}

		for(int i=0;i<results.getCount();i++){
			// This should be get by column name and not index
			place = new Place();
			place.setName(results.getString(0));
			place.setWeight(Integer.parseInt((results.getString(1))));
			list.add(place);
			if (results != null) {
				results.moveToNext();
			}
		}
		results.close();
		return list;
	}

	/**
	 * Destroy the reporter.
	 * 
	 * @throws Throwable
	 */
	public void destroy() throws Throwable {
		database.close();
	}
}
