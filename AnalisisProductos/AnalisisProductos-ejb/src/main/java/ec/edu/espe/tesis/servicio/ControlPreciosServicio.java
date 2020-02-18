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
public class ControlPreciosServicio implements Serializable {

    @Inject
    ControlPreciosFacade controlPreciosFacade;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ControlPrecios> obtenerListaPreciosOrdenada() {
        List<ControlPrecios> listaControlPrecios;
        listaControlPrecios = controlPreciosFacade.obtenerListaPreciosOrdenada();
        return listaControlPrecios;
    }

    public List<Object[]> obtenerListaPreciosPorProducto(int codigoProducto) {
        return controlPreciosFacade.obtenerListaPreciosPorProducto(codigoProducto + "");
    }
    
    public List<Object[]> obtenerListaProductoPorAnio(int anio, String codProd, String usuCodigo){
        List<Object[]> listaProdPorAnio;
        listaProdPorAnio=controlPreciosFacade.obtenerListaProductoPorAnio(0, codProd, usuCodigo);
        return listaProdPorAnio;
    }
      public List<Object[]> obtenerListaProductoAnios(String usuCod,String proCod){
        List<Object[]> listaProdPorAnio;
        listaProdPorAnio=controlPreciosFacade.obtenerListaProductoAnios(usuCod,proCod);
        return listaProdPorAnio;
    }
    
    
    public List<Object[]> obtenerListaPreciosPorProductoTodo() {
        return controlPreciosFacade.obtenerListaPreciosPorProductoTodo();
    }

    public List<Object[]> obtenerListaProdSuper(String ruc, String codProd, int anio) {
        List<Object[]> listaProdSuper;
        List<Object[]> listaProdSuperCompleta = new ArrayList();
        listaProdSuper = controlPreciosFacade.obtenerListaProdSuper(codProd, ruc, anio);
        for (int i = 0; i < listaProdSuper.size(); i++) {
            Object[] obj = new Object[10];
            for (int j = 0; j < 6; j++) {
                obj[j] = listaProdSuper.get(i)[j];

            }
            List<Object[]> listaFecha = controlPreciosFacade.obtenerFechaPrecio(obj[0].toString(), obj[1].toString(), anio);
            obj[6] = listaFecha.get(0)[1];
            obj[7] = listaFecha.get(listaFecha.size() - 1)[1];
            obj[8] = controlPreciosFacade.obtenerFechaActualPrecio(obj[0].toString(), obj[1].toString(), anio).get(0)[0];
            obj[9] = controlPreciosFacade.obtenerFechaActualPrecio(obj[0].toString(), obj[1].toString(), anio).get(0)[1];
            listaProdSuperCompleta.add(obj);
        }

        return listaProdSuperCompleta;
    }

    public Object[] obtenerMenorPrecioPorSupermercado(String codProd, String ruc) {
        List<Object[]> lista;
        lista = controlPreciosFacade.obtenerListaMenorPrecio(codProd, ruc);
        if (lista.size() > 0) {
            return lista.get(0);
        } else {
            return null;
        }
    }
    
   
}
