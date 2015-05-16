package at.ac.uibk.sepm.pixplorer.rest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AbstractReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.FavourReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FavourRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.ReplyException;

import com.google.gson.Gson;

public class FavourTest {
	private static final Gson gson = new Gson();
	
	@Test
	public void testExplore() {
		AppInitRequest initRequest = new AppInitRequest();
		initRequest.setGoogleId("max.mustermann@google.com");
		initRequest.setOption(User.TYPE_LOCAL);
		
		String json = gson.toJson(initRequest);
		
		AppInit initCall = new AppInit();
		String jsonReply = initCall.init(json);
		
		AppInitReply initReply = gson.fromJson(jsonReply, AppInitReply.class);
		
		FavourRequest request = new FavourRequest();
		request.setGoogleId("max.mustermann@google.com");
		
		List<Integer> favourites = new ArrayList<>();
		for (Place p : initReply.getPlaces()) {
			favourites.add(p.getId());
		}
		
		json = gson.toJson(request);
		
		Favour call = new Favour();
		jsonReply = call.favour(json);
		
		FavourReply reply = gson.fromJson(jsonReply, FavourReply.class);
		try {
			reply.checkReturnCode();
		} catch (ReplyException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testInvalidPlace() {
		FavourRequest request = new FavourRequest();
		request.setGoogleId("max.mustermann@google.com");
		
		List<Integer> favourites = new ArrayList<>();
		favourites.add(-1);
		request.setFavourites(favourites);
		
		String json = gson.toJson(request);
		
		Favour call = new Favour();
		String jsonReply = call.favour(json);
		
		FavourReply reply = gson.fromJson(jsonReply, FavourReply.class);
		try {
			reply.checkReturnCode();
			Assert.fail();
		} catch (ReplyException e) {
			Assert.assertEquals(AbstractReply.RET_PLACE_NOT_FOUND, reply.getReturnCode());
		}	
	}
	
	@Test
	public void testInvalidUser() {
		FavourRequest request = new FavourRequest();
		request.setGoogleId("invalid@google.com");
		
		String json = gson.toJson(request);
		
		Favour call = new Favour();
		String jsonReply = call.favour(json);
		
		FavourReply reply = gson.fromJson(jsonReply, FavourReply.class);
		try {
			reply.checkReturnCode();
			Assert.fail();
		} catch (ReplyException e) {
			Assert.assertEquals(AbstractReply.RET_USER_NOT_FOUND, reply.getReturnCode());
		}
	}
}
