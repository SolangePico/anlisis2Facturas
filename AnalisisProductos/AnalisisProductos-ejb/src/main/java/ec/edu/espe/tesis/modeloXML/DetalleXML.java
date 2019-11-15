/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.modeloXML;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author AGG
 */
public class DetalleXML {
    String codigoPrincipal;
    String codigoAuxiliar;
    BigDecimal cantidad;
    BigDecimal precioUnitario;
    BigDecimal descuento;
    BigDecimal precioTotalSinImpuesto;
    List<ImpuestoXML> impuestos;
    String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public void setCodigoPrincipal(String codigoPrincipal) {
        this.codigoPrincipal = codigoPrincipal;
    }

    public String getCodigoAuxiliar() {
        return codigoAuxiliar;
    }

    public void setCodigoAuxiliar(String codigoAuxiliar) {
        this.codigoAuxiliar = codigoAuxiliar;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getPrecioTotalSinImpuesto() {
        return precioTotalSinImpuesto;
    }

    public void setPrecioTotalSinImpuesto(BigDecimal precioTotalSinImpuesto) {
        this.precioTotalSinImpuesto = precioTotalSinImpuesto;
    }

    public List<ImpuestoXML> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<ImpuestoXML> impuestos) {
        this.impuestos = impuestos;
    }

    public DetalleXML(String codigoPrincipal, String codigoAuxiliar, BigDecimal cantidad, BigDecimal precioUnitario, BigDecimal descuento, BigDecimal precioTotalSinImpuesto, List<ImpuestoXML> impuestos) {
        this.codigoPrincipal = codigoPrincipal;
        this.codigoAuxiliar = codigoAuxiliar;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.precioTotalSinImpuesto = precioTotalSinImpuesto;
        this.impuestos = impuestos;
    }
}
