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
public class VertragJpaController implements Serializable {

    public VertragJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vertrag vertrag) {
        if (vertrag.getVertragsstopCollection() == null) {
            vertrag.setVertragsstopCollection(new ArrayList<Vertragsstop>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Zahlungsintervall intervallid = vertrag.getIntervallid();
            if (intervallid != null) {
                intervallid = em.getReference(intervallid.getClass(), intervallid.getId());
                vertrag.setIntervallid(intervallid);
            }
            Rubrik rubrikid = vertrag.getRubrikid();
            if (rubrikid != null) {
                rubrikid = em.getReference(rubrikid.getClass(), rubrikid.getId());
                vertrag.setRubrikid(rubrikid);
            }
            Mitglied mitgliederid = vertrag.getMitgliederid();
            if (mitgliederid != null) {
                mitgliederid = em.getReference(mitgliederid.getClass(), mitgliederid.getId());
                vertrag.setMitgliederid(mitgliederid);
            }
            Collection<Vertragsstop> attachedVertragsstopCollection = new ArrayList<Vertragsstop>();
            for (Vertragsstop vertragsstopCollectionVertragsstopToAttach : vertrag.getVertragsstopCollection()) {
                vertragsstopCollectionVertragsstopToAttach = em.getReference(vertragsstopCollectionVertragsstopToAttach.getClass(), vertragsstopCollectionVertragsstopToAttach.getId());
                attachedVertragsstopCollection.add(vertragsstopCollectionVertragsstopToAttach);
            }
            vertrag.setVertragsstopCollection(attachedVertragsstopCollection);
            em.persist(vertrag);
            if (intervallid != null) {
                intervallid.getVertragCollection().add(vertrag);
                intervallid = em.merge(intervallid);
            }
            if (rubrikid != null) {
                rubrikid.getVertragCollection().add(vertrag);
                rubrikid = em.merge(rubrikid);
            }
            if (mitgliederid != null) {
                mitgliederid.getVertragCollection().add(vertrag);
                mitgliederid = em.merge(mitgliederid);
            }
            for (Vertragsstop vertragsstopCollectionVertragsstop : vertrag.getVertragsstopCollection()) {
                Vertrag oldVertragidOfVertragsstopCollectionVertragsstop = vertragsstopCollectionVertragsstop.getVertragid();
                vertragsstopCollectionVertragsstop.setVertragid(vertrag);
                vertragsstopCollectionVertragsstop = em.merge(vertragsstopCollectionVertragsstop);
                if (oldVertragidOfVertragsstopCollectionVertragsstop != null) {
                    oldVertragidOfVertragsstopCollectionVertragsstop.getVertragsstopCollection().remove(vertragsstopCollectionVertragsstop);
                    oldVertragidOfVertragsstopCollectionVertragsstop = em.merge(oldVertragidOfVertragsstopCollectionVertragsstop);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vertrag vertrag) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vertrag persistentVertrag = em.find(Vertrag.class, vertrag.getId());
            Zahlungsintervall intervallidOld = persistentVertrag.getIntervallid();
            Zahlungsintervall intervallidNew = vertrag.getIntervallid();
            Rubrik rubrikidOld = persistentVertrag.getRubrikid();
            Rubrik rubrikidNew = vertrag.getRubrikid();
            Mitglied mitgliederidOld = persistentVertrag.getMitgliederid();
            Mitglied mitgliederidNew = vertrag.getMitgliederid();
            Collection<Vertragsstop> vertragsstopCollectionOld = persistentVertrag.getVertragsstopCollection();
            Collection<Vertragsstop> vertragsstopCollectionNew = vertrag.getVertragsstopCollection();
            List<String> illegalOrphanMessages = null;
            for (Vertragsstop vertragsstopCollectionOldVertragsstop : vertragsstopCollectionOld) {
                if (!vertragsstopCollectionNew.contains(vertragsstopCollectionOldVertragsstop)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vertragsstop " + vertragsstopCollectionOldVertragsstop + " since its vertragid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (intervallidNew != null) {
                intervallidNew = em.getReference(intervallidNew.getClass(), intervallidNew.getId());
                vertrag.setIntervallid(intervallidNew);
            }
            if (rubrikidNew != null) {
                rubrikidNew = em.getReference(rubrikidNew.getClass(), rubrikidNew.getId());
                vertrag.setRubrikid(rubrikidNew);
            }
            if (mitgliederidNew != null) {
                mitgliederidNew = em.getReference(mitgliederidNew.getClass(), mitgliederidNew.getId());
                vertrag.setMitgliederid(mitgliederidNew);
            }
            Collection<Vertragsstop> attachedVertragsstopCollectionNew = new ArrayList<Vertragsstop>();
            for (Vertragsstop vertragsstopCollectionNewVertragsstopToAttach : vertragsstopCollectionNew) {
                vertragsstopCollectionNewVertragsstopToAttach = em.getReference(vertragsstopCollectionNewVertragsstopToAttach.getClass(), vertragsstopCollectionNewVertragsstopToAttach.getId());
                attachedVertragsstopCollectionNew.add(vertragsstopCollectionNewVertragsstopToAttach);
            }
            vertragsstopCollectionNew = attachedVertragsstopCollectionNew;
            vertrag.setVertragsstopCollection(vertragsstopCollectionNew);
            vertrag = em.merge(vertrag);
            if (intervallidOld != null && !intervallidOld.equals(intervallidNew)) {
                intervallidOld.getVertragCollection().remove(vertrag);
                intervallidOld = em.merge(intervallidOld);
            }
            if (intervallidNew != null && !intervallidNew.equals(intervallidOld)) {
                intervallidNew.getVertragCollection().add(vertrag);
                intervallidNew = em.merge(intervallidNew);
            }
            if (rubrikidOld != null && !rubrikidOld.equals(rubrikidNew)) {
                rubrikidOld.getVertragCollection().remove(vertrag);
                rubrikidOld = em.merge(rubrikidOld);
            }
            if (rubrikidNew != null && !rubrikidNew.equals(rubrikidOld)) {
                rubrikidNew.getVertragCollection().add(vertrag);
                rubrikidNew = em.merge(rubrikidNew);
            }
            if (mitgliederidOld != null && !mitgliederidOld.equals(mitgliederidNew)) {
                mitgliederidOld.getVertragCollection().remove(vertrag);
                mitgliederidOld = em.merge(mitgliederidOld);
            }
            if (mitgliederidNew != null && !mitgliederidNew.equals(mitgliederidOld)) {
                mitgliederidNew.getVertragCollection().add(vertrag);
                mitgliederidNew = em.merge(mitgliederidNew);
            }
            for (Vertragsstop vertragsstopCollectionNewVertragsstop : vertragsstopCollectionNew) {
                if (!vertragsstopCollectionOld.contains(vertragsstopCollectionNewVertragsstop)) {
                    Vertrag oldVertragidOfVertragsstopCollectionNewVertragsstop = vertragsstopCollectionNewVertragsstop.getVertragid();
                    vertragsstopCollectionNewVertragsstop.setVertragid(vertrag);
                    vertragsstopCollectionNewVertragsstop = em.merge(vertragsstopCollectionNewVertragsstop);
                    if (oldVertragidOfVertragsstopCollectionNewVertragsstop != null && !oldVertragidOfVertragsstopCollectionNewVertragsstop.equals(vertrag)) {
                        oldVertragidOfVertragsstopCollectionNewVertragsstop.getVertragsstopCollection().remove(vertragsstopCollectionNewVertragsstop);
                        oldVertragidOfVertragsstopCollectionNewVertragsstop = em.merge(oldVertragidOfVertragsstopCollectionNewVertragsstop);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vertrag.getId();
                if (findVertrag(id) == null) {
                    throw new NonexistentEntityException("The vertrag with id " + id + " no longer exists.");
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
            Vertrag vertrag;
            try {
                vertrag = em.getReference(Vertrag.class, id);
                vertrag.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vertrag with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Vertragsstop> vertragsstopCollectionOrphanCheck = vertrag.getVertragsstopCollection();
            for (Vertragsstop vertragsstopCollectionOrphanCheckVertragsstop : vertragsstopCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vertrag (" + vertrag + ") cannot be destroyed since the Vertragsstop " + vertragsstopCollectionOrphanCheckVertragsstop + " in its vertragsstopCollection field has a non-nullable vertragid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Zahlungsintervall intervallid = vertrag.getIntervallid();
            if (intervallid != null) {
                intervallid.getVertragCollection().remove(vertrag);
                intervallid = em.merge(intervallid);
            }
            Rubrik rubrikid = vertrag.getRubrikid();
            if (rubrikid != null) {
                rubrikid.getVertragCollection().remove(vertrag);
                rubrikid = em.merge(rubrikid);
            }
            Mitglied mitgliederid = vertrag.getMitgliederid();
            if (mitgliederid != null) {
                mitgliederid.getVertragCollection().remove(vertrag);
                mitgliederid = em.merge(mitgliederid);
            }
            em.remove(vertrag);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vertrag> findVertragEntities() {
        return findVertragEntities(true, -1, -1);
    }

    public List<Vertrag> findVertragEntities(int maxResults, int firstResult) {
        return findVertragEntities(false, maxResults, firstResult);
    }

    private List<Vertrag> findVertragEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vertrag.class));
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

    public Vertrag findVertrag(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vertrag.class, id);
        } finally {
            em.close();
        }
    }

    public int getVertragCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vertrag> rt = cq.from(Vertrag.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
