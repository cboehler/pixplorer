package at.ac.uibk.sepm.pixplorer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ResourceConfig;

import at.ac.uibk.sepm.pixplorer.db.Place;

import com.google.gson.Gson;

@Path("/test")
public class PixplorerApplication extends ResourceConfig {
	// This method is called if JSON is request
	  @GET
	  //@Produces({MediaType.APPLICATION_JSON })
	  public String getJson() {
	    Place test = new Place();
	    Gson gson = new Gson();
	    String json = gson.toJson(test);
	    return json;
	  } 

	  
	  @POST
	  @Consumes(MediaType.APPLICATION_JSON)
	  public String returnJson(String json){
		  Gson gson = new Gson();
		  Place[] array = gson.fromJson(json, Place[].class);
		  array[2].setName("hello");
		  return gson.toJson(array);	  
		  
	  }

}
