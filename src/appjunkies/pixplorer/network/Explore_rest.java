package appjunkies.pixplorer.network;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import appjunkies.pixplorer.model.Place;



public class Explore_rest {

	/**
	 * @param args
	 */
	public static Place[] explore (String googleid){
		
		/*
		 * Order of objects: 1. String "username" 
		 * 	
		 */
		
		HttpPost request = new HttpPost(MyHttpClient.requestPath + "explore");	
		
		String json = JsonUtils.createJsonString(new Object[]{googleid});
		
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
