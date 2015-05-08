package at.ac.uibk.sepm.pixplorer.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
	
/**
 * Hibernate entity class for the categories table. Each place has a category
 * to distinguish the different place types.
 * 
 * @author cbo
 */
@Entity
@Table(name = "categories", schema = "public")
public class Category implements Serializable {
	private int id;	
	private String name;

	/**
	 * Returns the primary key for this category.
	 * @return primary key
	 */
    @Id
    @SequenceGenerator(name = "categories_id_seq",
            sequenceName = "categories_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "categories_id_seq")
    @Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	/**
	 * Sets the primary key for this category. Do not call this method, the primary
	 * key will be generated by Hibernate!
	 * 
	 * @return primary key - primary key to set
	 */    
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
