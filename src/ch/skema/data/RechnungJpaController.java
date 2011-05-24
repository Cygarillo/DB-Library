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
public class RechnungJpaController implements Serializable {

    public RechnungJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rechnung rechnung) {
        if (rechnung.getRechnungsinhaltCollection() == null) {
            rechnung.setRechnungsinhaltCollection(new ArrayList<Rechnungsinhalt>());
        }
        if (rechnung.getMahnungCollection() == null) {
            rechnung.setMahnungCollection(new ArrayList<Mahnung>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mitglied mitgliedid = rechnung.getMitgliedid();
            if (mitgliedid != null) {
                mitgliedid = em.getReference(mitgliedid.getClass(), mitgliedid.getId());
                rechnung.setMitgliedid(mitgliedid);
            }
            Collection<Rechnungsinhalt> attachedRechnungsinhaltCollection = new ArrayList<Rechnungsinhalt>();
            for (Rechnungsinhalt rechnungsinhaltCollectionRechnungsinhaltToAttach : rechnung.getRechnungsinhaltCollection()) {
                rechnungsinhaltCollectionRechnungsinhaltToAttach = em.getReference(rechnungsinhaltCollectionRechnungsinhaltToAttach.getClass(), rechnungsinhaltCollectionRechnungsinhaltToAttach.getId());
                attachedRechnungsinhaltCollection.add(rechnungsinhaltCollectionRechnungsinhaltToAttach);
            }
            rechnung.setRechnungsinhaltCollection(attachedRechnungsinhaltCollection);
            Collection<Mahnung> attachedMahnungCollection = new ArrayList<Mahnung>();
            for (Mahnung mahnungCollectionMahnungToAttach : rechnung.getMahnungCollection()) {
                mahnungCollectionMahnungToAttach = em.getReference(mahnungCollectionMahnungToAttach.getClass(), mahnungCollectionMahnungToAttach.getId());
                attachedMahnungCollection.add(mahnungCollectionMahnungToAttach);
            }
            rechnung.setMahnungCollection(attachedMahnungCollection);
            em.persist(rechnung);
            if (mitgliedid != null) {
                mitgliedid.getRechnungCollection().add(rechnung);
                mitgliedid = em.merge(mitgliedid);
            }
            for (Rechnungsinhalt rechnungsinhaltCollectionRechnungsinhalt : rechnung.getRechnungsinhaltCollection()) {
                Rechnung oldRechnungidOfRechnungsinhaltCollectionRechnungsinhalt = rechnungsinhaltCollectionRechnungsinhalt.getRechnungid();
                rechnungsinhaltCollectionRechnungsinhalt.setRechnungid(rechnung);
                rechnungsinhaltCollectionRechnungsinhalt = em.merge(rechnungsinhaltCollectionRechnungsinhalt);
                if (oldRechnungidOfRechnungsinhaltCollectionRechnungsinhalt != null) {
                    oldRechnungidOfRechnungsinhaltCollectionRechnungsinhalt.getRechnungsinhaltCollection().remove(rechnungsinhaltCollectionRechnungsinhalt);
                    oldRechnungidOfRechnungsinhaltCollectionRechnungsinhalt = em.merge(oldRechnungidOfRechnungsinhaltCollectionRechnungsinhalt);
                }
            }
            for (Mahnung mahnungCollectionMahnung : rechnung.getMahnungCollection()) {
                Rechnung oldRechnungidOfMahnungCollectionMahnung = mahnungCollectionMahnung.getRechnungid();
                mahnungCollectionMahnung.setRechnungid(rechnung);
                mahnungCollectionMahnung = em.merge(mahnungCollectionMahnung);
                if (oldRechnungidOfMahnungCollectionMahnung != null) {
                    oldRechnungidOfMahnungCollectionMahnung.getMahnungCollection().remove(mahnungCollectionMahnung);
                    oldRechnungidOfMahnungCollectionMahnung = em.merge(oldRechnungidOfMahnungCollectionMahnung);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rechnung rechnung) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rechnung persistentRechnung = em.find(Rechnung.class, rechnung.getId());
            Mitglied mitgliedidOld = persistentRechnung.getMitgliedid();
            Mitglied mitgliedidNew = rechnung.getMitgliedid();
            Collection<Rechnungsinhalt> rechnungsinhaltCollectionOld = persistentRechnung.getRechnungsinhaltCollection();
            Collection<Rechnungsinhalt> rechnungsinhaltCollectionNew = rechnung.getRechnungsinhaltCollection();
            Collection<Mahnung> mahnungCollectionOld = persistentRechnung.getMahnungCollection();
            Collection<Mahnung> mahnungCollectionNew = rechnung.getMahnungCollection();
            List<String> illegalOrphanMessages = null;
            for (Rechnungsinhalt rechnungsinhaltCollectionOldRechnungsinhalt : rechnungsinhaltCollectionOld) {
                if (!rechnungsinhaltCollectionNew.contains(rechnungsinhaltCollectionOldRechnungsinhalt)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rechnungsinhalt " + rechnungsinhaltCollectionOldRechnungsinhalt + " since its rechnungid field is not nullable.");
                }
            }
            for (Mahnung mahnungCollectionOldMahnung : mahnungCollectionOld) {
                if (!mahnungCollectionNew.contains(mahnungCollectionOldMahnung)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mahnung " + mahnungCollectionOldMahnung + " since its rechnungid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (mitgliedidNew != null) {
                mitgliedidNew = em.getReference(mitgliedidNew.getClass(), mitgliedidNew.getId());
                rechnung.setMitgliedid(mitgliedidNew);
            }
            Collection<Rechnungsinhalt> attachedRechnungsinhaltCollectionNew = new ArrayList<Rechnungsinhalt>();
            for (Rechnungsinhalt rechnungsinhaltCollectionNewRechnungsinhaltToAttach : rechnungsinhaltCollectionNew) {
                rechnungsinhaltCollectionNewRechnungsinhaltToAttach = em.getReference(rechnungsinhaltCollectionNewRechnungsinhaltToAttach.getClass(), rechnungsinhaltCollectionNewRechnungsinhaltToAttach.getId());
                attachedRechnungsinhaltCollectionNew.add(rechnungsinhaltCollectionNewRechnungsinhaltToAttach);
            }
            rechnungsinhaltCollectionNew = attachedRechnungsinhaltCollectionNew;
            rechnung.setRechnungsinhaltCollection(rechnungsinhaltCollectionNew);
            Collection<Mahnung> attachedMahnungCollectionNew = new ArrayList<Mahnung>();
            for (Mahnung mahnungCollectionNewMahnungToAttach : mahnungCollectionNew) {
                mahnungCollectionNewMahnungToAttach = em.getReference(mahnungCollectionNewMahnungToAttach.getClass(), mahnungCollectionNewMahnungToAttach.getId());
                attachedMahnungCollectionNew.add(mahnungCollectionNewMahnungToAttach);
            }
            mahnungCollectionNew = attachedMahnungCollectionNew;
            rechnung.setMahnungCollection(mahnungCollectionNew);
            rechnung = em.merge(rechnung);
            if (mitgliedidOld != null && !mitgliedidOld.equals(mitgliedidNew)) {
                mitgliedidOld.getRechnungCollection().remove(rechnung);
                mitgliedidOld = em.merge(mitgliedidOld);
            }
            if (mitgliedidNew != null && !mitgliedidNew.equals(mitgliedidOld)) {
                mitgliedidNew.getRechnungCollection().add(rechnung);
                mitgliedidNew = em.merge(mitgliedidNew);
            }
            for (Rechnungsinhalt rechnungsinhaltCollectionNewRechnungsinhalt : rechnungsinhaltCollectionNew) {
                if (!rechnungsinhaltCollectionOld.contains(rechnungsinhaltCollectionNewRechnungsinhalt)) {
                    Rechnung oldRechnungidOfRechnungsinhaltCollectionNewRechnungsinhalt = rechnungsinhaltCollectionNewRechnungsinhalt.getRechnungid();
                    rechnungsinhaltCollectionNewRechnungsinhalt.setRechnungid(rechnung);
                    rechnungsinhaltCollectionNewRechnungsinhalt = em.merge(rechnungsinhaltCollectionNewRechnungsinhalt);
                    if (oldRechnungidOfRechnungsinhaltCollectionNewRechnungsinhalt != null && !oldRechnungidOfRechnungsinhaltCollectionNewRechnungsinhalt.equals(rechnung)) {
                        oldRechnungidOfRechnungsinhaltCollectionNewRechnungsinhalt.getRechnungsinhaltCollection().remove(rechnungsinhaltCollectionNewRechnungsinhalt);
                        oldRechnungidOfRechnungsinhaltCollectionNewRechnungsinhalt = em.merge(oldRechnungidOfRechnungsinhaltCollectionNewRechnungsinhalt);
                    }
                }
            }
            for (Mahnung mahnungCollectionNewMahnung : mahnungCollectionNew) {
                if (!mahnungCollectionOld.contains(mahnungCollectionNewMahnung)) {
                    Rechnung oldRechnungidOfMahnungCollectionNewMahnung = mahnungCollectionNewMahnung.getRechnungid();
                    mahnungCollectionNewMahnung.setRechnungid(rechnung);
                    mahnungCollectionNewMahnung = em.merge(mahnungCollectionNewMahnung);
                    if (oldRechnungidOfMahnungCollectionNewMahnung != null && !oldRechnungidOfMahnungCollectionNewMahnung.equals(rechnung)) {
                        oldRechnungidOfMahnungCollectionNewMahnung.getMahnungCollection().remove(mahnungCollectionNewMahnung);
                        oldRechnungidOfMahnungCollectionNewMahnung = em.merge(oldRechnungidOfMahnungCollectionNewMahnung);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rechnung.getId();
                if (findRechnung(id) == null) {
                    throw new NonexistentEntityException("The rechnung with id " + id + " no longer exists.");
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
            Rechnung rechnung;
            try {
                rechnung = em.getReference(Rechnung.class, id);
                rechnung.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rechnung with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Rechnungsinhalt> rechnungsinhaltCollectionOrphanCheck = rechnung.getRechnungsinhaltCollection();
            for (Rechnungsinhalt rechnungsinhaltCollectionOrphanCheckRechnungsinhalt : rechnungsinhaltCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rechnung (" + rechnung + ") cannot be destroyed since the Rechnungsinhalt " + rechnungsinhaltCollectionOrphanCheckRechnungsinhalt + " in its rechnungsinhaltCollection field has a non-nullable rechnungid field.");
            }
            Collection<Mahnung> mahnungCollectionOrphanCheck = rechnung.getMahnungCollection();
            for (Mahnung mahnungCollectionOrphanCheckMahnung : mahnungCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rechnung (" + rechnung + ") cannot be destroyed since the Mahnung " + mahnungCollectionOrphanCheckMahnung + " in its mahnungCollection field has a non-nullable rechnungid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Mitglied mitgliedid = rechnung.getMitgliedid();
            if (mitgliedid != null) {
                mitgliedid.getRechnungCollection().remove(rechnung);
                mitgliedid = em.merge(mitgliedid);
            }
            em.remove(rechnung);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rechnung> findRechnungEntities() {
        return findRechnungEntities(true, -1, -1);
    }

    public List<Rechnung> findRechnungEntities(int maxResults, int firstResult) {
        return findRechnungEntities(false, maxResults, firstResult);
    }

    private List<Rechnung> findRechnungEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rechnung.class));
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

    public Rechnung findRechnung(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rechnung.class, id);
        } finally {
            em.close();
        }
    }

    public int getRechnungCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rechnung> rt = cq.from(Rechnung.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
