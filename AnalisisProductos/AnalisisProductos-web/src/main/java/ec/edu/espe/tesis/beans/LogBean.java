/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.facturas.model.Usuario;
import ec.edu.espe.tesis.servicio.UsuarioServicio;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

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
    private UsuarioServicio usuarioServicio;

    @PostConstruct
    public void Inicializar() {
        usuarioLogin = null;
    }

    public void validarUsuario() throws IOException {
        //System.out.println(this.servicio.validarUsuario(this.getUsuarioLogin()));
        System.out.println("entra");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        usuarioLogin = usuarioServicio.validarUsuario(correo, password);
        if (usuarioLogin == null) {
            facesContext.addMessage(null, new FacesMessage("Usuario no registrado", "Registrese! "));
        } else {
            facesContext.getExternalContext().redirect("charts.xhtml");
        }
    }

    public void registarUsuario() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        usuarioLogin = usuarioServicio.crearUsuario(correo, password);
        if (usuarioLogin == null) {
            facesContext.addMessage(null, new FacesMessage("Usuario no ingresado", "Registrese! "));
        } else {
            facesContext.getExternalContext().redirect("sample.xhtml");
        }
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

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
