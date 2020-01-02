package ec.edu.espe.tesis.facturas.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ControlPrecios.class)
public abstract class ControlPrecios_ {

	public static volatile SingularAttribute<ControlPrecios, Integer> codigo;
	public static volatile SingularAttribute<ControlPrecios, BigDecimal> precio;
	public static volatile SingularAttribute<ControlPrecios, BigDecimal> descuento;
	public static volatile SingularAttribute<ControlPrecios, BigDecimal> preciounitario;
	public static volatile SingularAttribute<ControlPrecios, Producto> proCodigo;
	public static volatile SingularAttribute<ControlPrecios, Factura> facCodigo;

}

