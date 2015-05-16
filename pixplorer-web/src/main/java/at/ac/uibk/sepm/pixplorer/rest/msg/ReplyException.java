package at.ac.uibk.sepm.pixplorer.rest.msg;

/**
 * Exception that is thrown if the request was not executed properly on
 * the server.
 * 
 * @author cbo
 */
public class ReplyException extends Exception {
	private final int returnCode;
	
	/**
	 * Constructor.
	 * 
	 * @param returnCode - return code from server
	 * @param message - message for exception
	 */
	public ReplyException(int returnCode, String message) {
		super(message);
		
		this.returnCode = returnCode;
	}
	
	public int getReturnCode() {
		return returnCode;
	}
}
