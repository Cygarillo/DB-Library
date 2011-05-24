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

/**
 *
 * @author Cyrill
 */
public class RechnungsinhaltJpaController implements Serializable {

    public RechnungsinhaltJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rechnungsinhalt rechnungsinhalt) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rechnung rechnungid = rechnungsinhalt.getRechnungid();
            if (rechnungid != null) {
                rechnungid = em.getReference(rechnungid.getClass(), rechnungid.getId());
                rechnungsinhalt.setRechnungid(rechnungid);
            }
            Preisvertrag preisvertragid = rechnungsinhalt.getPreisvertragid();
            if (preisvertragid != null) {
                preisvertragid = em.getReference(preisvertragid.getClass(), preisvertragid.getId());
                rechnungsinhalt.setPreisvertragid(preisvertragid);
            }
            Preisallgemein preisallgemeinid = rechnungsinhalt.getPreisallgemeinid();
            if (preisallgemeinid != null) {
                preisallgemeinid = em.getReference(preisallgemeinid.getClass(), preisallgemeinid.getId());
                rechnungsinhalt.setPreisallgemeinid(preisallgemeinid);
            }
            em.persist(rechnungsinhalt);
            if (rechnungid != null) {
                rechnungid.getRechnungsinhaltCollection().add(rechnungsinhalt);
                rechnungid = em.merge(rechnungid);
            }
            if (preisvertragid != null) {
                preisvertragid.getRechnungsinhaltCollection().add(rechnungsinhalt);
                preisvertragid = em.merge(preisvertragid);
            }
            if (preisallgemeinid != null) {
                preisallgemeinid.getRechnungsinhaltCollection().add(rechnungsinhalt);
                preisallgemeinid = em.merge(preisallgemeinid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rechnungsinhalt rechnungsinhalt) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rechnungsinhalt persistentRechnungsinhalt = em.find(Rechnungsinhalt.class, rechnungsinhalt.getId());
            Rechnung rechnungidOld = persistentRechnungsinhalt.getRechnungid();
            Rechnung rechnungidNew = rechnungsinhalt.getRechnungid();
            Preisvertrag preisvertragidOld = persistentRechnungsinhalt.getPreisvertragid();
            Preisvertrag preisvertragidNew = rechnungsinhalt.getPreisvertragid();
            Preisallgemein preisallgemeinidOld = persistentRechnungsinhalt.getPreisallgemeinid();
            Preisallgemein preisallgemeinidNew = rechnungsinhalt.getPreisallgemeinid();
            if (rechnungidNew != null) {
                rechnungidNew = em.getReference(rechnungidNew.getClass(), rechnungidNew.getId());
                rechnungsinhalt.setRechnungid(rechnungidNew);
            }
            if (preisvertragidNew != null) {
                preisvertragidNew = em.getReference(preisvertragidNew.getClass(), preisvertragidNew.getId());
                rechnungsinhalt.setPreisvertragid(preisvertragidNew);
            }
            if (preisallgemeinidNew != null) {
                preisallgemeinidNew = em.getReference(preisallgemeinidNew.getClass(), preisallgemeinidNew.getId());
                rechnungsinhalt.setPreisallgemeinid(preisallgemeinidNew);
            }
            rechnungsinhalt = em.merge(rechnungsinhalt);
            if (rechnungidOld != null && !rechnungidOld.equals(rechnungidNew)) {
                rechnungidOld.getRechnungsinhaltCollection().remove(rechnungsinhalt);
                rechnungidOld = em.merge(rechnungidOld);
            }
            if (rechnungidNew != null && !rechnungidNew.equals(rechnungidOld)) {
                rechnungidNew.getRechnungsinhaltCollection().add(rechnungsinhalt);
                rechnungidNew = em.merge(rechnungidNew);
            }
            if (preisvertragidOld != null && !preisvertragidOld.equals(preisvertragidNew)) {
                preisvertragidOld.getRechnungsinhaltCollection().remove(rechnungsinhalt);
                preisvertragidOld = em.merge(preisvertragidOld);
            }
            if (preisvertragidNew != null && !preisvertragidNew.equals(preisvertragidOld)) {
                preisvertragidNew.getRechnungsinhaltCollection().add(rechnungsinhalt);
                preisvertragidNew = em.merge(preisvertragidNew);
            }
            if (preisallgemeinidOld != null && !preisallgemeinidOld.equals(preisallgemeinidNew)) {
                preisallgemeinidOld.getRechnungsinhaltCollection().remove(rechnungsinhalt);
                preisallgemeinidOld = em.merge(preisallgemeinidOld);
            }
            if (preisallgemeinidNew != null && !preisallgemeinidNew.equals(preisallgemeinidOld)) {
                preisallgemeinidNew.getRechnungsinhaltCollection().add(rechnungsinhalt);
                preisallgemeinidNew = em.merge(preisallgemeinidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rechnungsinhalt.getId();
                if (findRechnungsinhalt(id) == null) {
                    throw new NonexistentEntityException("The rechnungsinhalt with id " + id + " no longer exists.");
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
            Rechnungsinhalt rechnungsinhalt;
            try {
                rechnungsinhalt = em.getReference(Rechnungsinhalt.class, id);
                rechnungsinhalt.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rechnungsinhalt with id " + id + " no longer exists.", enfe);
            }
            Rechnung rechnungid = rechnungsinhalt.getRechnungid();
            if (rechnungid != null) {
                rechnungid.getRechnungsinhaltCollection().remove(rechnungsinhalt);
                rechnungid = em.merge(rechnungid);
            }
            Preisvertrag preisvertragid = rechnungsinhalt.getPreisvertragid();
            if (preisvertragid != null) {
                preisvertragid.getRechnungsinhaltCollection().remove(rechnungsinhalt);
                preisvertragid = em.merge(preisvertragid);
            }
            Preisallgemein preisallgemeinid = rechnungsinhalt.getPreisallgemeinid();
            if (preisallgemeinid != null) {
                preisallgemeinid.getRechnungsinhaltCollection().remove(rechnungsinhalt);
                preisallgemeinid = em.merge(preisallgemeinid);
            }
            em.remove(rechnungsinhalt);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rechnungsinhalt> findRechnungsinhaltEntities() {
        return findRechnungsinhaltEntities(true, -1, -1);
    }

    public List<Rechnungsinhalt> findRechnungsinhaltEntities(int maxResults, int firstResult) {
        return findRechnungsinhaltEntities(false, maxResults, firstResult);
    }

    private List<Rechnungsinhalt> findRechnungsinhaltEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rechnungsinhalt.class));
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

    public Rechnungsinhalt findRechnungsinhalt(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rechnungsinhalt.class, id);
        } finally {
            em.close();
        }
    }

    public int getRechnungsinhaltCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rechnungsinhalt> rt = cq.from(Rechnungsinhalt.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
