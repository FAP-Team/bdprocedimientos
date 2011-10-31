package com.fap.bdp.domain;



public class CriterioListaValor{

	public Long id;
	
	public Double valor;
	
	public String descripcion;

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
