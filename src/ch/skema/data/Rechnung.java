/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.skema.data;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Cyrill
 */
@Entity
@Table(name = "RECHNUNG", catalog = "MITGLIEDERDB", schema = "PUBLIC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rechnung.findAll", query = "SELECT r FROM Rechnung r"),
    @NamedQuery(name = "Rechnung.findById", query = "SELECT r FROM Rechnung r WHERE r.id = :id"),
    @NamedQuery(name = "Rechnung.findByMitglied", query = "SELECT r FROM Rechnung r WHERE r.mitgliedid = :id"),
    @NamedQuery(name = "Rechnung.findByFaelligkeit", query = "SELECT r FROM Rechnung r WHERE r.faelligkeit = :faelligkeit"),
    @NamedQuery(name = "Rechnung.findByEingang", query = "SELECT r FROM Rechnung r WHERE r.eingang = :eingang"),
    @NamedQuery(name = "Rechnung.findOffenen", query = "SELECT r FROM Rechnung r WHERE r.mitgliedid.id in (SELECT m.id FROM Mitglied m  where m.id in (SELECT v.mitgliederid.id FROM Vertrag v where v.aktiv = true) and m.austrittsdatum is null) and r.eingang IS NULL"),
    @NamedQuery(name = "Rechnung.findByEsr", query = "SELECT r FROM Rechnung r WHERE r.esr = :esr"),
    @NamedQuery(name = "Rechnung.findByNotiz", query = "SELECT r FROM Rechnung r WHERE r.notiz = :notiz")})
public class Rechnung implements Serializable, MitgliederDBPersistenceInterface {

    @Basic(optional = false)
    @Column(name = "FAELLIGKEIT")
    @Temporal(TemporalType.DATE)
    private Date faelligkeit;
    @Column(name = "EINGANG")
    @Temporal(TemporalType.DATE)
    private Date eingang;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ESR")
    private BigDecimal esr;
    @Column(name = "NOTIZ")
    private String notiz;
    @JoinColumn(name = "MITGLIEDID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Mitglied mitgliedid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rechnungid")
    private Collection<Rechnungsinhalt> rechnungsinhaltCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rechnungid")
    private Collection<Mahnung> mahnungCollection;

    public Rechnung() {
    }

    public Rechnung(Integer id) {
        this.id = id;
    }

    public Rechnung(Integer id, Date faelligkeit) {
        this.id = id;
        this.faelligkeit = faelligkeit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getEsr() {
        return esr;
    }

    public void setEsr(BigDecimal esr) {
        this.esr = esr;
    }

    public String getNotiz() {
        return notiz;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }

    public Mitglied getMitgliedid() {
        return mitgliedid;
    }

    public void setMitgliedid(Mitglied mitgliedid) {
        this.mitgliedid = mitgliedid;
    }

    @XmlTransient
    public Collection<Rechnungsinhalt> getRechnungsinhaltCollection() {
        return rechnungsinhaltCollection;
    }

    public void setRechnungsinhaltCollection(Collection<Rechnungsinhalt> rechnungsinhaltCollection) {
        this.rechnungsinhaltCollection = rechnungsinhaltCollection;
    }

    @XmlTransient
    public Collection<Mahnung> getMahnungCollection() {
        return mahnungCollection;
    }

    public void setMahnungCollection(Collection<Mahnung> mahnungCollection) {
        this.mahnungCollection = mahnungCollection;
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
        if (!(object instanceof Rechnung)) {
            return false;
        }
        Rechnung other = (Rechnung) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Rechnung[ id=" + id + " ]";
    }

    public Date getFaelligkeit() {
        return faelligkeit;
    }

    public void setFaelligkeit(Date faelligkeit) {
        this.faelligkeit = faelligkeit;
    }

    public Date getEingang() {
        return eingang;
    }

    public void setEingang(Date eingang) {
        this.eingang = eingang;
        fire("date", null, eingang);
    }
    
    //need propertychange listener to change logo if Eingang is set
    @Transient
    private List listeners = Collections.synchronizedList(new LinkedList());

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        listeners.add(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        listeners.remove(pcl);
    }

    private void fire(String propertyName, Object old, Object nue) {
        //Passing 0 below on purpose, so you only synchronize for one atomic call:
        PropertyChangeListener[] pcls = (PropertyChangeListener[]) listeners.toArray(new PropertyChangeListener[0]);
        for (int i = 0; i < pcls.length; i++) {
            pcls[i].propertyChange(new PropertyChangeEvent(this, propertyName, old, nue));
        }
    }
    
    public String getDokname(){
        return mitgliedid.getName()+mitgliedid.getVorname()+id.toString();
    }
}
