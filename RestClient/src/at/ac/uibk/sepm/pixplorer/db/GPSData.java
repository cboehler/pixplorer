package at.ac.uibk.sepm.pixplorer.db;

import java.io.Serializable;


/**
 * Hibernate entity class for the gpsdata table. Each place has its unique gps 
 * coordinates. The gps data are used to determine if a user has found a place
 * or not.
 * 
 * @author cbo
 */
public class GPSData implements Serializable {
	private int id;
	private double longitude;	
	private double latitude;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

}
