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
@Table(name = "MEMBER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Membergebuehr.findAll", query = "SELECT m FROM Membergebuehr m"),
    @NamedQuery(name = "Membergebuehr.findById", query = "SELECT m FROM Membergebuehr m WHERE m.id = :id"),
    @NamedQuery(name = "Membergebuehr.findByMitgliedId", query = "SELECT m FROM Membergebuehr m WHERE m.mitgliederid = :id"),
    @NamedQuery(name = "Membergebuehr.findByMitgliedIdAndJahr", query = "SELECT m FROM Membergebuehr m WHERE m.mitgliederid = :id and m.jahr = :jahr"),
    @NamedQuery(name = "Membergebuehr.findByJahr", query = "SELECT m FROM Membergebuehr m WHERE m.jahr = :jahr"),
    @NamedQuery(name = "Membergebuehr.findByAbgerechnet", query = "SELECT m FROM Membergebuehr m WHERE m.abgerechnet = :abgerechnet")})
public class Membergebuehr implements Serializable {
    
    @Column(name = "ZAHLUNGSEINGANG")
    @Temporal(TemporalType.DATE)
    private Date zahlungseingang;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "JAHR")
    private int jahr;
    @Basic(optional = false)
    @Column(name = "ABGERECHNET")
    private boolean abgerechnet;
    @JoinColumn(name = "MITGLIEDERID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Mitglied mitgliederid;

    public Membergebuehr() {
    }

    public Membergebuehr(Integer id) {
        this.id = id;
    }

    public Membergebuehr(Integer id, int jahr, boolean abgerechnet) {
        this.id = id;
        this.jahr = jahr;
        this.abgerechnet = abgerechnet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    public boolean getAbgerechnet() {
        return abgerechnet;
    }

    public void setAbgerechnet(boolean abgerechnet) {
        this.abgerechnet = abgerechnet;
    }

    public Mitglied getMitgliederid() {
        return mitgliederid;
    }

    public void setMitgliederid(Mitglied mitgliederid) {
        this.mitgliederid = mitgliederid;
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
        if (!(object instanceof Membergebuehr)) {
            return false;
        }
        Membergebuehr other = (Membergebuehr) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Member1[ id=" + id + " ]";
    }

   

    public Date getZahlungseingang() {
        return zahlungseingang;
    }

    public void setZahlungseingang(Date zahlungseingang) {
        this.zahlungseingang = zahlungseingang;
    }
    
}
