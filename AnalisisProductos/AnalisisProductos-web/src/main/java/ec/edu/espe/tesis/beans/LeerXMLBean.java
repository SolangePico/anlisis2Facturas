package ec.edu.espe.tesis.beans;

//import com.thoughtworks.xstream.XStream;
import ec.edu.espe.tesis.modeloXML.ComprobanteXML;
import ec.edu.espe.tesis.modeloXML.InfoFacturaXML;
import ec.edu.espe.tesis.modeloXML.ImpuestoXML;
import ec.edu.espe.tesis.modeloXML.InfoTributariaXML;
import ec.edu.espe.tesis.modeloXML.TotalImpuestoXML;
import ec.edu.espe.tesis.modeloXML.FacturaXML;
import ec.edu.espe.tesis.modeloXML.DetalleXML;
import ec.edu.espe.tesis.modeloXML.AutorizacionXML;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;
import com.thoughtworks.xstream.XStream;
import ec.edu.espe.tesis.facturas.model.Usuario;
import ec.edu.espe.tesis.servicio.*;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author AGG
 */
@Named(value = "LeerXMLBean")
@ViewScoped

public class LeerXMLBean implements Serializable {

    @Inject
    FacturaServicio facturaServicio;
    @Inject
    UsuarioServicio usuarioServicio;

    @Inject
    HttpSessionHandler sesion;
    @Inject
    private LogBean log;

    private UploadedFile file;
    private FacturaXML factura;
    private ArrayList<AutorizacionXML> listaAutorizacion = new ArrayList();
    private int totFacturas;
    private int facturasSubidas;
    private boolean flagCargar;
    private int validar;
    private String codigoUsuario;

    public int getTotFacturas() {
        return totFacturas;
    }

    public boolean isFlagCargar() {
        return flagCargar;
    }

    public void setFlagCargar(boolean flagCargar) {
        this.flagCargar = flagCargar;
    }

    public void setTotFacturas(int totFacturas) {
        this.totFacturas = totFacturas;
    }

    public ArrayList<AutorizacionXML> getListaAutorizacion() {
        return listaAutorizacion;
    }

