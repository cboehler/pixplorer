package at.ac.uibk.sepm.pixplorer.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import at.ac.uibk.sepm.pixplorer.db.Category;
import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;

import com.google.gson.Gson;


@Path("/search")
public class Search {	
	
	Gson gson = new Gson();	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String search(String json){
		
		/*
		 * Order of objects: 1. String for searching Places
		 * 
		 */
		
		Object[] objects = JsonUtils.getObjectsFromJson(json);
		
		String search_str = String.class.cast(objects[0]);
		
		List<Category> categories = PersistenceManager.getAll(Category.class);
		
		String filter = null;
		
		/*First check if User searches Places of a certain Category*/
		for(Category c : categories){
			if(c.getName().equals(search_str)){
				filter = "where x.category = '" + c.getId() + "'";
				break;	
			}
		}
		
		/*If user does't search for Places of a certain Category, search for Places starting with search_str*/
		if(filter == null)
			filter = "where x.name like '" + search_str + "%'";			
						
		List<Place> places = PersistenceManager.get(Place.class,filter);		
		return JsonUtils.createJsonString(places.toArray());
		
	}

	
}
