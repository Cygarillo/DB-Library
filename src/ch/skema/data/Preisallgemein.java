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
@Table(name = "PREISALLGEMEIN", catalog = "MITGLIEDERDB", schema = "PUBLIC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preisallgemein.findAll", query = "SELECT p FROM Preisallgemein p"),
    @NamedQuery(name = "Preisallgemein.findById", query = "SELECT p FROM Preisallgemein p WHERE p.id = :id"),
    @NamedQuery(name = "Preisallgemein.findByBeschreibung", query = "SELECT p FROM Preisallgemein p WHERE p.beschreibung = :beschreibung"),
    @NamedQuery(name = "Preisallgemein.findByPreis", query = "SELECT p FROM Preisallgemein p WHERE p.preis = :preis")})
public class Preisallgemein implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "BESCHREIBUNG")
    private String beschreibung;
    @Basic(optional = false)
    @Column(name = "PREIS")
    private int preis;
    @OneToMany(mappedBy = "preisallgemeinid")
    private Collection<Rechnungsinhalt> rechnungsinhaltCollection;

    public Preisallgemein() {
    }

    public Preisallgemein(Integer id) {
        this.id = id;
    }

    public Preisallgemein(Integer id, String beschreibung, int preis) {
        this.id = id;
        this.beschreibung = beschreibung;
        this.preis = preis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getPreis() {
        return preis;
    }

    public void setPreis(int preis) {
        this.preis = preis;
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
        if (!(object instanceof Preisallgemein)) {
            return false;
        }
        Preisallgemein other = (Preisallgemein) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Preisallgemein[ id=" + id + " ]";
    }
    
}
