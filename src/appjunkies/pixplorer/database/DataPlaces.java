package appjunkies.pixplorer.database;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import appjunkies.pixplorer.model.Place;

/**
 * @author appjunkies
 *
 */
public class DataPlaces {

	public Activity activity;

	// Database fields
	private SQLiteDatabase database;
	private Database dbHelper;
	private String[] allColumns = { Database.PRIME_ID, Database.NAME,
			Database.LAST_ID };

	/**
	 * @param callingActivity
	 */
	public DataPlaces(Activity callingActivity) {
		dbHelper = new Database(callingActivity);
		this.activity = callingActivity;
	}

	/**
	 * open the DatabaseConnection
	 * 
	 * @throws SQLExceptions
	 */
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	/**
	 * close the DatabaseConnection
	 */
	public void close() {
		dbHelper.close();
		database.close();
	}

	// ______________________________________________________________________________________________________________________
	// *********************************************************************************************************************
	// Last Places
	// *********************************************************************************************************************
	// ______________________________________________________________________________________________________________________

	// Adding Place
	/**
	 * add the Places from current Session to Database to load the same Places
	 * the next time the user opens the app
	 * 
	 * @param place
	 */
	public void addPlace(Place place) {

		ContentValues values = new ContentValues();
		values.put(Database.NAME, place.getName());
		values.put(Database.LAST_ID, place.getId());
		// Inserting Row
		database.insert(Database.TABLE_LASTLIST, null, values);

	}

	// Edit existing Place
	/**
	 * edit the Place in the List. At the moment not neccessary for the
	 * behaviour of the App
	 * 
	 * @param place
	 * @param primeID
	 */
	public void editPlace(Place place, int primeid) {

		ContentValues values = new ContentValues();
		values.put(Database.NAME, place.getName());
		values.put(Database.LAST_ID, place.getId());

		// editing Row
		database.update(Database.TABLE_LASTLIST, values, Database.PRIME_ID
				+ "=" + primeid, null);

	}

	// Delete Row
	/**
	 * delete the Place out of the List for example if the User found it.
	 * 
	 * @param primid
	 */
	public void deleteRow(int primeid) {
		database.delete(Database.TABLE_LASTLIST, Database.PRIME_ID + "="
				+ primeid, null);

	}

	/**
	 * gets all Places from last Session to send the IDs to the Server and Query
	 * the Content (Picture,Wiki,Points).
	 * 
	 * @return placelist
	 */
	public List<Place> getLastPlaces() {
		List<Place> List = new ArrayList<Place>();
		// Select All Query
		String selectQuery = "Select * FROM " + Database.TABLE_LASTLIST;

		Cursor cursor = database.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Place p = new Place();
				p.setName(cursor.getString(1));
				p.setId(cursor.getInt(2)); // INFO ID of Place = PrimeID on
											// Server
				// Adding to list
				List.add(p);

			} while (cursor.moveToNext());
			// List.toString();
		}
		// return list
		return List;
	}

	// Getting Place Count
	/**
	 * gets the Count of Last Places. Normally 10.
	 * 
	 * @return count
	 */
	public int getPlaceCount() {
		String countQuery = "SELECT  * FROM " + Database.TABLE_LASTLIST;

		Cursor cursor = database.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		// return count
		return count;
	}

	// ______________________________________________________________________________________________________________________
	// *********************************************************************************************************************
	// Favorite Places
	// *********************************************************************************************************************
	// ______________________________________________________________________________________________________________________

	// Adding new Favorite
	/**
	 * add the Place to Favorites.
	 * 
	 * @param place
	 */
	public void addFavoritePlace(Place place) {

		ContentValues values = new ContentValues();
		values.put(Database.FAV_PLACE_ID, place.getId());
		// Inserting Row
		database.insert(Database.TABLE_FAVORITE, null, values);

	}

	// Edit Favorite Place
	/**
	 * edit Favorites
	 * 
	 * @param place
	 * @param fav_place_id
	 */
	public void editFavoritePlace(Place place, int fav_place_id) {
		ContentValues values = new ContentValues();
		values.put(Database.FAV_PLACE_ID, place.getId());

		// editing Row
		database.update(Database.TABLE_FAVORITE, values, Database.FAV_PLACE_ID
				+ "=" + fav_place_id, null);

	}

	// Delete from Favorite
	/**
	 * delete the Place from Favorites.
	 * 
	 * @param fav_place_id
	 */
	public void deleteFavorite(int fav_place_id) {
		database.delete(Database.TABLE_FAVORITE, Database.FAV_PLACE_ID + "="
				+ fav_place_id, null);

	}

	/**
	 * get all Favorites.
	 * 
	 * @return placelist
	 */
	public List<Place> getFavorites() {
		List<Place> List = new ArrayList<Place>();
		// Select All Query
		String selectQuery = "Select * FROM " + Database.TABLE_FAVORITE;

		Cursor cursor = database.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Place p = new Place();
				p.setId(cursor.getInt(1)); // INFO ID of Place = PrimeID on
											// Server
				// Adding to list
				List.add(p);

			} while (cursor.moveToNext());
			// List.toString();
		}
		// return list
		return List;
	}

	// Getting Place Count
	/**
	 * get the amount of Favorite Places
	 * 
	 * @return count
	 */
	public int getFavoriteCount() {
		String countQuery = "SELECT  * FROM " + Database.TABLE_FAVORITE;

		Cursor cursor = database.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		// return count
		return count;
	}

	// ______________________________________________________________________________________________________________________
	// *********************************************************************************************************************
	// MY Found Places
	// *********************************************************************************************************************
	// ______________________________________________________________________________________________________________________

	// Add new found Place
	/**
	 * add the Place that was currently found.
	 * 
	 * @param place
	 */
	public void donePlace(Place place) {

		ContentValues values = new ContentValues();
		values.put(Database.NAME, place.getName());
		values.put(Database.PLACE_ID, place.getId());
		// Inserting Row
		database.insert(Database.TABLE_MYPLACES, null, values);

	}

	// Edit Found Place
	/**
	 * edit the Place
	 * 
	 * @param place
	 * @param primeId
	 */
	public void editDonePlace(Place place, int primeid) {
		ContentValues values = new ContentValues();
		values.put(Database.NAME, place.getName());
		values.put(Database.PLACE_ID, place.getId());

		// editing Row
		database.update(Database.TABLE_MYPLACES, values, Database.PRIME_ID
				+ "=" + primeid, null);

	}

	// Delete
	/**
	 * Delete a Place that was Found
	 * 
	 * @param primeId
	 */
	public void deleteDonePlace(int primeid) {
		database.delete(Database.TABLE_MYPLACES, Database.PRIME_ID + "="
				+ primeid, null);

	}

	/**
	 * get all Places that the User found to Display them in the Profilactivity.
	 * 
	 * @return placelist
	 */
	public List<Place> getAllMYplaces() {
		List<Place> List = new ArrayList<Place>();
		// Select All Query
		String selectQuery = "Select * FROM " + Database.TABLE_MYPLACES;

		Cursor cursor = database.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Place p = new Place();
				p.setName(cursor.getString(1));
				p.setId(cursor.getInt(2)); // INFO ID of Place = PrimeID on
											// Server
				// Adding to list
				List.add(p);

			} while (cursor.moveToNext());
			// List.toString();
		}
		// return list
		return List;
	}

	// Getting Place Count
	/**
	 * how much places have been found
	 * 
	 * @return count
	 */
	public int getAllMyPlaceCount() {
		String countQuery = "SELECT  * FROM " + Database.TABLE_MYPLACES;

		Cursor cursor = database.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		// return count
		return count;
	}
}
