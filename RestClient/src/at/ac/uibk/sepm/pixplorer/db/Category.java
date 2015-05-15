package at.ac.uibk.sepm.pixplorer.db;

import java.io.Serializable;

/**
 * Hibernate entity class for the categories table. Each place has a category
 * to distinguish the different place types.
 * 
 * @author cbo
 */
public class Category implements Serializable {
	private int id;	
	private String name;

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

}
