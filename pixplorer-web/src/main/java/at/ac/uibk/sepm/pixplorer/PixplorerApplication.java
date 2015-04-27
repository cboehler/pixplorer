package at.ac.uibk.sepm.pixplorer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.glassfish.jersey.server.ResourceConfig;

import at.ac.uibk.sepm.pixplorer.db.*;

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
}