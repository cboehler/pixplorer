package rest;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import database.Place;

public class Found {

	/**
	 * @param args
	 */
	public static Place[] found(Place p, String googleid){
		
		/*
		 * Protocol order: 1. String "username"
		 * 				   2. Place "found place"
		 */		
		
		Object[] objects = new Object[]{googleid,p};
		String json = JsonUtils.createJsonString(objects);
		
		HttpPost request = new HttpPost(MyHttpClient.requestPath + "found");	
		try {
			request.setEntity(new StringEntity(json));
			request.setHeader("Content-type", "application/json");
			return MyHttpClient.createPostRequest(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}		

}
