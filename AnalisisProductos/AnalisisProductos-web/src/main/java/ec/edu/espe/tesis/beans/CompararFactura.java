/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.facturas.model.DetalleFactura;
import ec.edu.espe.tesis.facturas.model.Factura;
import ec.edu.espe.tesis.servicio.FacturaServicio;
import ec.edu.espe.tesis.servicio.InfoTributariaServicio;
import ec.edu.espe.tesis.util.FacturaCodificacion;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.view.facelets.FaceletContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author danny
 */
@Named(value = "compararFacturaBean")
@ViewScoped
public class CompararFactura implements Serializable {

    @Inject
    private FacturaServicio facturaServicio;
    @Inject
    private InfoTributariaServicio infoTributariaServicio;

    private Factura factura;
    private List<Object[]> listaDetalles;
    private List<Object[]> listaDetallesMasBarato;
    private String codFactura;
    private String supermercadoSeleccionado;
    private List<Object[]> listaSupermercados;
    private List<Integer> listaAnio;
    private int anio;
    private double totalAhorrado;

    @PostConstruct
    public void init() {
        listaDetallesMasBarato = new ArrayList();
        listaDetalles = new ArrayList();
        listaSupermercados = infoTributariaServicio.obtenerSupermercados();
        FaceletContext fc = (FaceletContext) FacesContext.getCurrentInstance().getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
        codFactura = (String) fc.getAttribute("facId");
        if (codFactura == null) {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            codFactura = request.getParameter("facId");
        }
        if (codFactura != null) {
            factura = facturaServicio.obtenerFacturaPorCodigo(FacturaCodificacion.decodificarId(codFactura));
            listaDetalles = facturaServicio.obtenerDetallesFactura(factura.getCodigo().toString());
            verProductosMasBaratos();
        }
        cargarAniosFactura();
    }

    public String obtenerPrecio(String descripcion) {
        for (int i = 0; i < listaDetalles.size(); i++) {
            if (listaDetalles.get(i)[0].equals(descripcion)) {
                return listaDetalles.get(i)[2].toString();
            }
        }
        return "0";
    }

    public String obtenerNombre(String ruc) {
        String nom = "";
        for (int i = 0; i < listaSupermercados.size(); i++) {
            if (ruc.equals(listaSupermercados.get(i)[0].toString())) {
                nom = listaSupermercados.get(i)[1].toString();
            }

        }
        return nom;
    }

