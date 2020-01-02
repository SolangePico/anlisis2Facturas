package ec.edu.espe.tesis.facturas.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TotalImpuesto.class)
public abstract class TotalImpuesto_ {

	public static volatile SingularAttribute<TotalImpuesto, String> codigo;
	public static volatile SingularAttribute<TotalImpuesto, BigDecimal> descuento;
	public static volatile SingularAttribute<TotalImpuesto, BigDecimal> baseimponible;
	public static volatile SingularAttribute<TotalImpuesto, Factura> facCodigo;

}

