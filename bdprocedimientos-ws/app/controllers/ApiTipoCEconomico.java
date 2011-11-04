package controllers;

import models.TipoCEconomico;
import models.TipoEvaluacion;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.Controller;

public class ApiTipoCEconomico extends Controller {
	
	public static void all(Long idTipoEvaluacion){
		JPAQuery query = TipoCEconomico.find("select t from TipoCEconomico t where t.tipoEvaluacion.id = ?", idTipoEvaluacion);
		ApiControllerMethods.all(query);
	}

	static TipoCEconomico find(Long idTipoEvaluacion, Long id){
		ApiControllerMethods.checkValidationErrors();
		return (TipoCEconomico)TipoCEconomico.find("select t from TipoCEconomico t where t.id = ? and t.tipoEvaluacion.id = ?", id, idTipoEvaluacion).first();		
	}
	
	public static void get(Long idTipoEvaluacion, Long id){
		ApiControllerMethods.get(find(idTipoEvaluacion, id));
	}
	
	public static void delete(Long idTipoEvaluacion, Long id){
		ApiControllerMethods.delete(find(idTipoEvaluacion, id));
	}
	
	public static void post(Long idTipoEvaluacion, String body){
		ApiControllerMethods.checkValidationErrors();
		TipoCEconomico tipoCEconomico = ApiControllerMethods.deserialize(body, TipoCEconomico.class);
		TipoEvaluacion tipoEvaluacion = TipoEvaluacion.findById(idTipoEvaluacion);
		ApiControllerMethods.notFoundIfNull(tipoEvaluacion);
		tipoCEconomico.tipoEvaluacion = tipoEvaluacion;
		ApiControllerMethods.post(tipoCEconomico);
	}
	
	public static void put(Long idTipoEvaluacion, Long id, String body){
		ApiControllerMethods.checkValidationErrors();
		TipoCEconomico tipoCEconomico = find(idTipoEvaluacion, id);
		ApiControllerMethods.put(tipoCEconomico, body);
	}
	
}
