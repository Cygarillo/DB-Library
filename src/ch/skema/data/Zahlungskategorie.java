/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.skema.data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "ZAHLUNGSKATEGORIE", catalog = "MITGLIEDERDB", schema = "PUBLIC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zahlungskategorie.findAll", query = "SELECT z FROM Zahlungskategorie z"),
    @NamedQuery(name = "Zahlungskategorie.findById", query = "SELECT z FROM Zahlungskategorie z WHERE z.id = :id"),
    @NamedQuery(name = "Zahlungskategorie.findByBeschreibung", query = "SELECT z FROM Zahlungskategorie z WHERE z.beschreibung = :beschreibung")})
public class Zahlungskategorie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "BESCHREIBUNG")
    private String beschreibung;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zahlungskategorieid")
    private Collection<Preisvertrag> preisvertragCollection;
    @OneToMany(mappedBy = "zahlungskategorieid")
    private Collection<Mitglied> mitgliedCollection;

    public Zahlungskategorie() {
    }

    public Zahlungskategorie(Integer id) {
        this.id = id;
    }

    public Zahlungskategorie(Integer id, String beschreibung) {
        this.id = id;
        this.beschreibung = beschreibung;
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

    @XmlTransient
    public Collection<Preisvertrag> getPreisvertragCollection() {
        return preisvertragCollection;
    }

    public void setPreisvertragCollection(Collection<Preisvertrag> preisvertragCollection) {
        this.preisvertragCollection = preisvertragCollection;
    }

    @XmlTransient
    public Collection<Mitglied> getMitgliedCollection() {
        return mitgliedCollection;
    }

    public void setMitgliedCollection(Collection<Mitglied> mitgliedCollection) {
        this.mitgliedCollection = mitgliedCollection;
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
        if (!(object instanceof Zahlungskategorie)) {
            return false;
        }
        Zahlungskategorie other = (Zahlungskategorie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return beschreibung;
    }
    
}
