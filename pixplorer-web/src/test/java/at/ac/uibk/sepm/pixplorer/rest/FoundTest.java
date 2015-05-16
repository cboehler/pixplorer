package at.ac.uibk.sepm.pixplorer.rest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AbstractReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.ReplyException;

import com.google.gson.Gson;

public class FoundTest {
	private static final Gson gson = new Gson();
	
	@Test
	public void testFound() {
		AppInitRequest initRequest = new AppInitRequest();
		initRequest.setGoogleId("max.mustermann@google.com");
		initRequest.setOption(User.TYPE_LOCAL);
		
		String json = gson.toJson(initRequest);
		
		AppInit initCall = new AppInit();
		String jsonReply = initCall.init(json);
		
		AppInitReply initReply = gson.fromJson(jsonReply, AppInitReply.class);
		
		FoundRequest request = new FoundRequest();
		request.setGoogleId("max.mustermann@google.com");
		
		Place p = initReply.getPlaces().get(0);
		long oldCount = p.getCount();
		
		request.setFoundPlace(p.getId());
		request.setLatitude(p.getGpsData().getLatitude());
		request.setLongitude(p.getGpsData().getLongitude());
		
		json = gson.toJson(request);
		
		Found call = new Found();
		jsonReply = call.found(json);
		
		FoundReply reply = gson.fromJson(jsonReply, FoundReply.class);
		try {
			reply.checkReturnCode();
			Assert.assertFalse(reply.getPlaces().isEmpty());
			
			// read place from db and check if count has been incremented
			List<Place> places = PersistenceManager.get(Place.class, "where x.id = " + p.getId()); 
			Assert.assertFalse(places.isEmpty());
			
			Place newPlace = places.get(0);
			Assert.assertEquals(oldCount + 1, newPlace.getCount());
		} catch (ReplyException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testFoundInvalidCoordinates() {
		AppInitRequest initRequest = new AppInitRequest();
		initRequest.setGoogleId("max.mustermann@google.com");
		initRequest.setOption(User.TYPE_LOCAL);
		
		String json = gson.toJson(initRequest);
		
		AppInit initCall = new AppInit();
		String jsonReply = initCall.init(json);
		
		AppInitReply initReply = gson.fromJson(jsonReply, AppInitReply.class);
		
		FoundRequest request = new FoundRequest();
		request.setGoogleId("max.mustermann@google.com");
		
		Place p = initReply.getPlaces().get(0);
		request.setFoundPlace(p.getId());
		request.setLatitude(-1.0d);
		request.setLongitude(-2.0d);
		
		json = gson.toJson(request);
		
		Found call = new Found();
		jsonReply = call.found(json);
		
		FoundReply reply = gson.fromJson(jsonReply, FoundReply.class);
		try {
			reply.checkReturnCode();
			Assert.fail();
		} catch (ReplyException e) {
			Assert.assertEquals(AbstractReply.RET_INVALUD_COORDINATES, reply.getReturnCode());
		}
	}	
	
	@Test
	public void testInvalidPlace() {
		FoundRequest request = new FoundRequest();
		request.setGoogleId("max.mustermann@google.com");
		request.setFoundPlace(-1);
		
		String json = gson.toJson(request);
		
		Found call = new Found();
		String jsonReply = call.found(json);
		
		FoundReply reply = gson.fromJson(jsonReply, FoundReply.class);
		try {
			reply.checkReturnCode();
			Assert.fail();
		} catch (ReplyException e) {
			Assert.assertEquals(AbstractReply.RET_PLACE_NOT_FOUND, reply.getReturnCode());
		}	
	}
	
	@Test
	public void testInvalidUser() {
		FoundRequest request = new FoundRequest();
		request.setGoogleId("invalid@google.com");
		request.setFoundPlace(-1);
		
		String json = gson.toJson(request);
		
		Found call = new Found();
		String jsonReply = call.found(json);
		
		FoundReply reply = gson.fromJson(jsonReply, FoundReply.class);
		try {
			reply.checkReturnCode();
			Assert.fail();
		} catch (ReplyException e) {
			Assert.assertEquals(AbstractReply.RET_USER_NOT_FOUND, reply.getReturnCode());
		}
	}

}
