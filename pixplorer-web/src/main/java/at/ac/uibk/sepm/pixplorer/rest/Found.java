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
import at.ac.uibk.sepm.pixplorer.db.Trophy;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AbstractReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundRequest;
import at.ac.uibk.sepm.pixplorer.rest.util.GpsUtils;
import at.ac.uibk.sepm.pixplorer.rest.util.RandomPlaceGenerator;

import com.google.gson.Gson;

/**
 * Web Service to check if the user has found a place. If so, it is checked
 * if there are some new trophies for this user. The trophies are sent to the
 * client so he can pass the trophy id to google play. 
 * 
 * @author cbo, cfi
 */
@Path("/found")
public class Found {
	/** placeholder that is substituted with the user id */
	private static final String USER_PLACEHOLDER = "USERID";
	
	/** if a featured place is found the user gets some extra points */
	private static final int FEATURED_PLACE_EXTRA_SCORE = 50;
	
	/** Gson reference */
	private static final Gson gson = new Gson();	

	/** gps distance tolerance in meter */
	private static final int TOLERANCE = 50;
	
	/**
	 * Method is called when user has found a place.
	 * 
	 * @param json - JSON message from client
	 * @return JSON reply to client
	 */
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
		
		double distance = GpsUtils.calculateDistance(gpsReference, gpsUser);
		
		if (distance > TOLERANCE) {
			reply.setReturnCode(AbstractReply.RET_INVALUD_COORDINATES);
			reply.setDistanceDelta(Math.abs(distance - TOLERANCE));
			return gson.toJson(reply);
		}
		
		Set<Place> update = user.getFoundPlaces();
		if (!update.contains(place)) {
			update.add(place);
		}
		
		place.setCount(place.getCount() + 1);
		
		// update the user score
		int score = place.getScore();
		if (place.isFeatured()) {
			score += FEATURED_PLACE_EXTRA_SCORE;
		}
		
		user.setScore(user.getScore() + score);
				
		// create trophies if not existing
		addTrophies();
		
		PersistenceManager.save(place, user);
		
		// check if a new trophy was achieved by this user
		boolean newTrophy = false;
		
		List<Trophy> trophies = PersistenceManager.getAll(Trophy.class);
		for (Trophy trophy : trophies) {
			if (trophy.getCode().isEmpty()) {
				continue;
			}
			
			if (!user.getTrophies().contains(trophy)) {
				String stmt = trophy.getCode().replaceAll(USER_PLACEHOLDER, Integer.toString(user.getId()));

				if (executeStatement(stmt)) {
					// update db and add new trophy to the reply
					user.getTrophies().add(trophy);
					reply.getTrophies().add(trophy);
					
					newTrophy = true;
				}
			}
		}
		
		// save new trophies in db as well
		if (newTrophy) {
			PersistenceManager.save(user);
		}
		
