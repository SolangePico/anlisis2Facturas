package ec.edu.espe.tesis.servicio;

import ec.edu.espe.tesis.facturas.facade.ControlPreciosFacade;
import ec.edu.espe.tesis.facturas.facade.DetalleFacturaFacade;
import ec.edu.espe.tesis.facturas.facade.FacturaFacade;
import ec.edu.espe.tesis.facturas.facade.ImpuestoFacade;

import ec.edu.espe.tesis.facturas.facade.InfoTributariaFacade;
import ec.edu.espe.tesis.facturas.facade.ProductoFacade;
import ec.edu.espe.tesis.facturas.facade.TotalImpuestoFacade;
import ec.edu.espe.tesis.facturas.facade.UsuarioFacade;
import ec.edu.espe.tesis.facturas.model.ControlPrecios;
import ec.edu.espe.tesis.facturas.model.DetalleFactura;
import ec.edu.espe.tesis.facturas.model.Factura;
import ec.edu.espe.tesis.facturas.model.Impuesto;
import ec.edu.espe.tesis.facturas.model.InfoTributaria;
import ec.edu.espe.tesis.facturas.model.Producto;
import ec.edu.espe.tesis.facturas.model.TotalImpuesto;
import ec.edu.espe.tesis.modeloXML.AutorizacionXML;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
//import org.jboss.logging.Logger;

/**
 *
 * @author danny
 */
@Stateless

public class FacturaServicio implements Serializable {

    @Inject
    private FacturaFacade facturaFacade;

    @Inject
    private UsuarioFacade usuarioFacade;

    @Inject
    private DetalleFacturaFacade detalleFacturaFacade;
//
    @Inject
    private ImpuestoFacade impuestoFacade;
//
    @Inject
    private TotalImpuestoFacade totalImpuestoFacade;

    @Inject
    private InfoTributariaFacade infoTributariaFacade;

    @Inject
    private ProductoFacade productoFacade;

    @Inject
    private ControlPreciosFacade controlPreciosFacade;
//
//    @Inject
//    private TotalImpuestoFacade totalImpuestoFacade;

