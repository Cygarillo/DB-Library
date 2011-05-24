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
public class ZahlungskategorieJpaController implements Serializable {

    public ZahlungskategorieJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Zahlungskategorie zahlungskategorie) {
        if (zahlungskategorie.getPreisvertragCollection() == null) {
            zahlungskategorie.setPreisvertragCollection(new ArrayList<Preisvertrag>());
        }
        if (zahlungskategorie.getMitgliedCollection() == null) {
            zahlungskategorie.setMitgliedCollection(new ArrayList<Mitglied>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Preisvertrag> attachedPreisvertragCollection = new ArrayList<Preisvertrag>();
            for (Preisvertrag preisvertragCollectionPreisvertragToAttach : zahlungskategorie.getPreisvertragCollection()) {
                preisvertragCollectionPreisvertragToAttach = em.getReference(preisvertragCollectionPreisvertragToAttach.getClass(), preisvertragCollectionPreisvertragToAttach.getId());
                attachedPreisvertragCollection.add(preisvertragCollectionPreisvertragToAttach);
            }
            zahlungskategorie.setPreisvertragCollection(attachedPreisvertragCollection);
            Collection<Mitglied> attachedMitgliedCollection = new ArrayList<Mitglied>();
            for (Mitglied mitgliedCollectionMitgliedToAttach : zahlungskategorie.getMitgliedCollection()) {
                mitgliedCollectionMitgliedToAttach = em.getReference(mitgliedCollectionMitgliedToAttach.getClass(), mitgliedCollectionMitgliedToAttach.getId());
                attachedMitgliedCollection.add(mitgliedCollectionMitgliedToAttach);
            }
            zahlungskategorie.setMitgliedCollection(attachedMitgliedCollection);
            em.persist(zahlungskategorie);
            for (Preisvertrag preisvertragCollectionPreisvertrag : zahlungskategorie.getPreisvertragCollection()) {
                Zahlungskategorie oldZahlungskategorieidOfPreisvertragCollectionPreisvertrag = preisvertragCollectionPreisvertrag.getZahlungskategorieid();
                preisvertragCollectionPreisvertrag.setZahlungskategorieid(zahlungskategorie);
                preisvertragCollectionPreisvertrag = em.merge(preisvertragCollectionPreisvertrag);
                if (oldZahlungskategorieidOfPreisvertragCollectionPreisvertrag != null) {
                    oldZahlungskategorieidOfPreisvertragCollectionPreisvertrag.getPreisvertragCollection().remove(preisvertragCollectionPreisvertrag);
                    oldZahlungskategorieidOfPreisvertragCollectionPreisvertrag = em.merge(oldZahlungskategorieidOfPreisvertragCollectionPreisvertrag);
                }
            }
            for (Mitglied mitgliedCollectionMitglied : zahlungskategorie.getMitgliedCollection()) {
                Zahlungskategorie oldZahlungskategorieidOfMitgliedCollectionMitglied = mitgliedCollectionMitglied.getZahlungskategorieid();
                mitgliedCollectionMitglied.setZahlungskategorieid(zahlungskategorie);
                mitgliedCollectionMitglied = em.merge(mitgliedCollectionMitglied);
                if (oldZahlungskategorieidOfMitgliedCollectionMitglied != null) {
                    oldZahlungskategorieidOfMitgliedCollectionMitglied.getMitgliedCollection().remove(mitgliedCollectionMitglied);
                    oldZahlungskategorieidOfMitgliedCollectionMitglied = em.merge(oldZahlungskategorieidOfMitgliedCollectionMitglied);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Zahlungskategorie zahlungskategorie) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Zahlungskategorie persistentZahlungskategorie = em.find(Zahlungskategorie.class, zahlungskategorie.getId());
            Collection<Preisvertrag> preisvertragCollectionOld = persistentZahlungskategorie.getPreisvertragCollection();
            Collection<Preisvertrag> preisvertragCollectionNew = zahlungskategorie.getPreisvertragCollection();
            Collection<Mitglied> mitgliedCollectionOld = persistentZahlungskategorie.getMitgliedCollection();
            Collection<Mitglied> mitgliedCollectionNew = zahlungskategorie.getMitgliedCollection();
            List<String> illegalOrphanMessages = null;
            for (Preisvertrag preisvertragCollectionOldPreisvertrag : preisvertragCollectionOld) {
                if (!preisvertragCollectionNew.contains(preisvertragCollectionOldPreisvertrag)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Preisvertrag " + preisvertragCollectionOldPreisvertrag + " since its zahlungskategorieid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Preisvertrag> attachedPreisvertragCollectionNew = new ArrayList<Preisvertrag>();
            for (Preisvertrag preisvertragCollectionNewPreisvertragToAttach : preisvertragCollectionNew) {
                preisvertragCollectionNewPreisvertragToAttach = em.getReference(preisvertragCollectionNewPreisvertragToAttach.getClass(), preisvertragCollectionNewPreisvertragToAttach.getId());
                attachedPreisvertragCollectionNew.add(preisvertragCollectionNewPreisvertragToAttach);
            }
            preisvertragCollectionNew = attachedPreisvertragCollectionNew;
            zahlungskategorie.setPreisvertragCollection(preisvertragCollectionNew);
            Collection<Mitglied> attachedMitgliedCollectionNew = new ArrayList<Mitglied>();
            for (Mitglied mitgliedCollectionNewMitgliedToAttach : mitgliedCollectionNew) {
                mitgliedCollectionNewMitgliedToAttach = em.getReference(mitgliedCollectionNewMitgliedToAttach.getClass(), mitgliedCollectionNewMitgliedToAttach.getId());
                attachedMitgliedCollectionNew.add(mitgliedCollectionNewMitgliedToAttach);
            }
            mitgliedCollectionNew = attachedMitgliedCollectionNew;
            zahlungskategorie.setMitgliedCollection(mitgliedCollectionNew);
            zahlungskategorie = em.merge(zahlungskategorie);
            for (Preisvertrag preisvertragCollectionNewPreisvertrag : preisvertragCollectionNew) {
                if (!preisvertragCollectionOld.contains(preisvertragCollectionNewPreisvertrag)) {
                    Zahlungskategorie oldZahlungskategorieidOfPreisvertragCollectionNewPreisvertrag = preisvertragCollectionNewPreisvertrag.getZahlungskategorieid();
                    preisvertragCollectionNewPreisvertrag.setZahlungskategorieid(zahlungskategorie);
                    preisvertragCollectionNewPreisvertrag = em.merge(preisvertragCollectionNewPreisvertrag);
                    if (oldZahlungskategorieidOfPreisvertragCollectionNewPreisvertrag != null && !oldZahlungskategorieidOfPreisvertragCollectionNewPreisvertrag.equals(zahlungskategorie)) {
                        oldZahlungskategorieidOfPreisvertragCollectionNewPreisvertrag.getPreisvertragCollection().remove(preisvertragCollectionNewPreisvertrag);
                        oldZahlungskategorieidOfPreisvertragCollectionNewPreisvertrag = em.merge(oldZahlungskategorieidOfPreisvertragCollectionNewPreisvertrag);
                    }
                }
            }
            for (Mitglied mitgliedCollectionOldMitglied : mitgliedCollectionOld) {
                if (!mitgliedCollectionNew.contains(mitgliedCollectionOldMitglied)) {
                    mitgliedCollectionOldMitglied.setZahlungskategorieid(null);
                    mitgliedCollectionOldMitglied = em.merge(mitgliedCollectionOldMitglied);
                }
            }
            for (Mitglied mitgliedCollectionNewMitglied : mitgliedCollectionNew) {
                if (!mitgliedCollectionOld.contains(mitgliedCollectionNewMitglied)) {
                    Zahlungskategorie oldZahlungskategorieidOfMitgliedCollectionNewMitglied = mitgliedCollectionNewMitglied.getZahlungskategorieid();
                    mitgliedCollectionNewMitglied.setZahlungskategorieid(zahlungskategorie);
                    mitgliedCollectionNewMitglied = em.merge(mitgliedCollectionNewMitglied);
                    if (oldZahlungskategorieidOfMitgliedCollectionNewMitglied != null && !oldZahlungskategorieidOfMitgliedCollectionNewMitglied.equals(zahlungskategorie)) {
                        oldZahlungskategorieidOfMitgliedCollectionNewMitglied.getMitgliedCollection().remove(mitgliedCollectionNewMitglied);
                        oldZahlungskategorieidOfMitgliedCollectionNewMitglied = em.merge(oldZahlungskategorieidOfMitgliedCollectionNewMitglied);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = zahlungskategorie.getId();
                if (findZahlungskategorie(id) == null) {
                    throw new NonexistentEntityException("The zahlungskategorie with id " + id + " no longer exists.");
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
            Zahlungskategorie zahlungskategorie;
            try {
                zahlungskategorie = em.getReference(Zahlungskategorie.class, id);
                zahlungskategorie.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The zahlungskategorie with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Preisvertrag> preisvertragCollectionOrphanCheck = zahlungskategorie.getPreisvertragCollection();
            for (Preisvertrag preisvertragCollectionOrphanCheckPreisvertrag : preisvertragCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Zahlungskategorie (" + zahlungskategorie + ") cannot be destroyed since the Preisvertrag " + preisvertragCollectionOrphanCheckPreisvertrag + " in its preisvertragCollection field has a non-nullable zahlungskategorieid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Mitglied> mitgliedCollection = zahlungskategorie.getMitgliedCollection();
            for (Mitglied mitgliedCollectionMitglied : mitgliedCollection) {
                mitgliedCollectionMitglied.setZahlungskategorieid(null);
                mitgliedCollectionMitglied = em.merge(mitgliedCollectionMitglied);
            }
            em.remove(zahlungskategorie);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Zahlungskategorie> findZahlungskategorieEntities() {
        return findZahlungskategorieEntities(true, -1, -1);
    }

    public List<Zahlungskategorie> findZahlungskategorieEntities(int maxResults, int firstResult) {
        return findZahlungskategorieEntities(false, maxResults, firstResult);
    }

    private List<Zahlungskategorie> findZahlungskategorieEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Zahlungskategorie.class));
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

    public Zahlungskategorie findZahlungskategorie(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Zahlungskategorie.class, id);
        } finally {
            em.close();
        }
    }

    public int getZahlungskategorieCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Zahlungskategorie> rt = cq.from(Zahlungskategorie.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
