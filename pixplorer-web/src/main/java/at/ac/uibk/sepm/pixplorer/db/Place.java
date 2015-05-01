package at.ac.uibk.sepm.pixplorer.db;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "places", schema = "public")
public class Place implements Serializable {
	private int id;
	private String name;
	private String wikiLink;
	private long count;
	private String picture;
	private Category category;
	private GPSData gpsData;
	private long modificationDate;
	private boolean featured;
	
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
    @SequenceGenerator(name = "places_id_seq",
            sequenceName = "places_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "places_id_seq")
    @Column(name = "id", unique = true, nullable = false)
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
	
	@Column(name = "name")
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

	@Column(name = "users_found")
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

	@OneToOne
	@OnDelete(action=OnDeleteAction.NO_ACTION)
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
	
	@Column(name = "modification_date")
	public long getModificationDate() {
		return modificationDate;
	}
	
	public void setModificationDate(long modificationDate) {
		this.modificationDate = modificationDate;
	}
}
