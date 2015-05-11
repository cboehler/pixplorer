package at.ac.uibk.sepm.pixplorer.rest;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;

@Path("/favour")
public class Favour {

	Gson gson = new Gson();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String favour(String json){
		
		/*Order of objects: 1. String "username" 
		 * 					2. Place  " favoured place"	
		 */
		
		Object[] objects = JsonUtils.getObjectsFromJson(json);
		
		String username = String.class.cast(objects[0]);
		Place favoured = Place.class.cast(objects[1]);
		
		String filter = "where x.googleId = '" + username + "'";
		
		User user = PersistenceManager.get(User.class, filter).get(0);
		
		if(user == null)
			return null;
		
		/*filter = "where x.name = '" + favoured.getName() + "'";
		favoured = PersistenceManager.get(Place.class, filter).get(0);*/
		
		Set<Place> update = user.getFavourites();
		update.add(favoured);				
		user.setFavourites(update);
		
		PersistenceManager.save(user);
		
		return JsonUtils.createJsonString(new Object[]{});
				
		
	}
}
