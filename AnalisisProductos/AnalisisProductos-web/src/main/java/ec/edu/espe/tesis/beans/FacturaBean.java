/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.facturas.model.Factura;
import ec.edu.espe.tesis.servicio.FacturaServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author alterbios
 */
@Named(value = "facturaBean")
@ViewScoped
public class FacturaBean implements Serializable {

    Factura facturaSeleccionada;
    List<Object[]> listaFacturasPorEstab;
    List<Object[]> listaDetallesFacturaSeleccionada;
    List<Factura> listaFacturas;
    String codigoFactura;

    @Inject
    FacturaServicio facturaServicio;
    
    @Inject
    HttpSessionHandler sesion;

    public List<Object[]> getListaDetallesFacturaSeleccionada() {
        return listaDetallesFacturaSeleccionada;
    }

    public void setListaDetallesFacturaSeleccionada(List<Object[]> listaDetallesFacturaSeleccionada) {
        this.listaDetallesFacturaSeleccionada = listaDetallesFacturaSeleccionada;
    }

    public String getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(String codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    public List<Object[]> getListaFacturasPorEstab() {
        return listaFacturasPorEstab;
    }

    public void setListaFacturasPorEstab(List<Object[]> listaFacturasPorEstab) {
        this.listaFacturasPorEstab = listaFacturasPorEstab;
    }
 
    public List<Factura> getListaFacturas() {
        return listaFacturas;
    }

    public void setListaFacturas(List<Factura> listaFacturas) {
        this.listaFacturas = listaFacturas;
    }

    public Factura getFacturaSeleccionada() {
        return facturaSeleccionada;
    }

    public void setFacturaSeleccionada(Factura facturaSeleccionada) {
        this.facturaSeleccionada = facturaSeleccionada;
    }

    @PostConstruct
    public void Init() {
        facturaSeleccionada=null;
        listaFacturas = facturaServicio.obtenerFacturasConCriterio(Integer.parseInt(sesion.getId()));
        listaFacturasPorEstab=facturaServicio.obtenerFacturasPorEstablecimiento(sesion.getId());

    }
    
    public void cargarDetallesFactura(){
        listaDetallesFacturaSeleccionada=facturaServicio.obtenerDetallesFactura(facturaSeleccionada.getCodigo().toString());
    }
    
    public void cerrarVentana() {
        facturaSeleccionada=null;
        RequestContext.getCurrentInstance().execute("PF('factura').hide();");
    }

    public void abrirInfo() {
        cargarDetallesFactura();
        RequestContext.getCurrentInstance().execute("PF('factura').show();");
    }

}
