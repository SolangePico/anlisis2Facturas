/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.facturas.facade;

import ec.edu.espe.tesis.facturas.model.Factura;
import ec.edu.espe.tesis.facturas.model.Producto;
import java.math.BigDecimal;
import java.math.BigInteger;
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
     public int totalFacturasPorUsuario(String usuCodigo) {
           String query = "select count(*) from factura where usu_codigo="+usuCodigo+";";
        Query q = em.createNativeQuery(query);
        return ((BigInteger) q.getSingleResult()).intValue();
       
    
    }
     public double totalGastadoPorUsuario(String usuCodigo) {
           String query = "select sum(importetotal) from factura where usu_codigo="+usuCodigo+";";
        Query q = em.createNativeQuery(query);
        return ((BigDecimal) q.getSingleResult()).doubleValue();
       
    
    }
     
     public double promedioFactura(String usuCodigo) {
           String query = "select avg(importetotal) from factura where usu_codigo="+usuCodigo+";";
        Query q = em.createNativeQuery(query);
        return ((BigDecimal) q.getSingleResult()).doubleValue();
       
    
    }
     
     public double maxGastadoFactura(String usuCodigo) {
           String query = "select max(importetotal) from factura where usu_codigo="+usuCodigo+";";
        Query q = em.createNativeQuery(query);
        return ((BigDecimal) q.getSingleResult()).doubleValue();
       
    
    }
     
     public String fechaUltimaFactura(String usuCodigo) {
           String query = "select max(fechaautori) from factura where usu_codigo="+usuCodigo+";";
        Query q = em.createNativeQuery(query);
        return  q.getSingleResult().toString();
       
    
    }
    
}
