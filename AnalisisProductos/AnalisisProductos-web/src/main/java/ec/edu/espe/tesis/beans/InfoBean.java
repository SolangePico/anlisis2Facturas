/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.servicio.FacturaServicio;
import ec.edu.espe.tesis.servicio.UsuarioServicio;
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
public class InfoBean {

    private int totalFacturas;
    private int ultimaFactura;
    private double totalGastado;
    private double totalAhorrado;
    private double promedioFactura;
    
    @Inject
    UsuarioServicio usuarioServicio;
     @Inject
    FacturaServicio facturaServicio;

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

    
     @PostConstruct
    public void Init() {
        totalFacturas = facturaServicio.obtenerFacturasPorUsuario("1");
        totalGastado= facturaServicio.obtenerTotalGastado("1");
        promedioFactura = facturaServicio.obtenerPromedioFactura("1");
    }
    /**
     * Creates a new instance of infoBean
     */
    
  
}
