/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.facturas.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author solan
 */
@Entity
@Table(name = "producto")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findByCodigo", query = "SELECT p FROM Producto p WHERE p.codigo = :codigo")
    , @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Producto.findByCodigoprincipal", query = "SELECT p FROM Producto p WHERE p.codigoprincipal = :codigoprincipal")
    , @NamedQuery(name = "Producto.findByCodigoauxiliar", query = "SELECT p FROM Producto p WHERE p.codigoauxiliar = :codigoauxiliar")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private Integer codigo;
    @Size(max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "CODIGOPRINCIPAL")
    private String codigoprincipal;
    @Size(max = 255)
    @Column(name = "CODIGOAUXILIAR")
    private String codigoauxiliar;
    @OneToMany(mappedBy = "proCodigo", fetch = FetchType.LAZY)
    private List<ControlPrecios> controlPreciosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proCodigo", fetch = FetchType.LAZY)
    private List<DetalleFactura> detalleFacturaList;

    public Producto() {
    }

    public Producto(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoprincipal() {
        return codigoprincipal;
    }

    public void setCodigoprincipal(String codigoprincipal) {
        this.codigoprincipal = codigoprincipal;
    }

    public String getCodigoauxiliar() {
        return codigoauxiliar;
    }

    public void setCodigoauxiliar(String codigoauxiliar) {
        this.codigoauxiliar = codigoauxiliar;
    }

    public List<ControlPrecios> getControlPreciosList() {
        return controlPreciosList;
    }

    public void setControlPreciosList(List<ControlPrecios> controlPreciosList) {
        this.controlPreciosList = controlPreciosList;
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
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.tesis.facturas.model.Producto[ codigo=" + codigo + " ]";
    }
    
}
