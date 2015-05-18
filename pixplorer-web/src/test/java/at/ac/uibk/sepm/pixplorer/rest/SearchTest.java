package at.ac.uibk.sepm.pixplorer.rest;

import org.junit.Assert;
import org.junit.Test;

import at.ac.uibk.sepm.pixplorer.rest.msg.AbstractReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.ReplyException;
import at.ac.uibk.sepm.pixplorer.rest.msg.SearchReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.SearchRequest;

import com.google.gson.Gson;

public class SearchTest {
	private static final Gson gson = new Gson();
	
	@Test
	public void testSearchCategory() {
		SearchRequest request = new SearchRequest();
		request.setGoogleId("max.mustermann@google.com");
		request.setFilter("SIGHT");
		
		String json = gson.toJson(request);
		
		Search call = new Search();
		String jsonReply = call.search(json);
		
		SearchReply reply = gson.fromJson(jsonReply, SearchReply.class);
		try {
			reply.checkReturnCode();
			
			Assert.assertEquals(100, reply.getPlaces().size());
		} catch (ReplyException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testSearchGoldenesDachl() {
		SearchRequest request = new SearchRequest();
		request.setGoogleId("max.mustermann@google.com");
		request.setFilter("Goldenes Dachl");
		
		String json = gson.toJson(request);
		
		Search call = new Search();
		String jsonReply = call.search(json);
		
		SearchReply reply = gson.fromJson(jsonReply, SearchReply.class);
		try {
			reply.checkReturnCode();
			
			Assert.assertEquals(25, reply.getPlaces().size());
		} catch (ReplyException e) {
			Assert.fail();
		}
	}	
	
	@Test
	public void testSearchCaseInsensitive() {
		SearchRequest request = new SearchRequest();
		request.setGoogleId("max.mustermann@google.com");
		request.setFilter("goldenes dachl");
		
		String json = gson.toJson(request);
		
		Search call = new Search();
		String jsonReply = call.search(json);
		
		SearchReply reply = gson.fromJson(jsonReply, SearchReply.class);
		try {
			reply.checkReturnCode();
			
			Assert.assertEquals(25, reply.getPlaces().size());
		} catch (ReplyException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}	
	
	@Test
	public void testInvalidUser() {
		SearchRequest request = new SearchRequest();
		request.setGoogleId("invalid@google.com");
		
		String json = gson.toJson(request);
		
		Favour call = new Favour();
		String jsonReply = call.favour(json);
		
		SearchReply reply = gson.fromJson(jsonReply, SearchReply.class);
		try {
			reply.checkReturnCode();
			Assert.fail();
		} catch (ReplyException e) {
			Assert.assertEquals(AbstractReply.RET_USER_NOT_FOUND, reply.getReturnCode());
		}
	}
}
