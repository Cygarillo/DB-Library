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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cyrill
 */
@Entity
@Table(name = "PREIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preis.findAll", query = "SELECT p FROM Preis p"),
    @NamedQuery(name = "Preis.findById", query = "SELECT p FROM Preis p WHERE p.id = :id"),
    @NamedQuery(name = "Preis.findByPreis", query = "SELECT p FROM Preis p WHERE p.preis = :preis"),
    @NamedQuery(name = "Preis.findByBeschreibung", query = "SELECT p FROM Preis p WHERE p.beschreibung = :beschreibung")})
public class Preis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "PREIS")
    private int preis;
    @Column(name = "BESCHREIBUNG")
    private String beschreibung;

    public Preis() {
    }

    public Preis(Integer id) {
        this.id = id;
    }

    public Preis(Integer id, int preis) {
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
        if (!(object instanceof Preis)) {
            return false;
        }
        Preis other = (Preis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Preis[ id=" + id + " ]";
    }
    
}
