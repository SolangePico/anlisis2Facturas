/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.servicio.FacturaServicio;
import ec.edu.espe.tesis.servicio.InfoTributariaServicio;
import ec.edu.espe.tesis.servicio.ProductoServicio;
import ec.edu.espe.tesis.servicio.UsuarioServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author alterbios
 */
@Named(value = "infoBean")
@ViewScoped
public class InfoBean implements Serializable {

    private int totalFacturas;
    private int ultimaFactura;
    private double totalGastado;
    private double totalAhorrado;
    private double promedioFactura;
    private List<Object[]> infoTributaria;
    private PieChartModel chartEstablecimientos;
    private List<Object[]> productoMasComprado;
     private BarChartModel barModel;

    @Inject
    HttpSessionHandler sesion;
    @Inject
    UsuarioServicio usuarioServicio;
    @Inject
    FacturaServicio facturaServicio;
    @Inject
    InfoTributariaServicio infoTributariaServicio;
    @Inject
    ProductoServicio productoServicio;

    public double getTotalAhorrado() {
        return totalAhorrado;
    }

    public PieChartModel getChartEstablecimientos() {
        return chartEstablecimientos;
    }

    public void setChartEstablecimientos(PieChartModel chartEstablecimientos) {
        this.chartEstablecimientos = chartEstablecimientos;
    }

    public void setTotalAhorrado(double totalAhorrado) {
        this.totalAhorrado = totalAhorrado;
    }

    public double getPromedioFactura() {
        return promedioFactura;
    }

    public void setPromedioFactura(double promedioFactura) {
        this.promedioFactura = promedioFactura;
    }

    public int getTotalFacturas() {
        return totalFacturas;
    }

    public void setTotalFacturas(int totalFacturas) {
        this.totalFacturas = totalFacturas;
    }

    public int getUltimaFactura() {
        return ultimaFactura;
    }

    public void setUltimaFactura(int ultimaFactura) {
        this.ultimaFactura = ultimaFactura;
    }

    public double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(double totalGastado) {
        this.totalGastado = totalGastado;
    }

    public List<Object[]> getInfoTributaria() {
        return infoTributaria;
    }

    public List<Object[]> getProductoMasComprado() {
        return productoMasComprado;
    }

    public void setProductoMasComprado(List<Object[]> productoMasComprado) {
        this.productoMasComprado = productoMasComprado;
    }

    public void setInfoTributaria(List<Object[]> infoTributaria) {
        this.infoTributaria = infoTributaria;
    }

    @PostConstruct
    public void Init() {
        totalFacturas = facturaServicio.obtenerFacturasPorUsuario(sesion.getId());
        if (totalFacturas > 0) {
            totalGastado = facturaServicio.obtenerTotalGastado(sesion.getId());
            promedioFactura = facturaServicio.obtenerPromedioFactura(sesion.getId());
            infoTributaria = infoTributariaServicio.obtenerEstablecimientoPorUsuario(Integer.parseInt(sesion.getId()));
            productoMasComprado=productoServicio.obtenerProductosPorUsuario(sesion.getId());
            crearChartEstablecimientos();
        }
    }

    private void crearChartEstablecimientos() {
        chartEstablecimientos = new PieChartModel();

        for (int i = 0; i < infoTributaria.size(); i++) {
            int aux = 0;
            aux = Integer.parseInt(infoTributaria.get(i)[3].toString());
            chartEstablecimientos.set((String) infoTributaria.get(i)[0], aux);

        }

        chartEstablecimientos.setTitle("Establecimientos más comprados");
        chartEstablecimientos.setLegendPosition("w");
        chartEstablecimientos.setExtender("skinPie");
        
          chartEstablecimientos.setLegendPosition("e");
        chartEstablecimientos.setSliceMargin(5);
        chartEstablecimientos.setShowDataLabels(true);
        chartEstablecimientos.setDataFormat("value");
        chartEstablecimientos.setShadow(false);
    }
    /**
     * Creates a new instance of infoBean
     */

}
