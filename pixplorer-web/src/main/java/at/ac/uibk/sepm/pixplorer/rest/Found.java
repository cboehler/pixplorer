package at.ac.uibk.sepm.pixplorer.rest;

import java.util.Random;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;

import com.google.gson.Gson;


@Path("/found")
public class Found {
	
	Gson gson = new Gson();
	Random random = new Random();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String found(String json){
		
		/*Order of objects: 1. String "username" 
		 * 					2. Place  " found place"
		 * 					3. GPS Coordinates
		 * 					4. String Image?	
		 */
		
		Object[] objects = JsonUtils.getObjectsFromJson(json);
		
		String username = String.class.cast(objects[0]);
		Place found_place = Place.class.cast(objects[1]);
						
		String filter = "where x.googleId = '" + username + "'";
		
		User user = PersistenceManager.get(User.class, filter).get(0);
		
		if(user == null)
			return null;
		
		/*Update User */
		/*filter = "where x.name = '" + found_place.getName() + "'";
		found_place = PersistenceManager.get(Place.class, filter).get(0);*/
		
		Set<Place> update = user.getFoundPlaces();
		update.add(found_place);				
		user.setFoundPlaces(update);
		//user.setScore(user.getScore()+found_place.get);
		
		PersistenceManager.save(user);
				
		/*Get randomplaces from RandomplaceGenerator*/
		RandomPlaceGenerator generator = new RandomPlaceGenerator();
		Place[] places = generator.getPlaces(user,1);
		
		/* Get Found Place from DataBase */
		filter = "where x.id = '" + found_place.getId() + "'";		
		Place p = PersistenceManager.get(Place.class, filter).get(0);
		
		if(p == null ){			
			return null;
		}
		
		/* Update place count */
		/* BE CAREFUL! Increment of place.count not atomic!!*/
		p.setCount(p.getCount()+1);
		PersistenceManager.save(p);		
				
		return JsonUtils.createJsonString(places);		
		
	}
}