    public String diferenciaFechas(String date) {
        int difM=0;
        Date fecha1;
        Date fecha2;
        fecha1 = ParseFecha(date);
        fecha2 = factura.getFechaemision();
        try {
            Calendar inicio = new GregorianCalendar();
            Calendar fin = new GregorianCalendar();
            inicio.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            fin.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(fecha2.toString()));
            int difA = fin.get(Calendar.YEAR) - inicio.get(Calendar.YEAR);
            difM = difA * 12 + fin.get(Calendar.MONTH) - inicio.get(Calendar.MONTH);
            
        } catch (ParseException ex) {

        }
        if(difM==0){
           return "-"; 
        }else{
            if(Math.abs(difM)==1){
                return Math.abs(difM)+" Mes";
            }else{
                return Math.abs(difM)+ " Meses";
            }
        }
    }

    public Date ParseFecha(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        return fechaDate;
    }

    public String obtenerCantidad(String ruc) {
        String cant = "";
        for (int i = 0; i < listaSupermercados.size(); i++) {
            if (ruc.equals(listaSupermercados.get(i)[0].toString())) {
                cant = listaSupermercados.get(i)[2].toString();
            }
        }
        return cant;
    }

    public void actualizarDatos() {
        listaDetallesMasBarato = new ArrayList();
        Boolean flagNo = true;
        if (!supermercadoSeleccionado.equals("1") || anio != -1) {
            for (int i = 0; i < listaDetalles.size(); i++) {
                if (facturaServicio.obtenerProductoMasBaratoPorEstabYAnio(listaDetalles.get(i)[4].toString(), factura.getCodigo().toString(), supermercadoSeleccionado, anio + "") != null) {
                    listaDetallesMasBarato.add(facturaServicio.obtenerProductoMasBaratoPorEstabYAnio(listaDetalles.get(i)[4].toString(), factura.getCodigo().toString(), supermercadoSeleccionado, anio + ""));
                    flagNo = false;
                } else {
                    Object[] obj = new Object[6];
                    obj[0] = listaDetalles.get(i)[2];
                    obj[1] = listaDetalles.get(i)[0];
                    obj[2] = factura.getInfCodigo().getRazonsocial();
                    obj[3] = factura.getFechaemision();
                    obj[4] = factura.getInfCodigo().getEstablecimiento();
                    obj[5] = factura.getInfCodigo().getDireccion();
                    listaDetallesMasBarato.add(obj);
                }

            }
            calcularTotal();
            if (flagNo) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No se encontraron productos en otros establecimientos"));

            }
        } else {
            verProductosMasBaratos();
        }
    }

    public void calcularTotal() {
        totalAhorrado = 0.0;
        for (int i = 0; i < listaDetallesMasBarato.size(); i++) {
            double aux;
            double cantidad;
            cantidad = Double.parseDouble(listaDetalles.get(i)[1].toString());
            aux = Double.parseDouble(listaDetallesMasBarato.get(i)[0].toString());
            totalAhorrado = totalAhorrado + aux * cantidad;

        }
    }
    
    public String formatoNumero(Object valor) {
        String valorFormat = "";
        DecimalFormat formato = new DecimalFormat("#.##");
        valorFormat = formato.format(valor);
        return valorFormat;
    }

    public void verProductosMasBaratos() {
        listaDetallesMasBarato = new ArrayList();
        boolean flagNo = true;
        for (int i = 0; i < listaDetalles.size(); i++) {
            if (facturaServicio.obtenerProductoMasBarato(listaDetalles.get(i)[4].toString(), factura.getCodigo().toString()) != null) {
                listaDetallesMasBarato.add(facturaServicio.obtenerProductoMasBarato(listaDetalles.get(i)[4].toString(), factura.getCodigo().toString()));
                flagNo = false;
            } else {
                Object[] obj = new Object[6];
                obj[0] = listaDetalles.get(i)[2];
                obj[1] = listaDetalles.get(i)[0];
                obj[2] = factura.getInfCodigo().getRazonsocial();
                obj[3] = factura.getFechaemision();
                obj[4] = factura.getInfCodigo().getEstablecimiento();
                obj[5] = factura.getInfCodigo().getDireccion();
                listaDetallesMasBarato.add(obj);
            }
        }
        calcularTotal();
        if (flagNo) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No se encontraron productos en otros establecimientos"));
        }
    }

    private void cargarAniosFactura() {
        List<Factura> listaFacturas;
        listaFacturas = facturaServicio.obtenerFacturasOrdenadas(-1);
        Calendar cal = null;
        listaAnio = new ArrayList();
        cal = Calendar.getInstance();
        cal.setTime(listaFacturas.get(0).getFechaemision());
        listaAnio.add(cal.get(Calendar.YEAR));
        for (int i = 1; i < listaFacturas.size(); i++) {
            cal.setTime(listaFacturas.get(i).getFechaemision());
            if (cal.get(Calendar.YEAR) != listaAnio.get(listaAnio.size() - 1)) {
                listaAnio.add(cal.get(Calendar.YEAR));
            }
        }
    }

    public String verificarProductoFactura(String razS, String estab, String Dir) {
        if (razS.equals(factura.getInfCodigo().getRazonsocial())) {
            if (estab.equals(factura.getInfCodigo().getEstablecimiento())) {
                if (Dir.equals(factura.getInfCodigo().getDireccion())) {
                    return "black";
                } else {
                    return "blue";
                }
            } else {
                return "blue";
            }
        } else {
            return "greenyellow";
        }
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<Object[]> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<Object[]> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public List<Object[]> getListaDetallesMasBarato() {
        return listaDetallesMasBarato;
    }

    public void setListaDetallesMasBarato(List<Object[]> listaDetallesMasBarato) {
        this.listaDetallesMasBarato = listaDetallesMasBarato;
    }

    public String getSupermercadoSeleccionado() {
        return supermercadoSeleccionado;
    }

    public void setSupermercadoSeleccionado(String supermercadoSeleccionado) {
        this.supermercadoSeleccionado = supermercadoSeleccionado;
    }

    public List<Object[]> getListaSupermercados() {
        return listaSupermercados;
    }

    public void setListaSupermercados(List<Object[]> listaSupermercados) {
        this.listaSupermercados = listaSupermercados;
    }

    public List<Integer> getListaAnio() {
        return listaAnio;
    }

    public void setListaAnio(List<Integer> listaAnio) {
        this.listaAnio = listaAnio;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getTotalAhorrado() {
        return totalAhorrado;
    }

    public void setTotalAhorrado(double totalAhorrado) {
        this.totalAhorrado = totalAhorrado;
    }

}
