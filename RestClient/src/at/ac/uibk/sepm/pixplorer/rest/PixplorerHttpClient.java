package at.ac.uibk.sepm.pixplorer.rest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;

import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.rest.msg.AbstractRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.ExploreReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.ExploreRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.FavourRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.ReplyException;
import at.ac.uibk.sepm.pixplorer.rest.msg.SearchReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.SearchRequest;
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
	
	public List<Place> init(String googleId, int option) throws IOException, ReplyException {
		AppInitRequest request = new AppInitRequest();
		request.setGoogleId(googleId);
		request.setOption(option);

		HttpResponse response = sendRequest("init", request);
		try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());) {
			AppInitReply reply = gson.fromJson(reader, AppInitReply.class);
			reply.checkReturnCode();
			return reply.getPlaces(); 
		}
	}
	
	public List<Place> explore(String googleId) throws IOException, ReplyException {
		ExploreRequest request = new ExploreRequest();
		request.setGoogleId(googleId);

		HttpResponse response = sendRequest("explore", request);
		try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());) {
			ExploreReply reply = gson.fromJson(reader, ExploreReply.class);
			reply.checkReturnCode();
			return reply.getPlaces(); 
		}
	}	
	
	public void favourites(String googleId, int... favourites) throws IOException, ReplyException {
		FavourRequest request = new FavourRequest();
		request.setGoogleId(googleId);

		List<Integer> idList = new ArrayList<>();
		for (int i = 0; i < favourites.length; i++) {
			idList.add(i);
		}
		request.setFavourites(idList);
		
		HttpResponse response = sendRequest("favour", request);
		try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());) {
			ExploreReply reply = gson.fromJson(reader, ExploreReply.class);
			reply.checkReturnCode();
		}
	}	

	public FoundReply found(String googleId, int placeId, double longitude, double latitude) throws IOException, ReplyException {
		FoundRequest request = new FoundRequest();
		request.setGoogleId(googleId);
		request.setFoundPlace(placeId);
		request.setLatitude(latitude);
		request.setLongitude(longitude);

		HttpResponse response = sendRequest("found", request);
		try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());) {
			FoundReply reply = gson.fromJson(reader, FoundReply.class);
			reply.checkReturnCode();
			return reply; 
		}
	}	
		
	public List<Place> search(String googleId, String filter) throws IOException, ReplyException {
		SearchRequest request = new SearchRequest();
		request.setGoogleId(googleId);
		request.setFilter(filter);
		
		HttpResponse response = sendRequest("search", request);
		try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());) {
			SearchReply reply = gson.fromJson(reader, SearchReply.class);
			reply.checkReturnCode();
			return reply.getPlaces(); 
		}
	}		
	
	public List<Place> special(String googleId) throws IOException, ReplyException {
		SpecialRequest request = new SpecialRequest();
		request.setGoogleId(googleId);

		HttpResponse response = sendRequest("special", request);
		try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());) {
			SpecialReply reply = gson.fromJson(reader, SpecialReply.class);
			reply.checkReturnCode();
			
			return reply.getPlaces(); 
		}
	}		
	
	
	private HttpResponse sendRequest(String function, AbstractRequest request) throws IOException, ReplyException {
		HttpPost post = new HttpPost(url + function);
		post.setHeader("Content-type", "application/json");
		post.setEntity(new StringEntity(gson.toJson(request)));
		
		return client.execute(post);
	}
}
