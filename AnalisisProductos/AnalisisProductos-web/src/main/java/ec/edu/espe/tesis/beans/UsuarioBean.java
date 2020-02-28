/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.servicio.UsuarioServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author solan
 */
@Named(value = "usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {
    
    private List<Object[]> listaUsuarios;
    @Inject
    private UsuarioServicio usuarioServicio;
    
    @PostConstruct
    public void init(){
        listaUsuarios=usuarioServicio.obtenerUsuarios();
    }

    public List<Object[]> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Object[]> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public UsuarioServicio getUsuarioServicio() {
        return usuarioServicio;
    }

    public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }
    
}
