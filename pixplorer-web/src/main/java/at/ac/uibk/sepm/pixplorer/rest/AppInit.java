package at.ac.uibk.sepm.pixplorer.rest;

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
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitRequest;
import at.ac.uibk.sepm.pixplorer.rest.util.RandomPlaceGenerator;

import com.google.gson.Gson;

/**
 * Web Service that is invoked when the App is started. 
 * @author cbo, cfi
 */
@Path("/init")
public class AppInit {
	
	private static final Gson gson = new Gson();	
	
	//Method is called when Client opened App and decided to play as Tourist or Local	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String init(String json){		
		// create some test content if necessary
		createDummyContent();
		
		// parse request from client
		AppInitRequest request = gson.fromJson(json, AppInitRequest.class);
		int option = request.getOption();
		String userName = request.getGoogleId();
		
		// check if user exists
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = '" + userName + "'");
		
		User user = null;
		if (users.isEmpty()) {
			user = new User();
			user.setGoogleId(request.getGoogleId());
			user.setType(request.getOption());
			PersistenceManager.save(user);			
		} else {
			user = users.get(0);
		}
		
		// check if user type is different
		if (user.getType() != request.getOption()) {
			user.setType(option);
			PersistenceManager.save(user);
		}
		
		// generate some new places for the user
		RandomPlaceGenerator generator = new RandomPlaceGenerator();
		List<Place> places = generator.getPlaces(user , 10);
		
		AppInitReply reply = new AppInitReply();
		reply.setPlaces(places);
		
		return gson.toJson(reply);
	}

	/**
	 * Creates some test content.. Will be removed
	 */
	private void createDummyContent() {
		List<Category> categories = PersistenceManager.getAll(Category.class);
		
		// dummy content already created??
		if (!categories.isEmpty()) {
			return;
		}
		
		Category cat = new Category();
		cat.setName("SIGHT");
		PersistenceManager.save(cat);
		Random random = new Random(0);
		
		User user = new User();
		user.setAdmin(true);
		user.setScore(0);
		user.setGoogleId("max.mustermann@google.com");
		PersistenceManager.save(user);
		
		for (int i = 0; i < 100; i++) {
			GPSData gps = new GPSData();
			gps.setLatitude(100.d + Math.PI * i);
			gps.setLatitude(200.d + Math.E * i);
			
			PersistenceManager.save(gps);
			
			Place place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			
			switch (i % 4) {
			case 0:
				place.setName("Goldenes Dachl " + i);
				place.setPicture("http://www.innsbruck.info/uploads/pics/innsbruck_goldenes_dachl_03.jpg");
				place.setWikiLink("http://de.wikipedia.org/wiki/Goldenes_Dachl");
				break;
			case 1:
				place.setName("Schloss Ambras " + i);
				place.setWikiLink("http://de.wikipedia.org/wiki/Schloss_Ambras");
				place.setPicture("http://www.innsbruck.info/uploads/pics/innsbruck_schloss_ambras_8_02.jpg");
				break;
			case 2:
				place.setName("Triumphpforte " + i);
				place.setWikiLink("http://de.wikipedia.org/wiki/Triumphpforte");
				place.setPicture("http://upload.wikimedia.org/wikipedia/commons/f/f7/Innsbruck_-_Triumphpforte2.jpg");
				break;
			case 3:
				place.setName("Kristallwelten " + i);
				place.setWikiLink("http://de.wikipedia.org/wiki/Swarovski_Kristallwelten");
				place.setPicture("http://www.bergland.com/uploads/pics/swarovski_1_09.jpg");
				break;				
			}
			
			place.setScore(random.nextInt(100));
			
			PersistenceManager.save(place);
		}
	}
}
