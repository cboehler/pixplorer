package at.ac.uibk.sepm.pixplorer.rest.util;

public enum UserType {
	
	TOURIST(1), LOCAL(2);
	
	int code ;
	
	private UserType(int c){
		this.code = c;
	}

	public int getcode(){
		return this.code;
	}
}
