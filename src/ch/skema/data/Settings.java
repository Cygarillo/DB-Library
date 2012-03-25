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
@Table(name = "SETTINGS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Settings.findAll", query = "SELECT s FROM Settings s"),
    @NamedQuery(name = "Settings.findById", query = "SELECT s FROM Settings s WHERE s.id = :id"),
    @NamedQuery(name = "Settings.findByWaehrung", query = "SELECT s FROM Settings s WHERE s.waehrung = :waehrung"),
    @NamedQuery(name = "Settings.findByRechnungstage", query = "SELECT s FROM Settings s WHERE s.rechnungstage = :rechnungstage"),
    @NamedQuery(name = "Settings.findByMahnungstage", query = "SELECT s FROM Settings s WHERE s.mahnungstage = :mahnungstage"),
    @NamedQuery(name = "Settings.findByNeuemitgliedertage", query = "SELECT s FROM Settings s WHERE s.neuemitgliedertage = :neuemitgliedertage"),
    @NamedQuery(name = "Settings.findByVorgeburtstag", query = "SELECT s FROM Settings s WHERE s.vorgeburtstag = :vorgeburtstag"),
    @NamedQuery(name = "Settings.findByNachgeburtstag", query = "SELECT s FROM Settings s WHERE s.nachgeburtstag = :nachgeburtstag"),
    @NamedQuery(name = "Settings.findByKinderjugendvorlage", query = "SELECT s FROM Settings s WHERE s.kinderjugendvorlage = :kinderjugendvorlage"),
    @NamedQuery(name = "Settings.findByAutomemberrechnung", query = "SELECT s FROM Settings s WHERE s.automemberrechnung = :automemberrechnung")})
public class Settings implements Serializable {
    @Column(name = "KINDERJUGENDVORLAGE")
    private boolean kinderjugendvorlage;
    @Column(name = "AUTOMEMBERRECHNUNG")
    private boolean automemberrechnung;
    @Column(name = "EIGENEERSTEMAHNUNG")
    private boolean eigeneerstemahnung;
    @Column(name = "LASTSTARTED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date laststarted;
    @Column(name =     "LASTBACKUP")
    @Temporal(TemporalType.DATE)
    private Date lastbackup;
   
    @Lob
    @Column(name = "MITGLIEDMAINVIEWTABLESETTINGS")
    private Serializable mitgliedmainviewtablesettings;
    
    
            @Lob
    @Column(name = "CUSTOMDOCUMENTROOT")
    private String customdocumentroot;

    
    
    @Column(name = "COLORWINGCHUN")
    private String colorwingchun;
    @Column(name = "COLORESKRIMA")
    private String coloreskrima;
    @Column(name = "COLORKINDERWUSHU")
    private String colorkinderwushu;
    @Column(name = "COLORTAICHI")
    private String colortaichi;
    @Column(name = "COLORCHIKUNG50PLUS")
    private String colorchikung50plus;
    @Column(name = "COLORKOMBI")
    private String colorkombi;
    @Column(name = "STANDORT")
    private String standort;
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "WAEHRUNG")
    private String waehrung;
    @Column(name = "RECHNUNGSTAGE")
    private Integer rechnungstage;
    @Column(name = "MAHNUNGSTAGE")
    private Integer mahnungstage;
    @Column(name = "NEUEMITGLIEDERTAGE")
    private Integer neuemitgliedertage;
    @Column(name = "VORGEBURTSTAG")
    private Integer vorgeburtstag;
    @Column(name = "NACHGEBURTSTAG")
    private Integer nachgeburtstag;
    @Column(name = "WOCHENZAHLFUERNEUEMEMBERGEBUER")
    private Integer wochenzahlFuerNeueMembergebuehr;
    @Column(name = "ISTELMSHOWN")
    private Boolean isTelmShown;
    @Column(name = "ISTELGSHOWN")
    private Boolean isTelgShown;
    @Column(name = "ISTELPSHOWN")
    private Boolean isTelpShown;
    
    @Column(name = "ANZAHLPRIVATSTUNDENANZEIGEN")
    private Integer anzahlPrivatStundenAnzeigen;
    @Column(name = "ANZAHLRECHNUNGENPROMITGLIEDANZEIGEN")
    private Integer anzahlRechnungenProMitgliedAnzeigen;

    public Settings() {
    }

