package at.ac.uibk.sepm.pixplorer.rest.msg;

public class SearchRequest extends AbstractRequest {
	private String filter;
	
	public String getFilter() {
		return filter;
	}
	
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
}
