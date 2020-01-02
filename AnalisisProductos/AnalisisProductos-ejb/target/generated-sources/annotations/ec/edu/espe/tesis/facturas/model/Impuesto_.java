package ec.edu.espe.tesis.facturas.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Impuesto.class)
public abstract class Impuesto_ {

	public static volatile SingularAttribute<Impuesto, String> tarifa;
	public static volatile SingularAttribute<Impuesto, String> codigo;
	public static volatile SingularAttribute<Impuesto, DetalleFactura> detCodigo;

}

