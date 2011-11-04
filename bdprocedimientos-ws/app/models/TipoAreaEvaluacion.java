package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import net.sf.oval.constraint.MaxLength;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class TipoAreaEvaluacion extends Model {

	@Required
	@MaxLength(300)
	@Column(length=300)
	public String codigo;
	
	@Required
	@MaxLength(3000)
	@Column(length=3000)
	public String descripcion;
	
}
