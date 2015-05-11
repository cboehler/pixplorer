package test;

import database.Place;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Place[] places = rest.AppInit.init("sampleuser@google.com", 1);
		
		for(Place p: places){
			System.out.println(p.getName());
		}
		
		places = rest.Explore.explore("sampleuser@google.com");
		
		for(Place p: places){
			System.out.println(p.getName());
		}
		
		rest.Favour.favour("sampleuser@google.com", places[0]);
		
		places = rest.Found.found(places[1], "sampleuser@google.com");
		
		for(Place p: places){
			System.out.println(p.getName());
		}
		
		places = rest.Search.search("Place1");
		
		for(Place p: places){
			System.out.println(p.getName());
		}
		
		places = rest.Special.getSpecialPlaces("sampleuser@google.com");
		
		for(Place p: places){
			System.out.println(p.getName());
		}
		

	}

}
