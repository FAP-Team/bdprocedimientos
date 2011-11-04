package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import net.sf.oval.constraint.MaxLength;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class TipoDocumentoAccesible extends Model {

	@Required
	@MaxLength(1000)
	@Column(length=1000)
	public String uri;
	
	@ManyToOne
	public TipoEvaluacion tipoEvaluacion;
	
}
