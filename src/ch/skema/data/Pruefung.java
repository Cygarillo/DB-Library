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
@Table(name = "PRUEFUNG", catalog = "MITGLIEDERDB", schema = "PUBLIC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pruefung.findAll", query = "SELECT p FROM Pruefung p"),
    @NamedQuery(name = "Pruefung.findById", query = "SELECT p FROM Pruefung p WHERE p.id = :id"),
    @NamedQuery(name = "Pruefung.findByMitgliedId", query = "SELECT p FROM Pruefung p WHERE p.mitgliedid = :id"),
    @NamedQuery(name = "Pruefung.findByKommentar", query = "SELECT p FROM Pruefung p WHERE p.kommentar = :kommentar"),
    @NamedQuery(name = "Pruefung.findByDatum", query = "SELECT p FROM Pruefung p WHERE p.datum = :datum")})
public class Pruefung implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "KOMMENTAR")
    private String kommentar;
    @Basic(optional = false)
    @Column(name = "DATUM")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @JoinColumn(name = "PRUEFUNGSLEVELID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Pruefungslevel pruefungslevelid;
    @JoinColumn(name = "MITGLIEDID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Mitglied mitgliedid;

    public Pruefung() {
    }

    public Pruefung(Integer id) {
        this.id = id;
    }

    public Pruefung(Integer id, Date datum) {
        this.id = id;
        this.datum = datum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Pruefungslevel getPruefungslevelid() {
        return pruefungslevelid;
    }

    public void setPruefungslevelid(Pruefungslevel pruefungslevelid) {
        this.pruefungslevelid = pruefungslevelid;
    }

    public Mitglied getMitgliedid() {
        return mitgliedid;
    }

    public void setMitgliedid(Mitglied mitgliedid) {
        this.mitgliedid = mitgliedid;
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
        if (!(object instanceof Pruefung)) {
            return false;
        }
        Pruefung other = (Pruefung) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Pruefung[ id=" + id + " ]";
    }
    
}
