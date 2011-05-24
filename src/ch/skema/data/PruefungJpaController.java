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
public class PruefungJpaController implements Serializable {

    public PruefungJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pruefung pruefung) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pruefungslevel pruefungslevelid = pruefung.getPruefungslevelid();
            if (pruefungslevelid != null) {
                pruefungslevelid = em.getReference(pruefungslevelid.getClass(), pruefungslevelid.getId());
                pruefung.setPruefungslevelid(pruefungslevelid);
            }
            Mitglied mitgliedid = pruefung.getMitgliedid();
            if (mitgliedid != null) {
                mitgliedid = em.getReference(mitgliedid.getClass(), mitgliedid.getId());
                pruefung.setMitgliedid(mitgliedid);
            }
            em.persist(pruefung);
            if (pruefungslevelid != null) {
                pruefungslevelid.getPruefungCollection().add(pruefung);
                pruefungslevelid = em.merge(pruefungslevelid);
            }
            if (mitgliedid != null) {
                mitgliedid.getPruefungCollection().add(pruefung);
                mitgliedid = em.merge(mitgliedid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pruefung pruefung) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pruefung persistentPruefung = em.find(Pruefung.class, pruefung.getId());
            Pruefungslevel pruefungslevelidOld = persistentPruefung.getPruefungslevelid();
            Pruefungslevel pruefungslevelidNew = pruefung.getPruefungslevelid();
            Mitglied mitgliedidOld = persistentPruefung.getMitgliedid();
            Mitglied mitgliedidNew = pruefung.getMitgliedid();
            if (pruefungslevelidNew != null) {
                pruefungslevelidNew = em.getReference(pruefungslevelidNew.getClass(), pruefungslevelidNew.getId());
                pruefung.setPruefungslevelid(pruefungslevelidNew);
            }
            if (mitgliedidNew != null) {
                mitgliedidNew = em.getReference(mitgliedidNew.getClass(), mitgliedidNew.getId());
                pruefung.setMitgliedid(mitgliedidNew);
            }
            pruefung = em.merge(pruefung);
            if (pruefungslevelidOld != null && !pruefungslevelidOld.equals(pruefungslevelidNew)) {
                pruefungslevelidOld.getPruefungCollection().remove(pruefung);
                pruefungslevelidOld = em.merge(pruefungslevelidOld);
            }
            if (pruefungslevelidNew != null && !pruefungslevelidNew.equals(pruefungslevelidOld)) {
                pruefungslevelidNew.getPruefungCollection().add(pruefung);
                pruefungslevelidNew = em.merge(pruefungslevelidNew);
            }
            if (mitgliedidOld != null && !mitgliedidOld.equals(mitgliedidNew)) {
                mitgliedidOld.getPruefungCollection().remove(pruefung);
                mitgliedidOld = em.merge(mitgliedidOld);
            }
            if (mitgliedidNew != null && !mitgliedidNew.equals(mitgliedidOld)) {
                mitgliedidNew.getPruefungCollection().add(pruefung);
                mitgliedidNew = em.merge(mitgliedidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pruefung.getId();
                if (findPruefung(id) == null) {
                    throw new NonexistentEntityException("The pruefung with id " + id + " no longer exists.");
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
            Pruefung pruefung;
            try {
                pruefung = em.getReference(Pruefung.class, id);
                pruefung.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pruefung with id " + id + " no longer exists.", enfe);
            }
            Pruefungslevel pruefungslevelid = pruefung.getPruefungslevelid();
            if (pruefungslevelid != null) {
                pruefungslevelid.getPruefungCollection().remove(pruefung);
                pruefungslevelid = em.merge(pruefungslevelid);
            }
            Mitglied mitgliedid = pruefung.getMitgliedid();
            if (mitgliedid != null) {
                mitgliedid.getPruefungCollection().remove(pruefung);
                mitgliedid = em.merge(mitgliedid);
            }
            em.remove(pruefung);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pruefung> findPruefungEntities() {
        return findPruefungEntities(true, -1, -1);
    }

    public List<Pruefung> findPruefungEntities(int maxResults, int firstResult) {
        return findPruefungEntities(false, maxResults, firstResult);
    }

    private List<Pruefung> findPruefungEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pruefung.class));
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

    public Pruefung findPruefung(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pruefung.class, id);
        } finally {
            em.close();
        }
    }

    public int getPruefungCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pruefung> rt = cq.from(Pruefung.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
