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
@Table(name = "ZAHLUNGSINTERVALL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zahlungsintervall.findAll", query = "SELECT z FROM Zahlungsintervall z"),
    @NamedQuery(name = "Zahlungsintervall.findById", query = "SELECT z FROM Zahlungsintervall z WHERE z.id = :id"),
    @NamedQuery(name = "Zahlungsintervall.findByBeschreibung", query = "SELECT z FROM Zahlungsintervall z WHERE z.beschreibung = :beschreibung")})
public class Zahlungsintervall implements Serializable,MitgliederDBPersistenceInterface {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "intervallid")
    private Collection<Mitglied> mitgliedCollection;
    @OneToMany(mappedBy = "zahlungsintervallid")
    private Collection<Preis> preisCollection;
    public static final int ID_JAEHRLICH = 1;
    public static final int ID_HALBJAEHRLICH = 2;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "BESCHREIBUNG")
    private String beschreibung;

    public Zahlungsintervall() {
    }

    public Zahlungsintervall(Integer id) {
        this.id = id;
    }

    public Zahlungsintervall(Integer id, String beschreibung) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zahlungsintervall)) {
            return false;
        }
        Zahlungsintervall other = (Zahlungsintervall) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return beschreibung;
    }

    @XmlTransient
    public Collection<Preis> getPreisCollection() {
        return preisCollection;
    }

    public void setPreisCollection(Collection<Preis> preisCollection) {
        this.preisCollection = preisCollection;
    }

    @XmlTransient
    public Collection<Mitglied> getMitgliedCollection() {
        return mitgliedCollection;
    }

    public void setMitgliedCollection(Collection<Mitglied> mitgliedCollection) {
        this.mitgliedCollection = mitgliedCollection;
    }
    
}
