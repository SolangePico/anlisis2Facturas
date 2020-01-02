/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.servicio;

import ec.edu.espe.tesis.facturas.facade.UsuarioFacade;
import ec.edu.espe.tesis.facturas.model.Usuario;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author solan
 */
@Stateless
public class UsuarioServicio implements Serializable {

    private static final Logger LOG = Logger.getLogger(UsuarioServicio.class.getName());

    @Inject
    private UsuarioFacade usuarioFacade;

    public Usuario validarUsuario(String correo, String password) {
        Usuario usuario;
        usuario = usuarioFacade.validarUsuario(correo);
        if (usuario != null) {
            if (!(usuario.getClave().length() == 60) || !BCrypt.checkpw(password, usuario.getClave())) {
                usuario = null;
            }
        }
        return usuario;
    }
    public boolean buscarUsuarioPorCorreo(String correo){
       Usuario usuario;  
        usuario = usuarioFacade.validarUsuario(correo);
        return usuario==null;
    }
    
    public void actualizarUsuario(Usuario usuario){
        try{
            usuarioFacade.edit(usuario);
        }catch(Exception e){
        }
    }
    public Usuario crearUsuario(String correo, String password) {
        Usuario usuario = new Usuario();
        try {
            usuario.setCorreo(correo);
            String hashPassw = BCrypt.hashpw(password, BCrypt.gensalt());
            usuario.setClave(hashPassw);
            usuario.setEstado('N');
            usuarioFacade.create(usuario);
        } catch (Exception ex) {

            usuario=null;

            System.out.println("Error al crear Usuario");

        }
        return usuario;
       
    }
}
