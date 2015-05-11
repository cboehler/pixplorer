package rest;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import database.Place;

public class Favour {

	/**
	 * @param args
	 */
	public static void favour(String username, Place favoured){
	/*
	 * Protocol order: 1. String "username"
	 * 				   2. Place "favoured place"
	 */		
	
	Object[] objects = new Object[]{username,favoured};
	String json = JsonUtils.createJsonString(objects);
	
	HttpPost request = new HttpPost(MyHttpClient.requestPath + "favour");	
	try {
		request.setEntity(new StringEntity(json));
		request.setHeader("Content-type", "application/json");
		MyHttpClient.createPostRequest(request);
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}		

}
