package at.ac.uibk.sepm.pixplorer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import at.ac.uibk.sepm.pixplorer.db.Category;
import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;

import com.google.gson.Gson;


@Path("/Suche")
public class Search {
	
	Gson gson = new Gson();
	
	//Method handling a Client's Search Request	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String search(String json){
		
		for(int i = 0;i<10;i++){
			Category c = new Category();
			c.setName("category"+i);
			Place to_save = new Place("Sehenswürdigkeit"+i,"http://de.wikipedia.org/wiki/Goldenes_Dachl",0,null,c);
			PersistenceManager.save(to_save);
		}
		
		String search_str = gson.fromJson(json, String.class);
		
		List<Category> categories = PersistenceManager.getAll(Category.class);
		
		String filter = null;
		
		for(Category c : categories){
			if(c.getName().equals(search_str)){
				filter = "where x.category = '" + search_str + "'";
				break;	
			}
		}
		
		if(filter == null)
			filter = "where x.name = '" + search_str + "'";
		
		System.out.println(filter);
		
		List<Place> place = new ArrayList<Place> ();
				
		place = PersistenceManager.get(Place.class,filter);
		
		/*
		 * 	Do some Databasestuff
		 */
		
		Place[] ret_places = new Place[place.size()];
		place.toArray(ret_places);
		return gson.toJson(ret_places);
		
	}

	
}
