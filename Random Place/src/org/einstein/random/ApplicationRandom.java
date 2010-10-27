package org.einstein.random;

import java.util.ArrayList;

import org.einstein.random.database.DatabaseQuery;
import org.einstein.random.entities.Place;

import android.app.Application;
import android.util.Log;

public class ApplicationRandom extends Application{
	
	private DatabaseQuery query = null;
	
	@Override
    public void onCreate() {
        super.onCreate();
        setDatabase(new DatabaseQuery(this));
    }

	public void setDatabase(DatabaseQuery query) {
		this.query = query;
	}

	public DatabaseQuery getDatabase() {
		return query;
	}
	
	
	public ArrayList<Place> getPlaces() {

		ArrayList<Place> places = new ArrayList<Place>();

		this.query = getDatabase();

		/*
		 * Start Demo Code
		 * 
		 * In this place it should access  and retrieve the places stored
		 */

		//TODO: SELECT DATA FROM PLACS AND ADD TO ARRAYLIST

		this.query.appendData("PLACE_NAME", "FORUM");
		this.query.appendData("PLACE_WEIGHT", "9");
		this.query.addRow();
		this.query.appendData("PLACE_NAME", "CONTI");
		this.query.appendData("PLACE_WEIGHT", "5");
		this.query.addRow();
		this.query.appendData("PLACE_NAME", "DV");
		this.query.appendData("PLACE_WEIGHT", "7");
		this.query.addRow();

		Place demo = new Place();

		places.add(null);
		try{
			places.addAll(this.query.getData(new String[] {"PLACE_NAME", "PLACE_WEIGHT"}, null, null, null, null, "PLACE_NAME", "DESC"));
			
		}catch(Exception e){

			Log.e("SQLite", "SQL Error:" , e);

			demo.setName("Forum");
			demo.setWeight(10);

			places.add(demo);

			demo = new Place();
			demo.setName("DV");
			demo.setWeight(8);

			places.add(demo);

		}

		/*
		 * End Demo Code
		 */
		return places;
	}

}
