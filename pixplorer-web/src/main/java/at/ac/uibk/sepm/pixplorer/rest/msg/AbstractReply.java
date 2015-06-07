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
	
	/** user hasn't found this place */
	public static final int RET_INVALUD_COORDINATES = -3;
	
	/** for debug reasons */
	public static final int RET_VERY_EVIL = 666;
	
	/** return code for reply */
	private int returnCode = RET_OK;
	
	/**
	 * Returns the code for this reply.
	 * @return return code
	 */
	public int getReturnCode() {
		return returnCode;
	}
	
	/**
	 * Sets the code for this reply.
	 * @param returnCode - return code to set
	 */	
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	
	/**
	 * Handling of the server return code. An exception is thrown t oindicate that the
	 * server operation didn't execute as expected. The exception delivers the server
	 * return code as well as an appropriate error message to display.
	 * 
	 * @throws ReplyException
	 */
	public void checkReturnCode() throws ReplyException {
		if (returnCode == RET_OK) {
			return;
		}
		
		switch (returnCode) {
		case RET_PLACE_NOT_FOUND:
			throw new ReplyException(RET_PLACE_NOT_FOUND, "Place not found on server");
		case RET_USER_NOT_FOUND:
			throw new ReplyException(RET_USER_NOT_FOUND, "User not found on server");
		case RET_INVALUD_COORDINATES:
			throw new ReplyException(RET_INVALUD_COORDINATES, "GPS coordinates doesn't match the place");
		default:
			throw new ReplyException(returnCode, "Unknown return code");
		}
	}

}
