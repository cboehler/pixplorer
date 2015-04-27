package appjunkies.pixplorer.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import appjunkies.pixplorer.model.Place;

import com.google.gson.Gson;



public class Client {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnsupportedOperationException 
	 */
	public Place getPlace() throws UnsupportedOperationException, IOException {
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://pixplorer-sepm.rhcloud.com/RestServer/rest/todo");
		HttpResponse response = client.execute(request);

		// Get the response
		BufferedReader rd = new BufferedReader
		  (new InputStreamReader(response.getEntity().getContent()));
		    
		String line = "";
		try {
			line = rd.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		rd.close();
		
		Gson gson = new Gson();
		Place place = gson.fromJson(line, Place.class);
		
		return place;


		

	}
	
//	public static void main(String[] args) throws UnsupportedOperationException, IOException{
//		Client client = new Client();
//		System.out.println(client.getPlace());
//	}

}
