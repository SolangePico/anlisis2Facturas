package ec.edu.espe.tesis.beans;


import ec.edu.espe.tesis.facturas.model.Usuario;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.omnifaces.util.Faces;

/**
 *
 * @author alterbios
 */
@Named("session")//para poder usar desde UI con el nombre de sesionQF
@SessionScoped
public class HttpSessionHandler implements Serializable {

    private HttpSession session;
    private String idUsuario = "";
    private String idPerfil = "";
    private String identificacionUsuario = "";
    private String menuXHTML = "";
    private String nombreUsuario = "";
    private String nombrePerfil = "";
    


    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(String idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdentificacionUsuario() {
        return identificacionUsuario;
    }

    public void setIdentificacionUsuario(String identificacionUsuario) {
        this.identificacionUsuario = identificacionUsuario;
    }

    public String getMenuXHTML() {
        return menuXHTML;
    }

    public void setMenuXHTML(String menuXHTML) {
        this.menuXHTML = menuXHTML;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public void login(String idUsuario, String idPerfil, HttpSession _session) {
        setSession(_session);
        session.setMaxInactiveInterval(60 * 60 * 6);
        setIdUsuario(idUsuario);
       // setIdPerfil(idPerfil);
        
    }

    public void loginReceptor(String idUsuario, String idPerfil, String identificacion, HttpSession _session) {
        setSession(session);
        session.setMaxInactiveInterval(60 * 60 * 6);
        setIdUsuario(idUsuario);
        setIdentificacionUsuario(identificacion);
       // setIdPerfil(idPerfil);
    }

    public boolean isLoggedIn() {
        return session != null && idUsuario != null && !idUsuario.isEmpty();
    }

    public void cerrarSesion() {
        Usuario u = new Usuario(Integer.valueOf(idUsuario));
        
        session.invalidate();
        idUsuario = null;
        session = null;
        try {
            Faces.redirect("login.xhtml?faces-redirect=true");
        } catch (IOException ex) {
            System.out.println("No se puede cerrar la session " + ex);
        }
    }

}
