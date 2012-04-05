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
@Table(name = "VERTRAG", catalog = "MITGLIEDERDB", schema = "PUBLIC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vertrag.findAll", query = "SELECT v FROM Vertrag v"),
    @NamedQuery(name = "Vertrag.findById", query = "SELECT v FROM Vertrag v WHERE v.id = :id"),
    @NamedQuery(name = "Vertrag.findByMitgliedId", query = "SELECT v FROM Vertrag v WHERE v.mitgliederid = :id"),
    @NamedQuery(name = "Vertrag.findByStartdatum", query = "SELECT v FROM Vertrag v WHERE v.startdatum = :startdatum"),
    @NamedQuery(name = "Vertrag.findByAktiv", query = "SELECT v FROM Vertrag v WHERE v.aktiv = :aktiv"),
    @NamedQuery(name = "Vertrag.findByAktivAndMitgliederId", query = "SELECT v FROM Vertrag v WHERE v.aktiv = :aktiv and v.mitgliederid = :id"),
    @NamedQuery(name = "Vertrag.findBySpezialpreis", query = "SELECT v FROM Vertrag v WHERE v.spezialpreis = :spezialpreis")})
public class Vertrag implements Serializable ,MitgliederDBPersistenceInterface{
    @Basic(optional =     false)
    @Column(name = "STARTDATUM")
    @Temporal(TemporalType.DATE)
    private Date startdatum;
    @Basic(optional = false)
    @Column(name = "AKTIV")
    private boolean aktiv;
    @JoinColumn(name = "SPEZIALPREIS", referencedColumnName = "ID")
    @ManyToOne
    private Preis spezialpreis;
    private static final long serialVersionUID = 1L;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
   

    @JoinColumn(name = "RUBRIKID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Rubrik rubrikid;
    @JoinColumn(name = "MITGLIEDERID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Mitglied mitgliederid;

    public Vertrag() {
    }

    public Vertrag(Integer id) {
        this.id = id;
    }

    public Vertrag(Integer id, Date startdatum, boolean aktiv) {
        this.id = id;
        this.startdatum = startdatum;
        this.aktiv = aktiv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public Rubrik getRubrikid() {
        return rubrikid;
    }

    public void setRubrikid(Rubrik rubrikid) {
        this.rubrikid = rubrikid;
    }

    public Mitglied getMitgliederid() {
        return mitgliederid;
    }

    public void setMitgliederid(Mitglied mitgliederid) {
        this.mitgliederid = mitgliederid;
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
        if (!(object instanceof Vertrag)) {
            return false;
        }
        Vertrag other = (Vertrag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Vertrag[ id=" + id + " ]";
    }

    public Preis getSpezialpreis() {
        return spezialpreis;
    }

    public void setSpezialpreis(Preis spezialpreis) {
        this.spezialpreis = spezialpreis;
    }

    public Date getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(Date startdatum) {
        this.startdatum = startdatum;
    }

    public boolean getAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean  aktiv) {
        this.aktiv = aktiv;
    }
    
}
