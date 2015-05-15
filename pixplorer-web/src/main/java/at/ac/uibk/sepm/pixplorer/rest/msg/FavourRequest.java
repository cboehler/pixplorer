package at.ac.uibk.sepm.pixplorer.rest.msg;

import java.util.ArrayList;
import java.util.List;

public class FavourRequest extends AbstractRequest {
	private List<Integer> favourites = new ArrayList<>();
	
	public List<Integer> getFavourites() {
		return favourites;
	}
	
	public void setFavourites(List<Integer> favourites) {
		this.favourites = favourites;
	}
	
}
