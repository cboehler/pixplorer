package test;

import java.io.IOException;
import java.util.List;

import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.rest.PixplorerHttpClient;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* For using openshift*/
		PixplorerHttpClient client = new PixplorerHttpClient("http://newpixplorer-sepm.rhcloud.com/pixplorer-web/rest/");
		
		/*For using localhost*/
		//PixplorerHttpClient client = new PixplorerHttpClient("http://localhost:8080/pixplorer-web/rest/");
		
		try {
			List<Place> places = client.init("sampleuser@google.com", 1);

			for (Place p : places) {
				System.out.println(p.getName());
			}

			places = client.explore("sampleuser@google.com");

			for (Place p : places) {
				System.out.println(p.getName());
			}

			client.favourites("sampleuser@google.com", places.get(0).getId());

			places = client.found("sampleuser@google.com",
					places.get(1).getId(), 0.0d, 1.0d).getPlaces();

			for (Place p : places) {
				System.out.println(p.getName());
			}

			places = client.search("sampleuser@google.com", "Goldenes Dachl");

			for (Place p : places) {
				System.out.println(p.getName());
			}

			places = client.special("sampleuser@google.com");

			for (Place p : places) {
				System.out.println(p.getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
	}

}
