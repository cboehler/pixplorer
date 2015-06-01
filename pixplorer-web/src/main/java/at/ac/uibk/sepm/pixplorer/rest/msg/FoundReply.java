package at.ac.uibk.sepm.pixplorer.rest.msg;

import java.util.ArrayList;
import java.util.List;

import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.Trophy;

/**
 * Reply for found call.
 * 
 * @author cbo
 */
public class FoundReply extends AbstractReply {
	/** list containing new trophies that the user has achieved */
	private List<Trophy> trophies = new ArrayList<>();
	
	/** list containing new places for the user */
	private List<Place> places = new ArrayList<>();	
	
	/** distance delta */
	private double distanceDelta;
	
	public List<Place> getPlaces() {
		return places;
	}
	
	public void setPlaces(List<Place> places) {
		this.places = places;
	}	
	
	public List<Trophy> getTrophies() {
		return trophies;
	}
	
	public void setTrophies(List<Trophy> trophies) {
		this.trophies = trophies;
	}
	
	public double getDistanceDelta() {
		return distanceDelta;
	}
	
	public void setDistanceDelta(double distanceDelta) {
		this.distanceDelta = distanceDelta;
	}
}
