/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.facturas.model.Factura;
import ec.edu.espe.tesis.servicio.FacturaServicio;
import ec.edu.espe.tesis.servicio.ProductoServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LinearAxis;

/**
 *
 * @author danny
 */
@Named(value = "gastosBean")
@ViewScoped
public class GastosBean implements Serializable {

    @Inject
    private FacturaServicio facturaServicio;
    @Inject
    private HttpSessionHandler sesion;
    @Inject
    private ProductoServicio productoServicio;
    private List<Object[]> gastoPorAnio;
    private List<Factura> listaFacturas;
    private BigDecimal promedioPorAnio;
    private BigInteger totalFacturasPorAnio;
    private List<Object[]> listaProductosPorAnio;
    private int anio;
    private BarChartModel barModel;

    @PostConstruct
    public void init() {
        int aux;
        Calendar cal = null;
        Object[] obj = new Object[2];
        gastoPorAnio = new ArrayList();
        listaFacturas = facturaServicio.obtenerFacturasOrdenadas(Integer.parseInt(sesion.getId()));
        cal = Calendar.getInstance();
        cal.setTime(listaFacturas.get(0).getFechaemision());
        aux = cal.get(Calendar.YEAR);
        obj[0] = aux;
        gastoPorAnio.add(obj);
        for (int i = 1; i < listaFacturas.size(); i++) {
            cal.setTime(listaFacturas.get(i).getFechaemision());
            if (cal.get(Calendar.YEAR) != (Integer) gastoPorAnio.get(gastoPorAnio.size() - 1)[0]) {
                obj = new Object[2];
                aux = cal.get(Calendar.YEAR);
                obj[0] = aux;
                gastoPorAnio.add(obj);
            }
        }
        for (int i = 0; i < gastoPorAnio.size(); i++) {

        }
        createBarModel();
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("Total Gastado");
        boys.setXaxis(AxisType.X);
        boys.setYaxis(AxisType.Y);
        boys.set("Ene", 120);
        boys.set("Feb", 100);
        boys.set("Mar", 44);
        boys.set("Abr", 150);
        boys.set("May", 25);
        boys.set("Jun", 25);
        boys.set("Jul", 25);
        boys.set("Ago", 25);
        boys.set("Sep", 25);
        boys.set("Oct", 25);
        boys.set("Nov", 25);
        boys.set("Dic", 25);

        ChartSeries girls = new ChartSeries();
        girls.setLabel("# de Productos");
        girls.setXaxis(AxisType.X);
        girls.setYaxis(AxisType.Y2);
        girls.set("Ene", 52);
        girls.set("Feb", 60);
        girls.set("Mar", 110);
        girls.set("Abr", 135);
        girls.set("May", 120);
        girls.set("Jun", 135);
        girls.set("Jul", 135);
        girls.set("Ago", 135);
        girls.set("Sep", 135);
        girls.set("Oct", 135);
        girls.set("Nov", 135);
        girls.set("Dic", 135);

        model.addSeries(boys);
        model.addSeries(girls);
        model.setShowPointLabels(true);
        return model;
    }

    private void createBarModel() {
        barModel = initBarModel();

        barModel.setTitle("Gastos");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Meses");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        yAxis.setMax(200);
        
        Axis y2Axis = new LinearAxis("Productos");
        y2Axis.setMin(0);
        y2Axis.setMax(200);
         barModel.getAxes().put(AxisType.Y2, new CategoryAxis("Productos"));

        barModel.setExtender("skinBar");
    }

    public void verConsumoPorAño(int year) {
        anio = year;
        promedioPorAnio = (BigDecimal) facturaServicio.obtenerGastoPromedioPorAnio(sesion.getId(), year);
        totalFacturasPorAnio = (BigInteger) facturaServicio.obtenerTotalFacturasPorAnio(sesion.getId(), year);
        listaProductosPorAnio = productoServicio.obtenerProductosPorUsuarioYAnio(sesion.getId(), year);
        RequestContext.getCurrentInstance().execute("PF('gasto').show();");
    }

    public List<Object[]> getGastoPorAnio() {
        return gastoPorAnio;
    }

    public void setGastoPorAnio(List<Object[]> gastoPorAnio) {
        this.gastoPorAnio = gastoPorAnio;
    }

    public List<Factura> getListaFacturas() {
        return listaFacturas;
    }

    public void setListaFacturas(List<Factura> listaFacturas) {
        this.listaFacturas = listaFacturas;
    }

    public BigDecimal getPromedioPorAnio() {
        return promedioPorAnio;
    }

    public void setPromedioPorAnio(BigDecimal promedioPorAnio) {
        this.promedioPorAnio = promedioPorAnio;
    }

    public BigInteger getTotalFacturasPorAnio() {
        return totalFacturasPorAnio;
    }

    public void setTotalFacturasPorAnio(BigInteger totalFacturasPorAnio) {
        this.totalFacturasPorAnio = totalFacturasPorAnio;
    }

    public List<Object[]> getListaProductosPorAnio() {
        return listaProductosPorAnio;
    }

    public void setListaProductosPorAnio(List<Object[]> listaProductosPorAnio) {
        this.listaProductosPorAnio = listaProductosPorAnio;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }
}
