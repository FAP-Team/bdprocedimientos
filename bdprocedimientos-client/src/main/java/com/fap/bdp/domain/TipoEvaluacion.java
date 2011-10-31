package com.fap.bdp.domain;

import java.util.ArrayList;
import java.util.List;

public class TipoEvaluacion{
	
	public Long id;
	
	public String nombre;
	
	public String tipoProcedimiento;
	
	public List<TipoDocumentoAccesible> tiposDocumentosAccesibles;
	
	public List<TipoCriterio> tiposCriterios;
	
	public List<TipoCEconomico> tiposCEconomicos;
	
	public boolean comentariosAdministracion;
	
	public boolean comentariosSolicitante;
	
	public int numeroEvaluaciones = 1;
	
	public TipoEvaluacion(){
		tiposDocumentosAccesibles = new ArrayList<TipoDocumentoAccesible>();
		tiposCriterios = new ArrayList<TipoCriterio>();
		tiposCEconomicos = new ArrayList<TipoCEconomico>();
	}
	
}
