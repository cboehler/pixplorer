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
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInfoReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInfoRequest;

import com.google.gson.Gson;

/**
 * Web Service that is invoked when the User asks for Information about the App.
 * 
 * @author cbo, cfi
 */
@Path("/appinfo")
public class AppInfo {
	/** Gson reference */
	private static final Gson gson = new Gson();	
	
	/**
	 * REST method to get application infos e.g. the number of places in the
	 * db
	 * 
	 * @param json - JSON message from client
	 * @return JSON reply to client
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String info(String json){		
		// parse request from client
		AppInfoRequest request = gson.fromJson(json, AppInfoRequest.class);
		
		String userName = request.getGoogleId();
		
		AppInfoReply reply = new AppInfoReply();
		
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = '" + userName + "'");
		if (users.isEmpty()) {
			reply.setReturnCode(AbstractReply.RET_USER_NOT_FOUND);
			return gson.toJson(reply);
		}
		
		User user = users.get(0);
			
		List<Place> places;
		
		if(user.getType() == User.TYPE_TOURIST) {
			places = PersistenceManager.get(Place.class,"where x.category = 1");
		} else {
			places = PersistenceManager.getAll(Place.class);
		}
		
		reply.setAmountOfPlaces(places.size()-user.getFoundPlaces().size());
		
		return gson.toJson(reply);
	}

}
