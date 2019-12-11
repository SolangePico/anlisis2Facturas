/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.modeloXML;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author AGG
 */
public class InfoFacturaXML {
    String fechaEmision;
    BigDecimal totalSinImpuestos;
    BigDecimal totalDescuento;
    List<TotalImpuestoXML> totalImpuestos;
    BigDecimal importeTotal;
    String dirEstablecimiento;

    public String getDirEstablecimiento() {
        return dirEstablecimiento;
    }

    public void setDirEstablecimiento(String dirEstablecimiento) {
        this.dirEstablecimiento = dirEstablecimiento;
    }

    public InfoFacturaXML() {
       
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(BigDecimal totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }

    public BigDecimal getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(BigDecimal totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public List<TotalImpuestoXML> getTotalImpuestos() {
        return totalImpuestos;
    }

    public void setTotalImpuestos(List<TotalImpuestoXML> totalImpuestos) {
        this.totalImpuestos = totalImpuestos;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }
    
}
