package test;

import java.io.IOException;
import java.util.List;

import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.rest.PixplorerHttpClient;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInfoReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.FoundReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.ReplyException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PixplorerHttpClient client = new PixplorerHttpClient("http://newpixplorer-sepm.rhcloud.com/pixplorer-web/rest/", "sample@google.com");

		//PixplorerHttpClient client = new PixplorerHttpClient("http://localhost:8080/pixplorer-web/rest/", "sampleuser1@google.com");
		
		try {
			List<Place> initPlaces = client.init(1);
			
			/*for (Place p : initPlaces) {
				System.out.println(p.getName()+ "\t" + p.getCategory().getName());
			}*/
			
			List<Place> places = client.special();
			
			

			for (Place p : places) {
				System.out.println(p.getName()+ "\t" + p.getCategory().getName());
			}
			

			/*for(int i = 0; i <10 ; i++){

				System.out.println("----------------Round"+(i+1)+"-------------------------");
						
			initPlaces = client.explore();
			
			for (Place p : initPlaces) {
				System.out.println(p.getName() + "\t" + p.getCategory().getName());
				client.found(p.getId(), p.getGpsData().getLongitude(), p.getGpsData().getLatitude());
			}
			

			System.out.println("-------------------------------------------------");
			System.out.println();
			}*/
			
			/*List<Place> searched = client.search("Museum");
			for(Place p : searched)
				System.out.println(p.getName() + "\t" + p.getPicture());*/
			
			/*AppInfoReply reply = client.getAppInfo();
			System.out.println(reply.getAmountOfPlaces());
			
			Place place = initPlaces.get(1);
			FoundReply found_reply = client.found(place.getId(), place.getGpsData().getLongitude(), place.getGpsData().getLatitude());
			System.out.println(found_reply.isFound());
			reply = client.getAppInfo();
			System.out.println(reply.getAmountOfPlaces());

			/*List<Place>places = client.explore();

			for (Place p : places) {
				System.out.println(p.getName());
			}

			/*client.favourites(initPlaces.get(0).getId());

			Place place = initPlaces.get(1);
			places = client.found(place.getId(), place.getGpsData().getLongitude(), place.getGpsData().getLatitude()).getPlaces();

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
			}*/
		} catch (IOException | ReplyException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
	}

}
