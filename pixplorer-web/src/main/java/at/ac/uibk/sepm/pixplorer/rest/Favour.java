package at.ac.uibk.sepm.pixplorer.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AbstractReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FavourReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FavourRequest;

import com.google.gson.Gson;

/**
 * Web Service to mark places as favourite. 
 * 
 * @author cbo, cfi
 */
@Path("/favour")
public class Favour {
	private static final Gson gson = new Gson();	

	/**
	 * Method is called when user decides to mark some favourites.
	 * 
	 * @param json - JSON message from client
	 * @return JSON reply to client
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String favour(String json){
		FavourRequest request = gson.fromJson(json, FavourRequest.class);
		FavourReply reply = new FavourReply();
		
		String username = request.getGoogleId();
		
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = '" + username + "'");
		if (users.isEmpty()) {
			reply.setReturnCode(AbstractReply.RET_USER_NOT_FOUND);
			return gson.toJson(reply);
		}
		
		User user = users.get(0);
		
		List<Integer> favourites = request.getFavourites();
		for (int id : favourites) {
			List<Place> places = PersistenceManager.get(Place.class, "where x.id = " + id); 
			if (places.isEmpty()) {
				reply.setReturnCode(AbstractReply.RET_PLACE_NOT_FOUND);
				return gson.toJson(reply);			
			}
			
			Place place = places.get(0);
			
			if (!user.getFavourites().contains(place)) {
				user.getFavourites().add(place);
			}
		}
		
		PersistenceManager.save(user);
		
		return gson.toJson(reply);
	}
}
