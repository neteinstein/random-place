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

public class RandomPlace {
	
	//Receives a list of places and selects a random place
	public Place random(ArrayList<Place> places){
		int totalWeight = 0;
        int i;

        for (i = 0; i < places.size(); i++) {
            totalWeight += places.get(i).getWeight();
        }
        
        int selection = new java.util.Random().nextInt(totalWeight);
        
        totalWeight = places.get(0).getWeight();

        for (i = 0; (i < places.size() - 1) && (selection >= totalWeight); i++) {
            totalWeight += places.get(i + 1).getWeight();
        }

        return places.get(i);
	} 
}
