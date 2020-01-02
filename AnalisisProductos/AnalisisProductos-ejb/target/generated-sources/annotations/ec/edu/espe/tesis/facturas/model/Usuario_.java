package ec.edu.espe.tesis.facturas.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usuario.class)
public abstract class Usuario_ {

	public static volatile SingularAttribute<Usuario, Integer> codigo;
	public static volatile SingularAttribute<Usuario, String> clave;
	public static volatile SingularAttribute<Usuario, Character> estado;
	public static volatile SingularAttribute<Usuario, Date> fechacreacion;
	public static volatile SingularAttribute<Usuario, String> correo;
	public static volatile SingularAttribute<Usuario, Date> fechaingreso;
	public static volatile ListAttribute<Usuario, Factura> facturaList;

}

