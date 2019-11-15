/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.modeloXML;

import java.util.Date;

/**
 *
 * @author AGG
 */
public class AutorizacionXML {
     String numeroAutorizacion;
     String fechaAutorizacion;
     ComprobanteXML comprobante = new ComprobanteXML();

    public AutorizacionXML() {
    }

    public AutorizacionXML(String numeroAutorizacion, String fechaAutorizacion, ComprobanteXML comprobante) {
        this.numeroAutorizacion = numeroAutorizacion;
        this.fechaAutorizacion = fechaAutorizacion;
        this.comprobante = comprobante;
    }
    

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public String getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(String fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public ComprobanteXML getComprobante() {
        return comprobante;
    }

    public void setComprobante(ComprobanteXML comprobante) {
        this.comprobante = comprobante;
    }
}
