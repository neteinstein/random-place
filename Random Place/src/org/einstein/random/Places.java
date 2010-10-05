/*
 * Copyleft (c) Einstein's Team
 * 
 * Code licensed under GPL v3.0 license. 
 * 
 * Pedro Vicente - neteinstein @ 2010/10/01
 */

package org.einstein.random;

import java.util.Vector;

import org.einstein.random.entities.Place;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Places extends ListActivity {

	/** The ALL. */
	private final int ABOUT = 0;

	/** The resources */
	private Resources res = null;

	/** The my alert dialog. */
	private AlertDialog myAlertDialog = null;

	protected void onStart() {
		super.onStart();
		this.res = getResources();

		PlacesListAdapter placeListAdapter = new PlacesListAdapter(Places.this,
				R.layout.place, R.id.placeName, getPlaces());
		setListAdapter(placeListAdapter);
	}

	private Vector<Place> getPlaces() {

		Vector<Place> places = new Vector<Place>();
		
		/*
		 * Start Demo Code
		 * 
		 * In this place it should access memory 
		 * and retrieve the places stored
		 */
		Place demo = new Place();
		demo.setName("Forum");
		demo.setWeight(10);
		
		//For the add layout...
		places.add(null);
		
		places.add(demo);
		
		/*
		 * End Demo Code
		 * 
		 */

		return places;
	}

	/**
	 * On create options menu.
	 * 
	 * @param menu
	 *            the menu
	 * @return true, if successful {@inheritDoc}
	 */

	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, ABOUT, 0, getString(R.string.places_team));

		return true;
	}

	/**
	 * On options item selected.
	 * 
	 * @param item
	 *            the item
	 * @return true, if successful {@inheritDoc}
	 */
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case ABOUT:
			alertDialog(getString(R.string.info),
					getString(R.string.places_team), getString(R.string.ok));
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * The Class HomeListAdapter.
	 */
	private class PlacesListAdapter extends ArrayAdapter<Place> {

		/** The LAYOU t_ more. */
		private final int LAYOUT_ADD = 0;

		/** The LAYOU t_ item. */
		private final int LAYOUT_ITEM = 1;

		/** The LAYOUTS. */
		private final int LAYOUTS = 2;

		/** The m inflater. */
		private LayoutInflater mInflater = null;

		/** The context. */
		private Context context = null;

		/**
		 * Instantiates a new home list adapter.
		 * 
		 * @param context
		 *            the context
		 * @param resource
		 *            the resource
		 * @param resourceId
		 *            the id of the layout
		 * @param data
		 *            the full list
		 */
		public PlacesListAdapter(Context context, int resource, int resourceId,
				Vector<Place> data) {
			super(context, resource, resourceId, data);
			mInflater = LayoutInflater.from(context);
			this.context = context;

		}

		/**
		 * Gets the item view type.
		 * 
		 * @param position
		 *            the position
		 * @return the item view type {@inheritDoc}
		 */
		@Override
		public int getItemViewType(int position) {
			if (position == 0) {
				return LAYOUT_ADD;
			} else {
				return LAYOUT_ITEM;
			}
		}

		/**
		 * 
		 * Gets the view type count.
		 * 
		 * @return the view type count {@inheritDoc}
		 */
		@Override
		public int getViewTypeCount() {
			return LAYOUTS;
		}

		/**
		 * Gets the view.
		 * 
		 * @param position
		 *            the position
		 * @param convertView
		 *            the convert view
		 * @param parent
		 *            the parent
		 * @return the view {@inheritDoc}
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (position != 0) {
				convertView = mInflater.inflate(R.layout.place, parent, false);
			} else {
				convertView = mInflater.inflate(R.layout.add, parent, false);
			}

			if (position != 0) {
				convertView = fillLayoutWithPlace(position, convertView);
			} else {
				convertView = fillLayoutWithAddLayout(position, convertView);
			}

			return convertView;
		}

		/**
		 * Fill layout with item.
		 * 
		 * @param position
		 *            the position
		 * @param convertView
		 *            the convert view
		 * @return the view
		 */
		private View fillLayoutWithPlace(int position, final View convertView) {

			final Place place = (Place) this.getItem(position);

			final TextView placeName = (TextView) convertView
					.findViewById(R.id.placeName);
			final EditText placeWeight = (EditText) convertView
					.findViewById(R.id.txtPlaceWeight);

			placeName.setText(place.getName());
			
			placeWeight.setText(place.getWeightString());

			return convertView;
		}

		/**
		 * Fill layout with place.
		 * 
		 * @param position
		 *            the position
		 * @param convertView
		 *            the convert view
		 * @return the view
		 */
		private View fillLayoutWithAddLayout(int position,
				final View convertView) {

			final Button addPlace = (Button) convertView.findViewById(R.id.buttonAddPlace);
			//final EditText placeName = (EditText) convertView
			//			.findViewById(R.id.placeName);
			
			addPlace.setText(res.getString(R.string.button_add_place));

			return convertView;
		}

	}

	/**
	 * Alert dialog.
	 * 
	 * @param title
	 *            the title
	 * @param content
	 *            the content
	 * @param button
	 *            the button
	 */
	protected void alertDialog(String title, String content, String button) {
		myAlertDialog = new AlertDialog.Builder(this).create();
		myAlertDialog.setTitle(title);
		myAlertDialog.setMessage(content);
		myAlertDialog.setButton(button, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				myAlertDialog.dismiss();
				return;
			}
		});
		myAlertDialog.show();
	}
}
