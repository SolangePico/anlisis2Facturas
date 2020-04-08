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
        String query = "SELECT c.PRECIOUNITARIO, f.FECHAEMISION, i.RAZONSOCIAL , i.DIRECCION, df.cantidad "
                + "FROM control_precios c, factura f, info_tributaria i, detalle_factura df "
                + "WHERE c.FAC_CODIGO=f.CODIGO and df.pro_codigo=c.pro_codigo and i.CODIGO=f.INF_CODIGO and c.PRO_CODIGO='" + codigoProducto + "' "
                + "order by f.FECHAEMISION;";
        Query q = em.createNativeQuery(query);

        List<Object[]> result = q.getResultList();
        return result;
    }

    public List<Object[]> obtenerListaPreciosPorProductoTodo() {
        String query = "SELECT p.descripcion ,c.PRECIOUNITARIO, f.FECHAEMISION, i.RAZONSOCIAL , i.DIRECCION, p.codigo, count(c.codigo) as tot "
                + "                             FROM control_precios c, factura f, info_tributaria i, producto p "
                + "                             WHERE c.FAC_CODIGO=f.CODIGO and i.CODIGO=f.INF_CODIGO and c.PRO_CODIGO=p.codigo "
                + "                     group by p.descripcion ,c.PRECIOUNITARIO, f.FECHAEMISION, i.RAZONSOCIAL , i.DIRECCION, p.codigo order by tot desc;";
        Query q = em.createNativeQuery(query);

        List<Object[]> result = q.getResultList();
        return result;
    }

    public List<Object[]> obtenerListaProdSuper(String codProd, String ruc, int anio) {
        String query;
        if (anio == -1) {
            query = "select i.codigo as estab, p.codigo as producto,i.establecimiento ,  "
                    + "i.direccion,max(cp.preciounitario) as maximo, min(cp.preciounitario) as minimo "
                    + "from info_tributaria i, factura f, control_precios cp, producto p "
                    + "where f.INF_CODIGO=i.CODIGO and cp.FAC_CODIGO=f.codigo and p.CODIGO='" + codProd + "' and p.codigo=cp.pro_codigo "
                    + "and i.ruc='" + ruc + "' group by p.codigo,i.establecimiento, p.descripcion, i.direccion order by maximo desc;";
        } else {
            query = "select i.codigo as estab, p.codigo as producto,i.establecimiento ,  "
                    + "i.direccion,max(cp.preciounitario) as maximo, min(cp.preciounitario) as minimo "
                    + "from info_tributaria i, factura f, control_precios cp, producto p "
                    + "where f.INF_CODIGO=i.CODIGO and year(f.fechaemision)='" + anio + "' and cp.FAC_CODIGO=f.codigo and p.CODIGO='" + codProd + "' and p.codigo=cp.pro_codigo "
                    + "and i.ruc='" + ruc + "' group by p.codigo,i.establecimiento, p.descripcion, i.direccion order by maximo desc;";

        }
        Query q = em.createNativeQuery(query);
        List<Object[]> result = q.getResultList();
        return result;
    }

    public List<Object[]> obtenerFechaPrecio(String codEstab, String codProd, int anio) {
        String query;
        if (anio == -1) {
            query = "select cp.preciounitario, f.FECHAEMISION from control_precios cp, factura f, info_tributaria i where cp.PRO_CODIGO=" + codProd + " and f.CODIGO=cp.FAC_CODIGO and f.INF_CODIGO=i.codigo and i.codigo='" + codEstab + "' order by cp.PRECIOUNITARIO desc, f.fechaemision;";
        } else {
            query = "select cp.preciounitario, f.FECHAEMISION from control_precios cp, "
                    + "factura f, info_tributaria i where cp.PRO_CODIGO=" + codProd + " and "
                    + "f.CODIGO=cp.FAC_CODIGO and year(f.fechaemision)='" + anio + "' and f.INF_CODIGO=i.codigo and i.codigo='" + codEstab + "' "
                    + "order by cp.PRECIOUNITARIO desc, f.fechaemision;";
        }
        Query q = em.createNativeQuery(query);
        List<Object[]> result = q.getResultList();
        return result;
    }

    public List<Object[]> obtenerFechaActualPrecio(String codEstab, String codProd, int anio) {
        String query;
        if (anio == -1) {
            query = "select cp.preciounitario, max(f.FECHAEMISION) "
                    + "from control_precios cp, factura f, info_tributaria "
                    + "i where cp.PRO_CODIGO=" + codProd + " and f.CODIGO=cp.FAC_CODIGO "
                    + "and f.INF_CODIGO=i.codigo and i.codigo='" + codEstab + "' group by cp.preciounitario;";
        } else {
            query = "select cp.preciounitario, max(f.FECHAEMISION) "
                    + "from control_precios cp, factura f, info_tributaria "
                    + "i where cp.PRO_CODIGO=" + codProd + " and f.CODIGO=cp.FAC_CODIGO and year(f.fechaemision)='" + anio + "' "
                    + "and f.INF_CODIGO=i.codigo and i.codigo='" + codEstab + "' group by cp.preciounitario;";
        }
        Query q = em.createNativeQuery(query);
        List<Object[]> result = q.getResultList();
        return result;
    }

    public List<Object[]> obtenerListaMenorPrecio(String proCod, String ruc) {
        String query = "select i.ruc,i.ESTABLECIMIENTO,i.razonsocial, max(f.fechaemision) fecmax,p.codigo ,p.descripcion, min(c.preciounitario) as minimo "
                + "from info_tributaria i, factura f, producto p, control_precios c "
                + "where i.ruc='" + ruc + "' and "
                + "i.codigo=f.INF_CODIGO and f.CODIGO=c.FAC_CODIGO and c.PRO_CODIGO=p.CODIGO and p.codigo=" + proCod + " "
                + "group by i.ruc,i.ESTABLECIMIENTO,i.razonsocial,p.codigo ,p.descripcion order by minimo asc, fecmax desc;";

        Query q = em.createNativeQuery(query);
        List<Object[]> result = q.getResultList();
        return result;
    }

    public List<Object[]> obtenerListaProductoPorAnio(int anio, String codProd, String usuCodigo) {
        String query;
        if (!usuCodigo.equals("-1")) {
            query = "select f.FECHAEMISION, c.preciounitario, month(f.fechaemision) from control_precios c, factura f, usuario u "
                    + "where u.codigo='" + usuCodigo + "'  and f.USU_CODIGO=u.CODIGO and c.fac_codigo=f.codigo and c.pro_codigo='" + codProd + "' "
                    + "and year(FECHAEMISION)='" + anio + "' order by f.FECHAEMISION;";
        } else {
            query = "select f.FECHAEMISION, c.preciounitario, month(f.fechaemision) from control_precios c, factura f, usuario u "
                    + "where c.fac_codigo=f.codigo and c.pro_codigo='" + codProd + "' "
                    + "and year(FECHAEMISION)='" + anio + "' order by f.FECHAEMISION;";

        }
        Query q = em.createNativeQuery(query);
        List<Object[]> result = q.getResultList();
        return result;
    }

    public List<Object[]> obtenerListaProductoAnios(String usuCodigo, String codProd) {
        String query;
        if (!usuCodigo.equals("-1")) {
            query = "select year(f.FECHAEMISION) as anio, c.pro_codigo from control_precios c, factura f, usuario u "
                    + "where u.codigo='" + usuCodigo + "'  and f.USU_CODIGO=u.CODIGO and c.fac_codigo=f.codigo and c.pro_codigo='" + codProd + "'"
                    + "group by anio order by anio desc;";
        } else {
            query = "select year(f.FECHAEMISION) as anio, c.pro_codigo from control_precios c, factura f, usuario u "
                    + "where c.fac_codigo=f.codigo and c.pro_codigo='" + codProd + "'"
                    + "group by anio order by anio desc;";

        }
        Query q = em.createNativeQuery(query);
        List<Object[]> result = q.getResultList();
        return result;
    }

}
