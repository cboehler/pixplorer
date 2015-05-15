package at.ac.uibk.sepm.pixplorer.rest.msg;

/**
 * Abstract class for all REST replies. Every reply contains a return code to indicate whether
 * the request was process successfully or not. If the return code isn't <code>RET_OK</code> the
 * reply doesn't contain any valid data and thus have to be ignored.
 * 
 * @author cbo
 */
public abstract class AbstractReply {
	/** request OK */
	public static final int RET_OK = 0;
	
	/** passed user was not found in the database */
	public static final int RET_USER_NOT_FOUND = -1;

	/** passed user was not found in the database */
	public static final int RET_PLACE_NOT_FOUND = -2;	
	
	/** return code for reply */
	private int returnCode = RET_OK;
	
	public int getReturnCode() {
		return returnCode;
	}
	
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

}
