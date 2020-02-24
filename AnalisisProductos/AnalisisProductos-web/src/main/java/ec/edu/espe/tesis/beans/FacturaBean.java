/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.facturas.model.Factura;
import ec.edu.espe.tesis.servicio.FacturaServicio;
import ec.edu.espe.tesis.servicio.ProductoServicio;
import ec.edu.espe.tesis.util.FacturaCodificacion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
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
    private List<Object[]> listaFacPorProd;
    private List<Factura> listaFacturas;
    private String codigoFactura;
    private Date fechaInicio;
    private Date fechaFin;
    private String producto;
    private boolean prodFlag;
    private boolean fechFlag;
    private List<Object[]> listaProds;

    @Inject
    FacturaServicio facturaServicio;

    @Inject
    ProductoServicio productoServicio;

    @Inject
    HttpSessionHandler sesion;

    public boolean isFechFlag() {
        return fechFlag;
    }

    public List<Object[]> getListaProds() {
        return listaProds;
    }

    public void setListaProds(List<Object[]> listaProds) {
        this.listaProds = listaProds;
    }

    public void setFechFlag(boolean fechFlag) {
        this.fechFlag = fechFlag;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public boolean isProdFlag() {
        return prodFlag;
    }

    public void setProdFlag(boolean prodFlag) {
        this.prodFlag = prodFlag;
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

    public List<Object[]> getListaFacPorProd() {
        return listaFacPorProd;
    }

    public void setListaFacPorProd(List<Object[]> listaFacPorProd) {
        this.listaFacPorProd = listaFacPorProd;
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

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Factura getFacturaSeleccionada() {
        return facturaSeleccionada;
    }

    public void setFacturaSeleccionada(Factura facturaSeleccionada) {
        this.facturaSeleccionada = facturaSeleccionada;
    }

    @PostConstruct
    public void Init() {
        fechFlag = true;
        facturaSeleccionada = null;
        fechaFin = getToday();
        fechaInicio = getToday();
        listaFacturas = facturaServicio.obtenerFacturasConCriterio(Integer.parseInt(sesion.getId()));
        listaFacturasPorEstab = facturaServicio.obtenerFacturasPorEstablecimiento(sesion.getId());
        listaProds=productoServicio.obtenerProductosPorUsuario(sesion.getId());
    }

    public String nombreProd(String cod) {
        return productoServicio.obtenerNombreProductoPorCodigo(cod);
    }

    public Date getToday() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    public void mostrarTodo() {
        listaFacturas = facturaServicio.obtenerFacturasConCriterio(Integer.parseInt(sesion.getId()));
    }

    public void verComparacion() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String contexto = request.getRequestURL().toString();
        String FacIdTemp = FacturaCodificacion.codificarId(String.valueOf(facturaSeleccionada.getCodigo()));
        contexto = contexto.substring(0, contexto.indexOf("user/") + 5);
        contexto = contexto.concat("CompararFacturas.xhtml?facId=" + FacIdTemp);
        PrimeFaces.current().executeScript("window.open('" + contexto + "','_blank');");

    }

    public void buscarRangoFechas() {
        if (fechaInicio.before(fechaFin) || fechaInicio.equals(fechaFin)) {
            listaFacturas = facturaServicio.obtenerFacturasPorFecha(fechaInicio, fechaFin, sesion.getId());
            if (listaFacturas.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No se encuentran facturas en este rango de fechas"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La Fecha inicial debe ser menor a la final"));
            fechaFin = getToday();
        }
    }

    public void buscarFacturasPorProducto() {

        listaFacPorProd = facturaServicio.obtenerFacturasPorProducto(sesion.getId(), producto, null, fechaFin);
        listaFacturas = new ArrayList();
        if (listaFacPorProd != null) {
            for (int i = 0; i < listaFacPorProd.size(); i++) {
                Factura fac = new Factura();
                fac = facturaServicio.obtenerFacturaPorCodigo(listaFacPorProd.get(i)[0].toString());
                listaFacturas.add(fac);

            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "No existen facturas con el producto seleccionado"));

        }
    }

    public void cargarDetallesFactura() {
        listaDetallesFacturaSeleccionada = facturaServicio.obtenerDetallesFactura(facturaSeleccionada.getCodigo().toString());
    }

    public void cerrarVentana() {
        facturaSeleccionada = null;
        RequestContext.getCurrentInstance().execute("PF('factura').hide();");
    }

    public void setFechaYmes(Object[] mes, int anio) {
        facturaSeleccionada = null;
        listaFacturas = facturaServicio.obtenerFacturasPorMesYAnio(mes[0].toString(), anio, sesion.getId());
    }

    public void abrirInfo() {
        cargarDetallesFactura();
        RequestContext.getCurrentInstance().execute("PF('factura').show();");
    }

}
