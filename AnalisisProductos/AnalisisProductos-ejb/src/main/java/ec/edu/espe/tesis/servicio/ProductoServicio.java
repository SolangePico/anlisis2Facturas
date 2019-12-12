/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.servicio;

import ec.edu.espe.tesis.facturas.facade.ProductoFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 *
 * @author danny
 */
@Stateless
public class ProductoServicio implements Serializable{

    @Inject
    ProductoFacade productoFacade;
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> obtenerProductosPorUsuario(String usuarioId) {
        List<Object[]> listaMasComprados = null;
            listaMasComprados=productoFacade.obtenerDescripcionProducto(usuarioId);
//            
        return listaMasComprados;
    }
    
}