    public void setListaAutorizacion(ArrayList<AutorizacionXML> listaAutorizacion) {
        this.listaAutorizacion = listaAutorizacion;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void fileUploadListener(FileUploadEvent e) {
        flagCargar = false;
        File f;
        this.file = e.getFile();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputstream(), "UTF-8"))) {
            //f = new File("C:\\Users\\solan\\OneDrive\\Escritorio\\factura.xml");

            f = new File("C:\\Users\\alterbios\\Desktop\\Releases-MARATHON-GO\\factura"+sesion.getCorreo()+".xml");
//            f = new File("E:\\Danny\\Escritorio\\anlisis2Facturas\\factura.xml");
            try {
                FileWriter w = new FileWriter(f);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);
                wr.write("");//escribimos en el archivo  

                String line;
                while ((line = bufferedReader.readLine()) != null) {

                    //   System.out.println(line);
                    if (line.contains("<![CDATA[<?xml version = '1.0' encoding = 'UTF-8'?>")) {
                        line = line.replace("<![CDATA[<?xml version = '1.0' encoding = 'UTF-8'?>", "");
                    }
                    if (line.contains("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>")) {
                        line = line.replace("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
                    }
                    if (line.contains("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")) {
                        line = line.replace("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
                    }
                    if (line.contains("<![CDATA[<?xml version = \"1.0\" encoding = \"UTF-8\"?>")) {
                        line = line.replace("<![CDATA[<?xml version = \"1.0\" encoding = \"UTF-8\"?>", "");
                    }

                    if (line.contains("]]>")) {
                        line = line.replaceAll("]]>", "");
                    }

                    wr.append(line); //concatenamos en el archivo sin borrar lo existente             
                }
                wr.close();
                bw.close();

            } catch (IOException p) {
            }
            capturarDatos(f, e.getFile().getFileName());
            f.delete();

        } catch (Exception ex) {
            System.out.println(" error    " + e);
        }
        verificarFacturas();

    }

    public void fileUploadListenerVerificar(FileUploadEvent e) {
        flagCargar = false;
        File f;
        this.file = e.getFile();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputstream(), "UTF-8"))) {
            //f = new File("C:\\Users\\solan\\OneDrive\\Escritorio\\factura.xml");

//            f = new File("C:\\Users\\alterbios\\Desktop\\Releases-MARATHON-GO\\factura.xml");
            f = new File("E:\\Danny\\Escritorio\\anlisis2Facturas\\factura.xml");
            try {
                FileWriter w = new FileWriter(f);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);
                wr.write("");//escribimos en el archivo  

                String line;
                while ((line = bufferedReader.readLine()) != null) {

                    //   System.out.println(line);
                    if (line.contains("<![CDATA[<?xml version = '1.0' encoding = 'UTF-8'?>")) {
                        line = line.replace("<![CDATA[<?xml version = '1.0' encoding = 'UTF-8'?>", "");
                    }
                    if (line.contains("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>")) {
                        line = line.replace("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
                    }
                    if (line.contains("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")) {
                        line = line.replace("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
                    }
                    if (line.contains("<![CDATA[<?xml version = \"1.0\" encoding = \"UTF-8\"?>")) {
                        line = line.replace("<![CDATA[<?xml version = \"1.0\" encoding = \"UTF-8\"?>", "");
                    }

                    if (line.contains("]]>")) {
                        line = line.replaceAll("]]>", "");
                    }

                    wr.append(line); //concatenamos en el archivo sin borrar lo existente             
                }
                wr.close();
                bw.close();

            } catch (IOException p) {
            }
            verificarDatos(f);
            f.delete();

        } catch (Exception ex) {
            System.out.println(" error    " + e);
        }

    }

    @PostConstruct
    public void init() {
        totFacturas = facturaServicio.obtenerFacturasPorUsuario(sesion.getId());
        if (sesion.isLoggedIn()) {
            if (totFacturas == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Sube al menos una factura para navegar por la plataforma"));
            }
        }
        facturasSubidas = 0;
        flagCargar = true;

    }

    public void verificarFacturas() {

        Usuario usu;
        usu = usuarioServicio.obtenerUsuarioPorCorreo(sesion.getCorreo());

        if (facturaServicio.obtenerFacturasPorUsuario(sesion.getId()) > 0) {
            usu.setEstado('S');
            sesion.setFlag(false);
            totFacturas = facturaServicio.obtenerFacturasPorUsuario(sesion.getId());
            PrimeFaces.current().ajax().update("menuform");
        } else {
            sesion.setFlag(true);
            usu.setEstado('N');
        }
        usuarioServicio.actualizarUsuario(usu);

    }

    public void capturarDatos(File file, String nombreFac) {
        AutorizacionXML obj;
        FacturaXML obj1;
        try {
            XStream xstream = new XStream();

            xstream.allowTypesByRegExp(new String[]{".*"});
            // xstream.processAnnotations(Autorizacion.class);
            xstream.alias("autorizacion", AutorizacionXML.class);
            xstream.alias("comprobante", ComprobanteXML.class);
            xstream.alias("factura", FacturaXML.class);
            xstream.alias("infoTributaria", InfoTributariaXML.class);
            xstream.alias("infoFactura", InfoFacturaXML.class);
            xstream.alias("detalle", DetalleXML.class);
            xstream.alias("impuesto", ImpuestoXML.class);
            xstream.alias("totalImpuesto", TotalImpuestoXML.class);
            xstream.ignoreUnknownElements();

            obj = (AutorizacionXML) xstream.fromXML(file);
            if (obj != null) {
                int err;
                err = facturaServicio.guardarFactura(obj, sesion.getId());
                if (err == 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Factura: " + nombreFac + " ya se encuentra registrada"));

                    PrimeFaces.current().ajax().update("cargarForm:growl");
                } else {
                    facturasSubidas++;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se han subido " + facturasSubidas + " Facturas"));
                    PrimeFaces.current().ajax().update("cargarForm:growl");
                }

            } else {

            }

        } catch (Exception e) {
            try {
                XStream xstream = new XStream();

                xstream.allowTypesByRegExp(new String[]{".*"});
//                 xstream.processAnnotations(Autorizacion.class);
//                xstream.alias("autorizacion", AutorizacionXML.class);
//                xstream.alias("comprobante", ComprobanteXML.class);
                xstream.alias("factura", FacturaXML.class);
                xstream.alias("infoTributaria", InfoTributariaXML.class);
                xstream.alias("infoFactura", InfoFacturaXML.class);
                xstream.alias("detalle", DetalleXML.class);
                xstream.alias("impuesto", ImpuestoXML.class);
                xstream.alias("totalImpuesto", TotalImpuestoXML.class);
                xstream.ignoreUnknownElements();

                obj1 = (FacturaXML) xstream.fromXML(file);
                if (obj1 != null) {
                    int err;
                    err = facturaServicio.guardarFacturaTipo2(obj1, sesion.getId());
                    if (err == 1) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Factura: " + nombreFac + " ya se encuentra registrada"));
                        PrimeFaces.current().ajax().update("cargarForm:growl");
                    } else {
                        facturasSubidas++;
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se han subido " + facturasSubidas + " Facturas"));
                        PrimeFaces.current().ajax().update("cargarForm:growl");
                    }

                } else {

                }

            } catch (Exception g) {

            }
        }
        totFacturas = facturaServicio.obtenerFacturasPorUsuario(sesion.getId());

    }

    public void verificarDatos(File file) {
        AutorizacionXML obj;
        FacturaXML obj1;
        try {
            XStream xstream = new XStream();

            xstream.allowTypesByRegExp(new String[]{".*"});
            // xstream.processAnnotations(Autorizacion.class);
            xstream.alias("autorizacion", AutorizacionXML.class);
            xstream.alias("comprobante", ComprobanteXML.class);
            xstream.alias("factura", FacturaXML.class);
            xstream.alias("infoTributaria", InfoTributariaXML.class);
            xstream.alias("infoFactura", InfoFacturaXML.class);
            xstream.alias("detalle", DetalleXML.class);
            xstream.alias("impuesto", ImpuestoXML.class);
            xstream.alias("totalImpuesto", TotalImpuestoXML.class);
            xstream.ignoreUnknownElements();

            obj = (AutorizacionXML) xstream.fromXML(file);
            if (obj != null) {

                validar = facturaServicio.verificarFactura(obj, codigoUsuario);
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Verifique su factura"));
                 log.validarFactura();
            } else {

            }

        } catch (Exception e) {
            try {
                XStream xstream = new XStream();

                xstream.allowTypesByRegExp(new String[]{".*"});
//                 xstream.processAnnotations(Autorizacion.class);
//                xstream.alias("autorizacion", AutorizacionXML.class);
//                xstream.alias("comprobante", ComprobanteXML.class);
                xstream.alias("factura", FacturaXML.class);
                xstream.alias("infoTributaria", InfoTributariaXML.class);
                xstream.alias("infoFactura", InfoFacturaXML.class);
                xstream.alias("detalle", DetalleXML.class);
                xstream.alias("impuesto", ImpuestoXML.class);
                xstream.alias("totalImpuesto", TotalImpuestoXML.class);
                xstream.ignoreUnknownElements();

                obj1 = (FacturaXML) xstream.fromXML(file);
                if (obj1 != null) {
                    validar = facturaServicio.verificarFactura1(obj1, codigoUsuario);
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Verifique su factura"));
                    log.validarFactura();
                } else {

                }

            } catch (Exception g) {

            }
        }

    }

    public void terminarCarga() {
        flagCargar = true;
    }

    public int getValidar() {
        return validar;
    }

    public void setValidar(int validar) {
        this.validar = validar;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

}
