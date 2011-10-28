package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import enums.ClaseCriterio;
import enums.TipoValorCriterio;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MaxLength;
import play.data.validation.Match;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class TipoCriterio extends Model{
	
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
	public String instrucciones;
	
	@Required
	@Enumerated(EnumType.STRING)
	public ClaseCriterio clase;
	
	@Required
	@Match("\\d+(\\.\\d+)*")
	@MaxLength(255)
	public String jerarquia;
	
	@Column(name="valor_precisio")
	public int precision = 0;
	
	public double valorMaximo;
	
	public double valorMinimoCorte;
	
	@Required
	@Enumerated(EnumType.STRING)
	public TipoValorCriterio tipoValor;
	
	@OneToMany(mappedBy="tipoCriterio", cascade=CascadeType.ALL)
	public List<CriterioListaValor> listaValores;
	
	@Required
	public int transparencia;
	
	@Required
	public boolean comentariosAdministracion;
	
	@Required
	public boolean comentariosSolicitante;
	
	public TipoCriterio(){
		listaValores = new ArrayList<CriterioListaValor>();
	}
}
