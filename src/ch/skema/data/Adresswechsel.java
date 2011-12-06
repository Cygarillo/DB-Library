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
@Table(name = "ADRESSWECHSEL", catalog = "MITGLIEDERDB", schema = "PUBLIC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adresswechsel.findAll", query = "SELECT a FROM Adresswechsel a"),
    @NamedQuery(name = "Adresswechsel.findById", query = "SELECT a FROM Adresswechsel a WHERE a.id = :id"),
    @NamedQuery(name = "Adresswechsel.findAlreadyChanged", query = "SELECT a FROM Adresswechsel a WHERE a.mitgliedid = :mitglied and a.abrechnungid is null"),
    @NamedQuery(name = "Adresswechsel.findNotAbgerechnet", query = "SELECT a FROM Adresswechsel a WHERE a.abrechnungid is null"),
    @NamedQuery(name = "Adresswechsel.findByAbrechnungid", query = "SELECT a FROM Adresswechsel a WHERE a.abrechnungid = :abrechnungid")})
public class Adresswechsel implements Serializable,MitgliederDBPersistenceInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ABRECHNUNGID")
    private Integer abrechnungid;
    @JoinColumn(name = "MITGLIEDID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Mitglied mitgliedid;

    public Adresswechsel() {
    }

    public Adresswechsel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAbrechnungid() {
        return abrechnungid;
    }

    public void setAbrechnungid(Integer abrechnungid) {
        this.abrechnungid = abrechnungid;
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
        if (!(object instanceof Adresswechsel)) {
            return false;
        }
        Adresswechsel other = (Adresswechsel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Adresswechsel[ id=" + id + " ]";
    }
    
}
