package at.ac.uibk.sepm.pixplorer.db;

import java.io.Serializable;


/**
 * Hibernate entity class for the trophies table. A trophy is a reward a user
 * can achieve. The trophy contains a SQL statement that specifies the conditions
 * for this trophy. 
 * <p>
 * It stores the Google Play trophy id as well. This id will be 
 * returned to the Android client if a user has achived it and will then be forwarded
 * to Google. 
 * 
 * @author cbo
 */

public class Trophy implements Serializable {
	private int id;
	private String name;
	private String description;
	private String code;

	public int getId() {
		return id;
	}
    
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
