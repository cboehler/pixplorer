package at.ac.uibk.sepm.pixplorer.rest;

import org.junit.Assert;
import org.junit.Test;

import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.rest.msg.AbstractReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundRequest;

import com.google.gson.Gson;

public class GPSTest {
	private static final Gson gson = new Gson();
		
	@Test	
	public void testDistanceCalculation() {
		FoundRequest request = new FoundRequest();
		request.setGoogleId("max.mustermann@google.com");
		
		Place p = PersistenceManager.get(Place.class, "where x.name = 'Stadtcafe'").get(0);
		
		request.setFoundPlace(p.getId());
		request.setLatitude(47.2663478);
		request.setLongitude(11.3621918);	
		
		String json = gson.toJson(request);
		
		Found call = new Found();
		String jsonReply = call.found(json);
		
		FoundReply reply = gson.fromJson(jsonReply, FoundReply.class);
		Assert.assertEquals(AbstractReply.RET_INVALUD_COORDINATES, reply.getReturnCode());
		Assert.assertTrue(reply.getDistanceDelta() / 1000.0 < 4.0);

		System.out.println("Delta = " + reply.getDistanceDelta() / 1000.0);
	}
}
