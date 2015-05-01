package at.ac.uibk.sepm.pixplorer.db;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class DatabaseTest {

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
}