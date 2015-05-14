package appjunkies.pixplorer.network;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import appjunkies.pixplorer.model.Place;

public class Search {

	/**
	 * @param args
	 */
	public static Place[] search(String search_str) {
		
		/*
		 * Order of objects: 1. String for searching Places
		 * 
		 */
						
		HttpPost request = new HttpPost(MyHttpClient.requestPath + "search");
		
		String json = JsonUtils.createJsonString(new Object[]{search_str});
		
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
