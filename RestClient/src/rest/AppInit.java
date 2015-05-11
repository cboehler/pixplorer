package rest;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.google.gson.Gson;

import database.Place;

public class AppInit {
	
	static Gson gson = new Gson();

	/**
	 * @param args
	 */
	public static Place[] init (String googleid, Integer option){
		
		/*Order of objects: 1. Integer option ( Determines whether User is playing as tourist or local )	
		 * 					2. String username ( Email of User's Google Account )
		 */
		
		String json = JsonUtils.createJsonString(new Object[]{option,googleid});
		
		HttpPost request = new HttpPost(MyHttpClient.requestPath + "init");	
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
