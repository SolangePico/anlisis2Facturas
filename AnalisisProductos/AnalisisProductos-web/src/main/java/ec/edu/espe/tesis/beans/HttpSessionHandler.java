package ec.edu.espe.tesis.beans;


import ec.edu.espe.tesis.facturas.model.Usuario;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.omnifaces.util.Faces;

/**
 *
 * @author alterbios
 */
@Named("sessionBean")//para poder usar desde UI con el nombre de sesionQF
@SessionScoped
public class HttpSessionHandler implements Serializable {

    private HttpSession session;
    private String correo=  "";
 

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void login(String idUsuario, HttpSession _session) {
        setSession(_session);
        session.setMaxInactiveInterval(60 * 60 * 6);
        setCorreo(idUsuario);
       // setIdPerfil(idPerfil);
        
    }

    public void loginReceptor(String idUsuario, HttpSession _session) {
        setSession(session);
        session.setMaxInactiveInterval(60 * 60 * 6);
        setCorreo(idUsuario);
      //  setIdentificacionUsuario(identificacion);
       // setIdPerfil(idPerfil);
    }

    public boolean isLoggedIn() {
        return session != null && correo != null && !correo.isEmpty();
    }

    public void cerrarSesion() {
       // Usuario u = new Usuario(Integer.valueOf(correo));
        
//        session.invalidate();
//        correo = null;
//        session = null;
//        try {
//            Faces.redirect("login.xhtml?faces-redirect=true");
//        } catch (IOException ex) {
//            System.out.println("No se puede cerrar la session " + ex);
//        }
    }

}
