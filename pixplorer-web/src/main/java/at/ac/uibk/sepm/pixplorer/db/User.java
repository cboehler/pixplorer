package at.ac.uibk.sepm.pixplorer.db;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users", schema = "public")
public class User implements Serializable {
	private String googleId;
	private long score;
	
//	private Set<Place> places = new HashSet<Place>();
//	private Set<Trophy> trophies = new HashSet<Trophy>();

	@Id
	@Column(name = "googleid")
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
	
//    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
//    @JoinTable(name = "usertrophymapping", joinColumns = { @JoinColumn(name = "user") }, 
//              inverseJoinColumns = { @JoinColumn(name = "trophy") })
//    public Set<Trophy> getTrophies()
//    {
//        return trophies;
//    }
//
//    public void setTrophies(Set<Trophy> trophies)
//    {
//        this.trophies = trophies;
//    }
//    
//    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
//    @JoinTable(name = "placehistory", joinColumns = { @JoinColumn(name = "user") }, 
//              inverseJoinColumns = { @JoinColumn(name = "place") })
//    public Set<Place> getPlaces()
//    {
//        return places;
//    }
//
//    public void setPlaces(Set<Place> places)
//    {
//        this.places = places;
//    }	    
}
