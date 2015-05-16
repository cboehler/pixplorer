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
import at.ac.uibk.sepm.pixplorer.rest.msg.ExploreReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.ExploreRequest;
import at.ac.uibk.sepm.pixplorer.rest.util.RandomPlaceGenerator;

import com.google.gson.Gson;

@Path("/explore")
public class Explore {
	private static final Gson gson = new Gson();	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String explore(String json){		
		ExploreRequest request = gson.fromJson(json, ExploreRequest.class);
		ExploreReply reply = new ExploreReply();
		
		String username = request.getGoogleId();
		
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = '" + username + "'");
		if (users.isEmpty()) {
			reply.setReturnCode(AbstractReply.RET_USER_NOT_FOUND);
			return gson.toJson(reply);
		}
		/*?*/
		if (users.isEmpty()) {
			reply.setReturnCode(AbstractReply.RET_USER_NOT_FOUND);
		} else {
			User user = users.get(0);
			RandomPlaceGenerator generator = new RandomPlaceGenerator();
			List<Place> places = generator.getPlaces(user,10);
			reply.setPlaces(places);
		}
		
		return gson.toJson(reply);
	}
}