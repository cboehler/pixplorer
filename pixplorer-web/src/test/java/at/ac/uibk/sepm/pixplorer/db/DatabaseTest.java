package at.ac.uibk.sepm.pixplorer.db;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class DatabaseTest {

	@Test
	public void testGPSData() {
		List<GPSData> dataList = PersistenceManager.getAll(GPSData.class);
		Assert.assertNotNull(dataList);

		int origSize = dataList.size();

		GPSData data = new GPSData();
		data.setLatitude(Math.PI);
		data.setLongitude(Math.E);

		PersistenceManager.save(data);

		dataList = PersistenceManager.getAll(GPSData.class);
		Assert.assertNotNull(dataList);
		Assert.assertTrue(!dataList.isEmpty());

		GPSData dataToCompare = null;
		for (GPSData d : dataList) {
			if (d.getId() == data.getId()) {
				dataToCompare = d;
				break;
			}
		}

		Assert.assertNotNull(dataToCompare);

		Assert.assertEquals(Math.PI, dataToCompare.getLatitude(), 0.001);
		Assert.assertEquals(Math.E, dataToCompare.getLongitude(), 0.001);

		PersistenceManager.delete(data);

		dataList = PersistenceManager.getAll(GPSData.class);
		Assert.assertNotNull(dataList);
		Assert.assertEquals(origSize, dataList.size());
	}

	@Test
	public void testManyGPSData() {
		List<GPSData> dataList = PersistenceManager.getAll(GPSData.class);
		Assert.assertNotNull(dataList);

		int origSize = dataList.size();

		List<GPSData> gpsList = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			GPSData data = new GPSData();
			data.setLatitude(Math.PI);
			data.setLongitude(Math.E);
			PersistenceManager.save(data);

			gpsList.add(data);
		}

		dataList = PersistenceManager.getAll(GPSData.class);
		Assert.assertNotNull(dataList);
		Assert.assertEquals(origSize + 100, dataList.size());

		for (GPSData data : gpsList) {
			PersistenceManager.delete(data);
		}

		dataList = PersistenceManager.getAll(GPSData.class);
		Assert.assertNotNull(dataList);

		Assert.assertEquals(origSize, dataList.size());
	}

	@Test
	public void testSQLStatement() {
		List<GPSData> dataList = PersistenceManager.getAll(GPSData.class);
		Assert.assertNotNull(dataList);

		int origSize = dataList.size();

		List<GPSData> gpsList = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			GPSData data = new GPSData();
			data.setLatitude(Math.PI);
			data.setLongitude(Math.E);
			PersistenceManager.save(data);

			gpsList.add(data);
		}

		List<?> result = PersistenceManager.executeSQl("SELECT * FROM gpsdata");
		Assert.assertNotNull(result);
		Assert.assertEquals(origSize + 100, result.size());

		for (GPSData obj : gpsList) {
			PersistenceManager.delete(obj);
		}
	}

	@Test
	public void testCategories() {
		List<Category> categories = PersistenceManager.getAll(Category.class);
		Assert.assertNotNull(categories);

		int origSize = categories.size();

		Category cat = new Category();
		cat.setName("test 1");

		PersistenceManager.save(cat);

		categories = PersistenceManager.getAll(Category.class);
		Assert.assertNotNull(categories);
		Assert.assertTrue(!categories.isEmpty());

		Category categoryToCompare = null;

		for (Category c : categories) {
			if (c.getId() == cat.getId()) {
				categoryToCompare = c;
				break;
			}
		}

		Assert.assertNotNull(categoryToCompare);

		Assert.assertEquals("test 1", categoryToCompare.getName());

		PersistenceManager.delete(cat);

		categories = PersistenceManager.getAll(Category.class);
		Assert.assertNotNull(categories);

		Assert.assertEquals(origSize, categories.size());
	}

	@Test
	public void testPlace() {
		List<Place> places = PersistenceManager.getAll(Place.class);
		Assert.assertNotNull(places);

		int origSize = places.size();

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
		place.setScore(1234);
		PersistenceManager.save(place);

		// check if everything was stored
		places = PersistenceManager.getAll(Place.class);
		Assert.assertNotNull(places);
		Assert.assertTrue(!places.isEmpty());

		Place placeToCompare = null;
		for (Place u : places) {
			if (u.getId() == place.getId()) {
				placeToCompare = u;
				break;
			}
		}

		Assert.assertNotNull(placeToCompare);

		Assert.assertEquals(1, placeToCompare.getCount());
		Assert.assertEquals(1234, placeToCompare.getScore());
		Assert.assertEquals("Goldenes Dachl", placeToCompare.getName());
		Assert.assertEquals("pic of goldenes dachl",
				placeToCompare.getPicture());
		Assert.assertEquals("http://blabla", placeToCompare.getWikiLink());
		Assert.assertEquals(false, placeToCompare.isFeatured());
		PersistenceManager.delete(place);

		// check if it was removed
		places = PersistenceManager.getAll(Place.class);
		Assert.assertNotNull(place);

		Assert.assertEquals(origSize, places.size());

		// the category should still be there
		List<Category> categories = PersistenceManager.getAll(Category.class);
		Assert.assertNotNull(categories);
		Assert.assertTrue(!categories.isEmpty());

		PersistenceManager.delete(cat);
	}

	@Test
	public void testUser() {
		List<User> users = PersistenceManager.getAll(User.class);
		Assert.assertNotNull(users);

		int origSize = users.size();

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

		User touristToCompare = null;
		for (User u : users) {
			if (u.getId() == tourist.getId()) {
				touristToCompare = u;
				break;
			}
		}

		Assert.assertNotNull(touristToCompare);

		Assert.assertEquals(1234, touristToCompare.getScore());
		Assert.assertEquals(User.TYPE_TOURIST, touristToCompare.getType());
		Assert.assertEquals("example@google.com",
				touristToCompare.getGoogleId());

		User localToCompare = null;
		for (User u : users) {
			if (u.getId() == local.getId()) {
				localToCompare = u;
				break;
			}
		}

		Assert.assertNotNull(localToCompare);

		Assert.assertEquals(666, localToCompare.getScore());
		Assert.assertEquals(User.TYPE_LOCAL, localToCompare.getType());
		Assert.assertEquals("example123@google.com",
				localToCompare.getGoogleId());

		PersistenceManager.delete(tourist);
		PersistenceManager.delete(local);

		users = PersistenceManager.getAll(User.class);
		Assert.assertNotNull(users);

		Assert.assertEquals(origSize, users.size());
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
		place.setScore(1234);
		PersistenceManager.save(place);

		user.getFavourites().add(place);
		PersistenceManager.save(user);

		User readUser = PersistenceManager.get(User.class,
				"google_id = \'example@google.com\'").get(0);
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
		place.setScore(1234);
		PersistenceManager.save(place);

		user.getFoundPlaces().add(place);
		PersistenceManager.save(user);

		User readUser = PersistenceManager.get(User.class,
				"google_id = \'example@google.com\'").get(0);
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

		int origSize = trophies.size();

		Trophy trophy = new Trophy();
		trophy.setCode("code");
		trophy.setDescription("desc");
		trophy.setName("trophy");
		PersistenceManager.save(trophy);

		trophies = PersistenceManager.getAll(Trophy.class);
		Assert.assertNotNull(trophies);
		Assert.assertTrue(!trophies.isEmpty());

		Trophy trophyToCompare = null;
		for (Trophy t : trophies) {
			if (t.getId() == trophy.getId()) {
				trophyToCompare = t;
				break;
			}
		}

		Assert.assertNotNull(trophyToCompare);

		Assert.assertEquals("trophy", trophyToCompare.getName());
		Assert.assertEquals("desc", trophyToCompare.getDescription());
		Assert.assertEquals("code", trophyToCompare.getCode());
		PersistenceManager.delete(trophy);

		trophies = PersistenceManager.getAll(Trophy.class);
		Assert.assertNotNull(trophies);

		Assert.assertEquals(origSize, trophies.size());
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

		User readUser = PersistenceManager.get(User.class,
				"google_id = \'example@google.com\'").get(0);
		Assert.assertNotNull(readUser);
		Assert.assertEquals(1, readUser.getTrophies().size());

		readUser.getTrophies().clear();
		PersistenceManager.save(readUser);

		PersistenceManager.delete(readUser);
		PersistenceManager.delete(trophy);
	}
}