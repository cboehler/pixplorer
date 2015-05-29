package at.ac.uibk.sepm.pixplorer.rest.msg;

/**
 * Abstract class for all rest requests. Every request contains the
 * google id of the user to make sure if this user is allowed to execute
 * the method.
 * 
 * @author cbo
 */
public abstract class AbstractRequest {
	/** google id */
	private String googleId;
	
	public String getGoogleId() {
		return googleId;
	}
	
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}	
}
