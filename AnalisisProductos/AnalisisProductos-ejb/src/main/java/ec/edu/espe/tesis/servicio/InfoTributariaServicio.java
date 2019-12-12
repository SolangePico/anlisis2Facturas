/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.servicio;

//import com.alterbios.marathongo.ejb.exception.BusinessException;
import ec.edu.espe.tesis.facturas.facade.ControlPreciosFacade;
import ec.edu.espe.tesis.facturas.facade.InfoTributariaFacade;
import ec.edu.espe.tesis.facturas.model.ControlPrecios;
import ec.edu.espe.tesis.facturas.model.InfoTributaria;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 *
 * @author alterbios
 */
@Stateless
public class InfoTributariaServicio implements Serializable {

    @Inject
    InfoTributariaFacade infoTributariaFacade;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InfoTributaria> obtenerListaEstablecimientos() {
        List<InfoTributaria> listaEstablecimientos;
        listaEstablecimientos = infoTributariaFacade.findAll();
        return listaEstablecimientos;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InfoTributaria obtenerEstablecimientoPorCodigo(String codigo) {
       
        return infoTributariaFacade.obtenerEstablecimientoPorCodigo(codigo);
    }
    
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> obtenerEstablecimientoPorUsuario(int codigo) {
       
        return infoTributariaFacade.obtenerEstablecimientoPorUsuario(codigo);
    }
}
