/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;


import org.primefaces.context.RequestContext;
import ec.edu.espe.tesis.facturas.model.ControlPrecios;
import ec.edu.espe.tesis.facturas.model.InfoTributaria;
import ec.edu.espe.tesis.servicio.ControlPreciosServicio;
import ec.edu.espe.tesis.servicio.InfoTributariaServicio;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.omnifaces.util.Messages;

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
    @Inject
     InfoTributariaServicio infoTributariaServicio;
    
    private String establecimientoId;
    private List<ControlPrecios> listaControlPrecios;
    private List<InfoTributaria> listaEstablecimientos;
    private InfoTributaria establecimientoSeleccionado;

    public InfoTributaria getEstablecimientoSeleccionado() {
        return establecimientoSeleccionado;
    }

    public void setEstablecimientoSeleccionado(InfoTributaria establecimientoSeleccionado) {
        this.establecimientoSeleccionado = establecimientoSeleccionado;
    }

    public String getEstablecimientoId() {
        return establecimientoId;
    }

    public void setEstablecimientoId(String establecimientoId) {
        this.establecimientoId = establecimientoId;
    }

    public List<InfoTributaria> getListaEstablecimientos() {
        return listaEstablecimientos;
    }

    public void setListaEstablecimientos(List<InfoTributaria> listaEstablecimientos) {
        this.listaEstablecimientos = listaEstablecimientos;
    }

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
        listaEstablecimientos=infoTributariaServicio.obtenerListaEstablecimientos();
    }
    
    public void buscarEstablecimiento() {
        establecimientoSeleccionado=infoTributariaServicio.obtenerEstablecimientoPorCodigo(establecimientoId);
    }

    private void cargarControlPrecios() {
       
    }
    
}
