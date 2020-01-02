package ec.edu.espe.tesis.facturas.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Factura.class)
public abstract class Factura_ {

	public static volatile SingularAttribute<Factura, Date> fechaautori;
	public static volatile SingularAttribute<Factura, BigDecimal> totalsinimpuesto;
	public static volatile SingularAttribute<Factura, Integer> codigo;
	public static volatile SingularAttribute<Factura, Date> fechaemision;
	public static volatile SingularAttribute<Factura, BigDecimal> totaldescuento;
	public static volatile SingularAttribute<Factura, InfoTributaria> infCodigo;
	public static volatile SingularAttribute<Factura, Usuario> usuCodigo;
	public static volatile SingularAttribute<Factura, BigDecimal> importetotal;
	public static volatile ListAttribute<Factura, DetalleFactura> detalleFacturaList;
	public static volatile SingularAttribute<Factura, String> numeroautorizacion;
	public static volatile ListAttribute<Factura, TotalImpuesto> totalImpuestoList;
	public static volatile ListAttribute<Factura, ControlPrecios> controlPreciosList;

}

