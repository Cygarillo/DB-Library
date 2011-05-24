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
public class MahnungJpaController implements Serializable {

    public MahnungJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mahnung mahnung) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rechnung rechnungid = mahnung.getRechnungid();
            if (rechnungid != null) {
                rechnungid = em.getReference(rechnungid.getClass(), rechnungid.getId());
                mahnung.setRechnungid(rechnungid);
            }
            em.persist(mahnung);
            if (rechnungid != null) {
                rechnungid.getMahnungCollection().add(mahnung);
                rechnungid = em.merge(rechnungid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mahnung mahnung) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mahnung persistentMahnung = em.find(Mahnung.class, mahnung.getId());
            Rechnung rechnungidOld = persistentMahnung.getRechnungid();
            Rechnung rechnungidNew = mahnung.getRechnungid();
            if (rechnungidNew != null) {
                rechnungidNew = em.getReference(rechnungidNew.getClass(), rechnungidNew.getId());
                mahnung.setRechnungid(rechnungidNew);
            }
            mahnung = em.merge(mahnung);
            if (rechnungidOld != null && !rechnungidOld.equals(rechnungidNew)) {
                rechnungidOld.getMahnungCollection().remove(mahnung);
                rechnungidOld = em.merge(rechnungidOld);
            }
            if (rechnungidNew != null && !rechnungidNew.equals(rechnungidOld)) {
                rechnungidNew.getMahnungCollection().add(mahnung);
                rechnungidNew = em.merge(rechnungidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mahnung.getId();
                if (findMahnung(id) == null) {
                    throw new NonexistentEntityException("The mahnung with id " + id + " no longer exists.");
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
            Mahnung mahnung;
            try {
                mahnung = em.getReference(Mahnung.class, id);
                mahnung.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mahnung with id " + id + " no longer exists.", enfe);
            }
            Rechnung rechnungid = mahnung.getRechnungid();
            if (rechnungid != null) {
                rechnungid.getMahnungCollection().remove(mahnung);
                rechnungid = em.merge(rechnungid);
            }
            em.remove(mahnung);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mahnung> findMahnungEntities() {
        return findMahnungEntities(true, -1, -1);
    }

    public List<Mahnung> findMahnungEntities(int maxResults, int firstResult) {
        return findMahnungEntities(false, maxResults, firstResult);
    }

    private List<Mahnung> findMahnungEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mahnung.class));
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

    public Mahnung findMahnung(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mahnung.class, id);
        } finally {
            em.close();
        }
    }

    public int getMahnungCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mahnung> rt = cq.from(Mahnung.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
