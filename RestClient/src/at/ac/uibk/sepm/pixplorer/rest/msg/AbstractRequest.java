package at.ac.uibk.sepm.pixplorer.rest.msg;

public abstract class AbstractRequest {
	private String googleId;
	
	public String getGoogleId() {
		return googleId;
	}
	
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}	
}
