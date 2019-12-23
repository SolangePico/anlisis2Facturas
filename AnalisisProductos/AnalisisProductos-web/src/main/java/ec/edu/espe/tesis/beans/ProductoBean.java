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
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;

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
    ProductoServicio productoServicio;

    @Inject
    ControlPreciosServicio controlPrecioServicio;

    @Inject
    ProductoFacade productoFacade;

    @Inject
    FacturaServicio facturaServicio;

    private List<Producto> listaProductos;
    private List<Object[]> listaPrecioProducto;
    private List<Object[]> listaMasComprados;
    private Producto productoSeleccionado;
    private Object[] codigoProducto;

    @PostConstruct
    public void init() {
        codigoProducto = null;
        listaMasComprados = productoServicio.obtenerProductosPorUsuario("1");
        listaProductos = productoFacade.findAll();
//        String path = "E:\\Danny\\Descargas\\all\\XML";
////        String path = "C:\\Users\\alterbios\\Downloads\\all\\XML";
//       // String path = "C:\\Users\\solan\\OneDrive\\Escritorio\\versionesTesis\\all\\XML";
//        File carpeta = new File(path);
//        File[] archivos;
//        archivos = carpeta.listFiles();
//        for (File archivo : archivos) {
//            capturarDatos(archivo);
//        }
    }

    public List<Object[]> getListaPrecioProducto() {
        return listaPrecioProducto;
    }

    public void setListaPrecioProducto(List<Object[]> listaPrecioProducto) {
        this.listaPrecioProducto = listaPrecioProducto;
    }

    public Object[] getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Object[] codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public List<Object[]> getListaMasComprados() {
        return listaMasComprados;
    }

    public void setListaMasComprados(List<Object[]> listaMasComprados) {
        this.listaMasComprados = listaMasComprados;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

   

    public void cerrarVentana() {

        RequestContext.getCurrentInstance().execute("PF('producto').hide();");
    }

    public void abrirInfo() {
        cargarSeleccionado();
        RequestContext.getCurrentInstance().execute("PF('producto').show();");
    }

    public void cargarSeleccionado() {
        
        
        listaPrecioProducto = controlPrecioServicio.obtenerListaPreciosPorProducto((Integer) codigoProducto[0]);
        productoSeleccionado = productoFacade.obtenerProductoPorCodigoP((Integer) codigoProducto[0]).get(0);
    }

}
