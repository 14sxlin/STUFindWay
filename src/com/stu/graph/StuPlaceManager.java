package com.stu.graph;

import java.io.Serializable;
import java.util.ArrayList;

public class StuPlaceManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<WayPoint> places;
	
	public StuPlaceManager() {
		places = new ArrayList<>();
	}
	
	public void addPlace(WayPoint wayPoint){
		places.add(wayPoint);
	}
	
	public void removePlace(int index){
		places.remove(index);
	}

	public ArrayList<WayPoint> getPlaces() {
		return places;
	}

	public void setPlaces(ArrayList<WayPoint> places) {
		this.places = places;
	}
	
	

}
