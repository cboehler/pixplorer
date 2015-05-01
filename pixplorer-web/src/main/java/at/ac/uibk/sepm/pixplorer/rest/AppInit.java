package at.ac.uibk.sepm.pixplorer.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import at.ac.uibk.sepm.pixplorer.db.Category;
import at.ac.uibk.sepm.pixplorer.db.DBContentGenerator;
import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;

import com.google.gson.Gson;


@Path("/Init")
public class AppInit {
	
	public class json_init{
		String googleid;
		Integer option;
		
		public json_init(String googleid, Integer option){
			this.googleid = googleid;
			this.option = option;
		}
		
		public String get_googleid(){
			return this.googleid;
		}
		
		public Integer get_option(){
			return this.option;
		}
		
		public void set_googleid(String googleid){
			this.googleid = googleid;
		}
		
		public void set_option(Integer option){
			this.option = option;
		}
	}
	
	//Method is called when Client opened App and decided to play as Tourist or Local	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String init(String json){
		
		//Just for testing - fills database - if empty -  with some arbitrary values
		DBContentGenerator.main(null);
		
		Gson gson = new Gson();
		
		json_init obj = gson.fromJson(json, json_init.class);
		
		if(obj.option == 0){
			
			//IMPORTANT!!!!!!!! Implement after Database update - save option in Table User & if name is not in database create a new one
		}
		
		else if(obj.option == 1){
			//IMPORTANT!!!!!!!! Implement after Database update - save option in Table User & if name is not in database create a new one
		}
		
		 RandomPlaces rp = new RandomPlaces();
		 return rp.getRandomPlaces(gson.toJson(obj.googleid));
		 
		
	}

}
