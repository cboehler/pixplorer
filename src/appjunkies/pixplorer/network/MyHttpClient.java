package appjunkies.pixplorer.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import appjunkies.pixplorer.model.Place;



public class MyHttpClient {	

	static String requestPath = "http://pixplorer-sepm.rhcloud.com/pixplorer-web/rest/";	
	
	public static Place[] createPostRequest(HttpPost request) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
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
				Object[] objects = JsonUtils.getObjectsFromJson(line);
				Place[] place_return = new Place[objects.length];
				for(int i = 0;i<objects.length;i++){
					place_return[i] = Place.class.cast(objects[i]);
				}
				return place_return;
	}
	
//	public static void main(String[] args) throws UnsupportedOperationException, IOException{
//		
//		//Test all client methods
//		Client client = new Client();
//		Place[] places = client.init("1@google.com", 0);
//		System.out.println(places.length + "\t" + places);		
//		
//		//Letter ü doesnt work so far
//		places = client.search("Place1");
//		System.out.println(places);
//		
//		places = client.search("category1");
//		System.out.println(places);
//		
//		places = client.getRandom("1@google.com");
//		System.out.println(places.length + "\t" + places);
//		
//		/*Note that server has no information about random objectes showed in clients-app, hence its possible to get a place which is currently shown by app*/
//		places = client.found(places[0], "1@google.com");
//		System.out.println(places);		
		
//		MyHttpClient client = new MyHttpClient();
//		
//		
//		Place[] places = client.getRandom("user1@google.com");
//		String out = "";
//		for(Place p:places){
//			out += p + "\t";
//		}
//		System.out.println(out);
//		
//		places = client.found(places[0], "user0@google.com");
//		out = "";
//		for(Place p:places){
//			out += p + "\t";
//		}
//		System.out.println(out);
//		
//		
//	}

}
