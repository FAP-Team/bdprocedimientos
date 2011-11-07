package com.fap.bdp.domain;



public class CriterioListaValor{

	private Long id;
	
	private Double valor;
	
	private String descripcion;

	private TipoCriterio tipoCriterio;
	
	public CriterioListaValor() {
		super();
	}
	
	public Long getId (){
		return id;
	}
	
	public Double getValor(){
		return valor;
	}
	
	public String getDescripcion(){
		return descripcion;
	}
	
	public TipoCriterio getTipoCriterio(){
		return tipoCriterio;
	}
	
	public void setId (Long id){
		this.id = id;
	}
	
	public void setValor(Double valor){
		this.valor = valor;
	}
	
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
	
	public void getTipoCriterio(TipoCriterio tipoCriterio){
		this.tipoCriterio = tipoCriterio;
	}

	public CriterioListaValor(Double valor, String descripcion,
			TipoCriterio tipoCriterio) {
		super();
		this.valor = valor;
		this.descripcion = descripcion;
		this.tipoCriterio = tipoCriterio;
	}
	
}
