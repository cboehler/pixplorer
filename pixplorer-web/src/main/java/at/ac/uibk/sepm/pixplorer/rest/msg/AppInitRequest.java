package at.ac.uibk.sepm.pixplorer.rest.msg;

import at.ac.uibk.sepm.pixplorer.db.User;

/**
 * Request class for application initialisation.
 * 
 * @author cbo
 */
public class AppInitRequest extends AbstractRequest {
	private int option;
	
	/**
	 * Returns whether the user is a local or client
	 * @return user option
	 * @see User#TYPE_LOCAL
	 * @see User#TYPE_TOURIST
	 */
	public int getOption() {
		return option;
	}
	
	public void setOption(int option) {
		this.option = option;
	}
		
}
