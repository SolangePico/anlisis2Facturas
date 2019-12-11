/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.facturas.facade;

import ec.edu.espe.tesis.facturas.model.DetalleFactura;
import ec.edu.espe.tesis.facturas.model.InfoTributaria;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solan
 */
@Stateless
public class DetalleFacturaFacade extends AbstractFacade<DetalleFactura> {

    @PersistenceContext(unitName = "ec.edu.espe.tesis.facturas_AnalisisProductos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetalleFacturaFacade() {
        super(DetalleFactura.class);
    }
     public List<DetalleFactura> obtenerUltimoRegistro() {
        String query = "SELECT d FROM DetalleFactura d order by codigo desc";
        Query q = em.createQuery(query);
        
        List<DetalleFactura> detalles = q
                .getResultList();
        return detalles;
    }
    
}
