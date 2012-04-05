/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.skema.data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cyrill
 */
@Entity
@Table(name = "MAHNUNG", catalog = "MITGLIEDERDB", schema = "PUBLIC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mahnung.findAll", query = "SELECT m FROM Mahnung m"),
    @NamedQuery(name = "Mahnung.findById", query = "SELECT m FROM Mahnung m WHERE m.id = :id"),
    @NamedQuery(name = "Mahnung.findByRechnungsID", query = "SELECT m FROM Mahnung m WHERE m.rechnungid = :id"),
    @NamedQuery(name = "Mahnung.findByFaelligkeit", query = "SELECT m FROM Mahnung m WHERE m.faelligkeit = :faelligkeit")})
public class Mahnung implements Serializable,MitgliederDBPersistenceInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "FAELLIGKEIT")
    @Temporal(TemporalType.DATE)
    private Date faelligkeit;
    @JoinColumn(name = "RECHNUNGID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Rechnung rechnungid;

    public Mahnung() {
    }

    public Mahnung(Integer id) {
        this.id = id;
    }

    public Mahnung(Integer id, Date faelligkeit) {
        this.id = id;
        this.faelligkeit = faelligkeit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFaelligkeit() {
        return faelligkeit;
    }

    public void setFaelligkeit(Date faelligkeit) {
        this.faelligkeit = faelligkeit;
    }

    public Rechnung getRechnungid() {
        return rechnungid;
    }

    public void setRechnungid(Rechnung rechnungid) {
        this.rechnungid = rechnungid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mahnung)) {
            return false;
        }
        Mahnung other = (Mahnung) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Mahnung[ id=" + id + " ]";
    }
    
    public String getDokname(){
        return rechnungid.getMitgliedid().getName()+rechnungid.getMitgliedid().getVorname()+id.toString();
    }
    
}
