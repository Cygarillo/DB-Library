/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.skema.data;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Cyrill
 */
@Entity
@Table(name = "PREISVERTRAG", catalog = "MITGLIEDERDB", schema = "PUBLIC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preisvertrag.findAll", query = "SELECT p FROM Preisvertrag p"),
    @NamedQuery(name = "Preisvertrag.findById", query = "SELECT p FROM Preisvertrag p WHERE p.id = :id"),
    @NamedQuery(name = "Preisvertrag.findByPreis", query = "SELECT p FROM Preisvertrag p WHERE p.preis = :preis")})
public class Preisvertrag implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "PREIS")
    private int preis;
    @JoinColumn(name = "ZAHLUNGSKATEGORIEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Zahlungskategorie zahlungskategorieid;
    @JoinColumn(name = "ZAHLUNGSINTERVALLID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Zahlungsintervall zahlungsintervallid;
    @JoinColumn(name = "RUBRIKID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Rubrik rubrikid;
    @OneToMany(mappedBy = "preisvertragid")
    private Collection<Rechnungsinhalt> rechnungsinhaltCollection;

    public Preisvertrag() {
    }

    public Preisvertrag(Integer id) {
        this.id = id;
    }

    public Preisvertrag(Integer id, int preis) {
        this.id = id;
        this.preis = preis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPreis() {
        return preis;
    }

    public void setPreis(int preis) {
        this.preis = preis;
    }

    public Zahlungskategorie getZahlungskategorieid() {
        return zahlungskategorieid;
    }

    public void setZahlungskategorieid(Zahlungskategorie zahlungskategorieid) {
        this.zahlungskategorieid = zahlungskategorieid;
    }

    public Zahlungsintervall getZahlungsintervallid() {
        return zahlungsintervallid;
    }

    public void setZahlungsintervallid(Zahlungsintervall zahlungsintervallid) {
        this.zahlungsintervallid = zahlungsintervallid;
    }

    public Rubrik getRubrikid() {
        return rubrikid;
    }

    public void setRubrikid(Rubrik rubrikid) {
        this.rubrikid = rubrikid;
    }

    @XmlTransient
    public Collection<Rechnungsinhalt> getRechnungsinhaltCollection() {
        return rechnungsinhaltCollection;
    }

    public void setRechnungsinhaltCollection(Collection<Rechnungsinhalt> rechnungsinhaltCollection) {
        this.rechnungsinhaltCollection = rechnungsinhaltCollection;
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
        if (!(object instanceof Preisvertrag)) {
            return false;
        }
        Preisvertrag other = (Preisvertrag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Preisvertrag[ id=" + id + " ]";
    }
    
}
