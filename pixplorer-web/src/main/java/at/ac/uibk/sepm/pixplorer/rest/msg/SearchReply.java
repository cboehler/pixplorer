package at.ac.uibk.sepm.pixplorer.rest.msg;

import java.util.ArrayList;
import java.util.List;

import at.ac.uibk.sepm.pixplorer.db.Place;

/**
 * Reply for search call.
 * 
 * @author cbo
 */
public class SearchReply extends AbstractReply {
	/** list containing the search result */
	private List<Place> places = new ArrayList<>();	
	
	/**
	 * Returns the list of places that match the search.
	 * 
	 * @return list of places or an empty list if no place was found
	 */
	public List<Place> getPlaces() {
		return places;
	}
	
	public void setPlaces(List<Place> places) {
		this.places = places;
	}
}
