/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.facturas.facade.ProductoFacade;
import ec.edu.espe.tesis.facturas.model.Factura;
import ec.edu.espe.tesis.facturas.model.Producto;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import ec.edu.espe.tesis.servicio.*;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private LineChartModel chartProducto1;
    private List<Integer> anio;
    private int anioSeleccionado;
    private List<Object[]> listaProdPorAnio;
    private List<Double[]> gastosTrimestre;
    private List<Object[]> listaProdAnios;

    @PostConstruct
    public void init() {
        codigoProducto = null;
        listaMasComprados = productoServicio.obtenerProductosPorUsuario(sesion.getId());
        listaProductos = productoFacade.findAll();
        codigoProducto = listaMasComprados.get(0);
        gastosTrimestre = new ArrayList();
        listaProdPorAnio = new ArrayList();
        listaProdAnios = controlPrecioServicio.obtenerListaProductoAnios(sesion.getId(), codigoProducto[0].toString());

        for (int i = 0; i < listaProdAnios.size(); i++) {
            anio.add((Integer) listaProdAnios.get(i)[0]);
        }
        anioSeleccionado = anio.get(0);
        if (listaMasComprados.size() > 0) {
            try {
                listaPrecioProducto = controlPrecioServicio.obtenerListaPreciosPorProducto(Integer.parseInt(listaMasComprados.get(0)[0].toString()));
                createLineModels();
            } catch (ParseException ex) {
                Logger.getLogger(ProductoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void actualizarAnio() {
        //VAlidar que solo salgan años q tenga el producto :v 

        listaProdPorAnio = controlPrecioServicio.obtenerListaProductoPorAnio(anioSeleccionado, codigoProducto[0].toString(), sesion.getId());
        listaProdAnios = controlPrecioServicio.obtenerListaProductoAnios(sesion.getId(), codigoProducto[0].toString());
        for (int i = 0; i < listaProdAnios.size(); i++) {
            anio.add(Integer.parseInt(listaProdAnios.get(i)[0].toString()));
        }
        try {

            Double[] Total = new Double[4];
            Double[] Promedio = new Double[4];
            Total[0] = 0.0;
            Total[1] = 0.0;
            Total[2] = 0.0;
            Total[3] = 0.0;
            Promedio[0] = 0.0;
            Promedio[1] = 0.0;
            Promedio[2] = 0.0;
            Promedio[3] = 0.0;
            for (int i = 0; i < listaProdPorAnio.size(); i++) {
                if ((Integer) listaProdPorAnio.get(i)[2] > 0 && (Integer) listaProdPorAnio.get(i)[2] < 4) {
                    Total[0] = Total[0] + Double.parseDouble(listaProdPorAnio.get(i)[1].toString());
                } else {
                    if ((Integer) listaProdPorAnio.get(i)[2] > 3 && (Integer) listaProdPorAnio.get(i)[2] < 7) {
                        Total[1] = Total[1] + Double.parseDouble(listaProdPorAnio.get(i)[1].toString());
                    } else {
                        if ((Integer) listaProdPorAnio.get(i)[2] > 6 && (Integer) listaProdPorAnio.get(i)[2] < 10) {
                            Total[2] = Total[2] + Double.parseDouble(listaProdPorAnio.get(i)[1].toString());
                        } else {
                            Total[3] = Total[3] + Double.parseDouble(listaProdPorAnio.get(i)[3].toString());
                        }
                    }
                }
            }

            Promedio[0] = Total[0] / 3;
            Promedio[1] = Total[1] / 3;
            Promedio[2] = Total[2] / 3;
            Promedio[3] = Total[3] / 3;
            for (int i = 0; i < 4; i++) {
                Double[] trim = new Double[2];
                trim[0] = Total[i];
                trim[1] = Promedio[i];
                gastosTrimestre.add(trim);
            }
            createLineModels1();
        } catch (ParseException ex) {
            Logger.getLogger(ProductoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String obtenerTrimestre(int index) {
        switch (index) {
            case 1:
                return "1er Trimestre";
            case 2:
                return "2do Trimestre";
            case 3:
                return "3er Trimestre";
            case 4:
                return "4to Trimestre";
            default:
                return "";
        }
    }

    public void abrirInfoConsumo() throws ParseException {
        actualizarAnio();
        RequestContext.getCurrentInstance().execute("PF('consumoT').show();");

    }

    private void createLineModels() throws ParseException {
        Double precioMaximo = 0.0;
        Double precioMinimo = 0.0;
        for (int i = 0; i < listaPrecioProducto.size(); i++) {
            if (precioMaximo < (Double.parseDouble(listaPrecioProducto.get(i)[0].toString()))) {
                precioMaximo = Double.parseDouble(listaPrecioProducto.get(i)[0].toString());
            }
            if (precioMinimo > (Double.parseDouble(listaPrecioProducto.get(i)[0].toString()))) {
                precioMinimo = Double.parseDouble(listaPrecioProducto.get(i)[0].toString());
            }
        }
        chartProducto = initLinearModel();
        chartProducto.setTitle("Cambio de precio en el tiempo");
        chartProducto.setLegendPosition("e");

        Axis yAxis = chartProducto.getAxis(AxisType.Y);
        Axis xAxis = chartProducto.getAxis(AxisType.X);
        xAxis.setMin(0);
        xAxis.setMax(12);
        xAxis.setLabel("Mes");
        xAxis.setTickInterval("1");
        yAxis.setMin(precioMinimo / 2);
        yAxis.setMax(precioMaximo * 1.3);
        yAxis.setLabel("Precio");
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
                } else {
                    series1.setLabel(cal.get(Calendar.YEAR) + "");
                }
            } catch (ParseException e) {
                dia = i;
            }

            series1.set(dia * 12 / 366, Double.parseDouble(listaPrecioProducto.get(i)[0].toString()));
            if ((i + 1) == listaPrecioProducto.size()) {
                model.addSeries(series1);
            }
        }

        return model;
    }

    private void createLineModels1() throws ParseException {
        Double precioMaximo = 0.0;
        Double precioMinimo = 0.0;
        for (int i = 0; i < listaProdPorAnio.size(); i++) {
            if (precioMaximo < (Double.parseDouble(listaProdPorAnio.get(i)[1].toString()))) {
                precioMaximo = Double.parseDouble(listaProdPorAnio.get(i)[1].toString());
            }
            if (precioMinimo > (Double.parseDouble(listaProdPorAnio.get(i)[1].toString()))) {
                precioMinimo = Double.parseDouble(listaProdPorAnio.get(i)[1].toString());
            }
        }
        chartProducto1 = initLinearModel1();
        chartProducto1.setTitle("Consumo del producto Por Anio");
        chartProducto1.setLegendPosition("e");

        Axis yAxis = chartProducto1.getAxis(AxisType.Y);
        Axis xAxis = chartProducto1.getAxis(AxisType.X);
        xAxis.setMin(0);
        xAxis.setMax(12);
        xAxis.setLabel("Mes");
        xAxis.setTickInterval("1");
        yAxis.setMin(precioMinimo / 2);
        yAxis.setMax(precioMaximo * 1.3);
        yAxis.setLabel("Precio");
        chartProducto1.setExtender("skinChart");
    }

    private LineChartModel initLinearModel1() throws ParseException {
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();

        int flag = 0;
        for (int i = 0; i < listaProdPorAnio.size(); i++) {
            Calendar cal = null;
            Calendar cal1 = null;
            Date date;
            Date date2;
            int dia;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                date = (Date) formatter.parse(listaProdPorAnio.get(i)[0].toString());
                cal = Calendar.getInstance();
                cal.setTime(date);
                dia = cal.get(Calendar.DAY_OF_YEAR);
                if (i > 0) {
                    date2 = (Date) formatter.parse(listaProdPorAnio.get(i - 1)[0].toString());
                    cal1 = Calendar.getInstance();
                    cal1.setTime(date2);
                    if (cal.get(Calendar.YEAR) != cal1.get(Calendar.YEAR)) {
                        model.addSeries(series1);
                        series1 = new LineChartSeries();
                        series1.setLabel(cal.get(Calendar.YEAR) + "");
                    }
                } else {
                    series1.setLabel(cal.get(Calendar.YEAR) + "");
                }
            } catch (ParseException e) {
                dia = i;
            }

            series1.set(dia * 12 / 366, Double.parseDouble(listaProdPorAnio.get(i)[1].toString()));
            if ((i + 1) == listaPrecioProducto.size()) {
                model.addSeries(series1);
            }
        }

        return model;
    }

    public LineChartModel getChartProducto() {
        return chartProducto;
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

    public List<Integer> getAnio() {
        return anio;
    }

    public void setAnio(List<Integer> anio) {
        this.anio = anio;
    }

    public int getAnioSeleccionado() {
        return anioSeleccionado;
    }

    public void setAnioSeleccionado(int anioSeleccionado) {
        this.anioSeleccionado = anioSeleccionado;
    }

    public LineChartModel getChartProducto1() {
        return chartProducto1;
    }

    public void setChartProducto1(LineChartModel chartProducto1) {
        this.chartProducto1 = chartProducto1;
    }

    public List<Object[]> getListaProdPorAnio() {
        return listaProdPorAnio;
    }

    public void setListaProdPorAnio(List<Object[]> listaProdPorAnio) {
        this.listaProdPorAnio = listaProdPorAnio;
    }

    public List<Double[]> getGastosTrimestre() {
        return gastosTrimestre;
    }

    public void setGastosTrimestre(List<Double[]> gastosTrimestre) {
        this.gastosTrimestre = gastosTrimestre;
    }

}
