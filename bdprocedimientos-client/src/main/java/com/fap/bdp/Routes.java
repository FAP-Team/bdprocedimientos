package com.fap.bdp;

public class Routes {

	public static String getUrlTiposEvaluaciones(){
		return "/tiposevaluaciones";
	}
	
	public static String getUrlTiposEvaluaciones(Long idTipoEvaluacion){
		return getUrlTiposEvaluaciones() + "/" + idTipoEvaluacion;
	}
	
	public static String getUrlTiposCriterios(Long idTipoEvaluacion){
		return getUrlTiposEvaluaciones(idTipoEvaluacion) + "/tiposcriterios";
	}
	
	public static String getUrlTiposCriterios(Long idTipoEvaluacion, Long idTipoCriterio){
		return getUrlTiposCriterios(idTipoEvaluacion) + "/" + idTipoCriterio;
	}
	
	public static String getUrlCriterioListaValor(Long idTipoEvaluacion, Long idTipoCriterio){
		return getUrlTiposCriterios(idTipoEvaluacion, idTipoCriterio) + "/listavalores";
	}
	
	public static String getUrlCriterioListaValor(Long idTipoEvaluacion, Long idTipoCriterio, Long idListaValor){
		return getUrlCriterioListaValor(idTipoEvaluacion, idTipoCriterio) + "/" + idListaValor;
	}
	
	public static String getUrlTiposCEconomicos(Long idTipoEvaluacion){
		return getUrlTiposEvaluaciones(idTipoEvaluacion) + "/tiposceconomicos";
	}
	
	public static String getUrlTiposCEconomicos(Long idTipoEvaluacion, Long idTipoCEconomico){
		return getUrlTiposCEconomicos(idTipoEvaluacion) + "/" + idTipoCEconomico;
	}
	
	public static String getUrlTiposDocumentosAccesibles(Long idTipoEvaluacion){
		return getUrlTiposEvaluaciones(idTipoEvaluacion) + "/tiposdocumentosaccesibles";
	}
	
	public static String getUrlTiposDocumentosAccesibles(Long idTipoEvaluacion, Long idTipoDocumentoAccesible){
		return getUrlTiposDocumentosAccesibles(idTipoEvaluacion) + "/" + idTipoDocumentoAccesible;
	}
}
