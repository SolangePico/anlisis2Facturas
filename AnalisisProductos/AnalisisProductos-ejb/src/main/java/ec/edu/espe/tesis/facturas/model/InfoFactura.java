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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author solan
 */
@Entity
@Table(name = "info_factura")
@NamedQueries({
    @NamedQuery(name = "InfoFactura.findAll", query = "SELECT i FROM InfoFactura i")})
public class InfoFactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private Integer codigo;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "infCodigo2", fetch = FetchType.LAZY)
    private List<Factura> facturaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "infCodigo", fetch = FetchType.LAZY)
    private List<TotalImpuesto> totalImpuestoList;
    @JoinColumn(name = "FAC_CODIGO", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Factura facCodigo;

    public InfoFactura() {
    }

    public InfoFactura(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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

    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
    }

    public List<TotalImpuesto> getTotalImpuestoList() {
        return totalImpuestoList;
    }

    public void setTotalImpuestoList(List<TotalImpuesto> totalImpuestoList) {
        this.totalImpuestoList = totalImpuestoList;
    }

    public Factura getFacCodigo() {
        return facCodigo;
    }

    public void setFacCodigo(Factura facCodigo) {
        this.facCodigo = facCodigo;
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
        if (!(object instanceof InfoFactura)) {
            return false;
        }
        InfoFactura other = (InfoFactura) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.tesis.facturas.model.InfoFactura[ codigo=" + codigo + " ]";
    }
    
}
