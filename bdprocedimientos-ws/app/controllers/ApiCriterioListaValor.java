package controllers;

import models.CriterioListaValor;
import models.TipoCriterio;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.Controller;

public class ApiCriterioListaValor extends Controller {

	public static void all(Long idTipoEvaluacion, Long idTipoCriterio){
		JPAQuery query = CriterioListaValor.find("select v from CriterioListaValor v where v.tipoCriterio.id = ? and v.tipoCriterio.tipoEvaluacion.id = ?", idTipoCriterio, idTipoEvaluacion);
		ApiControllerMethods.all(query);
	}

	static CriterioListaValor find(Long idTipoEvaluacion, Long idTipoCriterio, Long id){
		ApiControllerMethods.checkValidationErrors();
		return (CriterioListaValor)CriterioListaValor.find("select v from CriterioListaValor v where v.id = ? and v.tipoCriterio.id = ? and v.tipoCriterio.tipoEvaluacion.id = ?", id, idTipoCriterio, idTipoEvaluacion).first();		
	}
	
	public static void get(Long idTipoEvaluacion, Long idTipoCriterio, Long id){
		ApiControllerMethods.get(find(idTipoEvaluacion, idTipoCriterio, id));
	}
	
	public static void delete(Long idTipoEvaluacion, Long idTipoCriterio, Long id){
		ApiControllerMethods.delete(find(idTipoEvaluacion, idTipoCriterio, id));
	}
	
	public static void post(Long idTipoEvaluacion, Long idTipoCriterio, String body){
		ApiControllerMethods.checkValidationErrors();
		CriterioListaValor criterioListaValor = ApiControllerMethods.deserialize(body, CriterioListaValor.class);
		TipoCriterio tipoCriterio = CriterioListaValor.find("select t from TipoCriterio t where t.id = ? and t.tipoEvaluacion.id = ?", idTipoCriterio, idTipoEvaluacion).first();
		ApiControllerMethods.notFoundIfNull(tipoCriterio);
		criterioListaValor.tipoCriterio = tipoCriterio;
		ApiControllerMethods.post(criterioListaValor);
	}
	
	public static void put(Long idTipoEvaluacion, Long idTipoCriterio, Long id, String body){
		ApiControllerMethods.checkValidationErrors();
		CriterioListaValor criterioListaValor = find(idTipoEvaluacion, idTipoCriterio, id);
		ApiControllerMethods.put(criterioListaValor, body);
	}
	
}
