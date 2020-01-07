/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.facturas.facade;

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
public class ProductoFacade extends AbstractFacade<Producto> {

    @PersistenceContext(unitName = "ec.edu.espe.tesis.facturas_AnalisisProductos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }

    public List<Producto> obtenerProductoPorCriterio(String cp, String ca) {
        String query = "SELECT p FROM Producto p where p.codigoprincipal=:cp or p.codigoauxiliar=:ca";
        Query q = em.createQuery(query);
        q.setParameter("cp", cp);
        q.setParameter("ca", ca);
        List<Producto> productos = q
                .getResultList();
        return productos;
    }

    public List<Object[]> obtenerDescripcionProducto(String usuarioId) {
        String query;
        if (!"-1".equals(usuarioId)) {
            query = "SELECT p.codigo, p.descripcion, count(d.pro_codigo) as tot "
                    + "FROM producto p, detalle_factura d, usuario u, factura f "
                    + "where u.CODIGO='" + usuarioId + "' and f.USU_CODIGO='" + usuarioId + "' and d.FAC_CODIGO=f.CODIGO and d.PRO_CODIGO=p.codigo "
                    + "group by p.descripcion "
                    + "order by tot desc limit 10;";
        } else {
            query = "SELECT p.codigo, p.descripcion, count(d.pro_codigo) as tot "
                    + "FROM producto p, detalle_factura d, usuario u, factura f "
                    + "where d.FAC_CODIGO=f.CODIGO and d.PRO_CODIGO=p.codigo "
                    + "group by p.descripcion "
                    + "order by tot desc limit 10;";

        }
        Query q = em.createNativeQuery(query);

        List<Object[]> result = q.getResultList();
        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    public List<Producto> obtenerProductoPorCodigoP(int cod) {
        String query = "SELECT p FROM Producto p where p.codigo=:cod";
        Query q = em.createQuery(query);
        q.setParameter("cod", cod);
        List<Producto> productos = q
                .getResultList();
        return productos;
    }

}
