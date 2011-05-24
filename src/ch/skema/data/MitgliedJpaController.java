/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.skema.data;

import ch.skema.data.exceptions.IllegalOrphanException;
import ch.skema.data.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Cyrill
 */
public class MitgliedJpaController implements Serializable {

    public MitgliedJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mitglied mitglied) {
        if (mitglied.getVertragCollection() == null) {
            mitglied.setVertragCollection(new ArrayList<Vertrag>());
        }
        if (mitglied.getAustrittCollection() == null) {
            mitglied.setAustrittCollection(new ArrayList<Austritt>());
        }
        if (mitglied.getRechnungCollection() == null) {
            mitglied.setRechnungCollection(new ArrayList<Rechnung>());
        }
        if (mitglied.getAdresswechselCollection() == null) {
            mitglied.setAdresswechselCollection(new ArrayList<Adresswechsel>());
        }
        if (mitglied.getPruefungCollection() == null) {
            mitglied.setPruefungCollection(new ArrayList<Pruefung>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Zahlungskategorie zahlungskategorieid = mitglied.getZahlungskategorieid();
            if (zahlungskategorieid != null) {
                zahlungskategorieid = em.getReference(zahlungskategorieid.getClass(), zahlungskategorieid.getId());
                mitglied.setZahlungskategorieid(zahlungskategorieid);
            }
            Collection<Vertrag> attachedVertragCollection = new ArrayList<Vertrag>();
            for (Vertrag vertragCollectionVertragToAttach : mitglied.getVertragCollection()) {
                vertragCollectionVertragToAttach = em.getReference(vertragCollectionVertragToAttach.getClass(), vertragCollectionVertragToAttach.getId());
                attachedVertragCollection.add(vertragCollectionVertragToAttach);
            }
            mitglied.setVertragCollection(attachedVertragCollection);
            Collection<Austritt> attachedAustrittCollection = new ArrayList<Austritt>();
            for (Austritt austrittCollectionAustrittToAttach : mitglied.getAustrittCollection()) {
                austrittCollectionAustrittToAttach = em.getReference(austrittCollectionAustrittToAttach.getClass(), austrittCollectionAustrittToAttach.getId());
                attachedAustrittCollection.add(austrittCollectionAustrittToAttach);
            }
            mitglied.setAustrittCollection(attachedAustrittCollection);
            Collection<Rechnung> attachedRechnungCollection = new ArrayList<Rechnung>();
            for (Rechnung rechnungCollectionRechnungToAttach : mitglied.getRechnungCollection()) {
                rechnungCollectionRechnungToAttach = em.getReference(rechnungCollectionRechnungToAttach.getClass(), rechnungCollectionRechnungToAttach.getId());
                attachedRechnungCollection.add(rechnungCollectionRechnungToAttach);
            }
            mitglied.setRechnungCollection(attachedRechnungCollection);
            Collection<Adresswechsel> attachedAdresswechselCollection = new ArrayList<Adresswechsel>();
            for (Adresswechsel adresswechselCollectionAdresswechselToAttach : mitglied.getAdresswechselCollection()) {
                adresswechselCollectionAdresswechselToAttach = em.getReference(adresswechselCollectionAdresswechselToAttach.getClass(), adresswechselCollectionAdresswechselToAttach.getId());
                attachedAdresswechselCollection.add(adresswechselCollectionAdresswechselToAttach);
            }
            mitglied.setAdresswechselCollection(attachedAdresswechselCollection);
            Collection<Pruefung> attachedPruefungCollection = new ArrayList<Pruefung>();
            for (Pruefung pruefungCollectionPruefungToAttach : mitglied.getPruefungCollection()) {
                pruefungCollectionPruefungToAttach = em.getReference(pruefungCollectionPruefungToAttach.getClass(), pruefungCollectionPruefungToAttach.getId());
                attachedPruefungCollection.add(pruefungCollectionPruefungToAttach);
            }
            mitglied.setPruefungCollection(attachedPruefungCollection);
            em.persist(mitglied);
            if (zahlungskategorieid != null) {
                zahlungskategorieid.getMitgliedCollection().add(mitglied);
                zahlungskategorieid = em.merge(zahlungskategorieid);
            }
            for (Vertrag vertragCollectionVertrag : mitglied.getVertragCollection()) {
                Mitglied oldMitgliederidOfVertragCollectionVertrag = vertragCollectionVertrag.getMitgliederid();
                vertragCollectionVertrag.setMitgliederid(mitglied);
                vertragCollectionVertrag = em.merge(vertragCollectionVertrag);
                if (oldMitgliederidOfVertragCollectionVertrag != null) {
                    oldMitgliederidOfVertragCollectionVertrag.getVertragCollection().remove(vertragCollectionVertrag);
                    oldMitgliederidOfVertragCollectionVertrag = em.merge(oldMitgliederidOfVertragCollectionVertrag);
                }
            }
            for (Austritt austrittCollectionAustritt : mitglied.getAustrittCollection()) {
                Mitglied oldMitgliedidOfAustrittCollectionAustritt = austrittCollectionAustritt.getMitgliedid();
                austrittCollectionAustritt.setMitgliedid(mitglied);
                austrittCollectionAustritt = em.merge(austrittCollectionAustritt);
                if (oldMitgliedidOfAustrittCollectionAustritt != null) {
                    oldMitgliedidOfAustrittCollectionAustritt.getAustrittCollection().remove(austrittCollectionAustritt);
                    oldMitgliedidOfAustrittCollectionAustritt = em.merge(oldMitgliedidOfAustrittCollectionAustritt);
                }
            }
            for (Rechnung rechnungCollectionRechnung : mitglied.getRechnungCollection()) {
                Mitglied oldMitgliedidOfRechnungCollectionRechnung = rechnungCollectionRechnung.getMitgliedid();
                rechnungCollectionRechnung.setMitgliedid(mitglied);
                rechnungCollectionRechnung = em.merge(rechnungCollectionRechnung);
                if (oldMitgliedidOfRechnungCollectionRechnung != null) {
                    oldMitgliedidOfRechnungCollectionRechnung.getRechnungCollection().remove(rechnungCollectionRechnung);
                    oldMitgliedidOfRechnungCollectionRechnung = em.merge(oldMitgliedidOfRechnungCollectionRechnung);
                }
            }
            for (Adresswechsel adresswechselCollectionAdresswechsel : mitglied.getAdresswechselCollection()) {
                Mitglied oldMitgliedidOfAdresswechselCollectionAdresswechsel = adresswechselCollectionAdresswechsel.getMitgliedid();
                adresswechselCollectionAdresswechsel.setMitgliedid(mitglied);
                adresswechselCollectionAdresswechsel = em.merge(adresswechselCollectionAdresswechsel);
                if (oldMitgliedidOfAdresswechselCollectionAdresswechsel != null) {
                    oldMitgliedidOfAdresswechselCollectionAdresswechsel.getAdresswechselCollection().remove(adresswechselCollectionAdresswechsel);
                    oldMitgliedidOfAdresswechselCollectionAdresswechsel = em.merge(oldMitgliedidOfAdresswechselCollectionAdresswechsel);
                }
            }
            for (Pruefung pruefungCollectionPruefung : mitglied.getPruefungCollection()) {
                Mitglied oldMitgliedidOfPruefungCollectionPruefung = pruefungCollectionPruefung.getMitgliedid();
                pruefungCollectionPruefung.setMitgliedid(mitglied);
                pruefungCollectionPruefung = em.merge(pruefungCollectionPruefung);
                if (oldMitgliedidOfPruefungCollectionPruefung != null) {
                    oldMitgliedidOfPruefungCollectionPruefung.getPruefungCollection().remove(pruefungCollectionPruefung);
                    oldMitgliedidOfPruefungCollectionPruefung = em.merge(oldMitgliedidOfPruefungCollectionPruefung);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mitglied mitglied) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mitglied persistentMitglied = em.find(Mitglied.class, mitglied.getId());
            Zahlungskategorie zahlungskategorieidOld = persistentMitglied.getZahlungskategorieid();
            Zahlungskategorie zahlungskategorieidNew = mitglied.getZahlungskategorieid();
            Collection<Vertrag> vertragCollectionOld = persistentMitglied.getVertragCollection();
            Collection<Vertrag> vertragCollectionNew = mitglied.getVertragCollection();
            Collection<Austritt> austrittCollectionOld = persistentMitglied.getAustrittCollection();
            Collection<Austritt> austrittCollectionNew = mitglied.getAustrittCollection();
            Collection<Rechnung> rechnungCollectionOld = persistentMitglied.getRechnungCollection();
            Collection<Rechnung> rechnungCollectionNew = mitglied.getRechnungCollection();
            Collection<Adresswechsel> adresswechselCollectionOld = persistentMitglied.getAdresswechselCollection();
            Collection<Adresswechsel> adresswechselCollectionNew = mitglied.getAdresswechselCollection();
            Collection<Pruefung> pruefungCollectionOld = persistentMitglied.getPruefungCollection();
            Collection<Pruefung> pruefungCollectionNew = mitglied.getPruefungCollection();
            List<String> illegalOrphanMessages = null;
            for (Vertrag vertragCollectionOldVertrag : vertragCollectionOld) {
                if (!vertragCollectionNew.contains(vertragCollectionOldVertrag)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vertrag " + vertragCollectionOldVertrag + " since its mitgliederid field is not nullable.");
                }
            }
            for (Austritt austrittCollectionOldAustritt : austrittCollectionOld) {
                if (!austrittCollectionNew.contains(austrittCollectionOldAustritt)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Austritt " + austrittCollectionOldAustritt + " since its mitgliedid field is not nullable.");
                }
            }
            for (Rechnung rechnungCollectionOldRechnung : rechnungCollectionOld) {
                if (!rechnungCollectionNew.contains(rechnungCollectionOldRechnung)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rechnung " + rechnungCollectionOldRechnung + " since its mitgliedid field is not nullable.");
                }
            }
            for (Adresswechsel adresswechselCollectionOldAdresswechsel : adresswechselCollectionOld) {
                if (!adresswechselCollectionNew.contains(adresswechselCollectionOldAdresswechsel)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Adresswechsel " + adresswechselCollectionOldAdresswechsel + " since its mitgliedid field is not nullable.");
                }
            }
            for (Pruefung pruefungCollectionOldPruefung : pruefungCollectionOld) {
                if (!pruefungCollectionNew.contains(pruefungCollectionOldPruefung)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pruefung " + pruefungCollectionOldPruefung + " since its mitgliedid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (zahlungskategorieidNew != null) {
                zahlungskategorieidNew = em.getReference(zahlungskategorieidNew.getClass(), zahlungskategorieidNew.getId());
                mitglied.setZahlungskategorieid(zahlungskategorieidNew);
            }
            Collection<Vertrag> attachedVertragCollectionNew = new ArrayList<Vertrag>();
            for (Vertrag vertragCollectionNewVertragToAttach : vertragCollectionNew) {
                vertragCollectionNewVertragToAttach = em.getReference(vertragCollectionNewVertragToAttach.getClass(), vertragCollectionNewVertragToAttach.getId());
                attachedVertragCollectionNew.add(vertragCollectionNewVertragToAttach);
            }
            vertragCollectionNew = attachedVertragCollectionNew;
            mitglied.setVertragCollection(vertragCollectionNew);
            Collection<Austritt> attachedAustrittCollectionNew = new ArrayList<Austritt>();
            for (Austritt austrittCollectionNewAustrittToAttach : austrittCollectionNew) {
                austrittCollectionNewAustrittToAttach = em.getReference(austrittCollectionNewAustrittToAttach.getClass(), austrittCollectionNewAustrittToAttach.getId());
                attachedAustrittCollectionNew.add(austrittCollectionNewAustrittToAttach);
            }
            austrittCollectionNew = attachedAustrittCollectionNew;
            mitglied.setAustrittCollection(austrittCollectionNew);
            Collection<Rechnung> attachedRechnungCollectionNew = new ArrayList<Rechnung>();
            for (Rechnung rechnungCollectionNewRechnungToAttach : rechnungCollectionNew) {
                rechnungCollectionNewRechnungToAttach = em.getReference(rechnungCollectionNewRechnungToAttach.getClass(), rechnungCollectionNewRechnungToAttach.getId());
                attachedRechnungCollectionNew.add(rechnungCollectionNewRechnungToAttach);
            }
            rechnungCollectionNew = attachedRechnungCollectionNew;
            mitglied.setRechnungCollection(rechnungCollectionNew);
            Collection<Adresswechsel> attachedAdresswechselCollectionNew = new ArrayList<Adresswechsel>();
            for (Adresswechsel adresswechselCollectionNewAdresswechselToAttach : adresswechselCollectionNew) {
                adresswechselCollectionNewAdresswechselToAttach = em.getReference(adresswechselCollectionNewAdresswechselToAttach.getClass(), adresswechselCollectionNewAdresswechselToAttach.getId());
                attachedAdresswechselCollectionNew.add(adresswechselCollectionNewAdresswechselToAttach);
            }
            adresswechselCollectionNew = attachedAdresswechselCollectionNew;
            mitglied.setAdresswechselCollection(adresswechselCollectionNew);
            Collection<Pruefung> attachedPruefungCollectionNew = new ArrayList<Pruefung>();
            for (Pruefung pruefungCollectionNewPruefungToAttach : pruefungCollectionNew) {
                pruefungCollectionNewPruefungToAttach = em.getReference(pruefungCollectionNewPruefungToAttach.getClass(), pruefungCollectionNewPruefungToAttach.getId());
                attachedPruefungCollectionNew.add(pruefungCollectionNewPruefungToAttach);
            }
            pruefungCollectionNew = attachedPruefungCollectionNew;
            mitglied.setPruefungCollection(pruefungCollectionNew);
            mitglied = em.merge(mitglied);
            if (zahlungskategorieidOld != null && !zahlungskategorieidOld.equals(zahlungskategorieidNew)) {
                zahlungskategorieidOld.getMitgliedCollection().remove(mitglied);
                zahlungskategorieidOld = em.merge(zahlungskategorieidOld);
            }
            if (zahlungskategorieidNew != null && !zahlungskategorieidNew.equals(zahlungskategorieidOld)) {
                zahlungskategorieidNew.getMitgliedCollection().add(mitglied);
                zahlungskategorieidNew = em.merge(zahlungskategorieidNew);
            }
            for (Vertrag vertragCollectionNewVertrag : vertragCollectionNew) {
                if (!vertragCollectionOld.contains(vertragCollectionNewVertrag)) {
                    Mitglied oldMitgliederidOfVertragCollectionNewVertrag = vertragCollectionNewVertrag.getMitgliederid();
                    vertragCollectionNewVertrag.setMitgliederid(mitglied);
                    vertragCollectionNewVertrag = em.merge(vertragCollectionNewVertrag);
                    if (oldMitgliederidOfVertragCollectionNewVertrag != null && !oldMitgliederidOfVertragCollectionNewVertrag.equals(mitglied)) {
                        oldMitgliederidOfVertragCollectionNewVertrag.getVertragCollection().remove(vertragCollectionNewVertrag);
                        oldMitgliederidOfVertragCollectionNewVertrag = em.merge(oldMitgliederidOfVertragCollectionNewVertrag);
                    }
                }
            }
            for (Austritt austrittCollectionNewAustritt : austrittCollectionNew) {
                if (!austrittCollectionOld.contains(austrittCollectionNewAustritt)) {
                    Mitglied oldMitgliedidOfAustrittCollectionNewAustritt = austrittCollectionNewAustritt.getMitgliedid();
                    austrittCollectionNewAustritt.setMitgliedid(mitglied);
                    austrittCollectionNewAustritt = em.merge(austrittCollectionNewAustritt);
                    if (oldMitgliedidOfAustrittCollectionNewAustritt != null && !oldMitgliedidOfAustrittCollectionNewAustritt.equals(mitglied)) {
                        oldMitgliedidOfAustrittCollectionNewAustritt.getAustrittCollection().remove(austrittCollectionNewAustritt);
                        oldMitgliedidOfAustrittCollectionNewAustritt = em.merge(oldMitgliedidOfAustrittCollectionNewAustritt);
                    }
                }
            }
            for (Rechnung rechnungCollectionNewRechnung : rechnungCollectionNew) {
                if (!rechnungCollectionOld.contains(rechnungCollectionNewRechnung)) {
                    Mitglied oldMitgliedidOfRechnungCollectionNewRechnung = rechnungCollectionNewRechnung.getMitgliedid();
                    rechnungCollectionNewRechnung.setMitgliedid(mitglied);
                    rechnungCollectionNewRechnung = em.merge(rechnungCollectionNewRechnung);
                    if (oldMitgliedidOfRechnungCollectionNewRechnung != null && !oldMitgliedidOfRechnungCollectionNewRechnung.equals(mitglied)) {
                        oldMitgliedidOfRechnungCollectionNewRechnung.getRechnungCollection().remove(rechnungCollectionNewRechnung);
                        oldMitgliedidOfRechnungCollectionNewRechnung = em.merge(oldMitgliedidOfRechnungCollectionNewRechnung);
                    }
                }
            }
            for (Adresswechsel adresswechselCollectionNewAdresswechsel : adresswechselCollectionNew) {
                if (!adresswechselCollectionOld.contains(adresswechselCollectionNewAdresswechsel)) {
                    Mitglied oldMitgliedidOfAdresswechselCollectionNewAdresswechsel = adresswechselCollectionNewAdresswechsel.getMitgliedid();
                    adresswechselCollectionNewAdresswechsel.setMitgliedid(mitglied);
                    adresswechselCollectionNewAdresswechsel = em.merge(adresswechselCollectionNewAdresswechsel);
                    if (oldMitgliedidOfAdresswechselCollectionNewAdresswechsel != null && !oldMitgliedidOfAdresswechselCollectionNewAdresswechsel.equals(mitglied)) {
                        oldMitgliedidOfAdresswechselCollectionNewAdresswechsel.getAdresswechselCollection().remove(adresswechselCollectionNewAdresswechsel);
                        oldMitgliedidOfAdresswechselCollectionNewAdresswechsel = em.merge(oldMitgliedidOfAdresswechselCollectionNewAdresswechsel);
                    }
                }
            }
            for (Pruefung pruefungCollectionNewPruefung : pruefungCollectionNew) {
                if (!pruefungCollectionOld.contains(pruefungCollectionNewPruefung)) {
                    Mitglied oldMitgliedidOfPruefungCollectionNewPruefung = pruefungCollectionNewPruefung.getMitgliedid();
                    pruefungCollectionNewPruefung.setMitgliedid(mitglied);
                    pruefungCollectionNewPruefung = em.merge(pruefungCollectionNewPruefung);
                    if (oldMitgliedidOfPruefungCollectionNewPruefung != null && !oldMitgliedidOfPruefungCollectionNewPruefung.equals(mitglied)) {
                        oldMitgliedidOfPruefungCollectionNewPruefung.getPruefungCollection().remove(pruefungCollectionNewPruefung);
                        oldMitgliedidOfPruefungCollectionNewPruefung = em.merge(oldMitgliedidOfPruefungCollectionNewPruefung);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mitglied.getId();
                if (findMitglied(id) == null) {
                    throw new NonexistentEntityException("The mitglied with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mitglied mitglied;
            try {
                mitglied = em.getReference(Mitglied.class, id);
                mitglied.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mitglied with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Vertrag> vertragCollectionOrphanCheck = mitglied.getVertragCollection();
            for (Vertrag vertragCollectionOrphanCheckVertrag : vertragCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mitglied (" + mitglied + ") cannot be destroyed since the Vertrag " + vertragCollectionOrphanCheckVertrag + " in its vertragCollection field has a non-nullable mitgliederid field.");
            }
            Collection<Austritt> austrittCollectionOrphanCheck = mitglied.getAustrittCollection();
            for (Austritt austrittCollectionOrphanCheckAustritt : austrittCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mitglied (" + mitglied + ") cannot be destroyed since the Austritt " + austrittCollectionOrphanCheckAustritt + " in its austrittCollection field has a non-nullable mitgliedid field.");
            }
            Collection<Rechnung> rechnungCollectionOrphanCheck = mitglied.getRechnungCollection();
            for (Rechnung rechnungCollectionOrphanCheckRechnung : rechnungCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mitglied (" + mitglied + ") cannot be destroyed since the Rechnung " + rechnungCollectionOrphanCheckRechnung + " in its rechnungCollection field has a non-nullable mitgliedid field.");
            }
            Collection<Adresswechsel> adresswechselCollectionOrphanCheck = mitglied.getAdresswechselCollection();
            for (Adresswechsel adresswechselCollectionOrphanCheckAdresswechsel : adresswechselCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mitglied (" + mitglied + ") cannot be destroyed since the Adresswechsel " + adresswechselCollectionOrphanCheckAdresswechsel + " in its adresswechselCollection field has a non-nullable mitgliedid field.");
            }
            Collection<Pruefung> pruefungCollectionOrphanCheck = mitglied.getPruefungCollection();
            for (Pruefung pruefungCollectionOrphanCheckPruefung : pruefungCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mitglied (" + mitglied + ") cannot be destroyed since the Pruefung " + pruefungCollectionOrphanCheckPruefung + " in its pruefungCollection field has a non-nullable mitgliedid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Zahlungskategorie zahlungskategorieid = mitglied.getZahlungskategorieid();
            if (zahlungskategorieid != null) {
                zahlungskategorieid.getMitgliedCollection().remove(mitglied);
                zahlungskategorieid = em.merge(zahlungskategorieid);
            }
            em.remove(mitglied);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mitglied> findMitgliedEntities() {
        return findMitgliedEntities(true, -1, -1);
    }

    public List<Mitglied> findMitgliedEntities(int maxResults, int firstResult) {
        return findMitgliedEntities(false, maxResults, firstResult);
    }

    private List<Mitglied> findMitgliedEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mitglied.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Mitglied findMitglied(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mitglied.class, id);
        } finally {
            em.close();
        }
    }

    public int getMitgliedCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mitglied> rt = cq.from(Mitglied.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
