package at.ac.uibk.sepm.pixplorer.rest.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import at.ac.uibk.sepm.pixplorer.db.Category;
import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;

import com.google.gson.Gson;

public class RandomPlaceGenerator {

	Gson gson = new Gson();
	Random random = new Random();	
	
	/**
	 * 
	 * @param id_list
	 * @param amount (specifies the amount of Places to return)
	 * @return
	 */
	
	public List<Place> getPlaces(User user, Integer amount){
		return this.getPlaces(user, amount, false);
	}
	
	public List<Place> getPlaces(User user,Integer amount, boolean special){
		
		
		List<Place> places = null;
		
		//Determine if User plays as Tourist -> Show only Places of Category Sights then
		if(user.getType() == User.TYPE_TOURIST){
			String filter = "where x.name = 'SIGHT'";
			Category cat = PersistenceManager.get(Category.class, filter).get(0);
			filter = "where x.category = '" + cat.getId() + "'";
			places = PersistenceManager.get(Place.class,filter);
		}	
		
		Set<Place> excludeSet = user.getFavourites();
		excludeSet.addAll(user.getFoundPlaces());
		
		List<Integer> id_list = new ArrayList<Integer>();
		
		for(Place p: excludeSet)
			id_list.add(p.getId());
		
		if(places == null)
			places =  PersistenceManager.getAll(Place.class);
		
		List<Place> ret_places = new ArrayList<>();
		Integer place_id;
		
		for(int i = 0; i< amount ; i++){
			
			if(places.size() == 0)
				break;
			
			place_id = random.nextInt(places.size());
			Place temp = places.get(place_id);	
			
			/*Get a new place while User has id in its favour or found places or place was already picked*/
			while(temp.isFeatured() != special || id_list.contains(temp.getId())){
				places.remove(places.get(place_id));
				
				if(places.size() == 0)
					break;				
				
				place_id = random.nextInt(places.size());
				temp = places.get(place_id);
			}
			
			ret_places.add(temp);
			
			if (!places.isEmpty()) {
				places.remove(places.get(place_id));
			}
		}
		
		//System.out.println(places.size());
		
		return ret_places;
	}
	

}
