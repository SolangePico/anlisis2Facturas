/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import com.thoughtworks.xstream.XStream;
import ec.edu.espe.tesis.facturas.facade.ProductoFacade;
import ec.edu.espe.tesis.facturas.model.Producto;
import ec.edu.espe.tesis.modeloXML.AutorizacionXML;
import ec.edu.espe.tesis.modeloXML.ComprobanteXML;
import ec.edu.espe.tesis.modeloXML.DetalleXML;
import ec.edu.espe.tesis.modeloXML.FacturaXML;
import ec.edu.espe.tesis.modeloXML.ImpuestoXML;
import ec.edu.espe.tesis.modeloXML.InfoFacturaXML;
import ec.edu.espe.tesis.modeloXML.InfoTributariaXML;
import ec.edu.espe.tesis.modeloXML.TotalImpuestoXML;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import ec.edu.espe.tesis.servicio.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author danny
 */
@Named(value = "productoBean")
@ViewScoped
public class ProductoBean implements Serializable {

    /**
     * Creates a new instance of ProductoBean
     */
    @Inject
    ProductoFacade productoFacade;

    @Inject
    FacturaServicio facturaServicio;

    List<Producto> listaProductos;

    @PostConstruct
    public void init() {
        listaProductos = productoFacade.findAll();
          String path = "E:\\Danny\\Descargas\\all\\XML";
        //String path = "C:\\Users\\alterbios\\Downloads\\all\\XML";
        File carpeta = new File(path);
        File[] archivos;
        archivos = carpeta.listFiles();
        for (File archivo : archivos) {
            capturarDatos(archivo);
        }
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public void capturarDatos(File file) {
        AutorizacionXML obj;
        try {
            XStream xstream = new XStream();
            
            xstream.allowTypesByRegExp(new String[] { ".*" });
            // xstream.processAnnotations(Autorizacion.class);
            xstream.alias("autorizacion", AutorizacionXML.class);
            xstream.alias("comprobante", ComprobanteXML.class);
            xstream.alias("factura", FacturaXML.class);
            xstream.alias("infoTributaria", InfoTributariaXML.class);
            xstream.alias("infoFactura", InfoFacturaXML.class);
            xstream.alias("detalle", DetalleXML.class);
            xstream.alias("impuesto", ImpuestoXML.class);
            xstream.alias("totalImpuesto", TotalImpuestoXML.class);
            xstream.ignoreUnknownElements();
            try{
            obj = (AutorizacionXML) xstream.fromXML(file);
            facturaServicio.guardarFactura(obj, "0");
            }catch(Exception e){
                System.out.println("error en formato");
            }
           
        } catch (Exception e) {
            System.out.println("");

        }

    }
}
