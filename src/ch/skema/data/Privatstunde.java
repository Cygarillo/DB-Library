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
@Table(name = "PRIVATSTUNDE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Privatstunde.findAll", query = "SELECT p FROM Privatstunde p"),
    @NamedQuery(name = "Privatstunde.findByPrivatstundeid", query = "SELECT p FROM Privatstunde p WHERE p.privatstundeid = :privatstundeid"),
    @NamedQuery(name = "Privatstunde.findByDatum", query = "SELECT p FROM Privatstunde p WHERE p.datum = :datum"),
    @NamedQuery(name = "Privatstunde.findByMitglied", query = "SELECT p FROM Privatstunde p WHERE p.mitgliedid = :id"),
    @NamedQuery(name = "Privatstunde.findByZeit", query = "SELECT p FROM Privatstunde p WHERE p.zeit = :zeit")})
public class Privatstunde implements Serializable {
    @Column(name = "DATUM")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @Column(name = "ZEIT")
    @Temporal(TemporalType.TIME)
    private Date zeit;
    @Lob
    @Column(name = "NOTIZ")
    private Serializable notiz;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRIVATSTUNDEID")
    private Integer privatstundeid;
    @JoinColumn(name = "MITGLIEDID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Mitglied mitgliedid;

    public Privatstunde() {
    }

    public Privatstunde(Integer privatstundeid) {
        this.privatstundeid = privatstundeid;
    }

    public Integer getPrivatstundeid() {
        return privatstundeid;
    }

    public void setPrivatstundeid(Integer privatstundeid) {
        this.privatstundeid = privatstundeid;
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
        hash += (privatstundeid != null ? privatstundeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Privatstunde)) {
            return false;
        }
        Privatstunde other = (Privatstunde) object;
        if ((this.privatstundeid == null && other.privatstundeid != null) || (this.privatstundeid != null && !this.privatstundeid.equals(other.privatstundeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Privatstunde[ privatstundeid=" + privatstundeid + " ]";
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Date getZeit() {
        return zeit;
    }

    public void setZeit(Date zeit) {
        this.zeit = zeit;
    }

    public String getNotiz() {
        return (String)notiz;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }
    
}
