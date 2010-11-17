/*
 * Copyleft (c) Einstein's Team
 * 
 * Code licensed under GPL v3.0 license. 
 * 
 * Pedro Vicente - neteinstein @ 2010/10/01
 */

package org.einstein.random;

import java.util.ArrayList;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Places extends ListActivity {

	/** The ALL. */
	private final int ABOUT = 0;

	/** The resources */
	private Resources res = null;

	/** The my alert dialog. */
	private AlertDialog myAlertDialog = null;

	private ArrayList<Place> places = null;

	private PlacesListAdapter placeListAdapter = null;

	protected void onStart() {
		super.onStart();
		this.res = getResources();

		// TODO: Instead of inflate the header through a hack
		// create an xml file with it, that is inflated with the rest of the
		// stuff
		places = new ArrayList<Place>();
		places.add(null);

		places.addAll(((ApplicationRandom) getApplication()).getPlaces());

		placeListAdapter = new PlacesListAdapter(Places.this, R.layout.place,
				R.id.placeName, places);
		setListAdapter(placeListAdapter);
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
		// private Context context = null;

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
				ArrayList<Place> data) {
			super(context, resource, resourceId, data);
			mInflater = LayoutInflater.from(context);
			// this.context = context;

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

			if (position == 0) {
				convertView = mInflater.inflate(R.layout.add, parent, false);
				convertView = fillLayoutWithAddLayout(position, convertView);

			} else {
				convertView = mInflater.inflate(R.layout.place, parent, false);
				convertView = fillLayoutWithPlace(position, convertView);
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
			final TextView placeWeight = (TextView) convertView
					.findViewById(R.id.placeWeight);
			final Button deletePlace = (Button) convertView
			.findViewById(R.id.deletePlace);
			
			placeName.setText(place.getName());

			placeWeight.setText(place.getWeightString());

			deletePlace.setText("Delete");


			// TODO: This should be added, the position should be detected onClick...
			deletePlace.setTag(position);

			deletePlace.setOnClickListener(new OnClickListener() {

				public void onClick(View view) {
					// TODO: This code should be optimized... 
					int position = Integer.parseInt(deletePlace.getTag()
							.toString());

					Place removed = places.remove(position);
					
					boolean placeDeleted = ((ApplicationRandom) getApplication())
							.savePlaces(places);

					if (placeDeleted) {
						placeListAdapter.remove(removed);
						placeListAdapter.notifyDataSetChanged();
					}else{
						places.add(removed);
					}

					/* IF remove above doesnt work... use this for now
					 * placeListAdapter.clear(); for(int
					 * i=0;i<places.size();i++){
					 * placeListAdapter.add(places.get(i));
					 * placeListAdapter.notifyDataSetChanged(); }
					 */

				}
			});

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

			final Button addPlace = (Button) convertView
					.findViewById(R.id.buttonAddPlace);

			addPlace.setText(res.getString(R.string.button_add_place));

			addPlace.setOnClickListener(new OnClickListener() {

				public void onClick(View viewParam) {

					String name = ((EditText) convertView
							.findViewById(R.id.txtPlaceName)).getText()
							.toString();

					Integer weight = Integer.parseInt(((EditText) convertView
							.findViewById(R.id.txtPlaceWeight)).getText()
							.toString());

					Place place = new Place();
					place.setName(name);
					place.setWeight(weight);

					boolean placeSaved = ((ApplicationRandom) getApplication())
							.savePlace(place);

					if (placeSaved) {
						placeListAdapter.add(place);
						placeListAdapter.notifyDataSetChanged();
					}

				}
			});

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
