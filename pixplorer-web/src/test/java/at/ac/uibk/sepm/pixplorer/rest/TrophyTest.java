package at.ac.uibk.sepm.pixplorer.rest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.Trophy;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.ReplyException;

import com.google.gson.Gson;

public class TrophyTest {
	private static final Gson gson = new Gson();
	
	@Test	
	public void testTheBeginner() {
		AppInitRequest initRequest = new AppInitRequest();
		initRequest.setGoogleId("max.mustermann123456@google.com");
		initRequest.setOption(User.TYPE_LOCAL);
		
		String json = gson.toJson(initRequest);
		
		AppInit initCall = new AppInit();
		String jsonReply = initCall.init(json);
		
		AppInitReply initReply = gson.fromJson(jsonReply, AppInitReply.class);
		
		FoundRequest request = new FoundRequest();
		request.setGoogleId("max.mustermann123456@google.com");
		
		Place p = initReply.getPlaces().get(0);
		
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
			
			Assert.assertFalse(reply.getTrophies().isEmpty());
			
			Trophy theBeginner = reply.getTrophies().get(0);
			Assert.assertEquals("CgkI-d2H8pccEAIQAQ", theBeginner.getName());
			
			List<User> users = PersistenceManager.get(User.class, "where x.googleId = 'max.mustermann123456@google.com'");
			Assert.assertEquals(1, users.size());
			
			User user = users.get(0);
			PersistenceManager.delete(user);
		} catch (ReplyException e) {
			Assert.fail();
		}
	}
}
