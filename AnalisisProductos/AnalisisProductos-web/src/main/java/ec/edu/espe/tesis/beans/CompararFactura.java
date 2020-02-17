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
    private FacturaCodificacion codificar;
    private String codFactura;

//    select min(c.preciounitario), p.descripcion, i.razonsocial, max(f.FECHAEMISION), i.establecimiento, i.direccion
//from producto p, control_precios c, info_tributaria i, factura f, detalle_factura d
//where f.CODIGO<>'1' and f.codigo=d.FAC_CODIGO and p.codigo=c.PRO_CODIGO and p.CODIGO=d.PRO_CODIGO
//and i.CODIGO=f.INF_CODIGO and p.codigo='9484' group by p.descripcion, i.razonsocial, i.establecimiento, i.direccion;
//    
    @PostConstruct
    public void init() {
        listaDetalles = new ArrayList();
        FaceletContext fc = (FaceletContext) FacesContext.getCurrentInstance().getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
        codFactura = (String) fc.getAttribute("codFac");
        if (codFactura == null) {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            codFactura = request.getParameter("codFac");
        }
        if (codFactura != null) {
            factura = facturaServicio.obtenerFacturaPorCodigo(codificar.decodificarId(codFactura));
            listaDetalles = facturaServicio.obtenerDetallesFactura(factura.getCodigo().toString());
            for (int i = 0; i<listaDetalles.size(); i++) {
                if(facturaServicio.obtenerProductoMasBarato(listaDetalles.get(i)[4].toString(),factura.getCodigo().toString())!=null){
                
                }
            }
        }
    }

}
