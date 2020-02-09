/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.servicio.FacturaServicio;
import ec.edu.espe.tesis.servicio.InfoTributariaServicio;
import ec.edu.espe.tesis.servicio.ProductoServicio;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author alterbios
 */
@Named(value = "infoGeneralBean")
@ViewScoped
public class InfoGeneralBean implements Serializable {

    private int totalFacturas;
    private Object[] infoTributaria;
    private Object[] producto;

    @Inject
    private ProductoServicio productoServicio;
    @Inject
    private FacturaServicio facturaServicio;
    @Inject
    private InfoTributariaServicio infoTributariaServicio;

    public Object[] getProducto() {
        return producto;
    }

    public void setProducto(Object[] producto) {
        this.producto = producto;
    }

    public int getTotalFacturas() {
        return totalFacturas;
    }

    public void setTotalFacturas(int totalFacturas) {
        this.totalFacturas = totalFacturas;
    }

    public Object[] getInfoTributaria() {
        return infoTributaria;
    }

    public void setInfoTributaria(Object[] infoTributaria) {
        this.infoTributaria = infoTributaria;
    }

    @PostConstruct
    public void Init() {
        totalFacturas = facturaServicio.obtenerFacturasPorUsuario("-1");
             infoTributaria = infoTributariaServicio.obtenerEstablecimientoPorUsuario(-1).get(0);
            producto = productoServicio.obtenerProductosPorUsuario("-1").get(0);
    }

    /**
     * Creates a new instance of infoBean
     */
}
