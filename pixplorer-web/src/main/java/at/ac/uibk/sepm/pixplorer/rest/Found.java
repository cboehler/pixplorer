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


@Path("/Found")
public class Found {
	
	public class json_found{
		
		private String googleid;
		private Place place;
		
		public json_found(String googleid, Place place){
			this.googleid = googleid;
			this.place = place;
		}
		
		public String getGoogleid() {
			return googleid;
		}

		public void setGoogleid(String googleid) {
			this.googleid = googleid;
		}

		public Place getPlace() {
			return place;
		}

		public void setPlace(Place place) {
			this.place = place;
		}

	}
	
	Gson gson = new Gson();
	Random random = new Random();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String found(String json){
				
		Place place = gson.fromJson(json, Place.class);
		
		
//		/* BE CAREFUL! Incrementation of place.count not atomic!!*/ 
//		String filter = "where x.id = '" + place.getId() + "'";		
//		Place p = PersistenceManager.getOne(Place.class, filter);
//		
//		if(p == null ){			
//			return null;
//		}
//		
//		
//		p.setCount(p.getCount()+1);
//		PersistenceManager.save(p);
		
		
		/* IMPORTANT!!!!!!!!!!!!! After Database update implement code for saving found object in users FoundObjectTable */
		
		List<Integer> id_list = new ArrayList<Integer>();
		
		RandomPlaces rp = new RandomPlaces();
		Place[] places = rp.get10RandomPlaces(id_list);
		Place[] ret = new Place[1];
		ret[0] = places[0];
		
		return gson.toJson(ret);		
		
	}
}
