package controllers;

import models.TipoCriterio;
import models.TipoEvaluacion;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.Controller;

public class ApiTipoCriterio extends Controller {

	public static void all(Long idTipoEvaluacion){
		JPAQuery query = TipoCriterio.find("select t from TipoCriterio t where t.tipoEvaluacion.id = ?", idTipoEvaluacion);
		ApiControllerMethods.all(query);
	}

	static TipoCriterio find(Long idTipoEvaluacion, Long id){
		ApiControllerMethods.checkValidationErrors();
		return (TipoCriterio)TipoCriterio.find("select t from TipoCriterio t where t.id = ? and t.tipoEvaluacion.id = ?", id, idTipoEvaluacion).first();		
	}
	
	public static void get(Long idTipoEvaluacion, Long id){
		ApiControllerMethods.get(find(idTipoEvaluacion, id));
	}
	
	public static void delete(Long idTipoEvaluacion, Long id){
		ApiControllerMethods.delete(find(idTipoEvaluacion, id));
	}
	
	public static void post(Long idTipoEvaluacion, String body){
		ApiControllerMethods.checkValidationErrors();
		TipoCriterio tipoCriterio = ApiControllerMethods.deserialize(body, TipoCriterio.class);
		TipoEvaluacion tipoEvaluacion = TipoEvaluacion.findById(idTipoEvaluacion);
		ApiControllerMethods.notFoundIfNull(tipoEvaluacion);
		tipoCriterio.tipoEvaluacion = tipoEvaluacion;
		ApiControllerMethods.post(tipoCriterio);
	}
	
	public static void put(Long idEvaluacion, Long id, String body){
		ApiControllerMethods.checkValidationErrors();
		TipoCriterio tipoCriterio = find(idEvaluacion, id);
		ApiControllerMethods.put(tipoCriterio, body);
	}
	
}
