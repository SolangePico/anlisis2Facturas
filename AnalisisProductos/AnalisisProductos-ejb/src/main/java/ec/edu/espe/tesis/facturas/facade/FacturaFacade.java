/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.facturas.facade;

import ec.edu.espe.tesis.facturas.model.Factura;
import ec.edu.espe.tesis.facturas.model.Producto;
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
public class FacturaFacade extends AbstractFacade<Factura> {

    @PersistenceContext(unitName = "ec.edu.espe.tesis.facturas_AnalisisProductos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacturaFacade() {
        super(Factura.class);
    }

    public List<Factura> obtenerFacturaPorCodigo(String numAu) {
       String query = "SELECT f FROM Factura f where f.numeroautorizacion=:numAu";
        Query q = em.createQuery(query);
        q.setParameter("numAu", numAu);
        List<Factura> facturas = q
                .getResultList();
        return facturas;
    
    }
    
}
