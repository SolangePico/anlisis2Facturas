/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.facturas.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author solan
 */
@Entity
@Table(name = "registro_usuario")
@NamedQueries({
    @NamedQuery(name = "RegistroUsuario.findAll", query = "SELECT r FROM RegistroUsuario r")
    , @NamedQuery(name = "RegistroUsuario.findByCodigo", query = "SELECT r FROM RegistroUsuario r WHERE r.codigo = :codigo")
    , @NamedQuery(name = "RegistroUsuario.findByFechaultimoingreso", query = "SELECT r FROM RegistroUsuario r WHERE r.fechaultimoingreso = :fechaultimoingreso")})
public class RegistroUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private Integer codigo;
    @Column(name = "FECHAULTIMOINGRESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaultimoingreso;
    @JoinColumn(name = "USU_CODIGO", referencedColumnName = "CODIGO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuCodigo;
    @JoinColumn(name = "FAC_CODIGO", referencedColumnName = "CODIGO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Factura facCodigo;

    public RegistroUsuario() {
    }

    public RegistroUsuario(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFechaultimoingreso() {
        return fechaultimoingreso;
    }

    public void setFechaultimoingreso(Date fechaultimoingreso) {
        this.fechaultimoingreso = fechaultimoingreso;
    }

    public Usuario getUsuCodigo() {
        return usuCodigo;
    }

    public void setUsuCodigo(Usuario usuCodigo) {
        this.usuCodigo = usuCodigo;
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
        if (!(object instanceof RegistroUsuario)) {
            return false;
        }
        RegistroUsuario other = (RegistroUsuario) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.tesis.facturas.model.RegistroUsuario[ codigo=" + codigo + " ]";
    }
    
}
