/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import com.thoughtworks.xstream.XStream;
import ec.edu.espe.tesis.facturas.facade.ProductoFacade;
import ec.edu.espe.tesis.facturas.model.Producto;
import ec.edu.espe.tesis.modeloXML.AutorizacionXML;
import ec.edu.espe.tesis.modeloXML.ComprobanteXML;
import ec.edu.espe.tesis.modeloXML.DetalleXML;
import ec.edu.espe.tesis.modeloXML.FacturaXML;
import ec.edu.espe.tesis.modeloXML.ImpuestoXML;
import ec.edu.espe.tesis.modeloXML.InfoFacturaXML;
import ec.edu.espe.tesis.modeloXML.InfoTributariaXML;
import ec.edu.espe.tesis.modeloXML.TotalImpuestoXML;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import ec.edu.espe.tesis.servicio.*;
import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author danny
 */
@Named(value = "productoBean")
@ViewScoped
public class ProductoBean implements Serializable {

    /**
     * Creates a new instance of ProductoBean
     */
    @Inject
    ProductoServicio productoServicio;

    @Inject
    ControlPreciosServicio controlPrecioServicio;

    @Inject
    ProductoFacade productoFacade;

    @Inject
    FacturaServicio facturaServicio;

    @Inject
    HttpSessionHandler sesion;

    private List<Producto> listaProductos;
    private List<Object[]> listaPrecioProducto;
    private List<Object[]> listaMasComprados;
    private Producto productoSeleccionado;
    private Object[] codigoProducto;
    private LineChartModel chartProducto;

    @PostConstruct
    public void init() {
        codigoProducto = null;
        listaMasComprados = productoServicio.obtenerProductosPorUsuario(sesion.getId());
        listaProductos = productoFacade.findAll();
        if (listaMasComprados.size() > 0) {
            try {
                listaPrecioProducto = controlPrecioServicio.obtenerListaPreciosPorProducto(Integer.parseInt(listaMasComprados.get(0)[0].toString()));
                createLineModels();
            } catch (ParseException ex) {
                Logger.getLogger(ProductoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void createLineModels() throws ParseException {
        Double precioMaximo = 0.0;
        for (int i = 0; i < listaPrecioProducto.size(); i++) {
            if (precioMaximo < (Double.parseDouble(listaPrecioProducto.get(i)[0].toString()))) {
                precioMaximo = Double.parseDouble(listaPrecioProducto.get(i)[0].toString());
            }
        }
        chartProducto = initLinearModel();
        chartProducto.setTitle("Cambio de precio en el tiempo");
        chartProducto.setLegendPosition("e");

        Axis yAxis = chartProducto.getAxis(AxisType.Y);
        Axis xAxis = chartProducto.getAxis(AxisType.X);
        xAxis.setMin(0);
        xAxis.setMax(12);
        xAxis.setTickInterval("1");
        yAxis.setMin(0);
        yAxis.setMax(precioMaximo*2);
        chartProducto.setExtender("skinChart");
    }

    private LineChartModel initLinearModel() throws ParseException {
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();

        int flag = 0;
        for (int i = 0; i < listaPrecioProducto.size(); i++) {
            Calendar cal = null;
            Calendar cal1 = null;
            Date date;
            Date date2;
            int dia;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                date = (Date) formatter.parse(listaPrecioProducto.get(i)[1].toString());
                cal = Calendar.getInstance();
                cal.setTime(date);
                dia = cal.get(Calendar.DAY_OF_YEAR);
                if (i > 0) {
                    date2 = (Date) formatter.parse(listaPrecioProducto.get(i - 1)[1].toString());
                    cal1 = Calendar.getInstance();
                    cal1.setTime(date2);
                    if (cal.get(Calendar.YEAR) != cal1.get(Calendar.YEAR)) {
                        model.addSeries(series1);
                        series1 = new LineChartSeries();
                        series1.setLabel(cal.get(Calendar.YEAR) + "");
                    }
                }else{
                   series1.setLabel(cal.get(Calendar.YEAR) + ""); 
                }
            } catch (ParseException e) {
                dia = i;
            }

            series1.set(dia*12/366, Double.parseDouble(listaPrecioProducto.get(i)[0].toString()));
        }

        return model;
    }

    public LineChartModel getChartProducto() {
        return chartProducto;
    }

    public void setChartProducto(LineChartModel chartProducto) {
        this.chartProducto = chartProducto;
    }

    public List<Object[]> getListaPrecioProducto() {
        return listaPrecioProducto;
    }

    public void setListaPrecioProducto(List<Object[]> listaPrecioProducto) {
        this.listaPrecioProducto = listaPrecioProducto;
    }

    public Object[] getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Object[] codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public List<Object[]> getListaMasComprados() {
        return listaMasComprados;
    }

    public void setListaMasComprados(List<Object[]> listaMasComprados) {
        this.listaMasComprados = listaMasComprados;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public void cerrarVentana() {

        RequestContext.getCurrentInstance().execute("PF('producto').hide();");
    }

    public void abrirInfo() {
        cargarSeleccionado();
        RequestContext.getCurrentInstance().execute("PF('producto').show();");
    }

    public void cargarSeleccionado() {
        try {
            listaPrecioProducto = controlPrecioServicio.obtenerListaPreciosPorProducto((Integer) codigoProducto[0]);
            productoSeleccionado = productoFacade.obtenerProductoPorCodigoP((Integer) codigoProducto[0]).get(0);
            createLineModels();
        } catch (ParseException ex) {
            Logger.getLogger(ProductoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
