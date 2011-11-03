package com.fap.bdp.domain;

import java.util.ArrayList;
import java.util.List;

public class TipoCriterio {
	
	private Long id;

	private String nombre;

	private String descripcion;

	private String instrucciones;

	private ClaseCriterio clase;

	private String jerarquia;

	private int precision = 0;

	private Double valorMaximo;

	private Double valorMinimoCorte;

	private TipoValorCriterio tipoValor;

	private List<CriterioListaValor> listaValores;

	private Integer transparencia;

	private boolean comentariosAdministracion;

	private boolean comentariosSolicitante;

	public TipoCriterio() {
		listaValores = new ArrayList<CriterioListaValor>();
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}

	public ClaseCriterio getClase() {
		return clase;
	}

	public void setClase(ClaseCriterio clase) {
		this.clase = clase;
	}

	public String getJerarquia() {
		return jerarquia;
	}

	public void setJerarquia(String jerarquia) {
		this.jerarquia = jerarquia;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public Double getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(Double valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public Double getValorMinimoCorte() {
		return valorMinimoCorte;
	}

	public void setValorMinimoCorte(Double valorMinimoCorte) {
		this.valorMinimoCorte = valorMinimoCorte;
	}

	public TipoValorCriterio getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(TipoValorCriterio tipoValor) {
		this.tipoValor = tipoValor;
	}

	public List<CriterioListaValor> getListaValores() {
		return listaValores;
	}

	public void setListaValores(List<CriterioListaValor> listaValores) {
		this.listaValores = listaValores;
	}

	public Integer getTransparencia() {
		return transparencia;
	}

	public void setTransparencia(Integer transparencia) {
		this.transparencia = transparencia;
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
}
