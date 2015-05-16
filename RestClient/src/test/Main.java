package test;

import java.io.IOException;
import java.util.List;

import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.rest.PixplorerHttpClient;
import at.ac.uibk.sepm.pixplorer.rest.msg.ReplyException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PixplorerHttpClient client = new PixplorerHttpClient("http://localhost:8080/pixplorer-web/rest/", "sampleuser@google.com");
		
		try {
			List<Place> places = client.init(1);

			for (Place p : places) {
				System.out.println(p.getName());
			}

			places = client.explore();

			for (Place p : places) {
				System.out.println(p.getName());
			}

			client.favourites(places.get(0).getId());

			places = client.found(places.get(1).getId(), 0.0d, 1.0d).getPlaces();

			for (Place p : places) {
				System.out.println(p.getName());
			}

			places = client.search("Goldenes Dachl");

			for (Place p : places) {
				System.out.println(p.getName());
			}

			places = client.special();

			for (Place p : places) {
				System.out.println(p.getName());
			}
		} catch (IOException | ReplyException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
	}

}
