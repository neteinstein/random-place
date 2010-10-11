/*
 * Copyleft (c) Einstein's Team
 * 
 * Code licensed under GPL v3.0 license. 
 * 
 * Pedro Vicente - neteinstein @ 2010/10/01
 */

package org.einstein.random;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class Main extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.tabs);

        final TabHost mTabHost = getTabHost();

        mTabHost.addTab(mTabHost.newTabSpec("random").setIndicator("Random").setContent(
                new Intent(Main.this, RandomPlace.class)));
        mTabHost.addTab(mTabHost.newTabSpec("places").setIndicator("Places Added").setContent(
                new Intent(Main.this, Places.class)));

        mTabHost.setCurrentTab(0);
        
    }
    
}