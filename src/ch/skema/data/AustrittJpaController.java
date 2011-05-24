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
public class AustrittJpaController implements Serializable {

    public AustrittJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Austritt austritt) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mitglied mitgliedid = austritt.getMitgliedid();
            if (mitgliedid != null) {
                mitgliedid = em.getReference(mitgliedid.getClass(), mitgliedid.getId());
                austritt.setMitgliedid(mitgliedid);
            }
            em.persist(austritt);
            if (mitgliedid != null) {
                mitgliedid.getAustrittCollection().add(austritt);
                mitgliedid = em.merge(mitgliedid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Austritt austritt) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Austritt persistentAustritt = em.find(Austritt.class, austritt.getId());
            Mitglied mitgliedidOld = persistentAustritt.getMitgliedid();
            Mitglied mitgliedidNew = austritt.getMitgliedid();
            if (mitgliedidNew != null) {
                mitgliedidNew = em.getReference(mitgliedidNew.getClass(), mitgliedidNew.getId());
                austritt.setMitgliedid(mitgliedidNew);
            }
            austritt = em.merge(austritt);
            if (mitgliedidOld != null && !mitgliedidOld.equals(mitgliedidNew)) {
                mitgliedidOld.getAustrittCollection().remove(austritt);
                mitgliedidOld = em.merge(mitgliedidOld);
            }
            if (mitgliedidNew != null && !mitgliedidNew.equals(mitgliedidOld)) {
                mitgliedidNew.getAustrittCollection().add(austritt);
                mitgliedidNew = em.merge(mitgliedidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = austritt.getId();
                if (findAustritt(id) == null) {
                    throw new NonexistentEntityException("The austritt with id " + id + " no longer exists.");
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
            Austritt austritt;
            try {
                austritt = em.getReference(Austritt.class, id);
                austritt.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The austritt with id " + id + " no longer exists.", enfe);
            }
            Mitglied mitgliedid = austritt.getMitgliedid();
            if (mitgliedid != null) {
                mitgliedid.getAustrittCollection().remove(austritt);
                mitgliedid = em.merge(mitgliedid);
            }
            em.remove(austritt);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Austritt> findAustrittEntities() {
        return findAustrittEntities(true, -1, -1);
    }

    public List<Austritt> findAustrittEntities(int maxResults, int firstResult) {
        return findAustrittEntities(false, maxResults, firstResult);
    }

    private List<Austritt> findAustrittEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Austritt.class));
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

    public Austritt findAustritt(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Austritt.class, id);
        } finally {
            em.close();
        }
    }

    public int getAustrittCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Austritt> rt = cq.from(Austritt.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
