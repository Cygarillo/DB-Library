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
public class PruefungslevelJpaController implements Serializable {

    public PruefungslevelJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pruefungslevel pruefungslevel) {
        if (pruefungslevel.getPruefungCollection() == null) {
            pruefungslevel.setPruefungCollection(new ArrayList<Pruefung>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rubrik rubrikid = pruefungslevel.getRubrikid();
            if (rubrikid != null) {
                rubrikid = em.getReference(rubrikid.getClass(), rubrikid.getId());
                pruefungslevel.setRubrikid(rubrikid);
            }
            Collection<Pruefung> attachedPruefungCollection = new ArrayList<Pruefung>();
            for (Pruefung pruefungCollectionPruefungToAttach : pruefungslevel.getPruefungCollection()) {
                pruefungCollectionPruefungToAttach = em.getReference(pruefungCollectionPruefungToAttach.getClass(), pruefungCollectionPruefungToAttach.getId());
                attachedPruefungCollection.add(pruefungCollectionPruefungToAttach);
            }
            pruefungslevel.setPruefungCollection(attachedPruefungCollection);
            em.persist(pruefungslevel);
            if (rubrikid != null) {
                rubrikid.getPruefungslevelCollection().add(pruefungslevel);
                rubrikid = em.merge(rubrikid);
            }
            for (Pruefung pruefungCollectionPruefung : pruefungslevel.getPruefungCollection()) {
                Pruefungslevel oldPruefungslevelidOfPruefungCollectionPruefung = pruefungCollectionPruefung.getPruefungslevelid();
                pruefungCollectionPruefung.setPruefungslevelid(pruefungslevel);
                pruefungCollectionPruefung = em.merge(pruefungCollectionPruefung);
                if (oldPruefungslevelidOfPruefungCollectionPruefung != null) {
                    oldPruefungslevelidOfPruefungCollectionPruefung.getPruefungCollection().remove(pruefungCollectionPruefung);
                    oldPruefungslevelidOfPruefungCollectionPruefung = em.merge(oldPruefungslevelidOfPruefungCollectionPruefung);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pruefungslevel pruefungslevel) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pruefungslevel persistentPruefungslevel = em.find(Pruefungslevel.class, pruefungslevel.getId());
            Rubrik rubrikidOld = persistentPruefungslevel.getRubrikid();
            Rubrik rubrikidNew = pruefungslevel.getRubrikid();
            Collection<Pruefung> pruefungCollectionOld = persistentPruefungslevel.getPruefungCollection();
            Collection<Pruefung> pruefungCollectionNew = pruefungslevel.getPruefungCollection();
            List<String> illegalOrphanMessages = null;
            for (Pruefung pruefungCollectionOldPruefung : pruefungCollectionOld) {
                if (!pruefungCollectionNew.contains(pruefungCollectionOldPruefung)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pruefung " + pruefungCollectionOldPruefung + " since its pruefungslevelid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (rubrikidNew != null) {
                rubrikidNew = em.getReference(rubrikidNew.getClass(), rubrikidNew.getId());
                pruefungslevel.setRubrikid(rubrikidNew);
            }
            Collection<Pruefung> attachedPruefungCollectionNew = new ArrayList<Pruefung>();
            for (Pruefung pruefungCollectionNewPruefungToAttach : pruefungCollectionNew) {
                pruefungCollectionNewPruefungToAttach = em.getReference(pruefungCollectionNewPruefungToAttach.getClass(), pruefungCollectionNewPruefungToAttach.getId());
                attachedPruefungCollectionNew.add(pruefungCollectionNewPruefungToAttach);
            }
            pruefungCollectionNew = attachedPruefungCollectionNew;
            pruefungslevel.setPruefungCollection(pruefungCollectionNew);
            pruefungslevel = em.merge(pruefungslevel);
            if (rubrikidOld != null && !rubrikidOld.equals(rubrikidNew)) {
                rubrikidOld.getPruefungslevelCollection().remove(pruefungslevel);
                rubrikidOld = em.merge(rubrikidOld);
            }
            if (rubrikidNew != null && !rubrikidNew.equals(rubrikidOld)) {
                rubrikidNew.getPruefungslevelCollection().add(pruefungslevel);
                rubrikidNew = em.merge(rubrikidNew);
            }
            for (Pruefung pruefungCollectionNewPruefung : pruefungCollectionNew) {
                if (!pruefungCollectionOld.contains(pruefungCollectionNewPruefung)) {
                    Pruefungslevel oldPruefungslevelidOfPruefungCollectionNewPruefung = pruefungCollectionNewPruefung.getPruefungslevelid();
                    pruefungCollectionNewPruefung.setPruefungslevelid(pruefungslevel);
                    pruefungCollectionNewPruefung = em.merge(pruefungCollectionNewPruefung);
                    if (oldPruefungslevelidOfPruefungCollectionNewPruefung != null && !oldPruefungslevelidOfPruefungCollectionNewPruefung.equals(pruefungslevel)) {
                        oldPruefungslevelidOfPruefungCollectionNewPruefung.getPruefungCollection().remove(pruefungCollectionNewPruefung);
                        oldPruefungslevelidOfPruefungCollectionNewPruefung = em.merge(oldPruefungslevelidOfPruefungCollectionNewPruefung);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pruefungslevel.getId();
                if (findPruefungslevel(id) == null) {
                    throw new NonexistentEntityException("The pruefungslevel with id " + id + " no longer exists.");
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
            Pruefungslevel pruefungslevel;
            try {
                pruefungslevel = em.getReference(Pruefungslevel.class, id);
                pruefungslevel.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pruefungslevel with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Pruefung> pruefungCollectionOrphanCheck = pruefungslevel.getPruefungCollection();
            for (Pruefung pruefungCollectionOrphanCheckPruefung : pruefungCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pruefungslevel (" + pruefungslevel + ") cannot be destroyed since the Pruefung " + pruefungCollectionOrphanCheckPruefung + " in its pruefungCollection field has a non-nullable pruefungslevelid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Rubrik rubrikid = pruefungslevel.getRubrikid();
            if (rubrikid != null) {
                rubrikid.getPruefungslevelCollection().remove(pruefungslevel);
                rubrikid = em.merge(rubrikid);
            }
            em.remove(pruefungslevel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pruefungslevel> findPruefungslevelEntities() {
        return findPruefungslevelEntities(true, -1, -1);
    }

    public List<Pruefungslevel> findPruefungslevelEntities(int maxResults, int firstResult) {
        return findPruefungslevelEntities(false, maxResults, firstResult);
    }

    private List<Pruefungslevel> findPruefungslevelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pruefungslevel.class));
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

    public Pruefungslevel findPruefungslevel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pruefungslevel.class, id);
        } finally {
            em.close();
        }
    }

    public int getPruefungslevelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pruefungslevel> rt = cq.from(Pruefungslevel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
