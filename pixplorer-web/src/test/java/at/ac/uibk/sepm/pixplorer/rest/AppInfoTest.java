package at.ac.uibk.sepm.pixplorer.rest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInfoReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInfoRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitRequest;

import com.google.gson.Gson;

public class AppInfoTest {
	private static final Gson gson = new Gson();
	
	@Test
	public void testAppInfo() {
		AppInitRequest request = new AppInitRequest();
		request.setGoogleId("max.mustermann@google.com");
		request.setOption(User.TYPE_LOCAL);
		
		String json = gson.toJson(request);
		
		AppInit call = new AppInit();
		String jsonReply = call.init(json);
		
		AppInfoRequest infoRequest = new AppInfoRequest();
		infoRequest.setGoogleId("max.mustermann@google.com");
		
		AppInfo infoCall = new AppInfo();
		jsonReply = infoCall.info(gson.toJson(infoRequest));
		AppInfoReply infoReply = gson.fromJson(jsonReply, AppInfoReply.class);
		
		List<Place> places = PersistenceManager.getAll(Place.class);
		
		Assert.assertEquals(places.size(), infoReply.getAmountOfPlaces());
	}
}
