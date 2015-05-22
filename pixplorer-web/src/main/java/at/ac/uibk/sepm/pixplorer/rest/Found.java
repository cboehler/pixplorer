package at.ac.uibk.sepm.pixplorer.rest;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import at.ac.uibk.sepm.pixplorer.db.GPSData;
import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AbstractReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundRequest;
import at.ac.uibk.sepm.pixplorer.rest.util.GpsUtils;
import at.ac.uibk.sepm.pixplorer.rest.util.RandomPlaceGenerator;

import com.google.gson.Gson;


@Path("/found")
public class Found {
	private static final Gson gson = new Gson();	

	/** gps distance tolerance in meter */
	private static final int TOLERANCE = 50;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String found(String json) {
		FoundRequest request = gson.fromJson(json, FoundRequest.class);
		
		String username = request.getGoogleId();
		int foundPlaceId = request.getFoundPlace();
		
		FoundReply reply = new FoundReply();
		
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = '" + username + "'");
		if (users.isEmpty()) {
			reply.setReturnCode(AbstractReply.RET_USER_NOT_FOUND);
			return gson.toJson(reply);
		}
		
		User user = users.get(0);
		
		List<Place> places = PersistenceManager.get(Place.class, "where x.id = " + foundPlaceId); 
		if (places.isEmpty()) {
			reply.setReturnCode(AbstractReply.RET_PLACE_NOT_FOUND);
			return gson.toJson(reply);			
		}
		
		Place place = places.get(0);
		GPSData gpsReference = place.getGpsData();
		
		GPSData gpsUser = new GPSData();
		gpsUser.setLatitude(request.getLatitude());
		gpsUser.setLongitude(request.getLongitude());
		
		if (GpsUtils.calculateDistance(gpsReference, gpsUser) > TOLERANCE) {
			reply.setReturnCode(AbstractReply.RET_INVALUD_COORDINATES);
			reply.setFound(false);
			return gson.toJson(reply);
		}
		
		Set<Place> update = user.getFoundPlaces();
		if (!update.contains(place)) {
			update.add(place);
		}
		
		place.setCount(place.getCount() + 1);
		
		// update the user score
		user.setScore(user.getScore() + place.getScore());
		
		PersistenceManager.save(user);
				
		// TODO: check if the user has reached new trophies...
		
		/*Get randomplaces from RandomplaceGenerator*/
		RandomPlaceGenerator generator = new RandomPlaceGenerator();
		List<Place> newPlaces = generator.getPlaces(user,1);
		reply.setPlaces(newPlaces);
		reply.setFound(true);
		
		return gson.toJson(reply);		
		
	}
}
