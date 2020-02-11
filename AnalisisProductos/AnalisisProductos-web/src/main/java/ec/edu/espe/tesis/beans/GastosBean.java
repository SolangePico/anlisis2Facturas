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
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartSeries;
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

    private BigDecimal totalGastoPorAnio;
    private List<Object[]> gastoPorAnio;
    private List<Factura> listaFacturas;
    private BigDecimal promedioPorAnio;
    private BigInteger totalFacturasPorAnio;
    private List<Object[]> listaProductosPorAnio;
    private int anio;
    private BarChartModel barModel;
    private List<Object[]> listaFacturasPorMes;
    private List<Object[]> listaGastoFacturaPorMes;

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
            Object chart;
            listaFacturasPorMes = facturaServicio.obtenerFacturasPorMes(sesion.getId(), (int) gastoPorAnio.get(i)[0]);
            listaGastoFacturaPorMes = facturaServicio.obtenerGastoFacturasPorMes(sesion.getId(), (int) gastoPorAnio.get(i)[0]);
            chart = createBarModel();
            gastoPorAnio.get(i)[1] = chart;
        }

    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("# de productos");
        boys.setXaxis(AxisType.X);
        boys.setYaxis(AxisType.Y);
        for (int i = 0; i < listaFacturasPorMes.size(); i++) {
            switch ((int) listaFacturasPorMes.get(i)[1]) {
                case 1:
                    boys.set("Ene", (BigInteger) listaFacturasPorMes.get(i)[0]);
                    break;
                case 2:
                    boys.set("Feb", (BigInteger) listaFacturasPorMes.get(i)[0]);
                    break;
                case 3:
                    boys.set("Mar", (BigInteger) listaFacturasPorMes.get(i)[0]);
                    break;
                case 4:
                    boys.set("Abr", (BigInteger) listaFacturasPorMes.get(i)[0]);
                    break;
                case 5:
                    boys.set("May", (BigInteger) listaFacturasPorMes.get(i)[0]);
                    break;
                case 6:
                    boys.set("Jun", (BigInteger) listaFacturasPorMes.get(i)[0]);
                    break;
                case 7:
                    boys.set("Jul", (BigInteger) listaFacturasPorMes.get(i)[0]);
                    break;
                case 8:
                    boys.set("Ago", (BigInteger) listaFacturasPorMes.get(i)[0]);
                    break;
                case 9:
                    boys.set("Sep", (BigInteger) listaFacturasPorMes.get(i)[0]);
                    break;
                case 10:
                    boys.set("Oct", (BigInteger) listaFacturasPorMes.get(i)[0]);
                    break;
                case 11:
                    boys.set("Nov", (BigInteger) listaFacturasPorMes.get(i)[0]);
                    break;
                case 12:
                    boys.set("Dic", (BigInteger) listaFacturasPorMes.get(i)[0]);
                    break;
                default:
                    boys.set("Ene", 1);
            }

        }

        LineChartSeries valor = new LineChartSeries();
        valor.setLabel("Total Gastado");
        valor.setXaxis(AxisType.X);
        valor.setYaxis(AxisType.Y2);
        for (int i = 0; i < listaGastoFacturaPorMes.size(); i++) {
            switch ((int) listaGastoFacturaPorMes.get(i)[1]) {
                case 1:
                    valor.set("Ene", (BigDecimal) listaGastoFacturaPorMes.get(i)[0]);
                    break;
                case 2:
                    valor.set("Feb", (BigDecimal) listaGastoFacturaPorMes.get(i)[0]);
                    break;
                case 3:
                    valor.set("Mar", (BigDecimal) listaGastoFacturaPorMes.get(i)[0]);
                    break;
                case 4:
                    valor.set("Abr", (BigDecimal) listaGastoFacturaPorMes.get(i)[0]);
                    break;
                case 5:
                    valor.set("May", (BigDecimal) listaGastoFacturaPorMes.get(i)[0]);
                    break;
                case 6:
                    valor.set("Jun", (BigDecimal) listaGastoFacturaPorMes.get(i)[0]);
                    break;
                case 7:
                    valor.set("Jul", (BigDecimal) listaGastoFacturaPorMes.get(i)[0]);
                    break;
                case 8:
                    valor.set("Ago", (BigDecimal) listaGastoFacturaPorMes.get(i)[0]);
                    break;
                case 9:
                    valor.set("Sep", (BigDecimal) listaGastoFacturaPorMes.get(i)[0]);
                    break;
                case 10:
                    valor.set("Oct", (BigDecimal) listaGastoFacturaPorMes.get(i)[0]);
                    break;
                case 11:
                    valor.set("Nov", (BigDecimal) listaGastoFacturaPorMes.get(i)[0]);
                    break;
                case 12:
                    valor.set("Dic", (BigDecimal) listaGastoFacturaPorMes.get(i)[0]);
                    break;
                default:
                    valor.set("Ene", 1);
            }

        }
        model.addSeries(boys);
        model.addSeries(valor);
        model.setShowPointLabels(true);
        return model;
    }

    private BarChartModel createBarModel() {
        int totProd = 0;
        BigDecimal TotalGastado;
        TotalGastado = BigDecimal.ZERO;

        for (int i = 0; i < listaFacturasPorMes.size(); i++) {
            if (totProd < Integer.parseInt(listaFacturasPorMes.get(i)[0].toString())) {
                totProd = Integer.parseInt(listaFacturasPorMes.get(i)[0].toString());
            }
        }

        for (int i = 0; i < listaGastoFacturaPorMes.size(); i++) {
            if (TotalGastado.compareTo((BigDecimal) listaGastoFacturaPorMes.get(i)[0]) < 0) {
                TotalGastado = (BigDecimal) listaGastoFacturaPorMes.get(i)[0];
            }
        }
        barModel = new BarChartModel();
        barModel.getAxes().put(AxisType.Y2, new CategoryAxis("Productos"));
        barModel = initBarModel();

        barModel.setTitle("Gastos");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Meses");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        yAxis.setMax(totProd * 2);

        Axis y2Axis = new LinearAxis("Productos");
        y2Axis.setMin(0);
        y2Axis.setMax(Double.parseDouble(TotalGastado.toString()));

        barModel.setExtender("skinBar");
        barModel.setMouseoverHighlight(true);
        barModel.setLegendPosition("e");
        barModel.setShowPointLabels(true);
        barModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);

        return barModel;
    }

    public void verConsumoPorAño(int year) {
        anio = year;
        promedioPorAnio = (BigDecimal) facturaServicio.obtenerGastoPromedioPorAnio(sesion.getId(), year);
        totalFacturasPorAnio = (BigInteger) facturaServicio.obtenerTotalFacturasPorAnio(sesion.getId(), year);
        listaProductosPorAnio = productoServicio.obtenerProductosPorUsuarioYAnio(sesion.getId(), year);
        totalGastoPorAnio = (BigDecimal) facturaServicio.obtenerTotalGastoPorAnio(sesion.getId(), year);
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

    public BigDecimal getTotalGastoPorAnio() {
        return totalGastoPorAnio;
    }

    public void setTotalGastoPorAnio(BigDecimal totalGastoPorAnio) {
        this.totalGastoPorAnio = totalGastoPorAnio;
    }

}
