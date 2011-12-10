/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.skema.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Cyrill
 */
@Entity
@Table(name = "MITGLIED", catalog = "MITGLIEDERDB", schema = "PUBLIC") 
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mitglied.findAll", query = "SELECT m FROM Mitglied m"),
    @NamedQuery(name = "Mitglied.findAllAktive", query = "SELECT m FROM Mitglied m  where m.id in (SELECT v.mitgliederid.id FROM Vertrag v where v.aktiv = true)"),
    @NamedQuery(name = "Mitglied.findAllOhneAustritt", query = "SELECT m FROM Mitglied m  where m.austrittsdatum is null"),
    @NamedQuery(name = "Mitglied.findAllPassive", query = "SELECT m FROM Mitglied m  where m.id not in (SELECT v.mitgliederid.id FROM Vertrag v where v.aktiv = true)"),
    @NamedQuery(name = "Mitglied.findAllByRubrik", query = "SELECT m FROM Mitglied m  where m.id in (SELECT v.mitgliederid.id FROM Vertrag v where v.rubrikid = :rubrik)"),
    @NamedQuery(name = "Mitglied.findById", query = "SELECT m FROM Mitglied m WHERE m.id = :id"),
    @NamedQuery(name = "Mitglied.findByZahlungskategorie", query = "SELECT m FROM Mitglied m WHERE m.zahlungskategorieid = :zahlungskategorie"),
    @NamedQuery(name = "Mitglied.findByName", query = "SELECT m FROM Mitglied m WHERE m.name = :name"),
    @NamedQuery(name = "Mitglied.findByVorname", query = "SELECT m FROM Mitglied m WHERE m.vorname = :vorname"),
    @NamedQuery(name = "Mitglied.findByStrasse", query = "SELECT m FROM Mitglied m WHERE m.strasse = :strasse"),
    @NamedQuery(name = "Mitglied.findByPlz", query = "SELECT m FROM Mitglied m WHERE m.plz = :plz"),
    @NamedQuery(name = "Mitglied.findByOrt", query = "SELECT m FROM Mitglied m WHERE m.ort = :ort"),
    @NamedQuery(name = "Mitglied.findByTelp", query = "SELECT m FROM Mitglied m WHERE m.telp = :telp"),
    @NamedQuery(name = "Mitglied.findByTelg", query = "SELECT m FROM Mitglied m WHERE m.telg = :telg"),
    @NamedQuery(name = "Mitglied.findByTelm", query = "SELECT m FROM Mitglied m WHERE m.telm = :telm"),
    @NamedQuery(name = "Mitglied.findByEmail", query = "SELECT m FROM Mitglied m WHERE m.email = :email"),
    @NamedQuery(name = "Mitglied.findByGeburtstag", query = "SELECT m FROM Mitglied m WHERE m.geburtstag = :geburtstag"),
    @NamedQuery(name = "Mitglied.findByEintrittsdatum", query = "SELECT m FROM Mitglied m WHERE m.eintrittsdatum = :eintrittsdatum"),
    @NamedQuery(name = "Mitglied.findByTribe", query = "SELECT m FROM Mitglied m WHERE m.tribe = :tribe"),
    @NamedQuery(name = "Mitglied.findByFamilienrabat", query = "SELECT m FROM Mitglied m WHERE m.familienrabat = :familienrabat"),
    @NamedQuery(name = "Mitglied.findByAustrittsdatum", query = "SELECT m FROM Mitglied m WHERE m.austrittsdatum = :austrittsdatum")})
