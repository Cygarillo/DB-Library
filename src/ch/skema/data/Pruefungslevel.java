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
@Table(name = "PRUEFUNGSLEVEL", catalog = "MITGLIEDERDB", schema = "PUBLIC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pruefungslevel.findAll", query = "SELECT p FROM Pruefungslevel p"),
    @NamedQuery(name = "Pruefungslevel.findById", query = "SELECT p FROM Pruefungslevel p WHERE p.id = :id"),
    @NamedQuery(name = "Pruefungslevel.findByBeschreibung", query = "SELECT p FROM Pruefungslevel p WHERE p.beschreibung = :beschreibung"),
    @NamedQuery(name = "Pruefungslevel.findByRubrikID", query = "SELECT p FROM Pruefungslevel p WHERE p.rubrikid = :rubrikid")})

public class Pruefungslevel implements Serializable,MitgliederDBPersistenceInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "BESCHREIBUNG")
    private String beschreibung;
    @JoinColumn(name = "RUBRIKID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Rubrik rubrikid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pruefungslevelid")
    private Collection<Pruefung> pruefungCollection;

    public Pruefungslevel() {
    }

    public Pruefungslevel(Integer id) {
        this.id = id;
    }

    public Pruefungslevel(Integer id, String beschreibung) {
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

    public Rubrik getRubrikid() {
        return rubrikid;
    }

    public void setRubrikid(Rubrik rubrikid) {
        this.rubrikid = rubrikid;
    }

    @XmlTransient
    public Collection<Pruefung> getPruefungCollection() {
        return pruefungCollection;
    }

    public void setPruefungCollection(Collection<Pruefung> pruefungCollection) {
        this.pruefungCollection = pruefungCollection;
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
        if (!(object instanceof Pruefungslevel)) {
            return false;
        }
        Pruefungslevel other = (Pruefungslevel) object;
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
