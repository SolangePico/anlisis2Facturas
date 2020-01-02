package ec.edu.espe.tesis.facturas.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Producto.class)
public abstract class Producto_ {

	public static volatile SingularAttribute<Producto, String> descripcion;
	public static volatile SingularAttribute<Producto, Integer> codigo;
	public static volatile SingularAttribute<Producto, BigDecimal> total;
	public static volatile SingularAttribute<Producto, String> codigoauxiliar;
	public static volatile ListAttribute<Producto, DetalleFactura> detalleFacturaList;
	public static volatile SingularAttribute<Producto, String> codigoprincipal;
	public static volatile ListAttribute<Producto, ControlPrecios> controlPreciosList;

}

