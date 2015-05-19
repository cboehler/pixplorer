package at.ac.uibk.sepm.pixplorer.rest.msg;

import java.util.ArrayList;
import java.util.List;

import at.ac.uibk.sepm.pixplorer.db.Place;

public class UserInfoReply extends AbstractReply {
	private long score;
	
	private List<Place> foundPlaces = new ArrayList<>();
	
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
	
}