    public Settings(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWaehrung() {
        return waehrung;
    }

    public void setWaehrung(String waehrung) {
        this.waehrung = waehrung;
    }

    public Integer getRechnungstage() {
        return rechnungstage;
    }

    public void setRechnungstage(Integer rechnungstage) {
        this.rechnungstage = rechnungstage;
    }

    public Integer getMahnungstage() {
        return mahnungstage;
    }

    public void setMahnungstage(Integer mahnungstage) {
        this.mahnungstage = mahnungstage;
    }

    public Integer getNeuemitgliedertage() {
        return neuemitgliedertage;
    }

    public void setNeuemitgliedertage(Integer neuemitgliedertage) {
        this.neuemitgliedertage = neuemitgliedertage;
    }

    public Integer getVorgeburtstag() {
        return vorgeburtstag;
    }

    public void setVorgeburtstag(Integer vorgeburtstag) {
        this.vorgeburtstag = vorgeburtstag;
    }

    public Integer getNachgeburtstag() {
        return nachgeburtstag;
    }

    public void setNachgeburtstag(Integer nachgeburtstag) {
        this.nachgeburtstag = nachgeburtstag;
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
        if (!(object instanceof Settings)) {
            return false;
        }
        Settings other = (Settings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.skema.data.Settings[ id=" + id + " ]";
    }

    public Boolean getKinderjugendvorlage() {
        return kinderjugendvorlage;
    }

    public void setKinderjugendvorlage(Boolean kinderjugendvorlage) {
        this.kinderjugendvorlage = kinderjugendvorlage;
    }
    public Boolean getEigeneerstemahnung() {
        return eigeneerstemahnung;
    }

    public void setEigeneerstemahnung(Boolean eigeneerstemahnung) {
        this.eigeneerstemahnung = eigeneerstemahnung;
    }

    public Boolean getAutomemberrechnung() {
        return automemberrechnung;
    }

    public void setAutomemberrechnung(Boolean automemberrechnung) {
        this.automemberrechnung = automemberrechnung;
    }

    public Date getLaststarted() {
        return laststarted;
    }

    public void setLaststarted(Date laststarted) {
        this.laststarted = laststarted;
    }

    public Date getLastbackup() {
        return lastbackup;
    }

    public void setLastbackup(Date lastbackup) {
        this.lastbackup = lastbackup;
    }

    public String getColorwingchun() {
        return colorwingchun;
    }

    public void setColorwingchun(String colorwingchun) {
        this.colorwingchun = colorwingchun;
    }

    public String getColoreskrima() {
        return coloreskrima;
    }

    public void setColoreskrima(String coloreskrima) {
        this.coloreskrima = coloreskrima;
    }

    public String getColorkinderwushu() {
        return colorkinderwushu;
    }

    public void setColorkinderwushu(String colorkinderwushu) {
        this.colorkinderwushu = colorkinderwushu;
    }

    public String getColortaichi() {
        return colortaichi;
    }

    public void setColortaichi(String colortaichi) {
        this.colortaichi = colortaichi;
    }

    public String getColorchikung50plus() {
        return colorchikung50plus;
    }

    public void setColorchikung50plus(String colorchikung50plus) {
        this.colorchikung50plus = colorchikung50plus;
    }
    
    public String getColorkombi() {
        return colorkombi;
    }

    public void setColorkombi(String colorkombi) {
        this.colorkombi = colorkombi;
    }

    public String getStandort() {
        return standort;
    }

    public void setStandort(String standort) {
        this.standort = standort;
    }

    public Integer getWochenzahlFuerNeueMembergebuehr() {
        return wochenzahlFuerNeueMembergebuehr;
    }

    public void setWochenzahlFuerNeueMembergebuehr(Integer wochenzahlFuerNeueMembergebuehr) {
        this.wochenzahlFuerNeueMembergebuehr = wochenzahlFuerNeueMembergebuehr;
    }
    
    public boolean isTelpShown() {
        return isTelpShown;
    }
    
    public boolean isTelgShown() {
        return isTelgShown;
    }
    
    public boolean isTelmShown() {
        return isTelmShown;
    }
    
     public void setTelpShown(Boolean b) {
        isTelpShown = b;
    }

    public void setTelmShown(Boolean b) {
        isTelmShown = b;
    }
    public void setTelgShown(Boolean b) {
        isTelgShown = b;
    }
    
    
    

    public Integer getAnzahlPrivatStundenAnzeigen() {
        return anzahlPrivatStundenAnzeigen;
    }

    public void setAnzahlPrivatStundenAnzeigen(Integer anzahlPrivatStundenAnzeigen) {
        this.anzahlPrivatStundenAnzeigen = anzahlPrivatStundenAnzeigen;
    }

    public Integer getAnzahlRechnungenProMitgliedAnzeigen() {
        return anzahlRechnungenProMitgliedAnzeigen;
    }

    public void setAnzahlRechnungenProMitgliedAnzeigen(Integer anzahlRechnungenProMitgliedAnzeigen) {
        this.anzahlRechnungenProMitgliedAnzeigen = anzahlRechnungenProMitgliedAnzeigen;
    }
 

    public Serializable getMitgliedmainviewtablesettings() {
        return mitgliedmainviewtablesettings;
    }

    public void setMitgliedmainviewtablesettings(Serializable mitgliedmainviewtablesettings) {
        this.mitgliedmainviewtablesettings = mitgliedmainviewtablesettings;
    }

    public String getCustomdocumentroot() {
        return customdocumentroot;
    }

    public void setCustomdocumentroot(String customdocumentroot) {
        this.customdocumentroot = customdocumentroot;
    }
    
}
