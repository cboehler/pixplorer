package at.ac.uibk.sepm.pixplorer.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import at.ac.uibk.sepm.pixplorer.db.Category;
import at.ac.uibk.sepm.pixplorer.db.GPSData;
import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInfoReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInfoRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitRequest;
import at.ac.uibk.sepm.pixplorer.rest.util.RandomPlaceGenerator;

import com.google.gson.Gson;

/**
 * Web Service that is invoked when the User asks for Information about the App.
 * @author cbo, cfi
 */
@Path("/appinfo")
public class AppInfo {
		
	private static final Gson gson = new Gson();	
	
	//Method is called when Client opened App and decided to play as Tourist or Local	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String info(String json){		
		
		// parse request from client
		AppInfoRequest request = gson.fromJson(json, AppInfoRequest.class);
		String userName = request.getGoogleId();
		
		// check if user exists
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = '" + userName + "'");
		
		User user = users.get(0);
			
		List<Place> places;
		
		if(user.getType() == user.TYPE_TOURIST)
			places = PersistenceManager.get(Place.class,"where x.category = 1");
		
		else
			places = PersistenceManager.getAll(Place.class);
		
		AppInfoReply reply = new AppInfoReply();
		reply.setAmountOfPlaces(places.size()-user.getFoundPlaces().size());
		
		return gson.toJson(reply);
	}

}
