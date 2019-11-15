/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.modeloXML;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author AGG
 */
//@XStreamAlias("factura")
public class FacturaXML {
   
//@XStreamAsAttribute
//@XStreamAlias("ambiente")
   String ambiente;

  InfoTributariaXML infoTributaria= new InfoTributariaXML();
  InfoFacturaXML infoFactura= new InfoFacturaXML();
    List<DetalleXML> detalles= new ArrayList();
 

    

    public InfoTributariaXML getInfoTributaria() {
        return infoTributaria;
    }

    public void setInfoTributaria(InfoTributariaXML infoTributaria) {
        this.infoTributaria = infoTributaria;
    }

    public InfoFacturaXML getInfoFactura() {
        return infoFactura;
    }

    public void setInfoFactura(InfoFacturaXML infoFactura) {
        this.infoFactura = infoFactura;
    }

    public List<DetalleXML> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleXML> detalles) {
        this.detalles = detalles;
    }
  
    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
}
