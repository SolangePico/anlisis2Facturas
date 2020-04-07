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
    
    public List<Object[]> obtenerSupermercados(){
        String query;
            query = "select i.ruc, i.razonsocial, count(i.establecimiento)"
                    + "from info_tributaria i group by i.ruc, i.razonsocial ";
        Query q = em.createNativeQuery(query);
        List<Object[]> result = q.getResultList();
        return result;
    
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

    public InfoTributaria obtenerEstablecimientoPorCodigo(String cod) {
        String query = "SELECT i FROM InfoTributaria i where i.codigo=:cod";
        Query q = em.createQuery(query);
        q.setParameter("cod", cod);
        List<InfoTributaria> establecimientos = q
                .getResultList();
        return establecimientos.get(0);
    }

    public List<Object[]> obtenerEstablecimientoPorUsuario(int usu) {
        String query;
        if (usu != -1) {
            query = "select i.direccion ,i.razonsocial, f.inf_codigo, count(f.codigo) as tot "
                    + "from factura f , info_tributaria i "
                    + "where f.USU_CODIGO='" + usu + "' and f.INF_CODIGO=i.codigo "
                    + "group by f.inf_codigo , i.establecimiento "
                    + "order by tot desc limit 4;";
        } else {
            query = "select i.direccion ,i.razonsocial, f.inf_codigo, count(f.codigo) as tot "
                    + "from factura f , info_tributaria i "
                    + "where f.INF_CODIGO=i.codigo "
                    + "group by f.inf_codigo , i.establecimiento "
                    + "order by tot desc limit 4;";
        }
        Query q = em.createNativeQuery(query);

        List<Object[]> result = q.getResultList();
        return result;
    }

}
