/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.facturas.facade;

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
public class InfoTributariaFacade extends AbstractFacade<InfoTributaria> {

    @PersistenceContext(unitName = "ec.edu.espe.tesis.facturas_AnalisisProductos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InfoTributariaFacade() {
        super(InfoTributaria.class);
    }
    
    public List<InfoTributaria> obtenerEstablecimientoPorCriterio(String rucEst, String estab) {
        String query = "SELECT i FROM InfoTributaria i where i.ruc=:rucEst and i.establecimiento=:estab";
        Query q = em.createQuery(query);
        q.setParameter("rucEst", rucEst);
        q.setParameter("estab", estab);
        List<InfoTributaria> establecimientos = q
                .getResultList();
        return establecimientos;
    }
    
}
