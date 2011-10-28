package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class TipoEvaluacion extends Model {
	@Required
	public String nombre;
	
	@Required
	public String tipoProcedimiento;
	
	@OneToMany(mappedBy="tipoEvaluacion", cascade=CascadeType.REMOVE)
	public List<TipoDocumentoAccesible> tiposDocumentosAccesibles;
	
	@OneToMany(mappedBy="tipoEvaluacion", cascade=CascadeType.REMOVE)
	public List<TipoCriterio> tiposCriterios;
	
	@OneToMany(mappedBy="tipoEvaluacion", cascade=CascadeType.REMOVE)
	public List<TipoCEconomico> tiposCEconomicos;
	
	@Required
	public boolean comentariosAdministracion;
	
	@Required
	public boolean comentariosSolicitante;
	
	public int numeroEvaluaciones = 1;
	
	public TipoEvaluacion(){
		tiposDocumentosAccesibles = new ArrayList<TipoDocumentoAccesible>();
		tiposCriterios = new ArrayList<TipoCriterio>();
		tiposCEconomicos = new ArrayList<TipoCEconomico>();
	}
	
}
