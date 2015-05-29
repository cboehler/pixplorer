package at.ac.uibk.sepm.pixplorer.rest.msg;

/**
 * Reply for application information.
 * 
 * @author cbo
 */
public class AppInfoReply extends AbstractReply {
	
	public Integer amountOfPlaces;

	public int getAmountOfPlaces() {
		return amountOfPlaces;
	}

	public void setAmountOfPlaces(int amountOfPlaces) {
		this.amountOfPlaces = amountOfPlaces;
	}
}