		try {
			List<Place> newPlaces = RandomPlaceGenerator.getPlaces(user,1);
			reply.setPlaces(newPlaces);
			
			return gson.toJson(reply);
		} catch (Exception e) {
			reply.setReturnCode(AbstractReply.RET_VERY_EVIL);
			return gson.toJson(reply);			
		}
		
	}
	
	/**
	 * Creates all pixplorer trophies if they are not in the db yet.
	 */
	private void addTrophies() {
		List<Trophy> trophies = PersistenceManager.getAll(Trophy.class);
		
		if (!trophies.isEmpty()) {
			return;
		}
		
		createTrophy("CgkI-d2H8pccEAIQAQ", "achievementTheBeginner", "SELECT CAST(CASE WHEN COUNT(*) = 1 THEN 1 ELSE 0 END AS BIT) FROM history AS h WHERE h.user_id = USERID;");
		createTrophy("CgkI-d2H8pccEAIQDA", "achievement100Points", "SELECT CAST(CASE WHEN SUM(p.score) >= 100 THEN 1 ELSE 0 END AS BIT) FROM places AS p JOIN history AS h ON p.id = h.place_id WHERE h.user_id = USERID;");
		createTrophy("CgkI-d2H8pccEAIQBA", "achievement1000Points", "SELECT CAST(CASE WHEN SUM(p.score) >= 1000 THEN 1 ELSE 0 END AS BIT) FROM places AS p JOIN history AS h ON p.id = h.place_id WHERE h.user_id = USERID;");
		createTrophy("CgkI-d2H8pccEAIQDQ", "achievement10000Points", "SELECT CAST(CASE WHEN SUM(p.score) >= 10000 THEN 1 ELSE 0 END AS BIT) FROM places AS p JOIN history AS h ON p.id = h.place_id WHERE h.user_id = USERID;");
		createTrophy("CgkI-d2H8pccEAIQDg", "achievement100000Points", "SELECT CAST(CASE WHEN SUM(p.score) >= 100000 THEN 1 ELSE 0 END AS BIT) FROM places AS p JOIN history AS h ON p.id = h.place_id WHERE h.user_id = USERID;");
		
		createTrophy("CgkI-d2H8pccEAIQAw", "achievementTheTourist", "SELECT CAST(CASE WHEN COUNT(*) = (SELECT COUNT(*) FROM places AS p JOIN (SELECT * FROM categories WHERE name='SIGHT') AS c ON p.category = c.id) "
				+ "THEN 1 ELSE 0 END AS BIT) FROM places AS p JOIN (SELECT * FROM categories WHERE name='SIGHT') AS c ON p.category = c.id JOIN history AS h ON p.id = h.place_id WHERE h.user_id = USERID;");
		
		createTrophy("CgkI-d2H8pccEAIQBw", "achievementARealLocal", "SELECT CAST(CASE WHEN COUNT(*) = (SELECT COUNT(*) FROM places AS p JOIN categories AS c ON p.category = c.id) "
				+ "THEN 1 ELSE 0 END AS BIT) FROM places AS p JOIN categories AS c ON p.category = c.id JOIN history AS h ON p.id = h.place_id WHERE h.user_id = USERID;");
		
		createTrophy("CgkI-d2H8pccEAIQEA", "achievementTheAlcoholic", "SELECT CAST(CASE WHEN COUNT(*) = (SELECT COUNT(*) FROM places AS p JOIN (SELECT * FROM categories WHERE name='BAR' OR name='RESTAURANT') AS c ON p.category = c.id) "
				+ "THEN 1 ELSE 0 END AS BIT) FROM places AS p JOIN (SELECT * FROM categories WHERE name='BAR' OR name='RESTAURANT') AS c ON p.category = c.id JOIN history AS h ON p.id = h.place_id WHERE h.user_id = USERID;"); 
		
		createTrophy("CgkI-d2H8pccEAIQEQ", "achievementTheMountainjunkie", "SELECT CAST(CASE WHEN COUNT(*) = (SELECT COUNT(*) FROM places AS p JOIN (SELECT * FROM categories WHERE name='MOUNTAIN') AS c ON p.category = c.id) "
				+ "THEN 1 ELSE 0 END AS BIT) FROM places AS p JOIN (SELECT * FROM categories WHERE name='MOUNTAIN') AS c ON p.category = c.id JOIN history AS h ON p.id = h.place_id WHERE h.user_id = USERID;");
	}
	
	/**
	 * Creates a new trophy with the specified parameters.
	 * 
	 * @param name - trophy name i.e. google achievement id
	 * @param desc - trophy description
	 * @param sql - sql statement to determine if the trophy was achieved
	 */
	private void createTrophy(String name, String desc, String sql) {
		Trophy trophy = new Trophy();

		trophy.setCode(sql);
		trophy.setDescription(desc);
		trophy.setName(name);
		
		PersistenceManager.save(trophy);
	}
	
	/**
	 * Executes the specified SQL statement and returns the boolean result
	 * @param stmt - statement to execute
	 * @return true or false (SQL result)
	 */
	private boolean executeStatement(String stmt) {
		List<?> result = PersistenceManager.executeSQl(stmt);
		if (!result.isEmpty()) {
			Object obj = result.get(0);
			if (obj instanceof Boolean) {
				return (Boolean) obj;
			}
		}
		
		return false;
	}
}
