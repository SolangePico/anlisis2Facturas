package ec.edu.espe.tesis.servicio;

import ec.edu.espe.tesis.facturas.facade.DetalleFacturaFacade;
import ec.edu.espe.tesis.facturas.facade.FacturaFacade;
import ec.edu.espe.tesis.facturas.facade.ImpuestoFacade;

import ec.edu.espe.tesis.facturas.facade.InfoTributariaFacade;
import ec.edu.espe.tesis.facturas.facade.ProductoFacade;
import ec.edu.espe.tesis.facturas.facade.TotalImpuestoFacade;
import ec.edu.espe.tesis.facturas.model.DetalleFactura;
import ec.edu.espe.tesis.facturas.model.Impuesto;
import ec.edu.espe.tesis.facturas.model.InfoTributaria;
import ec.edu.espe.tesis.facturas.model.Producto;
import ec.edu.espe.tesis.modeloXML.AutorizacionXML;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import org.jboss.logging.Logger;

/**
 *
 * @author danny
 */
@Stateless

public class FacturaServicio implements Serializable {

//    @Inject
//    private FacturaFacade facturaFacade;
//
//    @Inject
//    private DetalleFacturaFacade detalleFacturaFacade;
//
//    @Inject
//    private ImpuestoFacade impuestoFacade;
//
//    @Inject
//    private InfoFacturaFacade infoFacturaFacade;
    @Inject
    private InfoTributariaFacade infoTributariaFacade;

    @Inject
    private ProductoFacade productoFacade;
//
//    @Inject
//    private TotalImpuestoFacade totalImpuestoFacade;

    public void guardarFactura(AutorizacionXML autorizacion) {
        InfoTributaria infoTributaria = new InfoTributaria();
        try {
            if (infoTributariaFacade.obtenerEstablecimientoPorCriterio(autorizacion.getComprobante().getFactura().getInfoTributaria().getRuc(), autorizacion.getComprobante().getFactura().getInfoTributaria().getEstab()).isEmpty()) {
                infoTributaria.setCodigo(infoTributariaFacade.count());
                infoTributaria.setRuc(autorizacion.getComprobante().getFactura().getInfoTributaria().getRuc());
                infoTributaria.setEstablecimiento(autorizacion.getComprobante().getFactura().getInfoTributaria().getEstab());
                infoTributaria.setRazonsocial(autorizacion.getComprobante().getFactura().getInfoTributaria().getRazonSocial());
                infoTributaria.setSecuencial(autorizacion.getComprobante().getFactura().getInfoTributaria().getSecuencial());
                infoTributariaFacade.create(infoTributaria);
            } else {
                System.out.println("Establecimiento ya esta Registrado");
            }
        } catch (Exception e) {
            System.out.println("" + e);
        }

        Producto producto;
        int numDetalles = autorizacion.getComprobante().getFactura().getDetalles().size();

        try {
            for (int i = 0; i < numDetalles; i++) {
              producto = new Producto();
                if (productoFacade.obtenerProductoPorCriterio(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoPrincipal(), autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoAuxiliar()).isEmpty()) {
                    producto.setCodigo(productoFacade.count());
                    System.out.println("resgistros " +productoFacade.count());
                    producto.setCodigoauxiliar(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoAuxiliar());
                    producto.setCodigoprincipal(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoPrincipal());
                    producto.setDescripcion(autorizacion.getComprobante().getFactura().getDetalles().get(i).getDescripcion());
                    productoFacade.create(producto);

                } else {
                    System.out.println("Producto ya esta Registrado");
                }
            }
        } catch (Exception e) {
            System.out.println("");
        }
//        int numDetalles = autorizacion.getComprobante().getFactura().getDetalles().size();
//        int numImpuestosInfoFac = autorizacion.getComprobante().getFactura().getInfoFactura().getTotalImpuestos().size();

//        Factura factura = new Factura();
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String fecha1 = autorizacion.getFechaAutorizacion();
//        Date fecha = null;
//        fecha = df.parse(fecha1);
//        factura.setNumeroautorizacion(autorizacion.getNumeroAutorizacion());
//        factura.setFechaautori(fecha);
//        factura.getInfCodigo().setEstablecimiento(autorizacion.getComprobante().getFactura().getInfoTributaria().getEstab());
//        factura.getInfCodigo().setRazonsocial(autorizacion.getComprobante().getFactura().getInfoTributaria().getRazonSocial());
//        factura.getInfCodigo().setRuc(autorizacion.getComprobante().getFactura().getInfoTributaria().getRuc());
//        factura.getInfCodigo().setSecuencial(autorizacion.getComprobante().getFactura().getInfoTributaria().getSecuencial());
//        for (int i = 0; i < numDetalles; i++) {
//            DetalleFactura detalle = new DetalleFactura();
//            detalle.setCantidad(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCantidad());
//            int numImpuestoDet = autorizacion.getComprobante().getFactura().getDetalles().get(i).getImpuestos().size();
//            for (int k = 0; k < numImpuestoDet; k++) {
//                Impuesto impuesto = new Impuesto();
//                impuesto.setCodigo(autorizacion.getComprobante().getFactura().getDetalles().get(i).getImpuestos().get(k).getCodigo());
//            }
//            factura.getDetalleFacturaList().add(detalle);
//        }
//    }
//
//    private static java.sql.Date convertUtilDateFromSqlDate(java.util.Date fecha) {
//        return new java.sql.Date(fecha.getTime());
    }

}
