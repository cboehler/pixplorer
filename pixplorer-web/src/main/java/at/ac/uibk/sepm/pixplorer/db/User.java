package at.ac.uibk.sepm.pixplorer.db;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users", schema = "public")
public class User implements Serializable {
	private int id;
	private String googleId;
	private long score;
	private int type;
	
	private Set<Place> places = new HashSet<Place>();
	private Set<Place> favourites = new HashSet<Place>();
	private Set<Trophy> trophies = new HashSet<Trophy>();

    @Id
    @SequenceGenerator(name = "users_id_seq",
            sequenceName = "users_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "users_id_seq")
    @Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "google_id")
	public String getGoogleId() {
		return googleId;
	}
	
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}
	
	@Column(name = "score")
	public long getScore() {
		return score;
	}
	
	public void setScore(long score) {
		this.score = score;
	}
	
	@Column(name = "user_type")
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "usertrophymapping", joinColumns = { @JoinColumn(name = "user_id") }, 
              inverseJoinColumns = { @JoinColumn(name = "trophy_id") })
    public Set<Trophy> getTrophies()
    {
        return trophies;
    }

    public void setTrophies(Set<Trophy> trophies)
    {
        this.trophies = trophies;
    }
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "history", joinColumns = { 
			@JoinColumn(name = "user_id", nullable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "place_id", 
					nullable = false) })
    public Set<Place> getFoundPlaces()
    {
        return places;
    }

    public void setFoundPlaces(Set<Place> places)
    {
        this.places = places;
    }
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "favourites", joinColumns = { 
			@JoinColumn(name = "user_id", nullable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "place_id", 
					nullable = false) })
    public Set<Place> getFavourites()
    {
        return favourites;
    }

    public void setFavourites(Set<Place> favourites)
    {
        this.favourites = favourites;
    }    
}
