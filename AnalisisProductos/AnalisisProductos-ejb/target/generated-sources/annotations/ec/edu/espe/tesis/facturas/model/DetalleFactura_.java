package ec.edu.espe.tesis.facturas.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DetalleFactura.class)
public abstract class DetalleFactura_ {

	public static volatile SingularAttribute<DetalleFactura, Integer> codigo;
	public static volatile ListAttribute<DetalleFactura, Impuesto> impuestoList;
	public static volatile SingularAttribute<DetalleFactura, BigDecimal> cantidad;
	public static volatile SingularAttribute<DetalleFactura, Producto> proCodigo;
	public static volatile SingularAttribute<DetalleFactura, Factura> facCodigo;

}

