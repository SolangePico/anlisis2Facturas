/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.servicio;

import ec.edu.espe.tesis.facturas.model.Usuario;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solan
 */

@Singleton
public class LoginServicio implements Service {
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;

    @Override
    public Boolean validarUsuario(Usuario usuarioPendiente) {
        Boolean retornar = null;
        String encontrarUsuario = "SELECT u FROM Usuario u where u.correo=:pCorreo AND u.clave=:pClave ";
        Query qEncontrarUsuario = em.createQuery(encontrarUsuario, Usuario.class);
        qEncontrarUsuario.setParameter("pCorreo", usuarioPendiente.getCorreo());
        qEncontrarUsuario.setParameter("pClave", usuarioPendiente.getClave());
        if (qEncontrarUsuario.getResultList().isEmpty()) {
            retornar = Boolean.FALSE;
        } else {
            retornar = Boolean.TRUE;
        }
        return retornar;
    }
}
