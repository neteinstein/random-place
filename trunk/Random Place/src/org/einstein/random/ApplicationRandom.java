package org.einstein.random;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.einstein.random.entities.Place;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

public class ApplicationRandom extends Application {

	// private DatabaseQuery query = null;
	private boolean mExternalStorageAvailable = false;
	private boolean mExternalStorageWriteable = false;
	private String storagePath = Environment.getExternalStorageDirectory()
			.toString() + "/randomPlace/";

	//TODO: This file should be stored in preferences and checked on start if exists
	private String currentFile = "Places.xml";

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public ArrayList<Place> getPlaces() {
		return getPlaces(currentFile);
	}
	
	public ArrayList<Place> getPlaces(String fileName) {

		ArrayList<Place> places = new ArrayList<Place>();

		checkStorageStatus();

		if (mExternalStorageAvailable) {
			
			File folder = new File(storagePath);
			if (!f.exists() || !f.isDirectory()) {
				folder.mkdirs();
			} 
			
			File f = new File(storagePath + fileName);

			BufferedReader buf = null;

			try {

				buf = new BufferedReader(new InputStreamReader(
						new FileInputStream(f)));

				String readString = new String();

				Place place = null;

				while ((readString = buf.readLine()) != null) {

					StringTokenizer st = new StringTokenizer(readString, ";");
					place = new Place();
					place.setName(st.nextToken());
					try {
						place.setWeight(Integer.parseInt(st.nextToken()));
					} catch (Exception e) {
						place.setWeight(1);
					}

					places.add(place);

				}

			} catch (FileNotFoundException e) {
				Log.d("--- DEBUG ---", "FILE NOT FOUND\n\n\n\n\n\n\n\n");
			} catch (IOException e) {
				Log.d("--- DEBUG ---", "FILE NOT FOUND\n\n\n\n\n\n\n\n");
			}

		} else {
			Log.d("---SDCARD PROB --- ", "WTF?");
		}

		currentFile = fileName;

		return places;
	}

	public void checkStorageStatus() {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
	}

	public boolean checkOrCreateDefaultFile() {
		checkStorageStatus();

		if (mExternalStorageAvailable) {
			File f = new File(storagePath);

			File[] files = f.listFiles();

			if (files.length < 0) {
				if (mExternalStorageWriteable) {
					OutputStream outStream = null;
					File file = new File(storagePath, currentFile);
					try {
						outStream = new FileOutputStream(file);
						outStream.flush();
						outStream.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						return false;
					} catch (IOException e) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public boolean savePlace(String name, String weight) {
		
		//TODO Needs to append data to the current file and save it
		
		/*OutputStream outStream = null;
		File file = new File(storagePath, "Places.xml");
		try {
			outStream = new FileOutputStream(file);
			outStream.flush();
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}*/

		return true;

	}

	public boolean ismExternalStorageAvailable() {
		return mExternalStorageAvailable;
	}

	public void setmExternalStorageAvailable(boolean mExternalStorageAvailable) {
		this.mExternalStorageAvailable = mExternalStorageAvailable;
	}

	public boolean ismExternalStorageWriteable() {
		return mExternalStorageWriteable;
	}

	public void setmExternalStorageWriteable(boolean mExternalStorageWriteable) {
		this.mExternalStorageWriteable = mExternalStorageWriteable;
	}

}
