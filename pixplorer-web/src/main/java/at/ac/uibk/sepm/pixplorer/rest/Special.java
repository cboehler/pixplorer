package at.ac.uibk.sepm.pixplorer.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AbstractReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.SpecialReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.SpecialRequest;
import at.ac.uibk.sepm.pixplorer.rest.util.RandomPlaceGenerator;

/**
 * Web Service to handling special places. 
 * 
 * @author cbo, cfi
 */
@Path("special")
public class Special {
	private static final Gson gson = new Gson();	
	
	/**
	 * Method is called when user wants some very special places..
	 * 
	 * @param json - JSON message from client
	 * @return JSON reply to client
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String special(String json) {
		SpecialRequest request = gson.fromJson(json, SpecialRequest.class);
		String username = request.getGoogleId();

		SpecialReply reply = new SpecialReply();
		
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = '" + username + "'");
		if (users.isEmpty()) {
			reply.setReturnCode(AbstractReply.RET_USER_NOT_FOUND);
			return gson.toJson(reply);
		}
		
		User user = users.get(0);
		
		try {
			List<Place> places = RandomPlaceGenerator.getPlaces(user, 5, true);  
			reply.setPlaces(places);
			
			return gson.toJson(reply);
		} catch (Exception e) {
			reply.setReturnCode(AbstractReply.RET_VERY_EVIL);
			return gson.toJson(reply);			
		}
	}

}
