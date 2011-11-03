package com.fap.bdp.domain;

import java.util.ArrayList;
import java.util.List;

public class TipoCriterio {

	public Long id;

	public String nombre;

	public String descripcion;

	public String instrucciones;

	public ClaseCriterio clase;

	public String jerarquia;

	public int precision = 0;

	public Double valorMaximo;

	public Double valorMinimoCorte;

	public TipoValorCriterio tipoValor;

	public List<CriterioListaValor> listaValores;

	public Integer transparencia;

	public boolean comentariosAdministracion;

	public boolean comentariosSolicitante;

	public TipoCriterio() {
		listaValores = new ArrayList<CriterioListaValor>();
	}
}
