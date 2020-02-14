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
import ec.edu.espe.tesis.modeloXML.FacturaXML;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public int guardarFactura(AutorizacionXML autorizacion, String usuCodigo) {
        int error = 0;
        InfoTributaria infoTributaria = new InfoTributaria();
//        try {
        if (infoTributariaFacade.obtenerEstablecimientoPorCriterio(autorizacion.getComprobante().getFactura().getInfoTributaria().getRuc(), autorizacion.getComprobante().getFactura().getInfoTributaria().getEstab()).isEmpty()) {
            //infoTributaria.setCodigo(infoTributariaFacade.count());
            infoTributaria.setRuc(autorizacion.getComprobante().getFactura().getInfoTributaria().getRuc());
            infoTributaria.setEstablecimiento(autorizacion.getComprobante().getFactura().getInfoTributaria().getEstab());
            infoTributaria.setRazonsocial(autorizacion.getComprobante().getFactura().getInfoTributaria().getRazonSocial());
            infoTributaria.setSecuencial(autorizacion.getComprobante().getFactura().getInfoTributaria().getSecuencial());
            infoTributaria.setDireccion(autorizacion.getComprobante().getFactura().getInfoFactura().getDirEstablecimiento());
            infoTributariaFacade.create(infoTributaria);
        } else {
            System.out.println("Establecimiento ya esta Registrado");
        }
//        } catch (Exception e) {
//            System.out.println("" + e);
//        }
        Factura factura = new Factura();
        try {
            if (facturaFacade.obtenerFacturaPorCodigo(autorizacion.getNumeroAutorizacion()).isEmpty()) {
                // factura.setCodigo(facturaFacade.count());
                String fecha1 = autorizacion.getComprobante().getFactura().getInfoFactura().getFechaEmision();
                String fecha2 = autorizacion.getComprobante().getFactura().getInfoFactura().getFechaEmision();
                Date fechaAu = null;
                Date fechaEm = null;
                SimpleDateFormat fl = new SimpleDateFormat("dd/MM/yyyy");
                fechaAu = fl.parse(fecha1);
                fechaEm = fl.parse(fecha2);
                factura.setFechaautori(fechaAu);
                factura.setFechaemision(fechaEm);
                factura.setImportetotal(autorizacion.getComprobante().getFactura().getInfoFactura().getImporteTotal());
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
//                            producto.setCodigo(productoFacade.count());
                            BigDecimal tot = new BigDecimal(1);
                            producto.setCodigoauxiliar(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoAuxiliar());
                            producto.setCodigoprincipal(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoPrincipal());
                            producto.setDescripcion(autorizacion.getComprobante().getFactura().getDetalles().get(i).getDescripcion());
                            producto.setTotal(tot);
                            productoFacade.create(producto);
                        } else {

                            producto = productoFacade.obtenerProductoPorCriterio(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoPrincipal(), autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoAuxiliar()).get(0);
                            producto.setTotal(producto.getTotal().add(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCantidad()));
                        }
                        ControlPrecios controlPrecios = new ControlPrecios();
                        List<ControlPrecios> listaControlPrecios;
                        listaControlPrecios = controlPreciosFacade.obtenerControlPedidoPorProducto(productoFacade.obtenerProductoPorCriterio(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoPrincipal(), autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoAuxiliar()).get(0));
                        // controlPrecios.setCodigo(controlPreciosFacade.count());
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
//                        
                        DetalleFactura detalleFactura = new DetalleFactura();
                        //detalleFactura.setCodigo(detalleFacturaFacade.count());
                        detalleFactura.setCantidad(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCantidad());
                        // detalleFactura.setCodigo(detalleFacturaFacade.count());
                        detalleFactura.setFacCodigo(facturaFacade.obtenerFacturaPorCodigo(autorizacion.getNumeroAutorizacion()).get(0));
                        detalleFactura.setProCodigo(productoFacade.obtenerProductoPorCriterio(autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoPrincipal(), autorizacion.getComprobante().getFactura().getDetalles().get(i).getCodigoAuxiliar()).get(0));
                        detalleFacturaFacade.create(detalleFactura);
                        for (int j = 0; j < autorizacion.getComprobante().getFactura().getDetalles().get(i).getImpuestos().size(); j++) {
                            Impuesto impuesto = new Impuesto();
                            impuesto.setCodigo(impuestoFacade.count() + "");
                            impuesto.setDetCodigo(detalleFacturaFacade.obtenerUltimoRegistro().get(0));
                            impuesto.setTarifa(autorizacion.getComprobante().getFactura().getDetalles().get(i).getImpuestos().get(j).getTarifa());
                            impuestoFacade.create(impuesto);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("No Creo Control");
                }
                if (autorizacion.getComprobante().getFactura().getInfoFactura().getTotalImpuestos() != null) {
                    for (int i = 0; i < autorizacion.getComprobante().getFactura().getInfoFactura().getTotalImpuestos().size(); i++) {
                        TotalImpuesto totalImpuesto = new TotalImpuesto();
                        try {
                            totalImpuesto.setBaseimponible(autorizacion.getComprobante().getFactura().getInfoFactura().getTotalImpuestos().get(i).getBaseImponible());
                            //  totalImpuesto.setCodigo(totalImpuestoFacade.count() + "");
                            totalImpuesto.setDescuento(autorizacion.getComprobante().getFactura().getInfoFactura().getTotalImpuestos().get(i).getDescuentoAdicional());
                            totalImpuesto.setFacCodigo(factura);
                            totalImpuestoFacade.create(totalImpuesto);
                        } catch (Exception e) {
                            System.out.println("Error al ingresar total impuesto a la factura " + e);
                        }
                    }
                }
            } else {
                error = 1;
                System.out.println("No se ingreso factura");
            }
        } catch (NumberFormatException | ParseException e) {

        }
        return error;
    }

    public int guardarFacturaTipo2(FacturaXML facturaxml, String usuCodigo) {
        int error = 0;
        InfoTributaria infoTributaria = new InfoTributaria();
//        try {
        if (infoTributariaFacade.obtenerEstablecimientoPorCriterio(facturaxml.getInfoTributaria().getRuc(), facturaxml.getInfoTributaria().getEstab()).isEmpty()) {
            //infoTributaria.setCodigo(infoTributariaFacade.count());
            infoTributaria.setRuc(facturaxml.getInfoTributaria().getRuc());
            infoTributaria.setEstablecimiento(facturaxml.getInfoTributaria().getEstab());
            infoTributaria.setRazonsocial(facturaxml.getInfoTributaria().getRazonSocial());
            infoTributaria.setSecuencial(facturaxml.getInfoTributaria().getSecuencial());
            infoTributaria.setDireccion(facturaxml.getInfoFactura().getDirEstablecimiento());
            infoTributariaFacade.create(infoTributaria);
        } else {
            System.out.println("Establecimiento ya esta Registrado");
        }
//        } catch (Exception e) {
//            System.out.println("" + e);
//        }
        Factura factura = new Factura();
        try {
            if (facturaFacade.obtenerFacturaPorCodigo(facturaxml.getInfoTributaria().getClaveAcceso()).isEmpty()) {
                // factura.setCodigo(facturaFacade.count());
                String fecha1 = facturaxml.getInfoFactura().getFechaEmision();
                String fecha2 = facturaxml.getInfoFactura().getFechaEmision();
                Date fechaAu = null;
                Date fechaEm = null;
                SimpleDateFormat fl = new SimpleDateFormat("dd/MM/yyyy");
                fechaAu = fl.parse(fecha1);
                fechaEm = fl.parse(fecha2);
                factura.setFechaautori(fechaAu);
                factura.setFechaemision(fechaEm);
                factura.setImportetotal(facturaxml.getInfoFactura().getImporteTotal());
                factura.setInfCodigo(infoTributariaFacade.obtenerEstablecimientoPorCriterio(facturaxml.getInfoTributaria().getRuc(), facturaxml.getInfoTributaria().getEstab()).get(0));
                factura.setNumeroautorizacion(facturaxml.getInfoTributaria().getClaveAcceso());
                factura.setTotalsinimpuesto(facturaxml.getInfoFactura().getTotalSinImpuestos());
                factura.setUsuCodigo(usuarioFacade.obtenerUsuarioPorCodigo(Integer.parseInt(usuCodigo)).get(0));

                facturaFacade.create(factura);

                Producto producto;
                int numDetalles = facturaxml.getDetalles().size();

                try {
                    for (int i = 0; i < numDetalles; i++) {
                        producto = new Producto();
                        if (productoFacade.obtenerProductoPorCriterio(facturaxml.getDetalles().get(i).getCodigoPrincipal(), facturaxml.getDetalles().get(i).getCodigoAuxiliar()).isEmpty()) {
//                            producto.setCodigo(productoFacade.count());
                            BigDecimal tot = new BigDecimal(1);
                            producto.setCodigoauxiliar(facturaxml.getDetalles().get(i).getCodigoAuxiliar());
                            producto.setCodigoprincipal(facturaxml.getDetalles().get(i).getCodigoPrincipal());
                            producto.setDescripcion(facturaxml.getDetalles().get(i).getDescripcion());
                            producto.setTotal(tot);
                            productoFacade.create(producto);
                        } else {

                            producto = productoFacade.obtenerProductoPorCriterio(facturaxml.getDetalles().get(i).getCodigoPrincipal(), facturaxml.getDetalles().get(i).getCodigoAuxiliar()).get(0);
                            producto.setTotal(producto.getTotal().add(facturaxml.getDetalles().get(i).getCantidad()));
                            productoFacade.edit(producto);
                        }
                        ControlPrecios controlPrecios = new ControlPrecios();
                        List<ControlPrecios> listaControlPrecios;
                        listaControlPrecios = controlPreciosFacade.obtenerControlPedidoPorProducto(productoFacade.obtenerProductoPorCriterio(facturaxml.getDetalles().get(i).getCodigoPrincipal(), facturaxml.getDetalles().get(i).getCodigoAuxiliar()).get(0));
                        // controlPrecios.setCodigo(controlPreciosFacade.count());
                        controlPrecios.setFacCodigo(facturaFacade.obtenerFacturaPorCodigo(facturaxml.getInfoTributaria().getClaveAcceso()).get(0));
                        controlPrecios.setDescuento(facturaxml.getDetalles().get(i).getDescuento());
                        controlPrecios.setPrecio(facturaxml.getDetalles().get(i).getPrecioTotalSinImpuesto());
                        controlPrecios.setPreciounitario(facturaxml.getDetalles().get(i).getPrecioUnitario());
                        controlPrecios.setProCodigo(productoFacade.obtenerProductoPorCriterio(facturaxml.getDetalles().get(i).getCodigoPrincipal(), facturaxml.getDetalles().get(i).getCodigoAuxiliar()).get(0));

                        if (listaControlPrecios.isEmpty()) {
                            controlPreciosFacade.create(controlPrecios);
                        } else {
                            for (int k = 0; k < listaControlPrecios.size(); k++) {
                                if (factura.getInfCodigo().getRuc().equals(listaControlPrecios.get(k).getFacCodigo().getInfCodigo().getRuc())
                                        && factura.getInfCodigo().getEstablecimiento().equals(listaControlPrecios.get(k).getFacCodigo().getInfCodigo().getEstablecimiento())) {
                                    if (listaControlPrecios.get(k).getPreciounitario() != facturaxml.getDetalles().get(i).getPrecioUnitario()) {
                                        controlPreciosFacade.create(controlPrecios);
                                    }
                                } else {
                                    controlPreciosFacade.create(controlPrecios);
                                }
                            }
                        }
//                        
                        DetalleFactura detalleFactura = new DetalleFactura();
                        //detalleFactura.setCodigo(detalleFacturaFacade.count());
                        detalleFactura.setCantidad(facturaxml.getDetalles().get(i).getCantidad());
                        // detalleFactura.setCodigo(detalleFacturaFacade.count());
                        detalleFactura.setFacCodigo(facturaFacade.obtenerFacturaPorCodigo(facturaxml.getInfoTributaria().getClaveAcceso()).get(0));
                        detalleFactura.setProCodigo(productoFacade.obtenerProductoPorCriterio(facturaxml.getDetalles().get(i).getCodigoPrincipal(), facturaxml.getDetalles().get(i).getCodigoAuxiliar()).get(0));
                        detalleFacturaFacade.create(detalleFactura);
                        for (int j = 0; j < facturaxml.getDetalles().get(i).getImpuestos().size(); j++) {
                            try {
                                Impuesto impuesto = new Impuesto();
                                impuesto.setCodigo(impuestoFacade.count() + "");
                                impuesto.setDetCodigo(detalleFacturaFacade.obtenerUltimoRegistro().get(0));
                                impuesto.setTarifa(facturaxml.getDetalles().get(i).getImpuestos().get(j).getTarifa());
                                impuestoFacade.create(impuesto);
                            } catch (Exception ex) {
                                System.out.println("error en impuesto");
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("No Creo Control" + e);
                }

                for (int i = 0; i < facturaxml.getInfoFactura().getTotalImpuestos().size(); i++) {
                    TotalImpuesto totalImpuesto = new TotalImpuesto();
                    try {
                        totalImpuesto.setBaseimponible(facturaxml.getInfoFactura().getTotalImpuestos().get(i).getBaseImponible());
                        //  totalImpuesto.setCodigo(totalImpuestoFacade.count() + "");
                        totalImpuesto.setDescuento(facturaxml.getInfoFactura().getTotalImpuestos().get(i).getDescuentoAdicional());
                        totalImpuesto.setFacCodigo(factura);
                        totalImpuestoFacade.create(totalImpuesto);
                    } catch (Exception e) {
                        System.out.println("Error al ingresar total impuesto a la factura " + e);
                    }
                }
            } else {
                error = 1;
            }
        } catch (Exception e) {

        }
        return error;
    }

    public int obtenerFacturasPorUsuario(String usuarioId) {
        int total = 0;
        total = facturaFacade.totalFacturasPorUsuario(usuarioId);
        return total;
    }

    public Object obtenerGastoPromedioPorAnio(String usuarioId, int year) {
        Object obj = facturaFacade.obtenerGastoPromedioPorAnio(usuarioId, year);

        return obj;
    }

    public Object obtenerTotalFacturasPorAnio(String usuarioId, int year) {
        Object obj = facturaFacade.obtenerTotalFacturasPorAnio(usuarioId, year);

        return obj;
    }

    public Object obtenerTotalGastoPorAnio(String usuarioId, int year) {
        Object obj = facturaFacade.obtenerTotalGastoPorAnio(usuarioId, year);

        return obj;
    }

    public double obtenerTotalGastado(String usuarioId) {
        double total = 0;
        total = facturaFacade.totalGastadoPorUsuario(usuarioId);
        return total;
    }

    public double obtenerPromedioFactura(String usuarioId) {
        double total;
        total = facturaFacade.promedioFactura(usuarioId);
        return total;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Factura> obtenerFacturasConCriterio(int usuarioId) {
        List<Factura> listaFacturas = facturaFacade.obtenerFacturasConCriterio(usuarioId);
        return listaFacturas;
        //return null;
    }

    public List<Factura> obtenerFacturasOrdenadas(int usuarioId) {
        List<Factura> listaFacturas = facturaFacade.obtenerFacturasOrdenadas(usuarioId);
        return listaFacturas;
        //return null;
    }

    public List<Object[]> obtenerFacturasPorMes(String usuarioId, int anio) {
        return facturaFacade.obtenerFacturasPorMes(usuarioId, anio);
    }

    public List<Factura> obtenerFacturasPorMesYAnio(String mes, int anio, String usuCodigo) {
        int numMes;
        switch (mes) {
            case "Enero":
                numMes=1;
                break;
            case "Febrero":
                numMes=2;
                break;
                case "Marzo":
                numMes=3;
                break;case "Abril":
                numMes=4;
                break;case "Mayo":
                numMes=5;
                break;case "Junio":
                numMes=6;
                break;case "Julio":
                numMes=7;
                break;case "Agosto":
                numMes=8;
                break;case "Septiembre":
                numMes=9;
                break;case "Octubre":
                numMes=10;
                break;case "Noviembre":
                numMes=11;
                break;case "Diciembre":
                numMes=12;
                break;

            default:
                numMes=1;
        }
        return facturaFacade.obtenerFacturasPorMesYAnio(numMes, anio, usuCodigo);
    }

    public List<Object[]> obtenerGastoFacturasPorMes(String usuarioId, int anio) {
        return facturaFacade.obtenerGastoFacturasPorMes(usuarioId, anio);
    }

    public List<Object[]> obtenerFacturasPorMesTot(String usuarioId, int anio) {
        return facturaFacade.obtenerFacturasPorMesTot(usuarioId, anio);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> obtenerFacturasPorEstablecimiento(String string) {

        return facturaFacade.obtenerFacturaPorEstablecimiento(string);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> obtenerDetallesFactura(String codigoFactura) {

        return facturaFacade.obtenerDetallesFactura(codigoFactura);
    }

    public List<Factura> obtenerFacturasPorFecha(Date fechaInicio, Date fechaFin, String id) {
        try {
            List<Factura> facturas = null;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String fecha1 = df.format(new java.sql.Date(fechaInicio.getTime()));
            fecha1 = fecha1 + " 00:00:00.000";
            String fecha2 = df.format(new java.sql.Date(fechaFin.getTime()));
            fecha2 = fecha2 + " 23:59:59.000";
            DateFormat dfsql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            facturas = facturaFacade.obtenerFacturasPorFecha(dfsql.parse(fecha1), dfsql.parse(fecha2), id);
            return facturas;
        } catch (ParseException ex) {
            Logger.getLogger(FacturaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
