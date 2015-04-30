package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;



public class Client {
	
	static String requestPath = "http://pixplorer-sepm.rhcloud.com/pixplorer/rest/";
	
	public class json_found{
		
		private String googleid;
		private Place place;
		
		public json_found(String googleid, Place place){
			this.googleid = googleid;
			this.place = place;
		}
		
		public String getGoogleid() {
			return googleid;
		}

		public void setGoogleid(String googleid) {
			this.googleid = googleid;
		}

		public Place getPlace() {
			return place;
		}

		public void setPlace(Place place) {
			this.place = place;
		}

	}
	
	public class json_init{
		String googleid;
		Integer option;
		
		public json_init(String googleid, Integer option){
			this.googleid = googleid;
			this.option = option;
		}
		
		public String get_googleid(){
			return this.googleid;
		}
		
		public Integer get_option(){
			return this.option;
		}
		
		public void set_googleid(String googleid){
			this.googleid = googleid;
		}
		
		public void set_option(Integer option){
			this.option = option;
		}
	}
	
	public Place[] found(Place p, String googleid){
		Gson gson = new Gson();
		json_found obj = new json_found(googleid,p);
		
		HttpPost request = new HttpPost(this.requestPath + "Found");	
		try {
			request.setEntity(new StringEntity(gson.toJson(obj)));
			request.setHeader("Content-type", "application/json");
			return createPostRequest(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}		
	
	public Place[] init (String googleid, Integer option){
		Gson gson = new Gson();
		json_init obj = new json_init(googleid,option);
		HttpPost request = new HttpPost(this.requestPath + "Init");	
		try {
			request.setEntity(new StringEntity(gson.toJson(obj)));
			request.setHeader("Content-type", "application/json");
			return createPostRequest(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Place[] getRandom (String googleid){
		
		Gson gson = new Gson();
		
		HttpPost request = new HttpPost(this.requestPath + "Random");	
		
		try {
			request.setEntity(new StringEntity(gson.toJson(googleid)));
			request.setHeader("Content-type", "application/json");
			return createPostRequest(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	 
	public Place[] search(String search_str) {
		
		Gson gson = new Gson();
				
		HttpPost request = new HttpPost(this.requestPath + "Suche");				
		
		try {
			request.setEntity(new StringEntity(gson.toJson(search_str)));
			request.setHeader("Content-type", "application/json");
			return createPostRequest(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
			

	}
	
	public Place[] createPostRequest(HttpPost request) throws ClientProtocolException, IOException{
		Gson gson = new Gson();
		CloseableHttpClient client = new DefaultHttpClient();
		HttpResponse post_response  = client.execute(request);
		
		BufferedReader rd = new BufferedReader
				  (new InputStreamReader(post_response.getEntity().getContent()));
				    
				String line = "";
				try {
					line = rd.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				rd.close();
				System.out.println(line);
				Place[] place_return = gson.fromJson(line, Place[].class);
				client.close();
				return place_return;
	}
	
	public static void main(String[] args) throws UnsupportedOperationException, IOException{
		
		//Test all client methods
		Client client = new Client();
		Place[] places = client.init("1@google.com", 0);
		System.out.println(places.length + "\t" + places);		
		
		//Letter ü doesnt work so far
		places = client.search("Sehenswurdigkeit1");
		System.out.println(places);
		
		places = client.search("category1");
		System.out.println(places);
		
		places = client.getRandom("1@google.com");
		System.out.println(places.length + "\t" + places);
		
		/*Note that server has no information about random objectes showed in clients-app, hence its possible to get a place which is currently shown by app*/
		places = client.found(places[0], "1@google.com");
		System.out.println(places);		
		
		
	}

}
