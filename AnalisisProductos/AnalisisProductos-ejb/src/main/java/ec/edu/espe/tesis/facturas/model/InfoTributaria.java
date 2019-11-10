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
@Table(name = "info_tributaria")
@NamedQueries({
    @NamedQuery(name = "InfoTributaria.findAll", query = "SELECT i FROM InfoTributaria i")})
public class InfoTributaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private Integer codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "RUC")
    private String ruc;
    @Size(max = 200)
    @Column(name = "RAZONSOCIAL")
    private String razonsocial;
    @Size(max = 50)
    @Column(name = "SECUENCIAL")
    private String secuencial;
    @Size(max = 120)
    @Column(name = "ESTABLECIMIENTO")
    private String establecimiento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "infCodigo", fetch = FetchType.LAZY)
    private List<Factura> facturaList;

    public InfoTributaria() {
    }

    public InfoTributaria(Integer codigo) {
        this.codigo = codigo;
    }

    public InfoTributaria(Integer codigo, String ruc) {
        this.codigo = codigo;
        this.ruc = ruc;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(String secuencial) {
        this.secuencial = secuencial;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
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
        if (!(object instanceof InfoTributaria)) {
            return false;
        }
        InfoTributaria other = (InfoTributaria) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.tesis.facturas.model.InfoTributaria[ codigo=" + codigo + " ]";
    }
    
}
