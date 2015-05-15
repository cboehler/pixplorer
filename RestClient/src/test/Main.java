package test;

import java.io.IOException;
import java.util.List;

import at.ac.uibk.sepm.pixplorer.db.PixplorerHttpClient;
import at.ac.uibk.sepm.pixplorer.db.Place;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PixplorerHttpClient client = new PixplorerHttpClient("http://localhost:8080/pixplorer-web/rest/");
			
			List<Place> places = client.init("sampleuser@google.com", 1);
			
			for(Place p: places){
				System.out.println(p.getName());
			}
			
			places = client.explore("sampleuser@google.com");
			
			for(Place p: places){
				System.out.println(p.getName());
			}
			
//			client.favour("sampleuser@google.com", places[0]);
			
			places = client.found("sampleuser@google.com", places.get(1).getId(), 0.0d, 1.0d).getPlaces();
			
			for(Place p: places){
				System.out.println(p.getName());
			}
			
//			places = rest.Search.search("Place1");
//			
//			for(Place p: places){
//				System.out.println(p.getName());
//			}
			
			places = client.special("sampleuser@google.com");
			
			for(Place p: places){
				System.out.println(p.getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
