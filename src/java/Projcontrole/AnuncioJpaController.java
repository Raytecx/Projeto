/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projcontrole;

import Projcontrole.exceptions.NonexistentEntityException;
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
 * @author Ray
 */
public class AnuncioJpaController implements Serializable {

    public AnuncioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Anuncio anuncio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(anuncio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Anuncio anuncio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            anuncio = em.merge(anuncio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = anuncio.getId();
                if (findAnuncio(id) == null) {
                    throw new NonexistentEntityException("The anuncio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Anuncio anuncio;
            try {
                anuncio = em.getReference(Anuncio.class, id);
                anuncio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The anuncio with id " + id + " no longer exists.", enfe);
            }
            em.remove(anuncio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Anuncio> findAnuncioEntities() {
        return findAnuncioEntities(true, -1, -1);
    }

    public List<Anuncio> findAnuncioEntities(int maxResults, int firstResult) {
        return findAnuncioEntities(false, maxResults, firstResult);
    }

    private List<Anuncio> findAnuncioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Anuncio.class));
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

    public Anuncio findAnuncio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Anuncio.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnuncioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Anuncio> rt = cq.from(Anuncio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Anuncio login(int matricula, String senha) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT a FROM Projcontrole.Anucio a where a.matricula = :matricula and a.senha = :senha");
            query.setParameter("matricula", matricula);
            query.setParameter("senha", senha);
            Anuncio a = (Anuncio) query.getSingleResult();
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Anuncio consultar(String nome, String senha) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT a FROM Projcontrole.Anuncio a where a.nome = :nome and a.senha = :senha");
            query.setParameter("nome", nome);
            query.setParameter("senha", senha);
            Anuncio a = (Anuncio) query.getSingleResult();
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
