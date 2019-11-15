/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.modeloXML;

import java.math.BigDecimal;

/**
 *
 * @author AGG
 */
public class ImpuestoXML {
    String codigo;
    String tarifa;

    public ImpuestoXML(String codigo, String tarifa, BigDecimal baseImponible, BigDecimal valor) {
        this.codigo = codigo;
        this.tarifa = tarifa;
        this.baseImponible = baseImponible;
        this.valor = valor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    BigDecimal baseImponible;
    BigDecimal valor;
}
