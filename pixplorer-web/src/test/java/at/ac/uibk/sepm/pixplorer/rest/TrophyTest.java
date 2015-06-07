package at.ac.uibk.sepm.pixplorer.rest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import at.ac.uibk.sepm.pixplorer.db.Category;
import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.Trophy;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.ReplyException;

import com.google.gson.Gson;

public class TrophyTest {
	private static final Gson gson = new Gson();
	
	@Test	
	public void testTheBeginner() {
		AppInitRequest initRequest = new AppInitRequest();
		initRequest.setGoogleId("max.mustermann123456@google.com");
		initRequest.setOption(User.TYPE_LOCAL);
		
		String json = gson.toJson(initRequest);
		
		AppInit initCall = new AppInit();
		String jsonReply = initCall.init(json);
		
		AppInitReply initReply = gson.fromJson(jsonReply, AppInitReply.class);
		
		FoundRequest request = new FoundRequest();
		request.setGoogleId("max.mustermann123456@google.com");
		
		Place p = initReply.getPlaces().get(0);
		
		request.setFoundPlace(p.getId());
		request.setLatitude(p.getGpsData().getLatitude());
		request.setLongitude(p.getGpsData().getLongitude());
		
		json = gson.toJson(request);
		
		Found call = new Found();
		jsonReply = call.found(json);
		
		FoundReply reply = gson.fromJson(jsonReply, FoundReply.class);
		try {
			reply.checkReturnCode();
			Assert.assertFalse(reply.getPlaces().isEmpty());
			
			Assert.assertFalse(reply.getTrophies().isEmpty());
		
			boolean found = false;
			for (Trophy t : reply.getTrophies()) {
				if (t.getName().equals("CgkI-d2H8pccEAIQAQ")) {
					found = true;
					break;
				}
			}
			
			if (!found) {
				Assert.fail("Trophy the beginner not found");
			}
			
			List<User> users = PersistenceManager.get(User.class, "where x.googleId = 'max.mustermann123456@google.com'");
			Assert.assertEquals(1, users.size());
			
			User user = users.get(0);
			PersistenceManager.delete(user);
		} catch (ReplyException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testTourist() {
		AppInitRequest initRequest = new AppInitRequest();
		initRequest.setGoogleId("max.mustermann123456@google.com");
		initRequest.setOption(User.TYPE_LOCAL);
		
		String json = gson.toJson(initRequest);
		
		AppInit initCall = new AppInit();
		String jsonReply = initCall.init(json);
		
		gson.fromJson(jsonReply, AppInitReply.class);
		
		// fin all sights
		List<Place> places = getPlaces("SIGHT");
		Assert.assertTrue(!places.isEmpty());
		
		FoundReply reply = null;
		for (Place place : places) {
			FoundRequest request = new FoundRequest();
			request.setGoogleId("max.mustermann123456@google.com");
			
			request.setFoundPlace(place.getId());
			request.setLatitude(place.getGpsData().getLatitude());
			request.setLongitude(place.getGpsData().getLongitude());
			
			json = gson.toJson(request);
			
			Found call = new Found();
			jsonReply = call.found(json);
			
			reply = gson.fromJson(jsonReply, FoundReply.class);
			try {
				reply.checkReturnCode();
			} catch (ReplyException e) {
				Assert.fail();
			}
		}
			
		// check if the last reply contains the trophy
		Assert.assertFalse(reply.getTrophies().isEmpty());
		
		boolean found = false;
		for (Trophy t : reply.getTrophies()) {
			if (t.getName().equals("CgkI-d2H8pccEAIQAw")) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			Assert.fail("Trophy the tourist not found");
		}
		
		// delete user
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = 'max.mustermann123456@google.com'");
		Assert.assertEquals(1, users.size());
		
		User user = users.get(0);
		PersistenceManager.delete(user);		
	}

	
	@Test
	public void testTheMountainJunkie() {
		AppInitRequest initRequest = new AppInitRequest();
		initRequest.setGoogleId("max.mustermann123456@google.com");
		initRequest.setOption(User.TYPE_LOCAL);
		
		String json = gson.toJson(initRequest);
		
		AppInit initCall = new AppInit();
		String jsonReply = initCall.init(json);
		
		gson.fromJson(jsonReply, AppInitReply.class);
		
		// fin all sights
		List<Place> places = getPlaces("MOUNTAIN");
		Assert.assertTrue(!places.isEmpty());
		
		FoundReply reply = null;
		for (Place place : places) {
			FoundRequest request = new FoundRequest();
			request.setGoogleId("max.mustermann123456@google.com");
			
			request.setFoundPlace(place.getId());
			request.setLatitude(place.getGpsData().getLatitude());
			request.setLongitude(place.getGpsData().getLongitude());
			
			json = gson.toJson(request);
			
			Found call = new Found();
			jsonReply = call.found(json);
			
			reply = gson.fromJson(jsonReply, FoundReply.class);
			try {
				reply.checkReturnCode();
			} catch (ReplyException e) {
				Assert.fail();
			}
		}
			
		// check if the last reply contains the trophy
		Assert.assertFalse(reply.getTrophies().isEmpty());
		
		boolean found = false;
		for (Trophy t : reply.getTrophies()) {
			if (t.getName().equals("CgkI-d2H8pccEAIQEQ")) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			Assert.fail("Trophy the mountain junkie not found");
		}
		
		// delete user
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = 'max.mustermann123456@google.com'");
		Assert.assertEquals(1, users.size());
		
		User user = users.get(0);
		PersistenceManager.delete(user);		
	}
	
	
	@Test
	public void testAlcoholic() {
		AppInitRequest initRequest = new AppInitRequest();
		initRequest.setGoogleId("max.mustermann123456@google.com");
		initRequest.setOption(User.TYPE_LOCAL);
		
		String json = gson.toJson(initRequest);
		
		AppInit initCall = new AppInit();
		String jsonReply = initCall.init(json);
		
		gson.fromJson(jsonReply, AppInitReply.class);
		
		List<Place> places = new ArrayList<Place>();
		places.addAll(getPlaces("RESTAURANT"));
		places.addAll(getPlaces("BAR"));
		Assert.assertTrue(!places.isEmpty());
		
		FoundReply reply = null;
		for (Place place : places) {
			FoundRequest request = new FoundRequest();
			request.setGoogleId("max.mustermann123456@google.com");
			
			request.setFoundPlace(place.getId());
			request.setLatitude(place.getGpsData().getLatitude());
			request.setLongitude(place.getGpsData().getLongitude());
			
			json = gson.toJson(request);
			
			Found call = new Found();
			jsonReply = call.found(json);
			
			reply = gson.fromJson(jsonReply, FoundReply.class);
			try {
				reply.checkReturnCode();
			} catch (ReplyException e) {
				Assert.fail();
			}
		}
			
		// check if the last reply contains the trophy
		Assert.assertFalse(reply.getTrophies().isEmpty());
		
		boolean found = false;
		for (Trophy t : reply.getTrophies()) {
			if (t.getName().equals("CgkI-d2H8pccEAIQEA")) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			Assert.fail("Trophy the alcoholic not found");
		}
		
		// delete user
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = 'max.mustermann123456@google.com'");
		Assert.assertEquals(1, users.size());
		
		User user = users.get(0);
		PersistenceManager.delete(user);		
	}
	
	@Test
	public void testRealLocal() {
		AppInitRequest initRequest = new AppInitRequest();
		initRequest.setGoogleId("max.localmann@google.com");
		initRequest.setOption(User.TYPE_LOCAL);
		
		String json = gson.toJson(initRequest);
		
		AppInit initCall = new AppInit();
		String jsonReply = initCall.init(json);
		
		gson.fromJson(jsonReply, AppInitReply.class);
		
		// fin all sights
		List<Place> places = PersistenceManager.getAll(Place.class);
		Assert.assertTrue(!places.isEmpty());
		
		FoundReply reply = null;
		for (Place place : places) {
			FoundRequest request = new FoundRequest();
			request.setGoogleId("max.localmann@google.com");
			
			request.setFoundPlace(place.getId());
			request.setLatitude(place.getGpsData().getLatitude());
			request.setLongitude(place.getGpsData().getLongitude());
			
			json = gson.toJson(request);
			
			Found call = new Found();
			jsonReply = call.found(json);
			
			reply = gson.fromJson(jsonReply, FoundReply.class);
			try {
				reply.checkReturnCode();
			} catch (ReplyException e) {
				Assert.fail();
			}
		}
			
		// check if the last reply contains the trophy
		Assert.assertFalse(reply.getTrophies().isEmpty());
		
		boolean found = false;
		for (Trophy t : reply.getTrophies()) {
			if (t.getName().equals("CgkI-d2H8pccEAIQBw")) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			Assert.fail("Trophy the real local not found");
		}
		
		// delete user
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = 'max.localmann@google.com'");
		Assert.assertEquals(1, users.size());
		
		User user = users.get(0);
		PersistenceManager.delete(user);		
	}	
	
	
	/** 
	 * Get all places by category.
	 */
	private List<Place> getPlaces(String categoryName) {
		List<Category> categories = PersistenceManager.get(Category.class, "where x.name = '" + categoryName + "'");
		Assert.assertEquals(1, categories.size());
		
		Category cat = categories.get(0);
		
		return PersistenceManager.get(Place.class, "where x.category = " + cat.getId());
	}
}
