package at.ac.uibk.sepm.pixplorer.rest.msg;

import java.util.ArrayList;
import java.util.List;

import at.ac.uibk.sepm.pixplorer.db.Place;

/**
 * Reply for user info call.
 * 
 * @author cbo
 */
public class UserInfoReply extends AbstractReply {
	/** the current user score */
	private long score;
	
	/** a list of places the user has already found */
	private List<Place> foundPlaces = new ArrayList<>();
	
	/** a list of places the user has marked as favourite */
	private List<Place> favourites = new ArrayList<>();
	
	public long getScore() {
		return score;
	}
	
	public void setScore(long score) {
		this.score = score;
	}
	
	public List<Place> getFoundPlaces() {
		return foundPlaces;
	}
	
	public void setFoundPlaces(List<Place> foundPlaces) {
		this.foundPlaces = foundPlaces;
	}
	
	public List<Place> getFavourites() {
		return favourites;
	}
	
	public void setFavourites(List<Place> favourites) {
		this.favourites = favourites;
	}
	
}
