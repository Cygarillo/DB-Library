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
@Table(name = "ABRECHNUNG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Abrechnung.findAll", query = "SELECT a FROM Abrechnung a"),
    @NamedQuery(name = "Abrechnung.findById", query = "SELECT a FROM Abrechnung a WHERE a.id = :id"),
    @NamedQuery(name = "Abrechnung.findByVertragsstop", query = "SELECT a FROM Abrechnung a WHERE a.vertragsstop = :vertragsstop"),
    @NamedQuery(name = "Abrechnung.findByMitglieder", query = "SELECT a FROM Abrechnung a WHERE a.mitglieder = :mitglieder"),
    @NamedQuery(name = "Abrechnung.findByVertragwcerwachsen", query = "SELECT a FROM Abrechnung a WHERE a.vertragwcerwachsen = :vertragwcerwachsen"),
    @NamedQuery(name = "Abrechnung.findByVertragwcjugend", query = "SELECT a FROM Abrechnung a WHERE a.vertragwcjugend = :vertragwcjugend"),
    @NamedQuery(name = "Abrechnung.findByVertragkt", query = "SELECT a FROM Abrechnung a WHERE a.vertragkt = :vertragkt"),
    @NamedQuery(name = "Abrechnung.findByVertragek", query = "SELECT a FROM Abrechnung a WHERE a.vertragek = :vertragek"),
    @NamedQuery(name = "Abrechnung.findByVertragtc", query = "SELECT a FROM Abrechnung a WHERE a.vertragtc = :vertragtc"),
    @NamedQuery(name = "Abrechnung.findByTribe", query = "SELECT a FROM Abrechnung a WHERE a.tribe = :tribe"),
    @NamedQuery(name = "Abrechnung.findByVertragerwachsenneu", query = "SELECT a FROM Abrechnung a WHERE a.vertragerwachsenneu = :vertragerwachsenneu"),
    @NamedQuery(name = "Abrechnung.findByVertragjugendneu", query = "SELECT a FROM Abrechnung a WHERE a.vertragjugendneu = :vertragjugendneu"),
    @NamedQuery(name = "Abrechnung.findByMembererwachsen", query = "SELECT a FROM Abrechnung a WHERE a.membererwachsen = :membererwachsen"),
    @NamedQuery(name = "Abrechnung.findByMemberjugend", query = "SELECT a FROM Abrechnung a WHERE a.memberjugend = :memberjugend"),
    @NamedQuery(name = "Abrechnung.findByVertragtotal", query = "SELECT a FROM Abrechnung a WHERE a.vertragtotal = :vertragtotal"),
    @NamedQuery(name = "Abrechnung.findByErstelldatum", query = "SELECT a FROM Abrechnung a WHERE a.erstelldatum = :erstelldatum"),
    @NamedQuery(name = "Abrechnung.findByAbrechnungsdatum", query = "SELECT a FROM Abrechnung a WHERE a.abrechnungsdatum = :abrechnungsdatum")})
public class Abrechnung implements Serializable, MitgliederDBPersistenceInterface  {
    @Column(name = "ERSTELLDATUM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date erstelldatum;
    @Column(name = "ABRECHNUNGSDATUM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date abrechnungsdatum;
    @Column(name = "PRIVATSTUNDEN")
    private Integer privatstunden;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "VERTRAGSSTOP")
    private Integer vertragsstop;
    @Column(name = "MITGLIEDER")
    private Integer mitglieder;
    @Column(name = "VERTRAGWCERWACHSEN")
    private Integer vertragwcerwachsen;
    @Column(name = "VERTRAGWCJUGEND")
    private Integer vertragwcjugend;
    @Column(name = "VERTRAGKT")
    private Integer vertragkt;
    @Column(name = "VERTRAGEK")
    private Integer vertragek;
    @Column(name = "VERTRAGTC")
    private Integer vertragtc;
    @Column(name = "TRIBE")
    private Integer tribe;
    @Column(name = "VERTRAGERWACHSENNEU")
    private Integer vertragerwachsenneu;
    @Column(name = "VERTRAGJUGENDNEU")
    private Integer vertragjugendneu;
    @Column(name = "MEMBERERWACHSEN")
    private Integer membererwachsen;
    @Column(name = "MEMBERJUGEND")
    private Integer memberjugend;
    @Column(name = "VERTRAGTOTAL")
    private Integer vertragtotal;

    public Abrechnung() {
    }

    public Abrechnung(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVertragsstop() {
        return vertragsstop;
    }

    public void setVertragsstop(Integer vertragsstop) {
        this.vertragsstop = vertragsstop;
    }

    public Integer getMitglieder() {
        return mitglieder;
    }

    public void setMitglieder(Integer mitglieder) {
        this.mitglieder = mitglieder;
    }

    public Integer getVertragwcerwachsen() {
        return vertragwcerwachsen;
    }

    public void setVertragwcerwachsen(Integer vertragwcerwachsen) {
        this.vertragwcerwachsen = vertragwcerwachsen;
    }

    public Integer getVertragwcjugend() {
        return vertragwcjugend;
    }

    public void setVertragwcjugend(Integer vertragwcjugend) {
        this.vertragwcjugend = vertragwcjugend;
    }

    public Integer getVertragkt() {
        return vertragkt;
    }

    public void setVertragkt(Integer vertragkt) {
        this.vertragkt = vertragkt;
    }

    public Integer getVertragek() {
        return vertragek;
    }

    public void setVertragek(Integer vertragek) {
        this.vertragek = vertragek;
    }

    public Integer getVertragtc() {
        return vertragtc;
    }

    public void setVertragtc(Integer vertragtc) {
        this.vertragtc = vertragtc;
    }

    public Integer getTribe() {
        return tribe;
    }

    public void setTribe(Integer tribe) {
        this.tribe = tribe;
    }

    public Integer getVertragerwachsenneu() {
        return vertragerwachsenneu;
    }

    public void setVertragerwachsenneu(Integer vertragerwachsenneu) {
        this.vertragerwachsenneu = vertragerwachsenneu;
    }

    public Integer getVertragjugendneu() {
        return vertragjugendneu;
    }

    public void setVertragjugendneu(Integer vertragjugendneu) {
        this.vertragjugendneu = vertragjugendneu;
    }

    public Integer getMembererwachsen() {
        return membererwachsen;
    }

    public void setMembererwachsen(Integer membererwachsen) {
        this.membererwachsen = membererwachsen;
    }

    public Integer getMemberjugend() {
        return memberjugend;
    }

    public void setMemberjugend(Integer memberjugend) {
        this.memberjugend = memberjugend;
    }

    public Integer getVertragtotal() {
        return vertragtotal;
    }

    public void setVertragtotal(Integer vertragtotal) {
        this.vertragtotal = vertragtotal;
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
        if (!(object instanceof Abrechnung)) {
            return false;
        }
        Abrechnung other = (Abrechnung) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Abrechnung[ id=" + id + " ]";
    }

    public Date getErstelldatum() {
        return erstelldatum;
    }

    public void setErstelldatum(Date erstelldatum) {
        this.erstelldatum = erstelldatum;
    }

    public Date getAbrechnungsdatum() {
        return abrechnungsdatum;
    }

    public void setAbrechnungsdatum(Date abrechnungsdatum) {
        this.abrechnungsdatum = abrechnungsdatum;
    }

    public Integer getPrivatstunden() {
        return privatstunden;
    }

    public void setPrivatstunden(Integer privatstunden) {
        this.privatstunden = privatstunden;
    }
    
}
