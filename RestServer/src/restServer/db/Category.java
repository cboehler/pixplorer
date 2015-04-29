package restServer.db;

import java.io.Serializable;

import javax.persistence.*;



@Entity
@Table(name="categories", schema="public")
public class Category implements Serializable
{
	@Id @GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name ="name")
	private String name;
	@Column(name="categoryid")
	private int categoryid;
	
	
	
	
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
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
