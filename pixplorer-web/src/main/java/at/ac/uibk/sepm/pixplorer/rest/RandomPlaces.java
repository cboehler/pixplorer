package at.ac.uibk.sepm.pixplorer.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;

import com.google.gson.Gson;

@Path("/Random")
public class RandomPlaces {

	Gson gson = new Gson();
	
	//Method handling a Client's Search Request	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String getRandomPlaces(String json){
		
		for(int i = 0;i<10;i++){
			Place to_save = new Place("Sehenswürdigkeit"+i,"http://de.wikipedia.org/wiki/Goldenes_Dachl",0,null,null);
			PersistenceManager.save(to_save);
		}
		
		Random random = new Random();
		Integer[] ids = gson.fromJson(json, Integer[].class);
		List<Integer> id_list = new ArrayList<Integer>();
		for(Integer id : ids){
			id_list.add(id);		
		}
		
		
		List<Place> places =  PersistenceManager.getAll(Place.class);
		List<Integer> random_ids = new ArrayList<Integer>();
		Place[] ret_places = new Place[10];
		Integer place_id;
		
		for(int i = 0; i< 10 ; i++){
			
			place_id = random.nextInt(places.size());
			if(id_list.contains(place_id) || random_ids.contains(place_id)){
				i--;
				continue;
			}				
			
			ret_places[i] = places.get(place_id);
			random_ids.add(place_id);					
		}
		
		return gson.toJson(ret_places);
		
	}
	
	

}
