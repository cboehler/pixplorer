package test;

import java.io.Serializable;

public class Place implements Serializable {
	
	private int id;
	private String name;
	private boolean featured;
	private String wikiLink;
	private long count;
	private String picture;
	private Category category;
	
	public Place(){}
	
	public Place(String name,String wikiLink,long count, String picture,Category category){
		this.name = name;
		this.wikiLink = wikiLink;
		this.count = count;
		this.picture = picture;
		this.category = category;
		
	}

	
	private Category category_id;

	public Category getCategory() {
		return category_id;
	}

	public void setCategory(Category category_id) {
		this.category_id = category_id;
	}

	// @OneToOne (cascade= CascadeType.ALL)
	// @JoinColumn(name= "gps_id")
	// public GPSData gps_id;
	//
	// public GPSData getGps_id() {
	// return gps_id;
	// }
	// public void setGps_id(GPSData gps_id) {
	// this.gps_id = gps_id;
	// }
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
