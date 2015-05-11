package at.ac.uibk.sepm.pixplorer.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;

@Path("special")
public class Special {
	
	//Method handling a Client's Special Places Request	
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		public String explore(String json){		
			
			/*Order of objects: 1. String "username" 	
			 */
			Object[] objects = JsonUtils.getObjectsFromJson(json);		
			String username = String.class.cast(objects[0]);
			
			String filter = "where x.googleId = '" + username + "'";
			
			User user = PersistenceManager.get(User.class, filter).get(0);
			if (user == null)
				return null;
					
			RandomPlaceGenerator generator = new RandomPlaceGenerator();
			Place[] places = generator.getPlaces(user,5,true);
					
			return JsonUtils.createJsonString(places);
			
		}

}
