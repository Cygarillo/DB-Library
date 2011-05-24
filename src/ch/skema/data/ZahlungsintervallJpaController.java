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
public class ZahlungsintervallJpaController implements Serializable {

    public ZahlungsintervallJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Zahlungsintervall zahlungsintervall) {
        if (zahlungsintervall.getVertragCollection() == null) {
            zahlungsintervall.setVertragCollection(new ArrayList<Vertrag>());
        }
        if (zahlungsintervall.getPreisvertragCollection() == null) {
            zahlungsintervall.setPreisvertragCollection(new ArrayList<Preisvertrag>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Vertrag> attachedVertragCollection = new ArrayList<Vertrag>();
            for (Vertrag vertragCollectionVertragToAttach : zahlungsintervall.getVertragCollection()) {
                vertragCollectionVertragToAttach = em.getReference(vertragCollectionVertragToAttach.getClass(), vertragCollectionVertragToAttach.getId());
                attachedVertragCollection.add(vertragCollectionVertragToAttach);
            }
            zahlungsintervall.setVertragCollection(attachedVertragCollection);
            Collection<Preisvertrag> attachedPreisvertragCollection = new ArrayList<Preisvertrag>();
            for (Preisvertrag preisvertragCollectionPreisvertragToAttach : zahlungsintervall.getPreisvertragCollection()) {
                preisvertragCollectionPreisvertragToAttach = em.getReference(preisvertragCollectionPreisvertragToAttach.getClass(), preisvertragCollectionPreisvertragToAttach.getId());
                attachedPreisvertragCollection.add(preisvertragCollectionPreisvertragToAttach);
            }
            zahlungsintervall.setPreisvertragCollection(attachedPreisvertragCollection);
            em.persist(zahlungsintervall);
            for (Vertrag vertragCollectionVertrag : zahlungsintervall.getVertragCollection()) {
                Zahlungsintervall oldIntervallidOfVertragCollectionVertrag = vertragCollectionVertrag.getIntervallid();
                vertragCollectionVertrag.setIntervallid(zahlungsintervall);
                vertragCollectionVertrag = em.merge(vertragCollectionVertrag);
                if (oldIntervallidOfVertragCollectionVertrag != null) {
                    oldIntervallidOfVertragCollectionVertrag.getVertragCollection().remove(vertragCollectionVertrag);
                    oldIntervallidOfVertragCollectionVertrag = em.merge(oldIntervallidOfVertragCollectionVertrag);
                }
            }
            for (Preisvertrag preisvertragCollectionPreisvertrag : zahlungsintervall.getPreisvertragCollection()) {
                Zahlungsintervall oldZahlungsintervallidOfPreisvertragCollectionPreisvertrag = preisvertragCollectionPreisvertrag.getZahlungsintervallid();
                preisvertragCollectionPreisvertrag.setZahlungsintervallid(zahlungsintervall);
                preisvertragCollectionPreisvertrag = em.merge(preisvertragCollectionPreisvertrag);
                if (oldZahlungsintervallidOfPreisvertragCollectionPreisvertrag != null) {
                    oldZahlungsintervallidOfPreisvertragCollectionPreisvertrag.getPreisvertragCollection().remove(preisvertragCollectionPreisvertrag);
                    oldZahlungsintervallidOfPreisvertragCollectionPreisvertrag = em.merge(oldZahlungsintervallidOfPreisvertragCollectionPreisvertrag);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Zahlungsintervall zahlungsintervall) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Zahlungsintervall persistentZahlungsintervall = em.find(Zahlungsintervall.class, zahlungsintervall.getId());
            Collection<Vertrag> vertragCollectionOld = persistentZahlungsintervall.getVertragCollection();
            Collection<Vertrag> vertragCollectionNew = zahlungsintervall.getVertragCollection();
            Collection<Preisvertrag> preisvertragCollectionOld = persistentZahlungsintervall.getPreisvertragCollection();
            Collection<Preisvertrag> preisvertragCollectionNew = zahlungsintervall.getPreisvertragCollection();
            List<String> illegalOrphanMessages = null;
            for (Vertrag vertragCollectionOldVertrag : vertragCollectionOld) {
                if (!vertragCollectionNew.contains(vertragCollectionOldVertrag)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vertrag " + vertragCollectionOldVertrag + " since its intervallid field is not nullable.");
                }
            }
            for (Preisvertrag preisvertragCollectionOldPreisvertrag : preisvertragCollectionOld) {
                if (!preisvertragCollectionNew.contains(preisvertragCollectionOldPreisvertrag)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Preisvertrag " + preisvertragCollectionOldPreisvertrag + " since its zahlungsintervallid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Vertrag> attachedVertragCollectionNew = new ArrayList<Vertrag>();
            for (Vertrag vertragCollectionNewVertragToAttach : vertragCollectionNew) {
                vertragCollectionNewVertragToAttach = em.getReference(vertragCollectionNewVertragToAttach.getClass(), vertragCollectionNewVertragToAttach.getId());
                attachedVertragCollectionNew.add(vertragCollectionNewVertragToAttach);
            }
            vertragCollectionNew = attachedVertragCollectionNew;
            zahlungsintervall.setVertragCollection(vertragCollectionNew);
            Collection<Preisvertrag> attachedPreisvertragCollectionNew = new ArrayList<Preisvertrag>();
            for (Preisvertrag preisvertragCollectionNewPreisvertragToAttach : preisvertragCollectionNew) {
                preisvertragCollectionNewPreisvertragToAttach = em.getReference(preisvertragCollectionNewPreisvertragToAttach.getClass(), preisvertragCollectionNewPreisvertragToAttach.getId());
                attachedPreisvertragCollectionNew.add(preisvertragCollectionNewPreisvertragToAttach);
            }
            preisvertragCollectionNew = attachedPreisvertragCollectionNew;
            zahlungsintervall.setPreisvertragCollection(preisvertragCollectionNew);
            zahlungsintervall = em.merge(zahlungsintervall);
            for (Vertrag vertragCollectionNewVertrag : vertragCollectionNew) {
                if (!vertragCollectionOld.contains(vertragCollectionNewVertrag)) {
                    Zahlungsintervall oldIntervallidOfVertragCollectionNewVertrag = vertragCollectionNewVertrag.getIntervallid();
                    vertragCollectionNewVertrag.setIntervallid(zahlungsintervall);
                    vertragCollectionNewVertrag = em.merge(vertragCollectionNewVertrag);
                    if (oldIntervallidOfVertragCollectionNewVertrag != null && !oldIntervallidOfVertragCollectionNewVertrag.equals(zahlungsintervall)) {
                        oldIntervallidOfVertragCollectionNewVertrag.getVertragCollection().remove(vertragCollectionNewVertrag);
                        oldIntervallidOfVertragCollectionNewVertrag = em.merge(oldIntervallidOfVertragCollectionNewVertrag);
                    }
                }
            }
            for (Preisvertrag preisvertragCollectionNewPreisvertrag : preisvertragCollectionNew) {
                if (!preisvertragCollectionOld.contains(preisvertragCollectionNewPreisvertrag)) {
                    Zahlungsintervall oldZahlungsintervallidOfPreisvertragCollectionNewPreisvertrag = preisvertragCollectionNewPreisvertrag.getZahlungsintervallid();
                    preisvertragCollectionNewPreisvertrag.setZahlungsintervallid(zahlungsintervall);
                    preisvertragCollectionNewPreisvertrag = em.merge(preisvertragCollectionNewPreisvertrag);
                    if (oldZahlungsintervallidOfPreisvertragCollectionNewPreisvertrag != null && !oldZahlungsintervallidOfPreisvertragCollectionNewPreisvertrag.equals(zahlungsintervall)) {
                        oldZahlungsintervallidOfPreisvertragCollectionNewPreisvertrag.getPreisvertragCollection().remove(preisvertragCollectionNewPreisvertrag);
                        oldZahlungsintervallidOfPreisvertragCollectionNewPreisvertrag = em.merge(oldZahlungsintervallidOfPreisvertragCollectionNewPreisvertrag);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = zahlungsintervall.getId();
                if (findZahlungsintervall(id) == null) {
                    throw new NonexistentEntityException("The zahlungsintervall with id " + id + " no longer exists.");
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
            Zahlungsintervall zahlungsintervall;
            try {
                zahlungsintervall = em.getReference(Zahlungsintervall.class, id);
                zahlungsintervall.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The zahlungsintervall with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Vertrag> vertragCollectionOrphanCheck = zahlungsintervall.getVertragCollection();
            for (Vertrag vertragCollectionOrphanCheckVertrag : vertragCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Zahlungsintervall (" + zahlungsintervall + ") cannot be destroyed since the Vertrag " + vertragCollectionOrphanCheckVertrag + " in its vertragCollection field has a non-nullable intervallid field.");
            }
            Collection<Preisvertrag> preisvertragCollectionOrphanCheck = zahlungsintervall.getPreisvertragCollection();
            for (Preisvertrag preisvertragCollectionOrphanCheckPreisvertrag : preisvertragCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Zahlungsintervall (" + zahlungsintervall + ") cannot be destroyed since the Preisvertrag " + preisvertragCollectionOrphanCheckPreisvertrag + " in its preisvertragCollection field has a non-nullable zahlungsintervallid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(zahlungsintervall);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Zahlungsintervall> findZahlungsintervallEntities() {
        return findZahlungsintervallEntities(true, -1, -1);
    }

    public List<Zahlungsintervall> findZahlungsintervallEntities(int maxResults, int firstResult) {
        return findZahlungsintervallEntities(false, maxResults, firstResult);
    }

    private List<Zahlungsintervall> findZahlungsintervallEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Zahlungsintervall.class));
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

    public Zahlungsintervall findZahlungsintervall(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Zahlungsintervall.class, id);
        } finally {
            em.close();
        }
    }

    public int getZahlungsintervallCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Zahlungsintervall> rt = cq.from(Zahlungsintervall.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
