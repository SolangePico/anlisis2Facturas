/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.facturas.model.Usuario;
import ec.edu.espe.tesis.servicio.Service;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
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

    @Inject
    private Service servicio;

    @PostConstruct
    public void Inicializar() {
        this.setUsuarioLogin(new Usuario());
    }

    public void validarUsuario() throws IOException {
        //System.out.println(this.servicio.validarUsuario(this.getUsuarioLogin()));
        if (this.servicio.validarUsuario(this.getUsuarioLogin())) {
            // System.out.println("======= OK ========");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().redirect("dashboard.xhtml");
        } else {
            System.out.println("======= NO OK ========");
        }
    }

    public Usuario getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(Usuario usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }
}
