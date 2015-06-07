package at.ac.uibk.sepm.pixplorer.rest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.ReplyException;
import at.ac.uibk.sepm.pixplorer.rest.msg.SpecialReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.SpecialRequest;

import com.google.gson.Gson;

public class SpecialTest {
	private static final Gson gson = new Gson();
	
	@Test
	public void testSpecial() {
		AppInitRequest request = new AppInitRequest();
		request.setGoogleId("max.specialmann@google.com");
		request.setOption(User.TYPE_LOCAL);
		
		String json = gson.toJson(request);
		
		AppInit call = new AppInit();
		String jsonReply = call.init(json);
		
		AppInitReply reply = gson.fromJson(jsonReply, AppInitReply.class);
		try {
			reply.checkReturnCode();
		} catch (ReplyException e) {
			Assert.fail();
		}		
		
		SpecialRequest specialRequest = new SpecialRequest();
		specialRequest.setGoogleId("max.specialmann@google.com");
		
		Special specialCall = new Special();
		jsonReply = specialCall.special(gson.toJson(specialRequest));
		SpecialReply specialReply = gson.fromJson(jsonReply, SpecialReply.class);
		
		try {
			specialReply.checkReturnCode();
			
			List<Place> places = reply.getPlaces();
			Assert.assertEquals(10, places.size());
			
			for (int i = 0; i < places.size(); i++) {
				Assert.assertTrue(places.get(i).isFeatured());
				
				for (int j = i + 1; j < places.size(); j++) {
					Assert.assertFalse(places.get(i).equals(places.get(j)));
				}
			}
		} catch (ReplyException e) {
			Assert.fail();
		}
		
	}
}
