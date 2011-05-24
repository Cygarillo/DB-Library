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
public class VertragsstopJpaController implements Serializable {

    public VertragsstopJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vertragsstop vertragsstop) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vertrag vertragid = vertragsstop.getVertragid();
            if (vertragid != null) {
                vertragid = em.getReference(vertragid.getClass(), vertragid.getId());
                vertragsstop.setVertragid(vertragid);
            }
            em.persist(vertragsstop);
            if (vertragid != null) {
                vertragid.getVertragsstopCollection().add(vertragsstop);
                vertragid = em.merge(vertragid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vertragsstop vertragsstop) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vertragsstop persistentVertragsstop = em.find(Vertragsstop.class, vertragsstop.getId());
            Vertrag vertragidOld = persistentVertragsstop.getVertragid();
            Vertrag vertragidNew = vertragsstop.getVertragid();
            if (vertragidNew != null) {
                vertragidNew = em.getReference(vertragidNew.getClass(), vertragidNew.getId());
                vertragsstop.setVertragid(vertragidNew);
            }
            vertragsstop = em.merge(vertragsstop);
            if (vertragidOld != null && !vertragidOld.equals(vertragidNew)) {
                vertragidOld.getVertragsstopCollection().remove(vertragsstop);
                vertragidOld = em.merge(vertragidOld);
            }
            if (vertragidNew != null && !vertragidNew.equals(vertragidOld)) {
                vertragidNew.getVertragsstopCollection().add(vertragsstop);
                vertragidNew = em.merge(vertragidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vertragsstop.getId();
                if (findVertragsstop(id) == null) {
                    throw new NonexistentEntityException("The vertragsstop with id " + id + " no longer exists.");
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
            Vertragsstop vertragsstop;
            try {
                vertragsstop = em.getReference(Vertragsstop.class, id);
                vertragsstop.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vertragsstop with id " + id + " no longer exists.", enfe);
            }
            Vertrag vertragid = vertragsstop.getVertragid();
            if (vertragid != null) {
                vertragid.getVertragsstopCollection().remove(vertragsstop);
                vertragid = em.merge(vertragid);
            }
            em.remove(vertragsstop);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vertragsstop> findVertragsstopEntities() {
        return findVertragsstopEntities(true, -1, -1);
    }

    public List<Vertragsstop> findVertragsstopEntities(int maxResults, int firstResult) {
        return findVertragsstopEntities(false, maxResults, firstResult);
    }

    private List<Vertragsstop> findVertragsstopEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vertragsstop.class));
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

    public Vertragsstop findVertragsstop(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vertragsstop.class, id);
        } finally {
            em.close();
        }
    }

    public int getVertragsstopCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vertragsstop> rt = cq.from(Vertragsstop.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
