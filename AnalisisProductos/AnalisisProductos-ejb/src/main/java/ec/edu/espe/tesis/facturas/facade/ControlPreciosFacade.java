/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.facturas.facade;

import ec.edu.espe.tesis.facturas.model.ControlPrecios;
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
public class ControlPreciosFacade extends AbstractFacade<ControlPrecios> {

    @PersistenceContext(unitName = "ec.edu.espe.tesis.facturas_AnalisisProductos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ControlPreciosFacade() {
        super(ControlPrecios.class);
    }

    public List<ControlPrecios> obtenerControlPedidoPorProducto(Producto cp) {

        String query = "SELECT c FROM ControlPrecios c where c.proCodigo=:cp";
        Query q = em.createQuery(query);
        q.setParameter("cp", cp);
        List<ControlPrecios> controlPrecios = q
                .getResultList();
        return controlPrecios;
    }

    public List<ControlPrecios> obtenerListaPreciosOrdenada() {
        String query = "SELECT c FROM ControlPrecios c ORDER BY precioUnitario";
        Query q = em.createQuery(query);
        List<ControlPrecios> controlPrecios = q
                .getResultList();
        return controlPrecios;

    }

    public List<Object[]> obtenerListaPreciosPorProducto(String codigoProducto) {
        String query = "SELECT c.PRECIOUNITARIO, f.FECHAEMISION, i.RAZONSOCIAL , i.DIRECCION "
                + "FROM control_precios c, factura f, info_tributaria i "
                + "WHERE c.FAC_CODIGO=f.CODIGO and i.CODIGO=f.INF_CODIGO and c.PRO_CODIGO='"+codigoProducto+"' "
                + "order by f.FECHAEMISION;";
        Query q = em.createNativeQuery(query);  

        List<Object[]> result = q.getResultList();
        return result;
    }
    
    public List<Object[]> obtenerListaPreciosPorProductoTodo() {
        String query = "SELECT p.descripcion ,c.PRECIOUNITARIO, f.FECHAEMISION, i.RAZONSOCIAL , i.DIRECCION, p.codigo, count(p.codigo)" +
"                FROM control_precios c, factura f, info_tributaria i, producto p " +
"                WHERE c.FAC_CODIGO=f.CODIGO and i.CODIGO=f.INF_CODIGO and c.PRO_CODIGO=p.codigo " +
"                group by p.descripcion order by p.descripcion,  f.FECHAEMISION;";
        Query q = em.createNativeQuery(query);

        List<Object[]> result = q.getResultList();
        return result;
    }

}
