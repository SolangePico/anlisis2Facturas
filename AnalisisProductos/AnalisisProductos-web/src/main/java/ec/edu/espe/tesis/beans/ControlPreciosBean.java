/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;


import ec.edu.espe.tesis.facturas.model.ControlPrecios;
import ec.edu.espe.tesis.servicio.ControlPreciosServicio;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author danny
 */
@Named(value = "controlPreciosBean")
@ViewScoped
public class ControlPreciosBean implements Serializable {

    /**
     * Creates a new instance of ControlPreciosBean
     */
    @Inject
     ControlPreciosServicio controlPreciosServicio;
    
    List<ControlPrecios> listaControlPrecios;

    public ControlPreciosServicio getControlPreciosServicio() {
        return controlPreciosServicio;
    }

    public void setControlPreciosServicio(ControlPreciosServicio controlPreciosServicio) {
        this.controlPreciosServicio = controlPreciosServicio;
    }

    public List<ControlPrecios> getListaControlPrecios() {
        return listaControlPrecios;
    }

    public void setListaControlPrecios(List<ControlPrecios> listaControlPrecios) {
        this.listaControlPrecios = listaControlPrecios;
    }
    
    @PostConstruct
    public void init() {
        listaControlPrecios=controlPreciosServicio.obtenerListaPreciosOrdenada();
        
    }

    
}
