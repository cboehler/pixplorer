package restServer.db;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name ="Place", schema= "public")
public class Place implements Serializable{
	@Id @GeneratedValue//(strategy= GenerationType.IDENTITY)
	@Column(name ="id", unique = true, nullable = false)
	public int id;
	@Column(name ="name", nullable = false)
	public String name;
	@Column(name ="featured")
	public boolean featured;
	@Column(name ="wikiLink")
	public String wikiLink;
	@Column(name ="count")
	public long count;
	@Column(name ="picture")
	public String picture;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Category category_id;
	
	
	

	public Category getCategory() {
		return category_id;
	}
	public void setCategory(Category category_id) {
		this.category_id = category_id;
	}
//	@OneToOne (cascade= CascadeType.ALL)
//	@JoinColumn(name= "gps_id")
//	public GPSData gps_id;
//
//	public GPSData getGps_id() {
//		return gps_id;
//	}
//	public void setGps_id(GPSData gps_id) {
//		this.gps_id = gps_id;
//	}
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
	public boolean isFeatured() {
		return featured;
	}
	public void setFeatured(boolean featured) {
		this.featured = featured;
	}
	public String getWikiLink() {
		return wikiLink;
	}
	public void setWikiLink(String wikiLink) {
		this.wikiLink = wikiLink;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

}
