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
@Table(name = "RUBRIK", catalog = "MITGLIEDERDB", schema = "PUBLIC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rubrik.findAll", query = "SELECT r FROM Rubrik r"),
    @NamedQuery(name = "Rubrik.findById", query = "SELECT r FROM Rubrik r WHERE r.id = :id"),
    @NamedQuery(name = "Rubrik.findByBeschreibung", query = "SELECT r FROM Rubrik r WHERE r.beschreibung = :beschreibung"),
    @NamedQuery(name = "Rubrik.findByKurzform", query = "SELECT r FROM Rubrik r WHERE r.kurzform = :kurzform")})
public class Rubrik implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "BESCHREIBUNG")
    private String beschreibung;
    @Column(name = "KURZFORM")
    private String kurzform;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rubrikid")
    private Collection<Vertrag> vertragCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rubrikid")
    private Collection<Preisvertrag> preisvertragCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rubrikid")
    private Collection<Pruefungslevel> pruefungslevelCollection;

    public Rubrik() {
    }

    public Rubrik(Integer id) {
        this.id = id;
    }

    public Rubrik(Integer id, String beschreibung) {
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

    public String getKurzform() {
        return kurzform;
    }

    public void setKurzform(String kurzform) {
        this.kurzform = kurzform;
    }

    @XmlTransient
    public Collection<Vertrag> getVertragCollection() {
        return vertragCollection;
    }

    public void setVertragCollection(Collection<Vertrag> vertragCollection) {
        this.vertragCollection = vertragCollection;
    }

    @XmlTransient
    public Collection<Preisvertrag> getPreisvertragCollection() {
        return preisvertragCollection;
    }

    public void setPreisvertragCollection(Collection<Preisvertrag> preisvertragCollection) {
        this.preisvertragCollection = preisvertragCollection;
    }

    @XmlTransient
    public Collection<Pruefungslevel> getPruefungslevelCollection() {
        return pruefungslevelCollection;
    }

    public void setPruefungslevelCollection(Collection<Pruefungslevel> pruefungslevelCollection) {
        this.pruefungslevelCollection = pruefungslevelCollection;
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
        if (!(object instanceof Rubrik)) {
            return false;
        }
        Rubrik other = (Rubrik) object;
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
