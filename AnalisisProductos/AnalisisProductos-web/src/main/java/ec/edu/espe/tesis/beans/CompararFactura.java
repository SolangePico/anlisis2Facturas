/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.beans;

import ec.edu.espe.tesis.facturas.model.DetalleFactura;
import ec.edu.espe.tesis.facturas.model.Factura;
import ec.edu.espe.tesis.servicio.FacturaServicio;
import ec.edu.espe.tesis.util.FacturaCodificacion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.view.facelets.FaceletContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author danny
 */
@Named(value = "compararFacturaBean")
@ViewScoped
public class CompararFactura implements Serializable {

    @Inject
    private FacturaServicio facturaServicio;

    private Factura factura;
    private List<Object[]> listaDetalles;
    private List<Object[]> listaDetallesMasBarato;
    private String codFactura;

    @PostConstruct
    public void init() {
        listaDetallesMasBarato= new ArrayList();
        listaDetalles = new ArrayList();
        FaceletContext fc = (FaceletContext) FacesContext.getCurrentInstance().getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
        codFactura = (String) fc.getAttribute("facId");
        if (codFactura == null) {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            codFactura = request.getParameter("facId");
        }
        if (codFactura != null) {
            factura = facturaServicio.obtenerFacturaPorCodigo(FacturaCodificacion.decodificarId(codFactura));
            listaDetalles = facturaServicio.obtenerDetallesFactura(factura.getCodigo().toString());
            for (int i = 0; i < listaDetalles.size(); i++) {
                if (facturaServicio.obtenerProductoMasBarato(listaDetalles.get(i)[4].toString(), factura.getCodigo().toString()) != null) {
                    listaDetallesMasBarato.add(facturaServicio.obtenerProductoMasBarato(listaDetalles.get(i)[4].toString(), factura.getCodigo().toString()));
                }
            }
        }
    }
    
    public void actualizarDatos(){
    
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<Object[]> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<Object[]> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public List<Object[]> getListaDetallesMasBarato() {
        return listaDetallesMasBarato;
    }

    public void setListaDetallesMasBarato(List<Object[]> listaDetallesMasBarato) {
        this.listaDetallesMasBarato = listaDetallesMasBarato;
    }
    
}
