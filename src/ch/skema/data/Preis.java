/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.skema.data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "PREIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preis.findAll", query = "SELECT p FROM Preis p"),
    @NamedQuery(name = "Preis.findById", query = "SELECT p FROM Preis p WHERE p.id = :id"),
    @NamedQuery(name = "Preis.findByKategorieAndIntervall", query = "SELECT p FROM Preis p WHERE p.zahlungskategorieid = :kat and p.zahlungsintervallid = :inter"),
    @NamedQuery(name = "Preis.findByPreis", query = "SELECT p FROM Preis p WHERE p.preis = :preis"),
    @NamedQuery(name = "Preis.findByBeschreibung", query = "SELECT p FROM Preis p WHERE p.beschreibung = :beschreibung")})
public class Preis implements Serializable, MitgliederDBPersistenceInterface {
    @OneToMany(mappedBy = "spezialpreis")
    private Collection<Vertrag> vertragCollection;
    @OneToMany(mappedBy = "preisid")
    private Collection<Rechnungsinhalt> rechnungsinhaltCollection;
    @JoinColumn(name = "ZAHLUNGSKATEGORIEID", referencedColumnName = "ID")
    @ManyToOne
    private Zahlungskategorie zahlungskategorieid;
    @JoinColumn(name = "ZAHLUNGSINTERVALLID", referencedColumnName = "ID")
    @ManyToOne
    private Zahlungsintervall zahlungsintervallid;

    public final static int Erwachsen1J = 1;
    public final static int Lehrling1J = 2;
    public final static int Kinder1J = 3;
    public final static int Erwachsen6M = 4;
    public final static int Lehrling6M = 5;
    public final static int Kinder6M = 6;
    public final static int Disziplin2fuer1J = 7;
    public final static int Disziplin2fuer6M = 8;
    public final static int Disziplin3fuer1J = 9;
    public final static int Disziplin3fuer6M = 10;
    public final static int Familienrabatt1J = 11;
    public final static int Familienrabatt6M = 12;
    public final static int MemberErwachsen = 13;
    public final static int MemberKindJugend = 14;
    public final static int MemberErwachsenAbAugust = 15;
    public final static int MemberKindJugendAbAugust = 16;
    public final static int Einschreibgebuehr = 17;
    public final static int UniformWCEskrima = 18;
    public final static int UniformKinder = 19;
    public final static int UniformTC = 20;
            
    
    private static final long serialVersionUID = 1L;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Zahlungskategorie getZahlungskategorieid() {
        return zahlungskategorieid;
    }

    public void setZahlungskategorieid(Zahlungskategorie zahlungskategorieid) {
        this.zahlungskategorieid = zahlungskategorieid;
    }

    public Zahlungsintervall getZahlungsintervallid() {
        return zahlungsintervallid;
    }

    public void setZahlungsintervallid(Zahlungsintervall zahlungsintervallid) {
        this.zahlungsintervallid = zahlungsintervallid;
    }

    @XmlTransient
    public Collection<Rechnungsinhalt> getRechnungsinhaltCollection() {
        return rechnungsinhaltCollection;
    }

    public void setRechnungsinhaltCollection(Collection<Rechnungsinhalt> rechnungsinhaltCollection) {
        this.rechnungsinhaltCollection = rechnungsinhaltCollection;
    }

    @XmlTransient
    public Collection<Vertrag> getVertragCollection() {
        return vertragCollection;
    }

    public void setVertragCollection(Collection<Vertrag> vertragCollection) {
        this.vertragCollection = vertragCollection;
    }
}
