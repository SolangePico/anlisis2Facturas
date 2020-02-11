/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.servicio;

import ec.edu.espe.tesis.facturas.facade.ControlPreciosFacade;
import ec.edu.espe.tesis.facturas.model.ControlPrecios;
import java.io.Serializable;
import java.util.ArrayList;
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
    public List<Object[]> obtenerListaPreciosPorProductoTodo() {
       return controlPreciosFacade.obtenerListaPreciosPorProductoTodo();
    }
    
    public List<Object[]> obtenerListaProdSuper(String ruc, String codProd){
        List<Object[]> listaProdSuper;
        List<Object[]> listaProdSuperCompleta= new ArrayList();
            listaProdSuper=controlPreciosFacade.obtenerListaProdSuper(codProd, ruc);
            for (int i = 0; i < listaProdSuper.size(); i++) {
                Object[] obj= new Object[8];
                for (int j = 0; j < 6; j++) {
                    obj[j]=listaProdSuper.get(i)[j];
                    
                }
                List<Object[]> listaFecha=controlPreciosFacade.obtenerFechaPrecio(obj[0].toString(), obj[1].toString());
                obj[6]=listaFecha.get(0);
                obj[7]=listaFecha.get(listaFecha.size()-1);
                listaProdSuperCompleta.add(obj);
            }
            
        return listaProdSuperCompleta;
    }
}
