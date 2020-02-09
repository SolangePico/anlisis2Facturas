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
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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

    @Inject
    HttpSessionHandler session;

    private String cPassword;

    @Inject
    FacturaServicio facturaServicio;
    @Inject
    UsuarioServicio usuarioServicio;

    public Usuario getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(Usuario usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getCorreo() {
        return correo;
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

    public String getcPassword() {
        return cPassword;
    }

    public void setcPassword(String cPassword) {
        this.cPassword = cPassword;
    }

    @PostConstruct
    public void Inicializar() {
        correo = "";
        password = "";
        cPassword = "";

    }

    public void validarUsuario() {
        //System.out.println(this.servicio.validarUsuario(this.getUsuarioLogin()));
        if (!correo.equals("") && !password.equals("")) {
            usuarioLogin = usuarioServicio.validarUsuario(correo, password);

            if (usuarioLogin != null) {
                session.setId(usuarioLogin.getCodigo() + "");
                session.setCorreo(correo);
                finalizeLogin();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No se encuentra registrado como usuario"));

            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Complete todos los campos"));

        }
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
    }

}
