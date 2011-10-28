package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import net.sf.oval.constraint.Length;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class CriterioListaValor extends Model {
	@Required
	public Double valor;
	
	@Required
	@Length(max=400)
	@Column(length=400)
	public String descripcion;

	@ManyToOne
	public TipoCriterio tipoCriterio;
	
	public CriterioListaValor() {
		super();
	}

	public CriterioListaValor(Double valor, String descripcion,
			TipoCriterio tipoCriterio) {
		super();
		this.valor = valor;
		this.descripcion = descripcion;
		this.tipoCriterio = tipoCriterio;
	}
	
}
