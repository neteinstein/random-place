/*
 * Copyleft (c) Einstein's Team
 * 
 * Code licensed under GPL v3.0 license. 
 * 
 * Pedro Vicente - neteinstein @ 2010/10/01
 */

package org.einstein.random;

import org.einstein.random.R;
import android.app.Activity;

public class Random extends Activity{

	protected void onStart() {
        super.onStart();

        setContentView(R.layout.random);
	}
}
