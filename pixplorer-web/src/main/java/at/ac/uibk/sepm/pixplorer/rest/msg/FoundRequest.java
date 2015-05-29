package at.ac.uibk.sepm.pixplorer.rest.msg;

/**
 * Request for found call.
 * 
 * @author cbo
 */
public class FoundRequest extends AbstractRequest {
	/** id of the found place */
	private int foundPlace;
	/** gps longitude */
	private double longitude;
	/** gps latitude */
	private double latitude;
	
	public int getFoundPlace() {
		return foundPlace;
	}
	
	public void setFoundPlace(int foundPlace) {
		this.foundPlace = foundPlace;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
}
