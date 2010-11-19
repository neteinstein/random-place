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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Random extends Activity {

	private RandomPlace randomPlace = new RandomPlace();
	private final String PREFS_NAME = "randomPreferences";

	/** The my alert dialog. */
	private AlertDialog myAlertDialog = null;

	protected void onStart() {
		super.onStart();

		setContentView(R.layout.random);

		((ApplicationRandom) getApplication()).checkOrCreateDefaultFile();

		//The shared preferences should be placed in the application so that is a common method to all the activities
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		if (!((ApplicationRandom) getApplication())
				.ismExternalStorageAvailable()) {
			alertDialog("External Storage Unavailable!",
					"Please enable access to the SD Card", "Done!");
		} else {

			boolean isFirstRun = settings.getBoolean("isFirstRun", true);
			if (isFirstRun) {
				// getApplication().getT authorize();
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("isFirstRun", false);
			}

		}

	}

	protected void onResume() {
		super.onResume();

		final Button buttonRandom = (Button) this
				.findViewById(R.id.buttonRandom);
		final ToggleButton buttonNear = (ToggleButton) findViewById(R.id.buttonNear);
		final ToggleButton buttonTweet = (ToggleButton) findViewById(R.id.buttonTweet);

		buttonRandom.setOnClickListener(new OnClickListener() {

			public void onClick(View viewParam) {

				ArrayList<Place> places = ((ApplicationRandom) getApplication())
						.getPlaces("Places.xml");

				if (buttonNear.isChecked()) {
					alertDialog("Random Near Place",
							"Sorry... Near Places isn't available yet!",
							"Proceed");

				}

				if (places != null && places.size() > 0) {
					Place random = randomPlace.random(places);
					setPlace(random);
					
					SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
					String accessToken = settings.getString("consumerToken", null);
					
					if (buttonTweet.isChecked() && accessToken != null) {
						((ApplicationRandom)getApplication()).getTwitter().postOnTwitter("Goin' to " + random.getName());
					}
					
				} else {
					alertDialog("Random Place",
							"Hey...don't be lame, add a place first!",
							"Proceed");
				}
				
				

			}
		}

		); // end of launch.setOnclickListener

	}

	public void setPlace(Place place) {
		TextView placeNameText = (TextView) findViewById(R.id.placeName);
		placeNameText.setText(place.getName());

		ImageView placeImageView = (ImageView) findViewById(R.id.placeImage);

		// TODO in the future this will load an image from the internet

		// Is the try catch necessary?
		try {
			int id = getResources().getIdentifier(place.getImageURL(),
					"drawable", getPackageName());

			// If no image is found
			if (id == 0) {
				placeImageView.setImageResource(R.drawable.place_default);
			} else {
				Bitmap bitmap = BitmapFactory
						.decodeResource(getResources(), id);
				placeImageView.setImageBitmap(bitmap);
			}
		} catch (Exception e) {
			placeImageView.setImageResource(R.drawable.place_default);
			e.printStackTrace();
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
