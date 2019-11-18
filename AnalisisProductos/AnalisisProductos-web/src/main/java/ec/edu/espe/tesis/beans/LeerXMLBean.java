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
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
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

    private UploadedFile file;
    private FacturaXML factura;
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

    public void fileUploadListener(FileUploadEvent e) {
        File f;
        this.file = e.getFile();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputstream(), "UTF-8"))) {
            f = new File("C:\\Users\\alterbios\\Downloads\\factura.xml");

            try {
                FileWriter w = new FileWriter(f);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);
                wr.write("");//escribimos en el archivo  

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                 //   System.out.println(line);
                    wr.append(line); //concatenamos en el archivo sin borrar lo existente             
                }
                wr.close();
                bw.close();

            } catch (IOException p) {
                System.out.println(" error de leer -----------   " + e);
            }
            capturarDatos(f);
            f.delete();
        } catch (Exception ex) {
            System.out.println(" error    " + e);
        }
    }

    public void capturarDatos(File file) {
        AutorizacionXML obj;
        try {
            XStream xstream = new XStream();
            
            xstream.allowTypesByRegExp(new String[] { ".*" });
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
            System.out.println(" ---  "+obj.getNumeroAutorizacion());
            if (obj != null) {
                facturaServicio.guardarFactura(obj, "0");
            } else {

            }
        } catch (Exception e) {
            System.out.println("");

        }
    }
}
