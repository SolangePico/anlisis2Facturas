/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.servicio;

import ec.edu.espe.tesis.facturas.facade.ControlPreciosFacade;
import ec.edu.espe.tesis.facturas.model.ControlPrecios;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;

/**
 *
 * @author danny
 */
@Stateless
public class ControlPreciosServicio implements Serializable{
    @Inject 
    ControlPreciosFacade controlPreciosFacade;
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ControlPrecios> obtenerListaPreciosOrdenada(){
     List<ControlPrecios> listaControlPrecios;
        listaControlPrecios=controlPreciosFacade.obtenerListaPreciosOrdenada();
        return listaControlPrecios;
    }
    
    public List<Object[]> obtenerListaPreciosPorProducto(int codigoProducto) {
       return controlPreciosFacade.obtenerListaPreciosPorProducto(codigoProducto+"");
    }
}
