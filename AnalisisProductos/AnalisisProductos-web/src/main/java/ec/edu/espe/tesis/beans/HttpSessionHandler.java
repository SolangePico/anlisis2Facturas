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

 

@Named("sesionBean")//para poder usar desde UI con el nombre de sesionQF
@SessionScoped
public class HttpSessionHandler implements Serializable {

    HttpSession session;
    private String correo;
    private String id;
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }
    
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


    public void loginReceptor(String idUsuario, HttpSession _session) {
        setSession(session);
        session.setMaxInactiveInterval(60 * 60 * 6);
        setCorreo(idUsuario);
      //  setIdentificacionUsuario(identificacion);
       // setIdPerfil(idPerfil);
    }

    public void login(String correo, String id ,HttpSession _session) {
        setSession(_session);
        session.setMaxInactiveInterval(60 * 60 * 6);
        setCorreo(correo);
       setId(id);

    }

    public void loginReceptor(String correo, String id ,HttpSession _session) {
        setSession(session);
        session.setMaxInactiveInterval(60 * 60 * 6);
        setCorreo(correo);
        // setIdentificacionUsuario(identificacion);
         setId(id);

    }

    public boolean isLoggedIn() {
        return session != null && correo != null && !correo.isEmpty();
    }

    public void cerrarSesion() {


        session.invalidate();
        correo = null;
        id=null;
        session = null;
        try {
            Faces.redirect("login.xhtml?faces-redirect=true");
        } catch (IOException ex) {
            System.out.println("No se puede cerrar la session " + ex);
        }

    }

}
