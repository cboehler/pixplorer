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
		
		//Determine if User plays as Tourist -> Consider only Places of Category Sights then
		if(user.getType() == User.TYPE_TOURIST){
			String filter = "where x.name = 'SIGHT'";
			Category cat = PersistenceManager.get(Category.class, filter).get(0);
			filter = "where x.category = " + cat.getId();
			places = PersistenceManager.get(Place.class,filter);
		}	
				
		//If User plays as Local, all Places must be considered
		if(places == null)
			places =  PersistenceManager.getAll(Place.class);
		
		//Get Places the User has already marked as favored or even found to exclude them 
		Set<Place> excludeSet = user.getFavourites();
		excludeSet.addAll(user.getFoundPlaces());
		
		//Create List of Places to return
		List<Place> ret_places = new ArrayList<>();
		
		//Fill return-list by iterating as long as its full or no Places are left
		for(int i = 0; i< amount ; i++){
			
			if(places.size() == 0)
				break;
			
			Place temp = places.get(random.nextInt(places.size()));	
			
			/*Get a new place while User has id in its favour or found places or place was already picked*/
			while(temp.isFeatured() != special || excludeSet.contains(temp)){
				places.remove(temp);
				
				if(places.isEmpty())
					break;				
				
				temp = places.get(random.nextInt(places.size()));
			}
			
			if (!places.isEmpty()) {
				ret_places.add(temp);
				places.remove(temp);
			}
			
			
		}
		return ret_places;
	}
	

}
