/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.facturas.model.Factura;
import ec.edu.espe.tesis.servicio.FacturaServicio;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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

    private Factura facturaSeleccionada;
    private List<Object[]> listaFacturasPorEstab;
    private List<Object[]> listaDetallesFacturaSeleccionada;
    private List<Factura> listaFacturas;
    private String codigoFactura;
    private Date fechaInicio;
    private Date fechaFin;

    @Inject
    FacturaServicio facturaServicio;

    @Inject
    HttpSessionHandler sesion;

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

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

        facturaSeleccionada = null;
        fechaFin = getToday();
        fechaInicio = getToday();
        listaFacturas = facturaServicio.obtenerFacturasConCriterio(Integer.parseInt(sesion.getId()));
        listaFacturasPorEstab = facturaServicio.obtenerFacturasPorEstablecimiento(sesion.getId());

    }

    public Date getToday() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    public void mostrarTodo(){
       listaFacturas = facturaServicio.obtenerFacturasConCriterio(Integer.parseInt(sesion.getId())); 
    }
    
    public void buscarRangoFechas() {
        if (fechaInicio.before(fechaFin) || fechaInicio.equals(fechaFin)) {
            listaFacturas = facturaServicio.obtenerFacturasPorFecha(fechaInicio, fechaFin, sesion.getId());
            if (listaFacturas.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No se encuentran facturas en este rango de fechas"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La Fecha inicial debe ser menor a la final"));
            fechaFin=getToday();
        }
    }

    public void cargarDetallesFactura() {
        listaDetallesFacturaSeleccionada = facturaServicio.obtenerDetallesFactura(facturaSeleccionada.getCodigo().toString());
    }

    public void cerrarVentana() {
        facturaSeleccionada = null;
        RequestContext.getCurrentInstance().execute("PF('factura').hide();");
    }
    
    public void setFechaYmes(Object[] mes, int anio){
        facturaSeleccionada = null;
        listaFacturas = facturaServicio.obtenerFacturasPorMesYAnio(mes[0].toString(), anio, sesion.getId());
    }

    public void abrirInfo() {
        cargarDetallesFactura();
        RequestContext.getCurrentInstance().execute("PF('factura').show();");
    }

}
