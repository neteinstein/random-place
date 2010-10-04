package org.einstein.random;

import org.einstein.random.R;
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
                new Intent(Main.this, Random.class)));
        mTabHost.addTab(mTabHost.newTabSpec("add").setIndicator("Add New Place").setContent(
                new Intent(Main.this, Add.class)));

        mTabHost.setCurrentTab(0);
        
    }
    
}