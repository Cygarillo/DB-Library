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
public class RubrikJpaController implements Serializable {

    public RubrikJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rubrik rubrik) {
        if (rubrik.getVertragCollection() == null) {
            rubrik.setVertragCollection(new ArrayList<Vertrag>());
        }
        if (rubrik.getPreisvertragCollection() == null) {
            rubrik.setPreisvertragCollection(new ArrayList<Preisvertrag>());
        }
        if (rubrik.getPruefungslevelCollection() == null) {
            rubrik.setPruefungslevelCollection(new ArrayList<Pruefungslevel>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Vertrag> attachedVertragCollection = new ArrayList<Vertrag>();
            for (Vertrag vertragCollectionVertragToAttach : rubrik.getVertragCollection()) {
                vertragCollectionVertragToAttach = em.getReference(vertragCollectionVertragToAttach.getClass(), vertragCollectionVertragToAttach.getId());
                attachedVertragCollection.add(vertragCollectionVertragToAttach);
            }
            rubrik.setVertragCollection(attachedVertragCollection);
            Collection<Preisvertrag> attachedPreisvertragCollection = new ArrayList<Preisvertrag>();
            for (Preisvertrag preisvertragCollectionPreisvertragToAttach : rubrik.getPreisvertragCollection()) {
                preisvertragCollectionPreisvertragToAttach = em.getReference(preisvertragCollectionPreisvertragToAttach.getClass(), preisvertragCollectionPreisvertragToAttach.getId());
                attachedPreisvertragCollection.add(preisvertragCollectionPreisvertragToAttach);
            }
            rubrik.setPreisvertragCollection(attachedPreisvertragCollection);
            Collection<Pruefungslevel> attachedPruefungslevelCollection = new ArrayList<Pruefungslevel>();
            for (Pruefungslevel pruefungslevelCollectionPruefungslevelToAttach : rubrik.getPruefungslevelCollection()) {
                pruefungslevelCollectionPruefungslevelToAttach = em.getReference(pruefungslevelCollectionPruefungslevelToAttach.getClass(), pruefungslevelCollectionPruefungslevelToAttach.getId());
                attachedPruefungslevelCollection.add(pruefungslevelCollectionPruefungslevelToAttach);
            }
            rubrik.setPruefungslevelCollection(attachedPruefungslevelCollection);
            em.persist(rubrik);
            for (Vertrag vertragCollectionVertrag : rubrik.getVertragCollection()) {
                Rubrik oldRubrikidOfVertragCollectionVertrag = vertragCollectionVertrag.getRubrikid();
                vertragCollectionVertrag.setRubrikid(rubrik);
                vertragCollectionVertrag = em.merge(vertragCollectionVertrag);
                if (oldRubrikidOfVertragCollectionVertrag != null) {
                    oldRubrikidOfVertragCollectionVertrag.getVertragCollection().remove(vertragCollectionVertrag);
                    oldRubrikidOfVertragCollectionVertrag = em.merge(oldRubrikidOfVertragCollectionVertrag);
                }
            }
            for (Preisvertrag preisvertragCollectionPreisvertrag : rubrik.getPreisvertragCollection()) {
                Rubrik oldRubrikidOfPreisvertragCollectionPreisvertrag = preisvertragCollectionPreisvertrag.getRubrikid();
                preisvertragCollectionPreisvertrag.setRubrikid(rubrik);
                preisvertragCollectionPreisvertrag = em.merge(preisvertragCollectionPreisvertrag);
                if (oldRubrikidOfPreisvertragCollectionPreisvertrag != null) {
                    oldRubrikidOfPreisvertragCollectionPreisvertrag.getPreisvertragCollection().remove(preisvertragCollectionPreisvertrag);
                    oldRubrikidOfPreisvertragCollectionPreisvertrag = em.merge(oldRubrikidOfPreisvertragCollectionPreisvertrag);
                }
            }
            for (Pruefungslevel pruefungslevelCollectionPruefungslevel : rubrik.getPruefungslevelCollection()) {
                Rubrik oldRubrikidOfPruefungslevelCollectionPruefungslevel = pruefungslevelCollectionPruefungslevel.getRubrikid();
                pruefungslevelCollectionPruefungslevel.setRubrikid(rubrik);
                pruefungslevelCollectionPruefungslevel = em.merge(pruefungslevelCollectionPruefungslevel);
                if (oldRubrikidOfPruefungslevelCollectionPruefungslevel != null) {
                    oldRubrikidOfPruefungslevelCollectionPruefungslevel.getPruefungslevelCollection().remove(pruefungslevelCollectionPruefungslevel);
                    oldRubrikidOfPruefungslevelCollectionPruefungslevel = em.merge(oldRubrikidOfPruefungslevelCollectionPruefungslevel);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rubrik rubrik) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rubrik persistentRubrik = em.find(Rubrik.class, rubrik.getId());
            Collection<Vertrag> vertragCollectionOld = persistentRubrik.getVertragCollection();
            Collection<Vertrag> vertragCollectionNew = rubrik.getVertragCollection();
            Collection<Preisvertrag> preisvertragCollectionOld = persistentRubrik.getPreisvertragCollection();
            Collection<Preisvertrag> preisvertragCollectionNew = rubrik.getPreisvertragCollection();
            Collection<Pruefungslevel> pruefungslevelCollectionOld = persistentRubrik.getPruefungslevelCollection();
            Collection<Pruefungslevel> pruefungslevelCollectionNew = rubrik.getPruefungslevelCollection();
            List<String> illegalOrphanMessages = null;
            for (Vertrag vertragCollectionOldVertrag : vertragCollectionOld) {
                if (!vertragCollectionNew.contains(vertragCollectionOldVertrag)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vertrag " + vertragCollectionOldVertrag + " since its rubrikid field is not nullable.");
                }
            }
            for (Preisvertrag preisvertragCollectionOldPreisvertrag : preisvertragCollectionOld) {
                if (!preisvertragCollectionNew.contains(preisvertragCollectionOldPreisvertrag)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Preisvertrag " + preisvertragCollectionOldPreisvertrag + " since its rubrikid field is not nullable.");
                }
            }
            for (Pruefungslevel pruefungslevelCollectionOldPruefungslevel : pruefungslevelCollectionOld) {
                if (!pruefungslevelCollectionNew.contains(pruefungslevelCollectionOldPruefungslevel)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pruefungslevel " + pruefungslevelCollectionOldPruefungslevel + " since its rubrikid field is not nullable.");
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
            rubrik.setVertragCollection(vertragCollectionNew);
            Collection<Preisvertrag> attachedPreisvertragCollectionNew = new ArrayList<Preisvertrag>();
            for (Preisvertrag preisvertragCollectionNewPreisvertragToAttach : preisvertragCollectionNew) {
                preisvertragCollectionNewPreisvertragToAttach = em.getReference(preisvertragCollectionNewPreisvertragToAttach.getClass(), preisvertragCollectionNewPreisvertragToAttach.getId());
                attachedPreisvertragCollectionNew.add(preisvertragCollectionNewPreisvertragToAttach);
            }
            preisvertragCollectionNew = attachedPreisvertragCollectionNew;
            rubrik.setPreisvertragCollection(preisvertragCollectionNew);
            Collection<Pruefungslevel> attachedPruefungslevelCollectionNew = new ArrayList<Pruefungslevel>();
            for (Pruefungslevel pruefungslevelCollectionNewPruefungslevelToAttach : pruefungslevelCollectionNew) {
                pruefungslevelCollectionNewPruefungslevelToAttach = em.getReference(pruefungslevelCollectionNewPruefungslevelToAttach.getClass(), pruefungslevelCollectionNewPruefungslevelToAttach.getId());
                attachedPruefungslevelCollectionNew.add(pruefungslevelCollectionNewPruefungslevelToAttach);
            }
            pruefungslevelCollectionNew = attachedPruefungslevelCollectionNew;
            rubrik.setPruefungslevelCollection(pruefungslevelCollectionNew);
            rubrik = em.merge(rubrik);
            for (Vertrag vertragCollectionNewVertrag : vertragCollectionNew) {
                if (!vertragCollectionOld.contains(vertragCollectionNewVertrag)) {
                    Rubrik oldRubrikidOfVertragCollectionNewVertrag = vertragCollectionNewVertrag.getRubrikid();
                    vertragCollectionNewVertrag.setRubrikid(rubrik);
                    vertragCollectionNewVertrag = em.merge(vertragCollectionNewVertrag);
                    if (oldRubrikidOfVertragCollectionNewVertrag != null && !oldRubrikidOfVertragCollectionNewVertrag.equals(rubrik)) {
                        oldRubrikidOfVertragCollectionNewVertrag.getVertragCollection().remove(vertragCollectionNewVertrag);
                        oldRubrikidOfVertragCollectionNewVertrag = em.merge(oldRubrikidOfVertragCollectionNewVertrag);
                    }
                }
            }
            for (Preisvertrag preisvertragCollectionNewPreisvertrag : preisvertragCollectionNew) {
                if (!preisvertragCollectionOld.contains(preisvertragCollectionNewPreisvertrag)) {
                    Rubrik oldRubrikidOfPreisvertragCollectionNewPreisvertrag = preisvertragCollectionNewPreisvertrag.getRubrikid();
                    preisvertragCollectionNewPreisvertrag.setRubrikid(rubrik);
                    preisvertragCollectionNewPreisvertrag = em.merge(preisvertragCollectionNewPreisvertrag);
                    if (oldRubrikidOfPreisvertragCollectionNewPreisvertrag != null && !oldRubrikidOfPreisvertragCollectionNewPreisvertrag.equals(rubrik)) {
                        oldRubrikidOfPreisvertragCollectionNewPreisvertrag.getPreisvertragCollection().remove(preisvertragCollectionNewPreisvertrag);
                        oldRubrikidOfPreisvertragCollectionNewPreisvertrag = em.merge(oldRubrikidOfPreisvertragCollectionNewPreisvertrag);
                    }
                }
            }
            for (Pruefungslevel pruefungslevelCollectionNewPruefungslevel : pruefungslevelCollectionNew) {
                if (!pruefungslevelCollectionOld.contains(pruefungslevelCollectionNewPruefungslevel)) {
                    Rubrik oldRubrikidOfPruefungslevelCollectionNewPruefungslevel = pruefungslevelCollectionNewPruefungslevel.getRubrikid();
                    pruefungslevelCollectionNewPruefungslevel.setRubrikid(rubrik);
                    pruefungslevelCollectionNewPruefungslevel = em.merge(pruefungslevelCollectionNewPruefungslevel);
                    if (oldRubrikidOfPruefungslevelCollectionNewPruefungslevel != null && !oldRubrikidOfPruefungslevelCollectionNewPruefungslevel.equals(rubrik)) {
                        oldRubrikidOfPruefungslevelCollectionNewPruefungslevel.getPruefungslevelCollection().remove(pruefungslevelCollectionNewPruefungslevel);
                        oldRubrikidOfPruefungslevelCollectionNewPruefungslevel = em.merge(oldRubrikidOfPruefungslevelCollectionNewPruefungslevel);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rubrik.getId();
                if (findRubrik(id) == null) {
                    throw new NonexistentEntityException("The rubrik with id " + id + " no longer exists.");
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
            Rubrik rubrik;
            try {
                rubrik = em.getReference(Rubrik.class, id);
                rubrik.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rubrik with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Vertrag> vertragCollectionOrphanCheck = rubrik.getVertragCollection();
            for (Vertrag vertragCollectionOrphanCheckVertrag : vertragCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rubrik (" + rubrik + ") cannot be destroyed since the Vertrag " + vertragCollectionOrphanCheckVertrag + " in its vertragCollection field has a non-nullable rubrikid field.");
            }
            Collection<Preisvertrag> preisvertragCollectionOrphanCheck = rubrik.getPreisvertragCollection();
            for (Preisvertrag preisvertragCollectionOrphanCheckPreisvertrag : preisvertragCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rubrik (" + rubrik + ") cannot be destroyed since the Preisvertrag " + preisvertragCollectionOrphanCheckPreisvertrag + " in its preisvertragCollection field has a non-nullable rubrikid field.");
            }
            Collection<Pruefungslevel> pruefungslevelCollectionOrphanCheck = rubrik.getPruefungslevelCollection();
            for (Pruefungslevel pruefungslevelCollectionOrphanCheckPruefungslevel : pruefungslevelCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rubrik (" + rubrik + ") cannot be destroyed since the Pruefungslevel " + pruefungslevelCollectionOrphanCheckPruefungslevel + " in its pruefungslevelCollection field has a non-nullable rubrikid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rubrik);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rubrik> findRubrikEntities() {
        return findRubrikEntities(true, -1, -1);
    }

    public List<Rubrik> findRubrikEntities(int maxResults, int firstResult) {
        return findRubrikEntities(false, maxResults, firstResult);
    }

    private List<Rubrik> findRubrikEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rubrik.class));
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

    public Rubrik findRubrik(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rubrik.class, id);
        } finally {
            em.close();
        }
    }

    public int getRubrikCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rubrik> rt = cq.from(Rubrik.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
