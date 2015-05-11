package at.ac.uibk.sepm.pixplorer.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import at.ac.uibk.sepm.pixplorer.db.DBContentGenerator;
import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;

import com.google.gson.Gson;


@Path("/init")
public class AppInit {
	
	Gson gson = new Gson();
	
	//Method is called when Client opened App and decided to play as Tourist or Local	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String init(String json){
		
		DBContentGenerator.main(null);
		
		/*Order of objects: 1. Integer option ( Determines whether User is playing as tourist or local )	
		 * 					2. String username ( Email of User's Google Account )
		 */
		
		Object[] objects = JsonUtils.getObjectsFromJson(json);
		Integer option = Integer.class.cast(objects[0]);
		String username = String.class.cast(objects[1]);
		
		String filter = "where x.googleId = '" + username + "'";
		
		List<User> users = PersistenceManager.get(User.class,filter);
		
		User user = new User();
		
		if(users.size() == 0){
			user = new User();
			user.setGoogleId(username);
			user.setType(option);
			PersistenceManager.save(user);
		}
		else{
			user = users.get(0);
		}
		
		if(user.getType() != option){
			user.setType(option);
			PersistenceManager.save(user);
		}
		
		RandomPlaceGenerator generator = new RandomPlaceGenerator();
		Place[] places = generator.getPlaces(user , 10);
		return JsonUtils.createJsonString(places);
		 
		
	}

}
