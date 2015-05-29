package at.ac.uibk.sepm.pixplorer.rest.msg;

import java.util.ArrayList;
import java.util.List;

import at.ac.uibk.sepm.pixplorer.db.Place;

/**
 * Reply for special call.
 * 
 * @author cbo
 */
public class SpecialReply extends AbstractReply {
	/** list of special places */
	private List<Place> places = new ArrayList<>();	
	
	public List<Place> getPlaces() {
		return places;
	}
	
	public void setPlaces(List<Place> places) {
		this.places = places;
	}
}
