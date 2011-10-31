package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MaxLength;

import enums.ClaseCEconomico;

import play.data.validation.Match;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class TipoCEconomico extends Model {

	@ManyToOne
	public TipoEvaluacion tipoEvaluacion;
	
	@Required
	@Length(max=400)
	@Column(length=400)
	public String nombre;

	@Length(max=3000)
	@Column(columnDefinition="LONGTEXT")
	public String descripcion;
	
	@Length(max=3000)
	@Column(columnDefinition="LONGTEXT")
	public String descripcionSolicitante;
	
	@Required
	@Enumerated(EnumType.STRING)
	public ClaseCEconomico clase;
	
	@Required
	@Match("\\d+(\\.\\d+)*")
	@MaxLength(255)
	public String jerarquia;
	
	public boolean comentariosAdministracion;
	
	public boolean comentariosSolicitante;
}
