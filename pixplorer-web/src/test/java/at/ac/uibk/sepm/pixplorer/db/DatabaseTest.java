package at.ac.uibk.sepm.pixplorer.db;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class DatabaseTest {

	@Test
	public void testGPSData() {
		List<GPSData> dataList = PersistenceManager.getAll(GPSData.class);
		Assert.assertNotNull(dataList);
		Assert.assertTrue(dataList.isEmpty());
		
		GPSData data = new GPSData();
		data.setLatitude(Math.PI);
		data.setLongitude(Math.E);
		
		PersistenceManager.save(data);
		
		dataList = PersistenceManager.getAll(GPSData.class);
		Assert.assertNotNull(dataList);
		Assert.assertTrue(!dataList.isEmpty());
		Assert.assertEquals(Math.PI, dataList.get(0).getLatitude(), 0.001);
		Assert.assertEquals(Math.E, dataList.get(0).getLongitude(), 0.001);
		
		PersistenceManager.delete(data);
		
		dataList = PersistenceManager.getAll(GPSData.class);
		Assert.assertNotNull(dataList);
		Assert.assertTrue(dataList.isEmpty());		
	}

	@Test
	public void testManyGPSData() {
		List<GPSData> dataList = PersistenceManager.getAll(GPSData.class);
		Assert.assertNotNull(dataList);
		Assert.assertTrue(dataList.isEmpty());
		
		for (int i = 0; i < 100; i++) {
			GPSData data = new GPSData();
			data.setLatitude(Math.PI);
			data.setLongitude(Math.E);
			PersistenceManager.save(data);
		}
				
		dataList = PersistenceManager.getAll(GPSData.class);
		Assert.assertNotNull(dataList);
		Assert.assertEquals(100, dataList.size());
		
		for (GPSData data : dataList) {
			PersistenceManager.delete(data);
		}
				
		dataList = PersistenceManager.getAll(GPSData.class);
		Assert.assertNotNull(dataList);
		Assert.assertTrue(dataList.isEmpty());		
	}	
	
	@Test	
	public void testCategories() {
		List<Category> categories = PersistenceManager.getAll(Category.class);
		Assert.assertNotNull(categories);
		Assert.assertTrue(categories.isEmpty());
		
		Category cat = new Category();
		cat.setName("test 1");

		PersistenceManager.save(cat);
		
		categories = PersistenceManager.getAll(Category.class);
		Assert.assertNotNull(categories);
		Assert.assertTrue(!categories.isEmpty());
		Assert.assertEquals("test 1", categories.get(0).getName());
		
		PersistenceManager.delete(cat);
		
		categories = PersistenceManager.getAll(Category.class);
		Assert.assertNotNull(categories);
		Assert.assertTrue(categories.isEmpty());
	}

	@Test	
	public void testPlace() {
		List<Place> places = PersistenceManager.getAll(Place.class);
		Assert.assertNotNull(places);
		Assert.assertTrue(places.isEmpty());
		
		// create category first
		Category cat = new Category();
		cat.setName("cat1");
		
		PersistenceManager.save(cat);
		
		GPSData gps = new GPSData();
		gps.setLatitude(123);
		gps.setLatitude(456);
		
		PersistenceManager.save(gps);
		
		// create new place
		Place place = new Place();
		place.setCount(1);
		place.setCategory(cat);
		place.setGpsData(gps);
		place.setName("Goldenes Dachl");
		place.setPicture("pic of goldenes dachl");
		place.setWikiLink("http://blabla");
		place.setFeatured(false);
		PersistenceManager.save(place);
		
		
		// check if everything was stored
		places = PersistenceManager.getAll(Place.class);
		Assert.assertNotNull(places);
		Assert.assertTrue(!places.isEmpty());
		Assert.assertEquals(1, places.get(0).getCount());
		Assert.assertEquals("Goldenes Dachl", places.get(0).getName());
		Assert.assertEquals("pic of goldenes dachl", places.get(0).getPicture());
		Assert.assertEquals("http://blabla", places.get(0).getWikiLink());
		Assert.assertEquals(false, places.get(0).isFeatured());
		PersistenceManager.delete(place);
		
		// check if it was removed
		places = PersistenceManager.getAll(Place.class);
		Assert.assertNotNull(place);
		Assert.assertTrue(places.isEmpty());
		
		// the category should still be there
		List<Category> categories = PersistenceManager.getAll(Category.class);
		Assert.assertNotNull(categories);
		Assert.assertTrue(!categories.isEmpty());
		
		PersistenceManager.delete(categories.get(0));
	}
	
	@Test
	public void testUser() {
		List<User> users = PersistenceManager.getAll(User.class);
		Assert.assertNotNull(users);
		Assert.assertTrue(users.isEmpty());
		
		User tourist = new User();
		tourist.setScore(1234);
		tourist.setGoogleId("example@google.com");
		tourist.setType(User.TYPE_TOURIST);
		
		PersistenceManager.save(tourist);

		User local = new User();
		local.setScore(666);
		local.setGoogleId("example123@google.com");
		local.setType(User.TYPE_LOCAL);
		
		PersistenceManager.save(local);		
		
		users = PersistenceManager.getAll(User.class);
		Assert.assertNotNull(users);
		Assert.assertEquals(2, users.size());
		
		Assert.assertEquals(1234, users.get(0).getScore());
		Assert.assertEquals(User.TYPE_TOURIST, users.get(0).getType());
		Assert.assertEquals("example@google.com", users.get(0).getGoogleId());

		Assert.assertEquals(666, users.get(1).getScore());
		Assert.assertEquals(User.TYPE_LOCAL, users.get(1).getType());
		Assert.assertEquals("example123@google.com", users.get(1).getGoogleId());		
		
		for (User user : users) {
			PersistenceManager.delete(user);
		}
		
		users = PersistenceManager.getAll(User.class);
		Assert.assertNotNull(users);
		Assert.assertTrue(users.isEmpty());
	}
	
	@Test
	public void testFavourite() {
		User user = new User();
		user.setScore(1234);
		user.setGoogleId("example@google.com");
		user.setType(User.TYPE_TOURIST);
		
		PersistenceManager.save(user);
		
		Category cat = new Category();
		cat.setName("cat1");
		PersistenceManager.save(cat);
		
		GPSData gps = new GPSData();
		gps.setLatitude(123);
		gps.setLatitude(456);
		PersistenceManager.save(gps);
		
		// create new place
		Place place = new Place();
		place.setCount(1);
		place.setCategory(cat);
		place.setGpsData(gps);
		place.setName("Goldenes Dachl");
		place.setPicture("pic of goldenes dachl");
		place.setWikiLink("http://blabla");
		place.setFeatured(false);
		PersistenceManager.save(place);
		
		user.getFavourites().add(place);
		PersistenceManager.save(user);
		
		User readUser = PersistenceManager.get(User.class, "google_id = \'example@google.com\'").get(0);
		Assert.assertNotNull(readUser);
		Assert.assertEquals(1, readUser.getFavourites().size());
		
		readUser.getFavourites().clear();
		PersistenceManager.save(readUser);
		
		PersistenceManager.delete(readUser);
		PersistenceManager.delete(place);
		PersistenceManager.delete(cat);
	}
	
	@Test
	public void testHistory() {
		User user = new User();
		user.setScore(1234);
		user.setGoogleId("example@google.com");
		user.setType(User.TYPE_TOURIST);
		
		PersistenceManager.save(user);
		
		Category cat = new Category();
		cat.setName("cat1");
		PersistenceManager.save(cat);
		
		GPSData gps = new GPSData();
		gps.setLatitude(123);
		gps.setLatitude(456);
		PersistenceManager.save(gps);
		
		// create new place
		Place place = new Place();
		place.setCount(1);
		place.setCategory(cat);
		place.setGpsData(gps);
		place.setName("Goldenes Dachl");
		place.setPicture("pic of goldenes dachl");
		place.setWikiLink("http://blabla");
		place.setFeatured(false);
		PersistenceManager.save(place);
		
		user.getFoundPlaces().add(place);
		PersistenceManager.save(user);
		
		User readUser = PersistenceManager.get(User.class, "google_id = \'example@google.com\'").get(0);
		Assert.assertNotNull(readUser);
		Assert.assertEquals(1, readUser.getFoundPlaces().size());
		
		readUser.getFoundPlaces().clear();
		PersistenceManager.save(readUser);
		
		PersistenceManager.delete(readUser);
		PersistenceManager.delete(place);
		PersistenceManager.delete(cat);
	}
	
	@Test	
	public void testTrophies() {
		List<Trophy> trophies = PersistenceManager.getAll(Trophy.class);
		Assert.assertNotNull(trophies);
		Assert.assertTrue(trophies.isEmpty());
		
		Trophy trophy = new Trophy();
		trophy.setCode("code");
		trophy.setDescription("desc");
		trophy.setName("trophy");
		PersistenceManager.save(trophy);
		
		trophies = PersistenceManager.getAll(Trophy.class);
		Assert.assertNotNull(trophies);
		Assert.assertTrue(!trophies.isEmpty());
		Assert.assertEquals("trophy", trophies.get(0).getName());
		Assert.assertEquals("desc", trophies.get(0).getDescription());
		Assert.assertEquals("code", trophies.get(0).getCode());
		PersistenceManager.delete(trophy);
		
		trophies = PersistenceManager.getAll(Trophy.class);
		Assert.assertNotNull(trophies);
		Assert.assertTrue(trophies.isEmpty());
	}
	
	@Test
	public void testUserTrophies() {
		User user = new User();
		user.setScore(1234);
		user.setGoogleId("example@google.com");
		user.setType(User.TYPE_TOURIST);
		
		PersistenceManager.save(user);
		
		Trophy trophy = new Trophy();
		trophy.setCode("code");
		trophy.setDescription("desc");
		trophy.setName("trophy");
		PersistenceManager.save(trophy);
		
		user.getTrophies().add(trophy);
		PersistenceManager.save(user);
		
		User readUser = PersistenceManager.get(User.class, "google_id = \'example@google.com\'").get(0);
		Assert.assertNotNull(readUser);
		Assert.assertEquals(1, readUser.getTrophies().size());
		
		readUser.getTrophies().clear();
		PersistenceManager.save(readUser);
		
		PersistenceManager.delete(readUser);
		PersistenceManager.delete(trophy);
	}	
}