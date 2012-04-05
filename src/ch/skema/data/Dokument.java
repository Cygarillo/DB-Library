/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.skema.data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cyrill
 */
@Entity
@Table(name = "DOKUMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dokument.findAll", query = "SELECT d FROM Dokument d"),
    @NamedQuery(name = "Dokument.findByDokumentid", query = "SELECT d FROM Dokument d WHERE d.dokumentid = :dokumentid"),
    @NamedQuery(name = "Dokument.findByMitgliedid", query = "SELECT d FROM Dokument d WHERE d.mitgliedid = :mitglied"),
    @NamedQuery(name = "Dokument.findByDatum", query = "SELECT d FROM Dokument d WHERE d.datum = :datum")})
public class Dokument implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DOKUMENTID")
    private Integer dokumentid;
    @Column(name = "DATUM")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @JoinColumn(name = "MITGLIEDID", referencedColumnName = "ID")
    @ManyToOne
    private Mitglied mitgliedid;
    @JoinColumn(name = "DOKUMENTVORLAGEID", referencedColumnName = "DOKUMENTVORLAGEID")
    @ManyToOne
    private Dokumentvorlage dokumentvorlageid;

    public Dokument() {
    }

    public Dokument(Integer dokumentid) {
        this.dokumentid = dokumentid;
    }

    public Integer getDokumentid() {
        return dokumentid;
    }

    public void setDokumentid(Integer dokumentid) {
        this.dokumentid = dokumentid;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Mitglied getMitgliedid() {
        return mitgliedid;
    }

    public void setMitgliedid(Mitglied mitgliedid) {
        this.mitgliedid = mitgliedid;
    }

    public Dokumentvorlage getDokumentvorlageid() {
        return dokumentvorlageid;
    }

    public void setDokumentvorlageid(Dokumentvorlage dokumentvorlageid) {
        this.dokumentvorlageid = dokumentvorlageid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dokumentid != null ? dokumentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dokument)) {
            return false;
        }
        Dokument other = (Dokument) object;
        if ((this.dokumentid == null && other.dokumentid != null) || (this.dokumentid != null && !this.dokumentid.equals(other.dokumentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Dokument[ dokumentid=" + dokumentid + " ]";
    }
    
    public String getDokname(){
        return mitgliedid.getName()+mitgliedid.getVorname()+dokumentvorlageid.getName()+dokumentid.toString();
    }
    
}
