/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.servicio.FacturaServicio;
import ec.edu.espe.tesis.servicio.InfoTributariaServicio;
import ec.edu.espe.tesis.servicio.UsuarioServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

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
    private  List<Object[]> infoTributaria;
    
    @Inject
    UsuarioServicio usuarioServicio;
     @Inject
    FacturaServicio facturaServicio;
     @Inject
     InfoTributariaServicio infoTributariaServicio;

    public double getTotalAhorrado() {
        return totalAhorrado;
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

    public void setInfoTributaria(List<Object[]> infoTributaria) {
        this.infoTributaria = infoTributaria;
    }

    
     @PostConstruct
    public void Init() {
        totalFacturas = facturaServicio.obtenerFacturasPorUsuario("1");
        totalGastado= facturaServicio.obtenerTotalGastado("1");
        promedioFactura = facturaServicio.obtenerPromedioFactura("1");
        infoTributaria=infoTributariaServicio.obtenerEstablecimientoPorUsuario(1);
    }
    /**
     * Creates a new instance of infoBean
     */
    
  
}
