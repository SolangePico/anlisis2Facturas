/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.facturas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author solan
 */
@Entity
@Table(name = "factura")
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private Integer codigo;
    @Size(max = 49)
    @Column(name = "NUMEROAUTORIZACION")
    private String numeroautorizacion;
    @Column(name = "FECHAAUTORI")
    @Temporal(TemporalType.DATE)
    private Date fechaautori;
    @Column(name = "FECHAEMISION")
    @Temporal(TemporalType.DATE)
    private Date fechaemision;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTALSINIMPUESTO")
    private BigDecimal totalsinimpuesto;
    @Column(name = "TOTALDESCUENTO")
    private BigDecimal totaldescuento;
    @Column(name = "IMPORTETOTAL")
    private BigDecimal importetotal;
    @JoinColumn(name = "USU_CODIGO", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuCodigo;
    @JoinColumn(name = "INF_CODIGO", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private InfoTributaria infCodigo;
    @OneToMany(mappedBy = "facCodigo", fetch = FetchType.LAZY)
    private List<ControlPrecios> controlPreciosList;
    @OneToMany(mappedBy = "facCodigo", fetch = FetchType.LAZY)
    private List<TotalImpuesto> totalImpuestoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facCodigo", fetch = FetchType.EAGER)
    private List<DetalleFactura> detalleFacturaList;

    public Factura() {
    }

    public Factura(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNumeroautorizacion() {
        return numeroautorizacion;
    }

    public void setNumeroautorizacion(String numeroautorizacion) {
        this.numeroautorizacion = numeroautorizacion;
    }

    public Date getFechaautori() {
        return fechaautori;
    }

    public void setFechaautori(Date fechaautori) {
        this.fechaautori = fechaautori;
    }

    public Date getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(Date fechaemision) {
        this.fechaemision = fechaemision;
    }

    public BigDecimal getTotalsinimpuesto() {
        return totalsinimpuesto;
    }

    public void setTotalsinimpuesto(BigDecimal totalsinimpuesto) {
        this.totalsinimpuesto = totalsinimpuesto;
    }

    public BigDecimal getTotaldescuento() {
        return totaldescuento;
    }

    public void setTotaldescuento(BigDecimal totaldescuento) {
        this.totaldescuento = totaldescuento;
    }

    public BigDecimal getImportetotal() {
        return importetotal;
    }

    public void setImportetotal(BigDecimal importetotal) {
        this.importetotal = importetotal;
    }

    public Usuario getUsuCodigo() {
        return usuCodigo;
    }

    public void setUsuCodigo(Usuario usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public InfoTributaria getInfCodigo() {
        return infCodigo;
    }

    public void setInfCodigo(InfoTributaria infCodigo) {
        this.infCodigo = infCodigo;
    }

    public List<ControlPrecios> getControlPreciosList() {
        return controlPreciosList;
    }

    public void setControlPreciosList(List<ControlPrecios> controlPreciosList) {
        this.controlPreciosList = controlPreciosList;
    }

    public List<TotalImpuesto> getTotalImpuestoList() {
        return totalImpuestoList;
    }

    public void setTotalImpuestoList(List<TotalImpuesto> totalImpuestoList) {
        this.totalImpuestoList = totalImpuestoList;
    }

    public List<DetalleFactura> getDetalleFacturaList() {
        return detalleFacturaList;
    }

    public void setDetalleFacturaList(List<DetalleFactura> detalleFacturaList) {
        this.detalleFacturaList = detalleFacturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.tesis.facturas.model.Factura[ codigo=" + codigo + " ]";
    }
    
}
