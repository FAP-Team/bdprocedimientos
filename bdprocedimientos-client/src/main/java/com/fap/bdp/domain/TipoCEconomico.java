package com.fap.bdp.domain;



public class TipoCEconomico{

	private Long id;
		
	private String nombre;

	private String descripcion;

	private String descripcionSolicitante;
	
	private ClaseCEconomico clase;
	
	private String jerarquia;
	
	private boolean comentariosAdministracion;
	
	private boolean comentariosSolicitante;

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

	public String getDescripcionSolicitante() {
		return descripcionSolicitante;
	}

	public void setDescripcionSolicitante(String descripcionSolicitante) {
		this.descripcionSolicitante = descripcionSolicitante;
	}

	public ClaseCEconomico getClase() {
		return clase;
	}

	public void setClase(ClaseCEconomico clase) {
		this.clase = clase;
	}

	public String getJerarquia() {
		return jerarquia;
	}

	public void setJerarquia(String jerarquia) {
		this.jerarquia = jerarquia;
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
