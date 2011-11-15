/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.skema.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cyrill
 */
@Entity
@Table(name = "RECHNUNGSINHALT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rechnungsinhalt.findAll", query = "SELECT r FROM Rechnungsinhalt r"),
    @NamedQuery(name = "Rechnungsinhalt.findById", query = "SELECT r FROM Rechnungsinhalt r WHERE r.id = :id"),
    @NamedQuery(name = "Rechnungsinhalt.findByRechnungsID", query = "SELECT r FROM Rechnungsinhalt r WHERE r.rechnungid = :id")})
public class Rechnungsinhalt implements Serializable,MitgliederDBPersistenceInterface {
    @JoinColumn(name = "PREISID", referencedColumnName = "ID")
    @ManyToOne
    private Preis preisid;
    @Lob
    @Column(name = "BESCHREIBUNG")
    private String beschreibung;
    @JoinColumn(name = "RECHNUNGID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Rechnung rechnungid;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    public Rechnungsinhalt() {
    }

    public Rechnungsinhalt(Integer id) {
        this.id = id;
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
        if (!(object instanceof Rechnungsinhalt)) {
            return false;
        }
        Rechnungsinhalt other = (Rechnungsinhalt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Rechnungsinhalt[ id=" + id + " ]";
    }

    public Rechnung getRechnungid() {
        return rechnungid;
    }

    public void setRechnungid(Rechnung rechnungid) {
        this.rechnungid = rechnungid;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Preis getPreisid() {
        return preisid;
    }

    public void setPreisid(Preis preisid) {
        this.preisid = preisid;
    }
    
}
