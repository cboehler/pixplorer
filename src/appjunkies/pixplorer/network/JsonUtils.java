package appjunkies.pixplorer.network;

import java.util.HashMap;
import java.util.Map;

import appjunkies.pixplorer.model.Place;

import com.google.gson.Gson;


public class JsonUtils {
	
	static Gson gson = new Gson();
	
	static Map <String,Object> map = new HashMap<String,Object>();
	
	static{
		map.put(String.class.getSimpleName(), new String() );
		map.put(String[].class.getSimpleName(), new String[0]);
		map.put(Integer.class.getSimpleName(), new Integer(0) );
		map.put(Integer[].class.getSimpleName(), new Integer[0] );
		map.put(Place.class.getSimpleName(), new Place() );
		map.put(Place[].class.getSimpleName(), new Place[0]);
	}
	
	public static String createJsonString(Object[] objects){
		
		String[] jsonElements = new String[objects.length+1];			
		String[] classes = new String[objects.length];
				
		for(int i = 0;i<objects.length;i++){			
			classes[i]=objects[i].getClass().getSimpleName();
			jsonElements[i+1] = gson.toJson(objects[i]);			
		}
		
		jsonElements[0] = gson.toJson(classes);
		
		return gson.toJson(jsonElements);
	}
	
	public static Object[] getObjectsFromJson(String json){
		
		String[] jsonElements = gson.fromJson(json, String[].class);
		String[] classes = gson.fromJson(jsonElements[0],String[].class);
		Object[] objects = new Object[classes.length];
		
		for(int i = 0; i< classes.length;i++ ){
			objects[i] = gson.fromJson(jsonElements[i+1],map.get(classes[i]).getClass());
		} 
		
		return objects;
	}
	
	public static void main(String[] args){
	}
	

}
