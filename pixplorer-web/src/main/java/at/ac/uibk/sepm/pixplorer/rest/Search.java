package at.ac.uibk.sepm.pixplorer.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import at.ac.uibk.sepm.pixplorer.db.Category;
import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AbstractReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.SearchReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.SearchRequest;

import com.google.gson.Gson;


@Path("/search")
public class Search {	
	private static final Gson gson = new Gson();	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String search(String json){
		SearchRequest request = gson.fromJson(json, SearchRequest.class);
		String username = request.getGoogleId();

		SearchReply reply = new SearchReply();
		
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = '" + username + "'");
		if (users.isEmpty()) {
			reply.setReturnCode(AbstractReply.RET_USER_NOT_FOUND);
			return gson.toJson(reply);
		}
		
		User user = users.get(0);
		
		List<Category> categories = PersistenceManager.getAll(Category.class);
		
		String filter = null;
		
		/*First check if User searches Places of a certain Category*/
		for(Category c : categories){
			if(c.getName().equalsIgnoreCase(request.getFilter())){
				filter = "where x.category = '" + c.getId() + "'";
				break;	
			}
		}
		
		/*If user does't search for Places of a certain Category, search for Places starting with search_str*/
		if (filter == null) {
			filter = "where lower(x.name) like '" + request.getFilter().toLowerCase() + "%'";			
		}
		
		List<Place> places = PersistenceManager.get(Place.class, filter);
		reply.setPlaces(places);
		
		return gson.toJson(reply);
		
	}

	
}
