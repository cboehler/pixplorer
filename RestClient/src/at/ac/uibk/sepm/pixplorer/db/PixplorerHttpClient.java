package at.ac.uibk.sepm.pixplorer.db;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;

import at.ac.uibk.sepm.pixplorer.rest.msg.AbstractRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.ExploreReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.ExploreRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.SpecialReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.SpecialRequest;

public class PixplorerHttpClient {
	private static final Gson gson = new Gson();
	
	private String url;
	private final CloseableHttpClient client;
	
	public PixplorerHttpClient(String url) {
		this.url = url;
		
		if (!this.url.endsWith("/")) {
			this.url += "/";
		}

		this.client = HttpClientBuilder.create().build();
	}
	
	public void close() {
		if (this.client != null) {
			try {
				client.close();
			} catch (IOException e) {
				// ignored.. nothing more to do if the connection cannot be closed
			}
		}
	}
	
	public List<Place> init(String googleId, int option) throws IOException {
		AppInitRequest request = new AppInitRequest();
		request.setGoogleId(googleId);
		request.setOption(option);

		HttpResponse response = sendRequest("init", request);
		try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());) {
			AppInitReply reply = gson.fromJson(reader, AppInitReply.class);
			return reply.getPlaces(); 
		}
	}
	
	public List<Place> explore(String googleId) throws IOException {
		ExploreRequest request = new ExploreRequest();
		request.setGoogleId(googleId);

		HttpResponse response = sendRequest("explore", request);
		try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());) {
			ExploreReply reply = gson.fromJson(reader, ExploreReply.class);
			return reply.getPlaces(); 
		}
	}	
	
	public FoundReply found(String googleId, int placeId, double longitude, double latitude) throws IOException {
		FoundRequest request = new FoundRequest();
		request.setGoogleId(googleId);
		request.setFoundPlace(placeId);
		request.setLatitude(latitude);
		request.setLongitude(longitude);

		HttpResponse response = sendRequest("found", request);
		try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());) {
			FoundReply reply = gson.fromJson(reader, FoundReply.class);
			return reply; 
		}
	}	
		
	public List<Place> special(String googleId) throws IOException {
		SpecialRequest request = new SpecialRequest();
		request.setGoogleId(googleId);

		HttpResponse response = sendRequest("special", request);
		try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());) {
			SpecialReply reply = gson.fromJson(reader, SpecialReply.class);
			return reply.getPlaces(); 
		}
	}		
	
	
	private HttpResponse sendRequest(String function, AbstractRequest request) throws IOException {
		HttpPost post = new HttpPost(url + function);
		post.setHeader("Content-type", "application/json");
		post.setEntity(new StringEntity(gson.toJson(request)));
		
		return client.execute(post);
	}
}
