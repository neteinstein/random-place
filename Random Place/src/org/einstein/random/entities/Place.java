/*
 * Copyleft (c) Einstein's Team
 * 
 * Code licensed under GPL v3.0 license. 
 * 
 * Pedro Vicente - neteinstein @ 2010/10/01
 */

package org.einstein.random.entities;

public class Place {
	
	private String name = "";
	private int weight = 1;
	private String imageURL = "";

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		//TO DELETE
		this.imageURL = name.replace(" ", "_").toLowerCase();
	}
	public int getWeight() {
		return weight;
	}
	public String getWeightString() {
		return ""+weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getImageURL() {
		return imageURL;
	}

	
	
	
	
}
