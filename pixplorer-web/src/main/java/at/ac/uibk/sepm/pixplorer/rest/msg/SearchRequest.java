package at.ac.uibk.sepm.pixplorer.rest.msg;

/**
 * Request for search call.
 * 
 * @author cbo
 */
public class SearchRequest extends AbstractRequest {
	/** client search filter */
	private String filter;
	
	public String getFilter() {
		return filter;
	}
	
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
}
