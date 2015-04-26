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
		cat.setCategoryid(5000);
		
		PersistenceManager.save(cat);
		
		categories = PersistenceManager.getAll(Category.class);
		Assert.assertNotNull(categories);
		Assert.assertTrue(!categories.isEmpty());
		Assert.assertEquals("test 1", categories.get(0).getName());
		Assert.assertEquals(5000, categories.get(0).getCategoryid());
		
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
		cat.setCategoryid(1234);
		cat.setName("cat1");
		
		PersistenceManager.save(cat);
		
		// create new place
		Place place = new Place();
		place.setCount(1);
		place.setCategory(cat);
		place.setFeatured(true);
		place.setName("Goldenes Dachl");
		place.setPicture("pic of goldenes dachl");
		place.setWikiLink("http://blabla");
		PersistenceManager.save(place);
		
		// check if everything was stored
		places = PersistenceManager.getAll(Place.class);
		Assert.assertNotNull(places);
		Assert.assertTrue(!places.isEmpty());
		Assert.assertEquals(1, places.get(0).getCount());
		Assert.assertEquals(true, places.get(0).isFeatured());
		Assert.assertEquals("Goldenes Dachl", places.get(0).getName());
		Assert.assertEquals("pic of goldenes dachl", places.get(0).getPicture());
		Assert.assertEquals("http://blabla", places.get(0).getWikiLink());
		PersistenceManager.delete(cat);
		
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
