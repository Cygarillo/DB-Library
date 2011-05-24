/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.skema.data;

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
public class PreisvertragJpaController implements Serializable {

    public PreisvertragJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Preisvertrag preisvertrag) {
        if (preisvertrag.getRechnungsinhaltCollection() == null) {
            preisvertrag.setRechnungsinhaltCollection(new ArrayList<Rechnungsinhalt>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Zahlungskategorie zahlungskategorieid = preisvertrag.getZahlungskategorieid();
            if (zahlungskategorieid != null) {
                zahlungskategorieid = em.getReference(zahlungskategorieid.getClass(), zahlungskategorieid.getId());
                preisvertrag.setZahlungskategorieid(zahlungskategorieid);
            }
            Zahlungsintervall zahlungsintervallid = preisvertrag.getZahlungsintervallid();
            if (zahlungsintervallid != null) {
                zahlungsintervallid = em.getReference(zahlungsintervallid.getClass(), zahlungsintervallid.getId());
                preisvertrag.setZahlungsintervallid(zahlungsintervallid);
            }
            Rubrik rubrikid = preisvertrag.getRubrikid();
            if (rubrikid != null) {
                rubrikid = em.getReference(rubrikid.getClass(), rubrikid.getId());
                preisvertrag.setRubrikid(rubrikid);
            }
            Collection<Rechnungsinhalt> attachedRechnungsinhaltCollection = new ArrayList<Rechnungsinhalt>();
            for (Rechnungsinhalt rechnungsinhaltCollectionRechnungsinhaltToAttach : preisvertrag.getRechnungsinhaltCollection()) {
                rechnungsinhaltCollectionRechnungsinhaltToAttach = em.getReference(rechnungsinhaltCollectionRechnungsinhaltToAttach.getClass(), rechnungsinhaltCollectionRechnungsinhaltToAttach.getId());
                attachedRechnungsinhaltCollection.add(rechnungsinhaltCollectionRechnungsinhaltToAttach);
            }
            preisvertrag.setRechnungsinhaltCollection(attachedRechnungsinhaltCollection);
            em.persist(preisvertrag);
            if (zahlungskategorieid != null) {
                zahlungskategorieid.getPreisvertragCollection().add(preisvertrag);
                zahlungskategorieid = em.merge(zahlungskategorieid);
            }
            if (zahlungsintervallid != null) {
                zahlungsintervallid.getPreisvertragCollection().add(preisvertrag);
                zahlungsintervallid = em.merge(zahlungsintervallid);
            }
            if (rubrikid != null) {
                rubrikid.getPreisvertragCollection().add(preisvertrag);
                rubrikid = em.merge(rubrikid);
            }
            for (Rechnungsinhalt rechnungsinhaltCollectionRechnungsinhalt : preisvertrag.getRechnungsinhaltCollection()) {
                Preisvertrag oldPreisvertragidOfRechnungsinhaltCollectionRechnungsinhalt = rechnungsinhaltCollectionRechnungsinhalt.getPreisvertragid();
                rechnungsinhaltCollectionRechnungsinhalt.setPreisvertragid(preisvertrag);
                rechnungsinhaltCollectionRechnungsinhalt = em.merge(rechnungsinhaltCollectionRechnungsinhalt);
                if (oldPreisvertragidOfRechnungsinhaltCollectionRechnungsinhalt != null) {
                    oldPreisvertragidOfRechnungsinhaltCollectionRechnungsinhalt.getRechnungsinhaltCollection().remove(rechnungsinhaltCollectionRechnungsinhalt);
                    oldPreisvertragidOfRechnungsinhaltCollectionRechnungsinhalt = em.merge(oldPreisvertragidOfRechnungsinhaltCollectionRechnungsinhalt);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Preisvertrag preisvertrag) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Preisvertrag persistentPreisvertrag = em.find(Preisvertrag.class, preisvertrag.getId());
            Zahlungskategorie zahlungskategorieidOld = persistentPreisvertrag.getZahlungskategorieid();
            Zahlungskategorie zahlungskategorieidNew = preisvertrag.getZahlungskategorieid();
            Zahlungsintervall zahlungsintervallidOld = persistentPreisvertrag.getZahlungsintervallid();
            Zahlungsintervall zahlungsintervallidNew = preisvertrag.getZahlungsintervallid();
            Rubrik rubrikidOld = persistentPreisvertrag.getRubrikid();
            Rubrik rubrikidNew = preisvertrag.getRubrikid();
            Collection<Rechnungsinhalt> rechnungsinhaltCollectionOld = persistentPreisvertrag.getRechnungsinhaltCollection();
            Collection<Rechnungsinhalt> rechnungsinhaltCollectionNew = preisvertrag.getRechnungsinhaltCollection();
            if (zahlungskategorieidNew != null) {
                zahlungskategorieidNew = em.getReference(zahlungskategorieidNew.getClass(), zahlungskategorieidNew.getId());
                preisvertrag.setZahlungskategorieid(zahlungskategorieidNew);
            }
            if (zahlungsintervallidNew != null) {
                zahlungsintervallidNew = em.getReference(zahlungsintervallidNew.getClass(), zahlungsintervallidNew.getId());
                preisvertrag.setZahlungsintervallid(zahlungsintervallidNew);
            }
            if (rubrikidNew != null) {
                rubrikidNew = em.getReference(rubrikidNew.getClass(), rubrikidNew.getId());
                preisvertrag.setRubrikid(rubrikidNew);
            }
            Collection<Rechnungsinhalt> attachedRechnungsinhaltCollectionNew = new ArrayList<Rechnungsinhalt>();
            for (Rechnungsinhalt rechnungsinhaltCollectionNewRechnungsinhaltToAttach : rechnungsinhaltCollectionNew) {
                rechnungsinhaltCollectionNewRechnungsinhaltToAttach = em.getReference(rechnungsinhaltCollectionNewRechnungsinhaltToAttach.getClass(), rechnungsinhaltCollectionNewRechnungsinhaltToAttach.getId());
                attachedRechnungsinhaltCollectionNew.add(rechnungsinhaltCollectionNewRechnungsinhaltToAttach);
            }
            rechnungsinhaltCollectionNew = attachedRechnungsinhaltCollectionNew;
            preisvertrag.setRechnungsinhaltCollection(rechnungsinhaltCollectionNew);
            preisvertrag = em.merge(preisvertrag);
            if (zahlungskategorieidOld != null && !zahlungskategorieidOld.equals(zahlungskategorieidNew)) {
                zahlungskategorieidOld.getPreisvertragCollection().remove(preisvertrag);
                zahlungskategorieidOld = em.merge(zahlungskategorieidOld);
            }
            if (zahlungskategorieidNew != null && !zahlungskategorieidNew.equals(zahlungskategorieidOld)) {
                zahlungskategorieidNew.getPreisvertragCollection().add(preisvertrag);
                zahlungskategorieidNew = em.merge(zahlungskategorieidNew);
            }
            if (zahlungsintervallidOld != null && !zahlungsintervallidOld.equals(zahlungsintervallidNew)) {
                zahlungsintervallidOld.getPreisvertragCollection().remove(preisvertrag);
                zahlungsintervallidOld = em.merge(zahlungsintervallidOld);
            }
            if (zahlungsintervallidNew != null && !zahlungsintervallidNew.equals(zahlungsintervallidOld)) {
                zahlungsintervallidNew.getPreisvertragCollection().add(preisvertrag);
                zahlungsintervallidNew = em.merge(zahlungsintervallidNew);
            }
            if (rubrikidOld != null && !rubrikidOld.equals(rubrikidNew)) {
                rubrikidOld.getPreisvertragCollection().remove(preisvertrag);
                rubrikidOld = em.merge(rubrikidOld);
            }
            if (rubrikidNew != null && !rubrikidNew.equals(rubrikidOld)) {
                rubrikidNew.getPreisvertragCollection().add(preisvertrag);
                rubrikidNew = em.merge(rubrikidNew);
            }
            for (Rechnungsinhalt rechnungsinhaltCollectionOldRechnungsinhalt : rechnungsinhaltCollectionOld) {
                if (!rechnungsinhaltCollectionNew.contains(rechnungsinhaltCollectionOldRechnungsinhalt)) {
                    rechnungsinhaltCollectionOldRechnungsinhalt.setPreisvertragid(null);
                    rechnungsinhaltCollectionOldRechnungsinhalt = em.merge(rechnungsinhaltCollectionOldRechnungsinhalt);
                }
            }
            for (Rechnungsinhalt rechnungsinhaltCollectionNewRechnungsinhalt : rechnungsinhaltCollectionNew) {
                if (!rechnungsinhaltCollectionOld.contains(rechnungsinhaltCollectionNewRechnungsinhalt)) {
                    Preisvertrag oldPreisvertragidOfRechnungsinhaltCollectionNewRechnungsinhalt = rechnungsinhaltCollectionNewRechnungsinhalt.getPreisvertragid();
                    rechnungsinhaltCollectionNewRechnungsinhalt.setPreisvertragid(preisvertrag);
                    rechnungsinhaltCollectionNewRechnungsinhalt = em.merge(rechnungsinhaltCollectionNewRechnungsinhalt);
                    if (oldPreisvertragidOfRechnungsinhaltCollectionNewRechnungsinhalt != null && !oldPreisvertragidOfRechnungsinhaltCollectionNewRechnungsinhalt.equals(preisvertrag)) {
                        oldPreisvertragidOfRechnungsinhaltCollectionNewRechnungsinhalt.getRechnungsinhaltCollection().remove(rechnungsinhaltCollectionNewRechnungsinhalt);
                        oldPreisvertragidOfRechnungsinhaltCollectionNewRechnungsinhalt = em.merge(oldPreisvertragidOfRechnungsinhaltCollectionNewRechnungsinhalt);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = preisvertrag.getId();
                if (findPreisvertrag(id) == null) {
                    throw new NonexistentEntityException("The preisvertrag with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Preisvertrag preisvertrag;
            try {
                preisvertrag = em.getReference(Preisvertrag.class, id);
                preisvertrag.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preisvertrag with id " + id + " no longer exists.", enfe);
            }
            Zahlungskategorie zahlungskategorieid = preisvertrag.getZahlungskategorieid();
            if (zahlungskategorieid != null) {
                zahlungskategorieid.getPreisvertragCollection().remove(preisvertrag);
                zahlungskategorieid = em.merge(zahlungskategorieid);
            }
            Zahlungsintervall zahlungsintervallid = preisvertrag.getZahlungsintervallid();
            if (zahlungsintervallid != null) {
                zahlungsintervallid.getPreisvertragCollection().remove(preisvertrag);
                zahlungsintervallid = em.merge(zahlungsintervallid);
            }
            Rubrik rubrikid = preisvertrag.getRubrikid();
            if (rubrikid != null) {
                rubrikid.getPreisvertragCollection().remove(preisvertrag);
                rubrikid = em.merge(rubrikid);
            }
            Collection<Rechnungsinhalt> rechnungsinhaltCollection = preisvertrag.getRechnungsinhaltCollection();
            for (Rechnungsinhalt rechnungsinhaltCollectionRechnungsinhalt : rechnungsinhaltCollection) {
                rechnungsinhaltCollectionRechnungsinhalt.setPreisvertragid(null);
                rechnungsinhaltCollectionRechnungsinhalt = em.merge(rechnungsinhaltCollectionRechnungsinhalt);
            }
            em.remove(preisvertrag);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Preisvertrag> findPreisvertragEntities() {
        return findPreisvertragEntities(true, -1, -1);
    }

    public List<Preisvertrag> findPreisvertragEntities(int maxResults, int firstResult) {
        return findPreisvertragEntities(false, maxResults, firstResult);
    }

    private List<Preisvertrag> findPreisvertragEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Preisvertrag.class));
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

    public Preisvertrag findPreisvertrag(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Preisvertrag.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreisvertragCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Preisvertrag> rt = cq.from(Preisvertrag.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
