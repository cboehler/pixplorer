package at.ac.uibk.sepm.pixplorer.rest.msg;

import java.util.ArrayList;
import java.util.List;

import at.ac.uibk.sepm.pixplorer.db.Place;

/**
 * Reply class for application initialisation.
 * 
 * @author cbo
 */
public class AppInitReply extends AbstractReply {
	/** list of places that the user has to find */
	private List<Place> places = new ArrayList<>();	
	
	/**
	 * list of places that the user has to find
	 * 
	 * @return list of places
	 */
	public List<Place> getPlaces() {
		return places;
	}
	
	public void setPlaces(List<Place> places) {
		this.places = places;
	}
}
