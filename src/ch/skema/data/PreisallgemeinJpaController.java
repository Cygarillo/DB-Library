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
public class PreisallgemeinJpaController implements Serializable {

    public PreisallgemeinJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Preisallgemein preisallgemein) {
        if (preisallgemein.getRechnungsinhaltCollection() == null) {
            preisallgemein.setRechnungsinhaltCollection(new ArrayList<Rechnungsinhalt>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Rechnungsinhalt> attachedRechnungsinhaltCollection = new ArrayList<Rechnungsinhalt>();
            for (Rechnungsinhalt rechnungsinhaltCollectionRechnungsinhaltToAttach : preisallgemein.getRechnungsinhaltCollection()) {
                rechnungsinhaltCollectionRechnungsinhaltToAttach = em.getReference(rechnungsinhaltCollectionRechnungsinhaltToAttach.getClass(), rechnungsinhaltCollectionRechnungsinhaltToAttach.getId());
                attachedRechnungsinhaltCollection.add(rechnungsinhaltCollectionRechnungsinhaltToAttach);
            }
            preisallgemein.setRechnungsinhaltCollection(attachedRechnungsinhaltCollection);
            em.persist(preisallgemein);
            for (Rechnungsinhalt rechnungsinhaltCollectionRechnungsinhalt : preisallgemein.getRechnungsinhaltCollection()) {
                Preisallgemein oldPreisallgemeinidOfRechnungsinhaltCollectionRechnungsinhalt = rechnungsinhaltCollectionRechnungsinhalt.getPreisallgemeinid();
                rechnungsinhaltCollectionRechnungsinhalt.setPreisallgemeinid(preisallgemein);
                rechnungsinhaltCollectionRechnungsinhalt = em.merge(rechnungsinhaltCollectionRechnungsinhalt);
                if (oldPreisallgemeinidOfRechnungsinhaltCollectionRechnungsinhalt != null) {
                    oldPreisallgemeinidOfRechnungsinhaltCollectionRechnungsinhalt.getRechnungsinhaltCollection().remove(rechnungsinhaltCollectionRechnungsinhalt);
                    oldPreisallgemeinidOfRechnungsinhaltCollectionRechnungsinhalt = em.merge(oldPreisallgemeinidOfRechnungsinhaltCollectionRechnungsinhalt);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Preisallgemein preisallgemein) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Preisallgemein persistentPreisallgemein = em.find(Preisallgemein.class, preisallgemein.getId());
            Collection<Rechnungsinhalt> rechnungsinhaltCollectionOld = persistentPreisallgemein.getRechnungsinhaltCollection();
            Collection<Rechnungsinhalt> rechnungsinhaltCollectionNew = preisallgemein.getRechnungsinhaltCollection();
            Collection<Rechnungsinhalt> attachedRechnungsinhaltCollectionNew = new ArrayList<Rechnungsinhalt>();
            for (Rechnungsinhalt rechnungsinhaltCollectionNewRechnungsinhaltToAttach : rechnungsinhaltCollectionNew) {
                rechnungsinhaltCollectionNewRechnungsinhaltToAttach = em.getReference(rechnungsinhaltCollectionNewRechnungsinhaltToAttach.getClass(), rechnungsinhaltCollectionNewRechnungsinhaltToAttach.getId());
                attachedRechnungsinhaltCollectionNew.add(rechnungsinhaltCollectionNewRechnungsinhaltToAttach);
            }
            rechnungsinhaltCollectionNew = attachedRechnungsinhaltCollectionNew;
            preisallgemein.setRechnungsinhaltCollection(rechnungsinhaltCollectionNew);
            preisallgemein = em.merge(preisallgemein);
            for (Rechnungsinhalt rechnungsinhaltCollectionOldRechnungsinhalt : rechnungsinhaltCollectionOld) {
                if (!rechnungsinhaltCollectionNew.contains(rechnungsinhaltCollectionOldRechnungsinhalt)) {
                    rechnungsinhaltCollectionOldRechnungsinhalt.setPreisallgemeinid(null);
                    rechnungsinhaltCollectionOldRechnungsinhalt = em.merge(rechnungsinhaltCollectionOldRechnungsinhalt);
                }
            }
            for (Rechnungsinhalt rechnungsinhaltCollectionNewRechnungsinhalt : rechnungsinhaltCollectionNew) {
                if (!rechnungsinhaltCollectionOld.contains(rechnungsinhaltCollectionNewRechnungsinhalt)) {
                    Preisallgemein oldPreisallgemeinidOfRechnungsinhaltCollectionNewRechnungsinhalt = rechnungsinhaltCollectionNewRechnungsinhalt.getPreisallgemeinid();
                    rechnungsinhaltCollectionNewRechnungsinhalt.setPreisallgemeinid(preisallgemein);
                    rechnungsinhaltCollectionNewRechnungsinhalt = em.merge(rechnungsinhaltCollectionNewRechnungsinhalt);
                    if (oldPreisallgemeinidOfRechnungsinhaltCollectionNewRechnungsinhalt != null && !oldPreisallgemeinidOfRechnungsinhaltCollectionNewRechnungsinhalt.equals(preisallgemein)) {
                        oldPreisallgemeinidOfRechnungsinhaltCollectionNewRechnungsinhalt.getRechnungsinhaltCollection().remove(rechnungsinhaltCollectionNewRechnungsinhalt);
                        oldPreisallgemeinidOfRechnungsinhaltCollectionNewRechnungsinhalt = em.merge(oldPreisallgemeinidOfRechnungsinhaltCollectionNewRechnungsinhalt);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = preisallgemein.getId();
                if (findPreisallgemein(id) == null) {
                    throw new NonexistentEntityException("The preisallgemein with id " + id + " no longer exists.");
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
            Preisallgemein preisallgemein;
            try {
                preisallgemein = em.getReference(Preisallgemein.class, id);
                preisallgemein.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preisallgemein with id " + id + " no longer exists.", enfe);
            }
            Collection<Rechnungsinhalt> rechnungsinhaltCollection = preisallgemein.getRechnungsinhaltCollection();
            for (Rechnungsinhalt rechnungsinhaltCollectionRechnungsinhalt : rechnungsinhaltCollection) {
                rechnungsinhaltCollectionRechnungsinhalt.setPreisallgemeinid(null);
                rechnungsinhaltCollectionRechnungsinhalt = em.merge(rechnungsinhaltCollectionRechnungsinhalt);
            }
            em.remove(preisallgemein);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Preisallgemein> findPreisallgemeinEntities() {
        return findPreisallgemeinEntities(true, -1, -1);
    }

    public List<Preisallgemein> findPreisallgemeinEntities(int maxResults, int firstResult) {
        return findPreisallgemeinEntities(false, maxResults, firstResult);
    }

    private List<Preisallgemein> findPreisallgemeinEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Preisallgemein.class));
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

    public Preisallgemein findPreisallgemein(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Preisallgemein.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreisallgemeinCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Preisallgemein> rt = cq.from(Preisallgemein.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
