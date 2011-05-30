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
@Table(name = "VERTRAGSSTOP", catalog = "MITGLIEDERDB", schema = "PUBLIC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vertragsstop.findAll", query = "SELECT v FROM Vertragsstop v"),
    @NamedQuery(name = "Vertragsstop.findById", query = "SELECT v FROM Vertragsstop v WHERE v.id = :id"),
    @NamedQuery(name = "Vertragsstop.findByMitgliedId", query = "SELECT v FROM Vertragsstop v WHERE v.mitgliedid = :id"),
    @NamedQuery(name = "Vertragsstop.findByStartdatum", query = "SELECT v FROM Vertragsstop v WHERE v.startdatum = :startdatum"),
    @NamedQuery(name = "Vertragsstop.findByEnddatum", query = "SELECT v FROM Vertragsstop v WHERE v.enddatum = :enddatum")})
public class Vertragsstop implements Serializable {
    @Basic(optional =     false)
    @Column(name = "STARTDATUM")
    @Temporal(TemporalType.DATE)
    private Date startdatum;
    @Basic(optional =     false)
    @Column(name = "ENDDATUM")
    @Temporal(TemporalType.DATE)
    private Date enddatum;
    @JoinColumn(name = "MITGLIEDID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Mitglied mitgliedid;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    

    public Vertragsstop() {
    }

    public Vertragsstop(Integer id) {
        this.id = id;
    }

    public Vertragsstop(Integer id, Date startdatum, Date enddatum) {
        this.id = id;
        this.startdatum = startdatum;
        this.enddatum = enddatum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Vertragsstop)) {
            return false;
        }
        Vertragsstop other = (Vertragsstop) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Vertragsstop[ id=" + id + " ]";
    }

    public Date getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(Date startdatum) {
        this.startdatum = startdatum;
    }

    public Date getEnddatum() {
        return enddatum;
    }

    public void setEnddatum(Date enddatum) {
        this.enddatum = enddatum;
    }

    public Mitglied getMitgliedid() {
        return mitgliedid;
    }

    public void setMitgliedid(Mitglied mitgliedid) {
        this.mitgliedid = mitgliedid;
    }
    
}
