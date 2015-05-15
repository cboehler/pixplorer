package at.ac.uibk.sepm.pixplorer.rest.msg;

public class FoundRequest extends AbstractRequest {
	private int foundPlace;
	private double longitude;
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
