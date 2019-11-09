/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.facturas.model;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

/**
 *
 * @author solan
 */
@Entity
@Table(name = "factura")
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f")
    , @NamedQuery(name = "Factura.findByCodigo", query = "SELECT f FROM Factura f WHERE f.codigo = :codigo")
    , @NamedQuery(name = "Factura.findByNumeroautorizacion", query = "SELECT f FROM Factura f WHERE f.numeroautorizacion = :numeroautorizacion")
    , @NamedQuery(name = "Factura.findByFechaautori", query = "SELECT f FROM Factura f WHERE f.fechaautori = :fechaautori")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private Integer codigo;
    @Size(max = 49)
    @Column(name = "NUMEROAUTORIZACION")
    private String numeroautorizacion;
    @Column(name = "FECHAAUTORI")
    @Temporal(TemporalType.DATE)
    private Date fechaautori;
    @OneToMany(mappedBy = "facCodigo", fetch = FetchType.LAZY)
    private List<RegistroUsuario> registroUsuarioList;
    @JoinColumn(name = "INF_CODIGO2", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private InfoFactura infCodigo2;
    @JoinColumn(name = "INF_CODIGO", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private InfoTributaria infCodigo;
    @OneToMany(mappedBy = "facCodigo", fetch = FetchType.LAZY)
    private List<ControlPrecios> controlPreciosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facCodigo", fetch = FetchType.LAZY)
    private List<DetalleFactura> detalleFacturaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facCodigo", fetch = FetchType.LAZY)
    private List<InfoFactura> infoFacturaList;

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

    public List<RegistroUsuario> getRegistroUsuarioList() {
        return registroUsuarioList;
    }

    public void setRegistroUsuarioList(List<RegistroUsuario> registroUsuarioList) {
        this.registroUsuarioList = registroUsuarioList;
    }

    public InfoFactura getInfCodigo2() {
        return infCodigo2;
    }

    public void setInfCodigo2(InfoFactura infCodigo2) {
        this.infCodigo2 = infCodigo2;
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

    public List<DetalleFactura> getDetalleFacturaList() {
        return detalleFacturaList;
    }

    public void setDetalleFacturaList(List<DetalleFactura> detalleFacturaList) {
        this.detalleFacturaList = detalleFacturaList;
    }

    public List<InfoFactura> getInfoFacturaList() {
        return infoFacturaList;
    }

    public void setInfoFacturaList(List<InfoFactura> infoFacturaList) {
        this.infoFacturaList = infoFacturaList;
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
