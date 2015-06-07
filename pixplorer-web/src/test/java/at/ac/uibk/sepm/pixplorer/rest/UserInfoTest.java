package at.ac.uibk.sepm.pixplorer.rest;

import org.junit.Assert;
import org.junit.Test;

import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.ReplyException;
import at.ac.uibk.sepm.pixplorer.rest.msg.UserInfoReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.UserInfoRequest;

import com.google.gson.Gson;

public class UserInfoTest {
	private static final Gson gson = new Gson();
	
	@Test	
	public void testTouristInfo() {
		AppInitRequest request = new AppInitRequest();
		request.setGoogleId("max.touristusermann@google.com");
		request.setOption(User.TYPE_TOURIST);
		
		String json = gson.toJson(request);
		
		AppInit call = new AppInit();
		String jsonReply = call.init(json);
		
		AppInitReply reply = gson.fromJson(jsonReply, AppInitReply.class);
		try {
			reply.checkReturnCode();
		} catch (ReplyException e) {
			Assert.fail();
		}		
		
		UserInfoRequest infoRequest = new UserInfoRequest();
		infoRequest.setGoogleId("max.touristusermann@google.com");
		
		UserInfo infoCall = new UserInfo();
		jsonReply = infoCall.userInfo(gson.toJson(infoRequest));
		UserInfoReply infoReply = gson.fromJson(jsonReply, UserInfoReply.class);
		
		try {
			infoReply.checkReturnCode();
		} catch (ReplyException e) {
			Assert.fail();
		}
	}
	
	
	@Test	
	public void testFindAndGetUserInfo() {
		AppInitRequest request = new AppInitRequest();
		request.setGoogleId("max.touristusermann@google.com");
		request.setOption(User.TYPE_TOURIST);
		
		String json = gson.toJson(request);
		
		AppInit call = new AppInit();
		String jsonReply = call.init(json);
		
		AppInitReply reply = gson.fromJson(jsonReply, AppInitReply.class);
		try {
			reply.checkReturnCode();
		} catch (ReplyException e) {
			Assert.fail();
		}		
		
		for (Place p : reply.getPlaces()) {
			FoundRequest req = new FoundRequest();
			req.setGoogleId("max.touristusermann@google.com");
			req.setFoundPlace(p.getId());
			req.setLatitude(p.getGpsData().getLatitude());
			req.setLongitude(p.getGpsData().getLongitude());
			
			jsonReply = new Found().found(gson.toJson(req));
			FoundReply foundReply = gson.fromJson(jsonReply, FoundReply.class);
			
			try {
				foundReply.checkReturnCode();
			} catch (ReplyException e) {
				Assert.fail();
			}		
		}
		
		UserInfoRequest infoRequest = new UserInfoRequest();
		infoRequest.setGoogleId("max.touristusermann@google.com");
		
		UserInfo infoCall = new UserInfo();
		jsonReply = infoCall.userInfo(gson.toJson(infoRequest));
		UserInfoReply infoReply = gson.fromJson(jsonReply, UserInfoReply.class);
		
		try {
			infoReply.checkReturnCode();
		} catch (ReplyException e) {
			Assert.fail();
		}
	}	
	
	@Test	
	public void testLocalInfo() {
		AppInitRequest request = new AppInitRequest();
		request.setGoogleId("max.localusermann@google.com");
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
		
		UserInfoRequest infoRequest = new UserInfoRequest();
		infoRequest.setGoogleId("max.localusermann@google.com");
		
		UserInfo infoCall = new UserInfo();
		jsonReply = infoCall.userInfo(gson.toJson(infoRequest));
		UserInfoReply infoReply = gson.fromJson(jsonReply, UserInfoReply.class);
		
		try {
			infoReply.checkReturnCode();
		} catch (ReplyException e) {
			Assert.fail();
		}
	}
	
}
