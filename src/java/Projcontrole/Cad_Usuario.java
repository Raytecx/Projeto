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
public class Cad_Usuario {

   public Usuario Cad_Usuario(String nome, String email, String senha, boolean adm){
       EntityManager em =  getEntityManager();
       try {
           Usuario u = new Usuario();
           u.setNome(nome);
           u.setAdm(adm);
           u.setEmail(email);
           u.setSenha(senha);
           em.getTransaction().begin();
                em.persist(u);
                em.getTransaction().commit();
                return u;
       } 
          catch (Exception ex) {
               if (em.getTransaction().isActive()) {
                   em.getTransaction().rollback();
               }
               throw ex;
       
          }
   }
    public EntityManager getEntityManager(){
        EntityManagerFactory emf;
       emf = Persistence.createEntityManagerFactory("ProjetoSitePU");
        return emf.createEntityManager();
    }
   
}
    

