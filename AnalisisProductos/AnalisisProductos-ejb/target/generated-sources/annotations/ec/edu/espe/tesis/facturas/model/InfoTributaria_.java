package ec.edu.espe.tesis.facturas.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InfoTributaria.class)
public abstract class InfoTributaria_ {

	public static volatile SingularAttribute<InfoTributaria, String> ruc;
	public static volatile SingularAttribute<InfoTributaria, Integer> codigo;
	public static volatile SingularAttribute<InfoTributaria, String> establecimiento;
	public static volatile SingularAttribute<InfoTributaria, String> razonsocial;
	public static volatile SingularAttribute<InfoTributaria, String> direccion;
	public static volatile ListAttribute<InfoTributaria, Factura> facturaList;
	public static volatile SingularAttribute<InfoTributaria, String> secuencial;

}

