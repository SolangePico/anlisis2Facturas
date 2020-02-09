/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.facturas.facade;

import ec.edu.espe.tesis.facturas.model.Factura;
import ec.edu.espe.tesis.facturas.model.Producto;
import ec.edu.espe.tesis.facturas.model.Usuario;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
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

    @Inject
    UsuarioFacade usuarioFacade;

    public List<Factura> obtenerFacturaPorCodigo(String numAu) {
        String query = "SELECT f FROM Factura f where f.numeroautorizacion=:numAu";
        Query q = em.createQuery(query);
        q.setParameter("numAu", numAu);
        List<Factura> facturas = q
                .getResultList();
        return facturas;

    }

    public int totalFacturasPorUsuario(String usuCodigo) {
        String query;
        if (!"-1".equals(usuCodigo)) {
            query = "select count(*) from factura where usu_codigo=" + usuCodigo + ";";
        } else {
            query = "select count(*) from factura;";
        }
        Query q = em.createNativeQuery(query);
        return ((BigInteger) q.getSingleResult()).intValue();

    }

    public double totalGastadoPorUsuario(String usuCodigo) {
        String query = "select sum(importetotal) from factura where usu_codigo=" + usuCodigo + ";";
        Query q = em.createNativeQuery(query);
        return ((BigDecimal) q.getSingleResult()).doubleValue();

    }

    public double promedioFactura(String usuCodigo) {
        String query = "select avg(importetotal) from factura where usu_codigo=" + usuCodigo + ";";
        Query q = em.createNativeQuery(query);
        return ((BigDecimal) q.getSingleResult()).doubleValue();

    }

    public double maxGastadoFactura(String usuCodigo) {
        String query = "select max(importetotal) from factura where usu_codigo=" + usuCodigo + ";";
        Query q = em.createNativeQuery(query);
        return ((BigDecimal) q.getSingleResult()).doubleValue();

    }

    public String fechaUltimaFactura(String usuCodigo) {
        String query = "select max(fechaautori) from factura where usu_codigo=" + usuCodigo + ";";
        Query q = em.createNativeQuery(query);
        return q.getSingleResult().toString();

    }

    public List<Factura> obtenerFacturasConCriterio(int usuarioId) {
        Usuario usu;
        usu = usuarioFacade.obtenerUsuarioPorCodigo(usuarioId).get(0);
        String query = "SELECT f FROM Factura f where f.usuCodigo=:usuCodigo";
        Query q = em.createQuery(query);
        q.setParameter("usuCodigo", usu);
        List<Factura> facturas = q.getResultList();
        return facturas;
    }

    public List<Factura> obtenerFacturasOrdenadas(int usuarioId) {
        Usuario usu;
        usu = usuarioFacade.obtenerUsuarioPorCodigo(usuarioId).get(0);
        String query = "SELECT f FROM Factura f where f.usuCodigo=:usuCodigo order by f.fechaemision desc";
        Query q = em.createQuery(query);
        q.setParameter("usuCodigo", usu);
        List<Factura> facturas = q.getResultList();
        return facturas;
    }

    public List<Object[]> obtenerFacturaPorEstablecimiento(String usuCodigo) {
        String query = "SELECT i.establecimiento, i.direccion, count(f.inf_codigo) as tot "
                + "from info_tributaria i, factura f  "
                + "where i.codigo=f.INF_CODIGO and f.USU_CODIGO='" + usuCodigo + "' "
                + "group by i.establecimiento, i.direccion order by tot desc;";
        Query q = em.createNativeQuery(query);

        List<Object[]> result = q.getResultList();
        return result;
    }

    public Object obtenerGastoPromedioPorAnio(String usuCodigo, int year) {
        String query = "select round(avg(f.importeTotal),2) from factura f, "
                + "usuario u where f.USU_CODIGO=u.CODIGO and "
                + "u.CODIGO=" + usuCodigo + " and year(f.FECHAEMISION)='" + year + "';";
        Query q = em.createNativeQuery(query);

        Object result = q.getSingleResult();
        return result;
    }

    public Object obtenerTotalFacturasPorAnio(String usuCodigo, int year) {
        String query = "select count(*) from factura f, "
                + "usuario u where f.USU_CODIGO=u.CODIGO and "
                + "u.CODIGO=" + usuCodigo + " and year(f.FECHAEMISION)='" + year + "';";
        Query q = em.createNativeQuery(query);

        Object result = q.getSingleResult();
        return result;
    }

    public List<Object[]> obtenerDetallesFactura(String codigoFactura) {
        String query = "SELECT p.descripcion, f.cantidad, c.preciounitario, c.PRECIO FROM detalle_factura f, producto p, control_precios c where f.fac_codigo='" + codigoFactura + "' and f.PRO_CODIGO=p.CODIGO and c.pro_codigo=p.CODIGO and c.fac_codigo=f.FAC_CODIGO;";
        Query q = em.createNativeQuery(query);

        List<Object[]> result = q.getResultList();
        return result;
    }

    public List<Factura> obtenerFacturasPorFecha(Date fechaInicio, Date fechaFin, String id) {
        Usuario usu;
        usu = usuarioFacade.obtenerUsuarioPorCodigo(Integer.parseInt(id)).get(0);
        String query = "SELECT f FROM Factura f where f.usuCodigo=:usuCodigo and f.fechaemision<=:fechaFin and f.fechaemision>=:fechaInicio";
        Query q = em.createQuery(query);
        q.setParameter("usuCodigo", usu);
        q.setParameter("fechaInicio", fechaInicio);
        q.setParameter("fechaFin", fechaFin);
        List<Factura> facturas = q.getResultList();
        return facturas;
    }

}
