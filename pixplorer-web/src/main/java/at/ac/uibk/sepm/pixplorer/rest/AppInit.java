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


@Path("/init")
public class AppInit {
	
	//Method is called when Client opened App and decided to play as Tourist or Local	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String init(String json){		
		createDummyContent();
		
		Object[] objects = JsonUtils.getObjectsFromJson(json);
		Integer option = Integer.class.cast(objects[0]);
		String username = String.class.cast(objects[1]);
		
		// check if user exists
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = '" + username + "'");
		
		User user = null;
		if (users.isEmpty()) {
			user = new User();
			user.setGoogleId(username);
			user.setType(option);
			PersistenceManager.save(user);			
		} else {
			user = users.get(0);
		}
		
		if (user.getType() != option) {
			user.setType(option);
			PersistenceManager.save(user);
		}
		
		RandomPlaceGenerator generator = new RandomPlaceGenerator();
		Place[] places = generator.getPlaces(user , 10);
		
		return JsonUtils.createJsonString(places);
	}

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
			place.setName("Goldenes Dachl " + i);
			place.setPicture("Picture of " + i);
			place.setScore(random.nextInt());
			place.setWikiLink("http://de.wikipedia.org/wiki/Goldenes_Dachl");
			PersistenceManager.save(place);
		}
	}
}
