package at.ac.uibk.sepm.pixplorer.rest.msg;

import java.util.ArrayList;
import java.util.List;

import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.Trophy;

public class FoundReply extends AbstractReply {
	private List<Trophy> trophies = new ArrayList<>();
	private List<Place> places = new ArrayList<>();	
	
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
}
