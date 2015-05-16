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
import at.ac.uibk.sepm.pixplorer.rest.msg.FavourReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FavourRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.ReplyException;
import at.ac.uibk.sepm.pixplorer.rest.msg.SearchReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.SearchRequest;
import at.ac.uibk.sepm.pixplorer.rest.msg.SpecialReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.SpecialRequest;

/**
 * Class that handles the server communication. A connection string and a google user
 * id have to be specified. Every method represents one single REST method on the
 * Pixplorer server.
 * <p>
 * The methods throw an {@link IOException} if the communication fails or an {@link ReplyException} if
 * the reqeust could not be executed properly on the server.
 * 
 * @author cbo
 *
 */
public class PixplorerHttpClient {
	/** gson reference to parse/stringify the JSON objects */
	private static final Gson gson = new Gson();
	
	/** server url including ip, port and rest path */
	private final String url;
	
	/** google id of the user playing pixplorer */
	private final String googleId;
	
	/** apache http client class to send the requests */
	private CloseableHttpClient client;
	
	/**
	 * Constructor.
	 * 
	 * @param url - url to pixplorer server location e.g. localhost:8080/pixplorer/rest
	 * @param googleId - google id of the user
	 */
	public PixplorerHttpClient(String url, String googleId) {
		this.url = url;
		this.googleId = googleId;
		
		// user builder to create a new http client
		HttpClientBuilder builder = HttpClientBuilder.create();
		try {
			this.client = builder.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the http connection. This method should be called if the app is closed.
	 */
	public void close() {
		if (this.client != null) {
			try {
				client.close();
			} catch (IOException e) {
				// ignored.. nothing more to do if the connection cannot be closed
			}
		}
	}
	
	public List<Place> init(int option) throws IOException, ReplyException {
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
	
	public List<Place> explore() throws IOException, ReplyException {
		ExploreRequest request = new ExploreRequest();
		request.setGoogleId(googleId);

		HttpResponse response = sendRequest("explore", request);
		try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());) {
			ExploreReply reply = gson.fromJson(reader, ExploreReply.class);
			reply.checkReturnCode();
			return reply.getPlaces(); 
		}
	}	
	
	public void favourites(int... favourites) throws IOException, ReplyException {
		FavourRequest request = new FavourRequest();
		request.setGoogleId(googleId);

		List<Integer> idList = new ArrayList<>();
		for (int i = 0; i < favourites.length; i++) {
			idList.add(favourites[i]);
		}
		request.setFavourites(idList);
		
		HttpResponse response = sendRequest("favour", request);
		try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());) {
			FavourReply reply = gson.fromJson(reader, FavourReply.class);
			reply.checkReturnCode();
		}
	}	

	public FoundReply found(int placeId, double longitude, double latitude) throws IOException, ReplyException {
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
		
	public List<Place> search(String filter) throws IOException, ReplyException {
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
	
	public List<Place> special() throws IOException, ReplyException {
		SpecialRequest request = new SpecialRequest();
		request.setGoogleId(googleId);

		HttpResponse response = sendRequest("special", request);
		try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());) {
			SpecialReply reply = gson.fromJson(reader, SpecialReply.class);
			reply.checkReturnCode();
			
			return reply.getPlaces(); 
		}
	}		
	
	/**
	 * Method to send a http post request i.e. invoke a REST function.
	 * 
	 * @param function - function name that should be called
	 * @param request - request containing the needed information
	 * @return an HttpResponse containing the answer from the server
	 * @throws IOException if the server communication fails
	 */
	private HttpResponse sendRequest(String function, AbstractRequest request) throws IOException {
		HttpPost post = new HttpPost(url + function);
		post.setHeader("Content-type", "application/json");
		post.setEntity(new StringEntity(gson.toJson(request)));
		
		return client.execute(post);
	}
}
