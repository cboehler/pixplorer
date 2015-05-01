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
	Random random = new Random();
	
	//Method handling a Client's Search Request	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String getRandomPlaces(String json){		
				
		/*String user = gson.fromJson(json, String.class);
		
		String filter = "where x.googleid = '" + user + "'";
		
		List<User> users = PersistenceManager.get(User.class, filter);*/
		
		//IMPORTANT!!!!!!!!!!!!!!!!!!!!!
		//To implement after Database is updated : Get Users found and favored places (Database queries)
		
		//IMPORTANT!!!!!!!!!!!!!!!!!!!!!
		// Delete this line after implementation 		
		
		return gson.toJson(this.get10RandomPlaces(new ArrayList<Integer>()));
		
	}
	
	public Place[] get10RandomPlaces(List<Integer> id_list){
	
		List<Integer> random_ids = new ArrayList<Integer>();
	
		List<Place> places =  PersistenceManager.getAll(Place.class);
		
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
		
		return ret_places;
	}

}
