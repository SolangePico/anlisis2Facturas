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
import ec.edu.espe.tesis.servicio.*;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author AGG
 */
//@ManagedBean
@Named(value = "LeerXMLBean")
@ViewScoped

public class LeerXMLBean implements Serializable {

    @Inject
    FacturaServicio facturaServicio;

    private ArrayList<DetalleXML> listaDetalles = new ArrayList();
    private UploadedFile file;
    private FacturaXML factura;

    @PostConstruct
    public void init() {
        String path = "E:\\Danny\\Descargas\\all\\XML";
        File carpeta = new File(path);
        File[] archivos;
        archivos = carpeta.listFiles();
        for (File archivo : archivos) {
            capturarDatos(archivo);
        }
        System.out.println("Leidos :" + listaAutorizacion.size());
        System.out.println("Detalles :" + listaDetalles.size());
    }

    public ArrayList<DetalleXML> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(ArrayList<DetalleXML> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    private ArrayList<AutorizacionXML> listaAutorizacion = new ArrayList();

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

    public void upload() {
        if (file != null && !"".equals(file.getFileName())) {
            File f;

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputstream(), "UTF-8"))) {
                f = new File("E:\\Danny\\Documentos\\factura.xml");

                try {
                    FileWriter w = new FileWriter(f);
                    BufferedWriter bw = new BufferedWriter(w);
                    PrintWriter wr = new PrintWriter(bw);
                    wr.write("");//escribimos en el archivo  

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                        wr.append(line); //concatenamos en el archivo sin borrar lo existente             
                    }
                    wr.close();
                    bw.close();

                } catch (IOException e) {
                }
                AutorizacionXML obj;
                obj = LeerArchivo(f);
                f.delete();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public void capturarDatos(File file) {
        AutorizacionXML obj;
        try {
            XStream xstream = new XStream();

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

            for (int k = 0; k < obj.getComprobante().getFactura().getDetalles().size(); k++) {
                DetalleXML det;
                System.out.println("Prod:" + obj.getComprobante().getFactura().getDetalles().get(k).getDescripcion());
                det = obj.getComprobante().getFactura().getDetalles().get(k);
                //  if(obj.getComprobante().getFactura().getDetalles().get(k).getDescripcion().contains("LECHE"))
                listaDetalles.add(det);
               
            }
            listaAutorizacion.add(obj);
            if (obj != null) {
                facturaServicio.guardarFactura(obj);
            }
        } catch (Exception e) {
            System.out.println("");

        }

    }

    public AutorizacionXML LeerArchivo(File file) {

        AutorizacionXML aut = new AutorizacionXML();
        try {
            XStream xstream = new XStream();

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

            aut = (AutorizacionXML) xstream.fromXML(file);
        } catch (Exception e) {
            System.out.println(e);

        }
        return aut;

    }

}
