/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.facturas.facade;

import ec.edu.espe.tesis.facturas.model.Usuario;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solan
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {
    
    private static final Logger LOG = Logger.getLogger(UsuarioFacade.class.getName());
    
    @PersistenceContext(unitName = "ec.edu.espe.tesis.facturas_AnalisisProductos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public List<Usuario> obtenerUsuarioPorCodigo(int usuCodigo) {
        String query = "SELECT u FROM Usuario u where u.codigo=:usuCodigo";
        Query q = em.createQuery(query);
        q.setParameter("usuCodigo", usuCodigo);
        List<Usuario> usuarios = q
                .getResultList();
        return usuarios;
        
    }
    
    public List<Object[]> obtenerUsuarios(){
       String query = "SELECT u.correo, u.fechacreacion, u.fechaingreso, u.estado, count(f.codigo), sum(f.importetotal) "
               + "FROM Usuario u, Factura f where u.codigo=f.usu_codigo group by u.correo, u.fechacreacion, u.fechaingreso, u.estado;";
        Query q = em.createNativeQuery(query);
         List<Object[]> usuarios = q
                .getResultList();
        return usuarios; 
    }
    
    public Usuario validarUsuario(String correo) {
        Usuario temp = null;
        try {
            temp = em.createQuery("SELECT u FROM Usuario u "
                    + "WHERE u.correo=:correo ", Usuario.class)
                    .setParameter("correo", correo)
                    .getSingleResult();
        } catch (Exception ex) {
            LOG.warning(ex.toString());
            temp=null;
        }
        return temp;
    }

   
}
