package pl.cormay.equisse.datamodel.general;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "departments", schema = "public")
public class Department implements Serializable
{

    private int id;
    private String name;
    private Hospital hospital;
    private Set<Doctor> doctors = new HashSet<>();        
    private String telnumber;
    private boolean archive;    
    
    @Id
    @SequenceGenerator(name="departments_id_seq",
                       sequenceName="departments_id_seq",
                       allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator="departments_id_seq")
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

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "hospital")
    public Hospital getHospital()
    {
        return this.hospital;
    }

    public void setHospital(Hospital hospital)
    {
        this.hospital = hospital;
    }
    
    @Override
    public boolean equals(Object obj) 
    {        
        if (obj instanceof Department)
        {
            Department other = (Department) obj;
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
        int hash = 7;
        hash = 53 * hash + this.id;
        return hash;
    }

    @OneToMany(mappedBy="department", fetch=FetchType.EAGER)
    public Set<Doctor> getDoctors()
    {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors)
    {
        this.doctors = doctors;
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
}
