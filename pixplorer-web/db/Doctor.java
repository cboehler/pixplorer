package pl.cormay.equisse.datamodel.general;

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
import javax.persistence.Transient;

@Entity
@Table(name = "doctors", schema = "public")
public class Doctor implements Serializable
{
    private int id;
    private String name;
    private Department department;
    private String telnumber;
    private boolean archive;
    
    @Id
    @SequenceGenerator(name="doctors_id_seq",
                       sequenceName="doctors_id_seq",
                       allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator="doctors_id_seq")
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

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "department")
    public Department getDepartment()
    {
        return this.department;
    }

    public void setDepartment(Department department)
    {
        this.department = department;
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