    public void guardarFactura(AutorizacionXML autorizacion, String usuCodigo) {
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
        Factura factura = new Factura();
        try {
            if (facturaFacade.obtenerFacturaPorCodigo(autorizacion.getNumeroAutorizacion()).isEmpty()) {
                factura.setCodigo(facturaFacade.count());
                String fecha1 = autorizacion.getFechaAutorizacion();
                String fecha2 = autorizacion.getComprobante().getFactura().getInfoFactura().getFechaEmision();
                Date fechaAu = null;
                Date fechaEm = null;
                SimpleDateFormat fl = new SimpleDateFormat("dd/MM/yyyy");
                fechaAu = fl.parse(fecha1);
                fechaEm = fl.parse(fecha2);
                factura.setFechaautori(fechaAu);
                factura.setFechaemision(fechaEm);
                factura.setImporteTotal(autorizacion.getComprobante().getFactura().getInfoFactura().getImporteTotal());
                factura.setInfCodigo(infoTributariaFacade.obtenerEstablecimientoPorCriterio(autorizacion.getComprobante().getFactura().getInfoTributaria().getRuc(), autorizacion.getComprobante().getFactura().getInfoTributaria().getEstab()).get(0));
                factura.setNumeroautorizacion(autorizacion.getNumeroAutorizacion());
                factura.setTotalsinimpuesto(autorizacion.getComprobante().getFactura().getInfoFactura().getTotalSinImpuestos());
                factura.setUsuCodigo(usuarioFacade.obtenerUsuarioPorCodigo(Integer.parseInt(usuCodigo)).get(0));

                facturaFacade.create(factura);

                Producto producto;
                int numDetalles = autorizacion.getComprobante().getFactura().getDetalles().size();

                try {
                    for (int i = 0; i < numDetalles; i++) {
                        producto = new Producto();
                        if (productoFacade.obtenerProductoPorCriterio(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoPrincipal(), autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoAuxiliar()).isEmpty()) {
                            producto.setCodigo(productoFacade.count());
                            //  System.out.println("resgistros " + productoFacade.count());
                            producto.setCodigoauxiliar(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoAuxiliar());
                            producto.setCodigoprincipal(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoPrincipal());
                            producto.setDescripcion(autorizacion.getComprobante().getFactura().getDetalles().get(i).getDescripcion());
                            productoFacade.create(producto);
                        } else {
                            //    System.out.println("Producto ya esta Registrado");
                        }
                        ControlPrecios controlPrecios = new ControlPrecios();
                        List<ControlPrecios> listaControlPrecios;
                        listaControlPrecios = controlPreciosFacade.obtenerControlPedidoPorProducto(productoFacade.obtenerProductoPorCriterio(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoPrincipal(), autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoAuxiliar()).get(0));
                        controlPrecios.setCodigo(controlPreciosFacade.count());
                        controlPrecios.setFacCodigo(facturaFacade.obtenerFacturaPorCodigo(autorizacion.getNumeroAutorizacion()).get(0));
                        controlPrecios.setDescuento(autorizacion.getComprobante().getFactura().getDetalles().get(i).getDescuento());
                        controlPrecios.setPrecio(autorizacion.getComprobante().getFactura().getDetalles().get(i).getPrecioTotalSinImpuesto());
                        controlPrecios.setPreciounitario(autorizacion.getComprobante().getFactura().getDetalles().get(i).getPrecioUnitario());
                        controlPrecios.setProCodigo(productoFacade.obtenerProductoPorCriterio(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoPrincipal(), autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoAuxiliar()).get(0));

                        if (listaControlPrecios.isEmpty()) {
                            controlPreciosFacade.create(controlPrecios);
                        } else {
                            for (int k = 0; k < listaControlPrecios.size(); k++) {
                                if (factura.getInfCodigo().getRuc().equals(listaControlPrecios.get(k).getFacCodigo().getInfCodigo().getRuc())
                                        && factura.getInfCodigo().getEstablecimiento().equals(listaControlPrecios.get(k).getFacCodigo().getInfCodigo().getEstablecimiento())) {
                                    if (listaControlPrecios.get(k).getPreciounitario() != autorizacion.getComprobante().getFactura().getDetalles().get(i).getPrecioUnitario()) {
                                        controlPreciosFacade.create(controlPrecios);
                                    }
                                } else {
                                    controlPreciosFacade.create(controlPrecios);
                                }
                            }
                        }

                        DetalleFactura detalleFactura = new DetalleFactura();
                        detalleFactura.setCodigo(detalleFacturaFacade.count());
                        detalleFactura.setCantidad(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCantidad());
                        // detalleFactura.setCodigo(detalleFacturaFacade.count());
                        detalleFactura.setFacCodigo(factura);
                        detalleFactura.setProCodigo(productoFacade.obtenerProductoPorCriterio(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoPrincipal(), autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoAuxiliar()).get(0));
                        detalleFacturaFacade.create(detalleFactura);
                        for (int j = 0; j < autorizacion.getComprobante().getFactura().getDetalles().get(i).getImpuestos().size(); j++) {
                            Impuesto impuesto = new Impuesto();
                            impuesto.setCodigo(impuestoFacade.count() + "");
                            impuesto.setDetCodigo(detalleFactura);
                            impuesto.setTarifa(autorizacion.getComprobante().getFactura().getDetalles().get(i).getImpuestos().get(j).getTarifa());
                            impuestoFacade.create(impuesto);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("No Creo Control");
                }

                for (int i = 0; i < autorizacion.getComprobante().getFactura().getInfoFactura().getTotalImpuestos().size(); i++) {
                    TotalImpuesto totalImpuesto = new TotalImpuesto();
                    try {
                        totalImpuesto.setBaseimponible(autorizacion.getComprobante().getFactura().getInfoFactura().getTotalImpuestos().get(i).getBaseImponible());
                        totalImpuesto.setCodigo(totalImpuestoFacade.count() + "");
                        totalImpuesto.setDescuento(autorizacion.getComprobante().getFactura().getInfoFactura().getTotalImpuestos().get(i).getDescuentoAdicional());
                        totalImpuesto.setFacCodigo(factura);
                        totalImpuestoFacade.create(totalImpuesto);
                    } catch (Exception e) {
                        System.out.println("Error al ingresar total impuesto a la factura " + e);
                    }
                }
            } else {
                System.out.println("No se ingreso factura");
            }
        } catch (Exception e) {

        }
    }

}
