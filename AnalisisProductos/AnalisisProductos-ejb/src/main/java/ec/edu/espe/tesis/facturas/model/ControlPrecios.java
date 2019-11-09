/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.facturas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author solan
 */
@Entity
@Table(name = "control_precios")
@NamedQueries({
    @NamedQuery(name = "ControlPrecios.findAll", query = "SELECT c FROM ControlPrecios c")
    , @NamedQuery(name = "ControlPrecios.findByCodigo", query = "SELECT c FROM ControlPrecios c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "ControlPrecios.findByPreciounitario", query = "SELECT c FROM ControlPrecios c WHERE c.preciounitario = :preciounitario")
    , @NamedQuery(name = "ControlPrecios.findByDescuento", query = "SELECT c FROM ControlPrecios c WHERE c.descuento = :descuento")
    , @NamedQuery(name = "ControlPrecios.findByPrecio", query = "SELECT c FROM ControlPrecios c WHERE c.precio = :precio")})
public class ControlPrecios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private Integer codigo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIOUNITARIO")
    private BigDecimal preciounitario;
    @Column(name = "DESCUENTO")
    private BigDecimal descuento;
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @JoinColumn(name = "FAC_CODIGO", referencedColumnName = "CODIGO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Factura facCodigo;
    @JoinColumn(name = "PRO_CODIGO", referencedColumnName = "CODIGO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Producto proCodigo;

    public ControlPrecios() {
    }

    public ControlPrecios(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(BigDecimal preciounitario) {
        this.preciounitario = preciounitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Factura getFacCodigo() {
        return facCodigo;
    }

    public void setFacCodigo(Factura facCodigo) {
        this.facCodigo = facCodigo;
    }

    public Producto getProCodigo() {
        return proCodigo;
    }

    public void setProCodigo(Producto proCodigo) {
        this.proCodigo = proCodigo;
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
        if (!(object instanceof ControlPrecios)) {
            return false;
        }
        ControlPrecios other = (ControlPrecios) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.tesis.facturas.model.ControlPrecios[ codigo=" + codigo + " ]";
    }
    
}
