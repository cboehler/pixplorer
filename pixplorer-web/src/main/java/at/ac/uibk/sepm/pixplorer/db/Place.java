package at.ac.uibk.sepm.pixplorer.db;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Place", schema = "public")
public class Place implements Serializable {
	private int id;
	private String name;
	private String wikiLink;
	private long count;
	private String picture;
	private Category category;
	private GPSData gpsData;
	
	public Place(){
		
	}
	
	public Place(String name, String wikiLink, long count, String picture, Category category){
		this.name = name;
		this.wikiLink = wikiLink;
		this.count = count;
		this.picture = picture;
		this.category = category;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "wikiLink")
	public String getWikiLink() {
		return wikiLink;
	}

	public void setWikiLink(String wikiLink) {
		this.wikiLink = wikiLink;
	}

	@Column(name = "count")
	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	@Column(name = "picture")
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "category")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gpsdata")
	public GPSData getGpsData() {
		return gpsData;
	}
	
	public void setGpsData(GPSData gpsData) {
		this.gpsData = gpsData;
	}
	
}