public class Mitglied implements Serializable ,MitgliederDBPersistenceInterface{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @Column(name = "VORNAME")
    private String vorname;
    @Column(name = "STRASSE")
    private String strasse;
    @Column(name = "PLZ")
    private Integer plz;
    @Column(name = "ORT")
    private String ort;
    @Column(name = "TELP")
    private Integer telp;
    @Column(name = "TELG")
    private Integer telg;
    @Column(name = "TELM")
    private Integer telm;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "GEBURTSTAG")
    @Temporal(TemporalType.DATE)
    private Date geburtstag;
    @Basic(optional = false)
    @Column(name = "EINTRITTSDATUM")
    @Temporal(TemporalType.DATE)
    private Date eintrittsdatum;
    @Column(name = "TRIBE")
    private boolean tribe;
    @Column(name = "FAMILIENRABAT")
    private boolean familienrabat;
    @Column(name = "AUSTRITTSDATUM")
    @Temporal(TemporalType.DATE)
    private Date austrittsdatum;
    @Lob
    @Column(name = "NOTIZ")
    private String notiz;
    @Lob
    @Column(name = "BILD")
    private byte[] bild;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mitgliederid")
    private Collection<Vertrag> vertragCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mitgliedid")
    private Collection<Austritt> austrittCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mitgliedid")
    private Collection<Rechnung> rechnungCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mitgliedid")
    private Collection<Adresswechsel> adresswechselCollection;
    @JoinColumn(name = "ZAHLUNGSKATEGORIEID", referencedColumnName = "ID")
    @ManyToOne
    private Zahlungskategorie zahlungskategorieid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mitgliedid")
    private Collection<Pruefung> pruefungCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mitgliederid")
    private Collection<Membergebuehr> memberGebuehrCollection;
    
    public Mitglied() {
    }

    public Mitglied(Integer id) {
        this.id = id;
    }

    public Mitglied(Integer id, String name, String vorname, Date eintrittsdatum) {
        this.id = id;
        this.name = name;
        this.vorname = vorname;
        this.eintrittsdatum = eintrittsdatum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public Integer getPlz() {
        return plz;
    }

    public void setPlz(Integer plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public Integer getTelp() {
        return telp;
    }

    public void setTelp(Integer telp) {
        this.telp = telp;
    }

    public Integer getTelg() {
        return telg;
    }

    public void setTelg(Integer telg) {
        this.telg = telg;
    }

    public Integer getTelm() {
        return telm;
    }

    public void setTelm(Integer telm) {
        this.telm = telm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getGeburtstag() {
        return geburtstag;
    }

    public void setGeburtstag(Date geburtstag) {
        this.geburtstag = geburtstag;
    }

    public Date getEintrittsdatum() {
        return eintrittsdatum;
    }

    public void setEintrittsdatum(Date eintrittsdatum) {
        this.eintrittsdatum = eintrittsdatum;
    }

    public boolean getTribe() {
        return tribe;
    }

    public void setTribe(boolean tribe) {
        this.tribe = tribe;
    }



    public boolean getFamilienrabat() {
        return familienrabat;
    }

    public void setFamilienrabat(boolean familienrabat) {
        this.familienrabat = familienrabat;
    }

    public Date getAustrittsdatum() {
        return austrittsdatum;
    }

    public void setAustrittsdatum(Date austrittsdatum) {
        this.austrittsdatum = austrittsdatum;
    }

    public String getNotiz() {
        return notiz;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }

    public byte[] getBild() {
        return bild;
    }

    public void setBild(byte[] bild) {
        this.bild = bild;
    }

    @XmlTransient
    public Collection<Vertrag> getVertragCollection() {
        return vertragCollection;
    }

    public void setVertragCollection(Collection<Vertrag> vertragCollection) {
        this.vertragCollection = vertragCollection;
    }

    @XmlTransient
    public Collection<Austritt> getAustrittCollection() {
        return austrittCollection;
    }

    public void setAustrittCollection(Collection<Austritt> austrittCollection) {
        this.austrittCollection = austrittCollection;
    }

    @XmlTransient
    public Collection<Rechnung> getRechnungCollection() {
        return rechnungCollection;
    }

    public void setRechnungCollection(Collection<Rechnung> rechnungCollection) {
        this.rechnungCollection = rechnungCollection;
    }

    @XmlTransient
    public Collection<Adresswechsel> getAdresswechselCollection() {
        return adresswechselCollection;
    }

    public void setAdresswechselCollection(Collection<Adresswechsel> adresswechselCollection) {
        this.adresswechselCollection = adresswechselCollection;
    }

    public Zahlungskategorie getZahlungskategorieid() {
        return zahlungskategorieid;
    }

    public void setZahlungskategorieid(Zahlungskategorie zahlungskategorieid) {
        this.zahlungskategorieid = zahlungskategorieid;
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
        if (!(object instanceof Mitglied)) {
            return false;
        }
        Mitglied other = (Mitglied) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Mitglied[ id=" + id + " ]";
    }



    @XmlTransient
    public Collection<Membergebuehr> getMembergebuehrCollection() {
        return memberGebuehrCollection;
    }

    public void setMembergebuehrCollection(Collection<Membergebuehr> member1Collection) {
        this.memberGebuehrCollection = member1Collection;
    }
    
    
        
}
