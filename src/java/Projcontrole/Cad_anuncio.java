/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projcontrole;

import javax.persistence.*;



/**
 *
 * @author Ray
 */
public class Cad_anuncio {

    public Anuncio  Cad_anuncio( Usuario usuario, Long id) {
        EntityManager em = getEntityManager();
        try {
            Anuncio a = new Anuncio();
            a.setId(id);
            a.setUsuario(usuario);
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
            return a;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                   em.getTransaction().rollback();
               }
               throw ex;
        }
    }

    private EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
