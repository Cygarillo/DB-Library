/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.skema.data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Cyrill
 */
@Entity
@Table(name = "DOKUMENTVORLAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dokumentvorlage.findAll", query = "SELECT d FROM Dokumentvorlage d"),
    @NamedQuery(name = "Dokumentvorlage.findByDokumentvorlageid", query = "SELECT d FROM Dokumentvorlage d WHERE d.dokumentvorlageid = :dokumentvorlageid"),
    @NamedQuery(name = "Dokumentvorlage.findByName", query = "SELECT d FROM Dokumentvorlage d WHERE d.name = :name"),
    @NamedQuery(name = "Dokumentvorlage.findByUeberschrift", query = "SELECT d FROM Dokumentvorlage d WHERE d.ueberschrift = :ueberschrift")})
public class Dokumentvorlage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DOKUMENTVORLAGEID")
    private Integer dokumentvorlageid;
    @Column(name = "NAME")
    private String name;
    @Lob
    @Column(name = "UEBERSCHRIFT")
    private String ueberschrift;
    @Lob
    @Column(name = "TEXT")
    private String text;
    @OneToMany(mappedBy = "dokumentvorlageid")
    private Collection<Dokument> dokumentCollection;

    public Dokumentvorlage() {
    }

    public Dokumentvorlage(Integer dokumentvorlageid) {
        this.dokumentvorlageid = dokumentvorlageid;
    }

    public Integer getDokumentvorlageid() {
        return dokumentvorlageid;
    }

    public void setDokumentvorlageid(Integer dokumentvorlageid) {
        this.dokumentvorlageid = dokumentvorlageid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUeberschrift() {
        return ueberschrift;
    }

    public void setUeberschrift(String ueberschrift) {
        this.ueberschrift = ueberschrift;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @XmlTransient
    public Collection<Dokument> getDokumentCollection() {
        return dokumentCollection;
    }

    public void setDokumentCollection(Collection<Dokument> dokumentCollection) {
        this.dokumentCollection = dokumentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dokumentvorlageid != null ? dokumentvorlageid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dokumentvorlage)) {
            return false;
        }
        Dokumentvorlage other = (Dokumentvorlage) object;
        if ((this.dokumentvorlageid == null && other.dokumentvorlageid != null) || (this.dokumentvorlageid != null && !this.dokumentvorlageid.equals(other.dokumentvorlageid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
