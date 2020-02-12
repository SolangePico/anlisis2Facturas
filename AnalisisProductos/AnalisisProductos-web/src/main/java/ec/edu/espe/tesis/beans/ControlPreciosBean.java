/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.facturas.model.ControlPrecios;
import ec.edu.espe.tesis.facturas.model.Factura;
import ec.edu.espe.tesis.facturas.model.InfoTributaria;
import ec.edu.espe.tesis.servicio.ControlPreciosServicio;
import ec.edu.espe.tesis.servicio.FacturaServicio;
import ec.edu.espe.tesis.servicio.InfoTributariaServicio;
import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

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
    @Inject
    private FacturaServicio facturaServicio;
    @Inject 
    private HttpSessionHandler sesion;

    private String establecimientoId;
    private List<Object[]> listaControlPrecios;
    private List<InfoTributaria> listaEstablecimientos;
    private InfoTributaria establecimientoSeleccionado;
    private List<Object[]> listaProductosVariacion;
    private String producto;
    private LineChartModel chartProducto;
    private List<Object[]> listaSupermercados;
    private String supermercadoSeleccionado;
    private List<Object[]> listaProductosPorSupermercado;// obtener producto por establecimiento del supermercado seleccionado
    private List<Integer> Anio;
    private List<Factura> listaFacturas;
    private int anioSeleccionado;
    
    @PostConstruct
    public void init() {
        listaFacturas = facturaServicio.obtenerFacturasOrdenadas(-1);
        Calendar cal = null;
        Anio= new ArrayList();
        cal = Calendar.getInstance();
        cal.setTime(listaFacturas.get(0).getFechaemision());
        Anio.add(cal.get(Calendar.YEAR));
        for (int i = 1; i < listaFacturas.size(); i++) {
            cal.setTime(listaFacturas.get(i).getFechaemision());
            if (cal.get(Calendar.YEAR) != Anio.get(Anio.size() - 1)) {
                Anio.add(cal.get(Calendar.YEAR));
            }
        }
        listaProductosVariacion = controlPreciosServicio.obtenerListaPreciosPorProductoTodo();
        listaSupermercados = infoTributariaServicio.obtenerSupermercados();
        producto = listaProductosVariacion.get(0)[5].toString();
        supermercadoSeleccionado = listaSupermercados.get(0)[0].toString();
        listaEstablecimientos = infoTributariaServicio.obtenerListaEstablecimientos();
        listaControlPrecios = controlPreciosServicio.obtenerListaPreciosPorProducto(Integer.parseInt(producto));
        try {
            createLineModels();
        } catch (ParseException ex) {
            Logger.getLogger(ControlPreciosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaFacturas = facturaServicio.obtenerFacturasOrdenadas(Integer.parseInt(sesion.getId()));
    }

    public void buscarEstablecimiento() {
        establecimientoSeleccionado = infoTributariaServicio.obtenerEstablecimientoPorCodigo(establecimientoId);
    }

    public String obtenerNombre(String ruc) {
        String nom="";
        for (int i = 0; i < listaSupermercados.size(); i++) {
            if(ruc.equals(listaSupermercados.get(i)[0].toString())){
                nom=listaSupermercados.get(i)[1].toString();
            }

        }
        return nom;
    }

    public String obtenerCantidad(String ruc) {
        String cant="";
        for (int i = 0; i < listaSupermercados.size(); i++) {
            if(ruc.equals(listaSupermercados.get(i)[0].toString())){
                cant=listaSupermercados.get(i)[2].toString();
            }
        }
        return cant;
    }

    public void obtenerListaDetalleProducto() {
        listaProductosPorSupermercado = controlPreciosServicio.obtenerListaProdSuper(supermercadoSeleccionado, producto, anioSeleccionado);
    }

    public void mostrarVariacion() {
        try {
            listaControlPrecios = controlPreciosServicio.obtenerListaPreciosPorProducto(Integer.parseInt(producto));
            if (listaControlPrecios.size() > 0) {

                createLineModels();
            }
        } catch (ParseException ex) {
            Logger.getLogger(ControlPreciosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createLineModels() throws ParseException {
        Double precioMaximo = 0.0;
        Double precioMinimo = 0.0;
        for (int i = 0; i < listaControlPrecios.size(); i++) {
            if (precioMaximo < (Double.parseDouble(listaControlPrecios.get(i)[0].toString()))) {
                precioMaximo = Double.parseDouble(listaControlPrecios.get(i)[0].toString());
            }
            if (precioMinimo > (Double.parseDouble(listaControlPrecios.get(i)[0].toString()))) {
                precioMinimo = Double.parseDouble(listaControlPrecios.get(i)[0].toString());
            }

        }
        chartProducto = new LineChartModel();
        chartProducto = initLinearModel();

        chartProducto.setTitle("Cambio de precio en el tiempo");
        chartProducto.setLegendPosition("e");

        Axis yAxis = chartProducto.getAxis(AxisType.Y);
        Axis xAxis = chartProducto.getAxis(AxisType.X);
        xAxis.setMin(0);
        xAxis.setMax(12);
        xAxis.setTickInterval("1");
        yAxis.setMin(precioMinimo - 1);
        yAxis.setMax(precioMaximo + 1);
        chartProducto.setExtender("skinChart");
    }

    private LineChartModel initLinearModel() throws ParseException {
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();

        int flag = 0;
        for (int i = 0; i < listaControlPrecios.size(); i++) {
            Calendar cal = null;
            Calendar cal1 = null;
            Date date;
            Date date2;
            int dia;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                date = (Date) formatter.parse(listaControlPrecios.get(i)[1].toString());
                cal = Calendar.getInstance();
                cal.setTime(date);
                dia = cal.get(Calendar.DAY_OF_YEAR);
                if (i > 0) {
                    date2 = (Date) formatter.parse(listaControlPrecios.get(i - 1)[1].toString());
                    cal1 = Calendar.getInstance();
                    cal1.setTime(date2);
                    if (cal.get(Calendar.YEAR) != cal1.get(Calendar.YEAR)) {
                        model.addSeries(series1);
                        series1 = new LineChartSeries();
                        series1.setLabel(cal.get(Calendar.YEAR) + "");
                    }
                } else {
                    series1.setLabel(cal.get(Calendar.YEAR) + "");
                    model.addSeries(series1);
                }
            } catch (ParseException e) {
                dia = i;
            }

            series1.set(dia * 12 / 366, Double.parseDouble(listaControlPrecios.get(i)[0].toString()));
        }

        return model;
    }

    public InfoTributaria getEstablecimientoSeleccionado() {
        return establecimientoSeleccionado;
    }

    public List<Object[]> getListaProductosPorSupermercado() {
        return listaProductosPorSupermercado;
    }

    public void setListaProductosPorSupermercado(List<Object[]> listaProductosPorSupermercado) {
        this.listaProductosPorSupermercado = listaProductosPorSupermercado;
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

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public List<Object[]> getListaProductosVariacion() {
        return listaProductosVariacion;
    }

    public void setListaProductosVariacion(List<Object[]> listaProductosVariacion) {
        this.listaProductosVariacion = listaProductosVariacion;
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

    public List<Object[]> getListaControlPrecios() {
        return listaControlPrecios;
    }

    public void setListaControlPrecios(List<Object[]> listaControlPrecios) {
        this.listaControlPrecios = listaControlPrecios;
    }

    public LineChartModel getChartProducto() {
        return chartProducto;
    }

    public void setChartProducto(LineChartModel chartProducto) {
        this.chartProducto = chartProducto;

    }

    public List<Integer> getAnio() {
        return Anio;
    }

    public void setAnio(List<Integer> Anio) {
        this.Anio = Anio;
    }

    public int getAnioSeleccionado() {
        return anioSeleccionado;
    }

    public void setAnioSeleccionado(int anioSeleccionado) {
        this.anioSeleccionado = anioSeleccionado;
    }
    
}
