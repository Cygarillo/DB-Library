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
public class AdresswechselJpaController implements Serializable {

    public AdresswechselJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Adresswechsel adresswechsel) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mitglied mitgliedid = adresswechsel.getMitgliedid();
            if (mitgliedid != null) {
                mitgliedid = em.getReference(mitgliedid.getClass(), mitgliedid.getId());
                adresswechsel.setMitgliedid(mitgliedid);
            }
            em.persist(adresswechsel);
            if (mitgliedid != null) {
                mitgliedid.getAdresswechselCollection().add(adresswechsel);
                mitgliedid = em.merge(mitgliedid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Adresswechsel adresswechsel) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Adresswechsel persistentAdresswechsel = em.find(Adresswechsel.class, adresswechsel.getId());
            Mitglied mitgliedidOld = persistentAdresswechsel.getMitgliedid();
            Mitglied mitgliedidNew = adresswechsel.getMitgliedid();
            if (mitgliedidNew != null) {
                mitgliedidNew = em.getReference(mitgliedidNew.getClass(), mitgliedidNew.getId());
                adresswechsel.setMitgliedid(mitgliedidNew);
            }
            adresswechsel = em.merge(adresswechsel);
            if (mitgliedidOld != null && !mitgliedidOld.equals(mitgliedidNew)) {
                mitgliedidOld.getAdresswechselCollection().remove(adresswechsel);
                mitgliedidOld = em.merge(mitgliedidOld);
            }
            if (mitgliedidNew != null && !mitgliedidNew.equals(mitgliedidOld)) {
                mitgliedidNew.getAdresswechselCollection().add(adresswechsel);
                mitgliedidNew = em.merge(mitgliedidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = adresswechsel.getId();
                if (findAdresswechsel(id) == null) {
                    throw new NonexistentEntityException("The adresswechsel with id " + id + " no longer exists.");
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
            Adresswechsel adresswechsel;
            try {
                adresswechsel = em.getReference(Adresswechsel.class, id);
                adresswechsel.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adresswechsel with id " + id + " no longer exists.", enfe);
            }
            Mitglied mitgliedid = adresswechsel.getMitgliedid();
            if (mitgliedid != null) {
                mitgliedid.getAdresswechselCollection().remove(adresswechsel);
                mitgliedid = em.merge(mitgliedid);
            }
            em.remove(adresswechsel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Adresswechsel> findAdresswechselEntities() {
        return findAdresswechselEntities(true, -1, -1);
    }

    public List<Adresswechsel> findAdresswechselEntities(int maxResults, int firstResult) {
        return findAdresswechselEntities(false, maxResults, firstResult);
    }

    private List<Adresswechsel> findAdresswechselEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Adresswechsel.class));
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

    public Adresswechsel findAdresswechsel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Adresswechsel.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdresswechselCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Adresswechsel> rt = cq.from(Adresswechsel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
