package org.einstein.random;

import org.einstein.random.database.DatabaseQuery;

import android.app.Application;

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
	

}
