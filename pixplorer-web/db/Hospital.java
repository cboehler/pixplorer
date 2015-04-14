package pl.cormay.equisse.datamodel.general;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "hospitals", schema = "public")
public class Hospital implements Serializable
{
    private int id;
    private String name;
    private String address;
    private String zipcode;
    private String town;
    private String country;
    private String telnumber;
    private boolean archive;    
    
    private Set<Department> departments = new HashSet<>();       
    
    @Id
    @SequenceGenerator(name="hospitals_id_seq",
                       sequenceName="hospitals_id_seq",
                       allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator="hospitals_id_seq")
    @Column(name = "id", unique = true, nullable = false)
    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Column(name = "archive", nullable = false)
    public boolean getArchive()
    {
        return this.archive;
    }

    public void setArchive(boolean archive)
    {
        this.archive = archive;
    }      
    
    @Column(name = "name", nullable = false)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "address", nullable = false)
    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Column(name = "telnumber", nullable = false)
    public String getTelNumber()
    {
        return this.telnumber;
    }

    public void setTelNumber(String telnumber)
    {
        this.telnumber = telnumber;
    }    
    
    @Column(name = "zipcode", nullable = false)
    public String getZipCode()
    {
        return this.zipcode;
    }

    public void setZipCode(String zipcode)
    {
        this.zipcode = zipcode;
    }

    @Column(name = "town", nullable = false)
    public String getTown()
    {
        return this.town;
    }

    public void setTown(String town)
    {
        this.town = town;
    }

    @Column(name = "country", nullable = false)
    public String getCountry()
    {
        return this.country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }
    
    @Override
    public boolean equals(Object obj) 
    {        
        if (obj instanceof Hospital)
        {
            Hospital other = (Hospital) obj;
            if((this.getId() == other.getId()))
            {
                return true;
            }            
        }
        
        return false;
    }    

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 23 * hash + this.id;
        return hash;
    }

    @OneToMany(mappedBy="hospital", fetch=FetchType.EAGER)
    public Set<Department> getDepartments()
    {
        return departments;
    }

    public void setDepartments(Set<Department> departments)
    {
        this.departments = departments;
    }       
 
}
