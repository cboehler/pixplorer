package at.ac.uibk.sepm.pixplorer.rest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.rest.msg.AbstractReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.ExploreReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.ExploreRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.ReplyException;

import com.google.gson.Gson;

public class ExploreTest {
	private static final Gson gson = new Gson();
	
	@Test
	public void testExplore() {
		ExploreRequest request = new ExploreRequest();
		request.setGoogleId("max.mustermann@google.com");
		
		String json = gson.toJson(request);
		
		Explore call = new Explore();
		String jsonReply = call.explore(json);
		
		ExploreReply reply = gson.fromJson(jsonReply, ExploreReply.class);
		try {
			reply.checkReturnCode();
			
			List<Place> places = reply.getPlaces();
			Assert.assertEquals(10, places.size());
			
			for (int i = 0; i < places.size(); i++) {
				for (int j = i + 1; j < places.size(); j++) {
					Assert.assertFalse(places.get(i).equals(places.get(j)));
				}
			}
		} catch (ReplyException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testInvalidUser() {
		ExploreRequest request = new ExploreRequest();
		request.setGoogleId("invalid@google.com");
		
		String json = gson.toJson(request);
		
		Explore call = new Explore();
		String jsonReply = call.explore(json);
		
		ExploreReply reply = gson.fromJson(jsonReply, ExploreReply.class);
		try {
			reply.checkReturnCode();
			Assert.fail();
		} catch (ReplyException e) {
			Assert.assertEquals(AbstractReply.RET_USER_NOT_FOUND, reply.getReturnCode());
		}
	}
}
