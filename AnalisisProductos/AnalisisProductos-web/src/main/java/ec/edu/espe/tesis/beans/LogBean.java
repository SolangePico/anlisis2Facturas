/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.facturas.model.Usuario;
import ec.edu.espe.tesis.servicio.FacturaServicio;
import ec.edu.espe.tesis.servicio.UsuarioServicio;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.ConditionalFeature;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.PrimeFaces;

/**
 *
 * @author solan
 */
@Named(value = "logBean")
@ViewScoped
public class LogBean implements Serializable {

    private Usuario usuarioLogin;
    private String correo;
    private String password;
    private String cPassword;
    private boolean flagCambiar;
    private boolean flagFactura;
    private boolean flagUsuario;
    private boolean flagExito;

    @Inject
    HttpSessionHandler session;

    @Inject
    private FacturaServicio facturaServicio;
    @Inject
    private UsuarioServicio usuarioServicio;
    @Inject
    private LeerXMLBean leer;

    public boolean isFlagExito() {
        return flagExito;
    }

    public void setFlagExito(boolean flagExito) {
        this.flagExito = flagExito;
    }

    public Usuario getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(Usuario usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getCorreo() {
        return correo;
    }

    public boolean isFlagUsuario() {
        return flagUsuario;
    }

    public void setFlagUsuario(boolean flagUsuario) {
        this.flagUsuario = flagUsuario;
    }

    public boolean isFlagFactura() {
        return flagFactura;
    }

    public void setFlagFactura(boolean flagFactura) {
        this.flagFactura = flagFactura;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFlagCambiar() {
        return flagCambiar;
    }

    public void setFlagCambiar(boolean flagCambiar) {
        this.flagCambiar = flagCambiar;
    }

    public String getcPassword() {
        return cPassword;
    }

    public void setcPassword(String cPassword) {
        this.cPassword = cPassword;
    }

    @PostConstruct
    public void Inicializar() {
        flagCambiar = false;
        flagFactura = false;
        flagUsuario = true;
        flagExito = false;
        correo = "";
        password = "";
        cPassword = "";

    }

    public void validarUsuario() {
        //System.out.println(this.servicio.validarUsuario(this.getUsuarioLogin()));
        if (!correo.equals("") && !password.equals("")) {
            if (!usuarioServicio.buscarUsuarioPorCorreo(correo)) {
                usuarioLogin = usuarioServicio.validarUsuario(correo, password);
                if (usuarioLogin != null) {
                    session.setId(usuarioLogin.getCodigo() + "");
                    session.setCorreo(correo);
                    finalizeLogin();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Usuario o Contraseña incorrectos"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No se encuentra registrado como usuario"));

            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Complete todos los campos"));
        }
    }

    public void validarFactura() {
        if (leer.getValidar() != 0) {
            switch (leer.getValidar()) {
                case 1:
                    flagFactura = false;
                    flagCambiar = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Cambie su contraseña"));
                    break;
                case 2:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "La factura no se encuentra registrada con su usuario"));
                    break;
                default:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "La factura no se encuentra registrada"));
                    break;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Suba una factura para verificar"));
        }

    }

    public void buscarUsuario() {
        if (!usuarioServicio.buscarUsuarioPorCorreo(correo)) {
            usuarioLogin = usuarioServicio.obtenerUsuarioPorCorreo(correo);
            flagFactura = true;
            flagUsuario = false;
            leer.setCodigoUsuario(usuarioLogin.getCodigo() + "");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Suba una factura que haya sido registrada con su Usuario para continuar con la verificación"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No existe un usuario registrado con el correo " + correo));
        }
    }

    public void cambiarContrasena() {
        if (!password.equals("")) {
            if (password.equals(cPassword)) {
                String hashPassw = BCrypt.hashpw(password, BCrypt.gensalt());
                usuarioLogin.setClave(hashPassw);
                usuarioServicio.actualizarUsuario(usuarioLogin);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Se actualizo su constraseña con éxito"));
                flagExito = true;
                flagCambiar = false;
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Las Contraseñas no coinciden"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Digite la contraseña nueva"));
        }
        // redireccionarLogin();
    }

    public void redireccionarLogin() {
        PrimeFaces.current().executeScript("window.open('/login.xhtml','_tarjet');");

    }

    public void registarUsuario() throws IOException {
        if (!"".equals(correo) && !"".equals(password)) {
            if (usuarioServicio.buscarUsuarioPorCorreo(correo)) {
                if (password.equals(cPassword)) {
                    usuarioServicio.crearUsuario(correo, password);
                    usuarioLogin = usuarioServicio.validarUsuario(correo, password);

                    if (usuarioLogin != null) {
                        session.setId(usuarioLogin.getCodigo() + "");
                        session.setCorreo(correo);
                        finalizeLogin();
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No se encuentra registrado como usuario"));

                    }

                } else {
                    password = "";
                    cPassword = "";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "La constraseña no coincide"));

                }
            } else {
                correo = "";

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "El usuario ya se encuentra registrado"));

            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Complete todos los campos"));

        }

    }

    public void finalizeLogin() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facturaServicio.obtenerFacturasPorUsuario(usuarioLogin.getCodigo().toString()) > 0) {
            usuarioLogin.setEstado('S');
        } else {
            usuarioLogin.setEstado('N');
        }
        usuarioServicio.actualizarUsuario(usuarioLogin);
        if (!usuarioLogin.getCorreo().equals("admin")) {
            session.setIsAdmin(false);
            if (usuarioLogin.getEstado() != 'N') {
                session.setFlag(false);
                try {

                    facesContext.getExternalContext().redirect("user/InfoPerfil.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(LogBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                session.setFlag(true);
                try {
                    facesContext.getExternalContext().redirect("user/CargarFactura.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(LogBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            session.setFlag(false);
            session.setId("-1");
            session.setIsAdmin(true);
            try {
                facesContext.getExternalContext().redirect("admin/InfoPerfilAdmin.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LogBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
