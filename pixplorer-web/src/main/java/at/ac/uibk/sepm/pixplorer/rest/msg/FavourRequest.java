package at.ac.uibk.sepm.pixplorer.rest.msg;

import java.util.ArrayList;
import java.util.List;

/**
 * Request for favour call.
 * 
 * @author cbo
 */
public class FavourRequest extends AbstractRequest {
	/** list of place ids that the user marked as favourite */
	private List<Integer> favourites = new ArrayList<>();
	
	/** 
	 * Returns a list of place ids that the user marked as favourite.
	 *  
	 * @return list with place ids. 
	 **/
	public List<Integer> getFavourites() {
		return favourites;
	}
	
	public void setFavourites(List<Integer> favourites) {
		this.favourites = favourites;
	}
	
}
