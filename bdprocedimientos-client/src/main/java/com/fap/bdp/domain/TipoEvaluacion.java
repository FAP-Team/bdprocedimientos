package com.fap.bdp.domain;

import java.util.ArrayList;
import java.util.List;

public class TipoEvaluacion{
	
	private Long id;
	
	private String nombre;
	
	private String tipoProcedimiento;
	
	private List<TipoDocumentoAccesible> tiposDocumentosAccesibles;
	
	private List<TipoCriterio> tiposCriterios;
	
	private List<TipoCEconomico> tiposCEconomicos;
	
	private boolean comentariosAdministracion;
	
	private boolean comentariosSolicitante;
	
	private int numeroEvaluaciones = 1;
	
	public TipoEvaluacion(){
		tiposDocumentosAccesibles = new ArrayList<TipoDocumentoAccesible>();
		tiposCriterios = new ArrayList<TipoCriterio>();
		tiposCEconomicos = new ArrayList<TipoCEconomico>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoProcedimiento() {
		return tipoProcedimiento;
	}

	public void setTipoProcedimiento(String tipoProcedimiento) {
		this.tipoProcedimiento = tipoProcedimiento;
	}

	public List<TipoDocumentoAccesible> getTiposDocumentosAccesibles() {
		return tiposDocumentosAccesibles;
	}

	public void setTiposDocumentosAccesibles(
			List<TipoDocumentoAccesible> tiposDocumentosAccesibles) {
		this.tiposDocumentosAccesibles = tiposDocumentosAccesibles;
	}

	public List<TipoCriterio> getTiposCriterios() {
		return tiposCriterios;
	}

	public void setTiposCriterios(List<TipoCriterio> tiposCriterios) {
		this.tiposCriterios = tiposCriterios;
	}

	public List<TipoCEconomico> getTiposCEconomicos() {
		return tiposCEconomicos;
	}

	public void setTiposCEconomicos(List<TipoCEconomico> tiposCEconomicos) {
		this.tiposCEconomicos = tiposCEconomicos;
	}

	public boolean isComentariosAdministracion() {
		return comentariosAdministracion;
	}

	public void setComentariosAdministracion(boolean comentariosAdministracion) {
		this.comentariosAdministracion = comentariosAdministracion;
	}

	public boolean isComentariosSolicitante() {
		return comentariosSolicitante;
	}

	public void setComentariosSolicitante(boolean comentariosSolicitante) {
		this.comentariosSolicitante = comentariosSolicitante;
	}

	public int getNumeroEvaluaciones() {
		return numeroEvaluaciones;
	}

	public void setNumeroEvaluaciones(int numeroEvaluaciones) {
		this.numeroEvaluaciones = numeroEvaluaciones;
	}

}
