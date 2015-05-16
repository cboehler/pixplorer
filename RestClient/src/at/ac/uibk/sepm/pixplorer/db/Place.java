package at.ac.uibk.sepm.pixplorer.db;

import java.io.Serializable;


/**
 * Hibernate entity class for the places table. A place is the object that has to be
 * found by any Pixplorer user.
 * 
 * @author cbo
 */
public class Place implements Serializable {
	private int id;
	private String name;
	private String wikiLink;
	private int score;
	private long count;
	private String picture;
	private Category category;
	private GPSData gpsData;
	private long modificationDate;
	private boolean featured;
	
	/**
	 * Constructor.
	 */
	public Place(){
	}
	
	/**
	 * Constructor.
	 * 
	 * @param name - place name
	 * @param wikiLink - link to wiki page
	 * @param picture - Base64 representation of place image
	 * @param category -  category for this place
	 */
	public Place(String name, String wikiLink, long count, String picture, Category category){
		this.name = name;
		this.wikiLink = wikiLink;
		this.count = count;
		this.picture = picture;
		this.category = category;
	}
	
	public int getId() {
		return id;
	}
   
	public void setId(int id) {
		this.id = id;
	}

	public boolean isFeatured() {
		return featured;
	}
	
	public void setFeatured(boolean featured) {
		this.featured = featured;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public GPSData getGpsData() {
		return gpsData;
	}
	
	public void setGpsData(GPSData gpsData) {
		this.gpsData = gpsData;
	}

	public long getModificationDate() {
		return modificationDate;
	}
	
	public void setModificationDate(long modificationDate) {
		this.modificationDate = modificationDate;
	}

	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj instanceof Place) {
			Place place = (Place) obj;
			return place.getId() == id;
		}
		
		return false;
	}
}
