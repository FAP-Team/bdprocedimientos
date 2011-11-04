package controllers;

import models.TipoDocumentoAccesible;
import models.TipoEvaluacion;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.Controller;

public class ApiTipoDocumentoAccesible extends Controller {
	
	public static void all(Long idTipoEvaluacion){
		JPAQuery query = TipoDocumentoAccesible.find("select d from TipoDocumentoAccesible d where d.tipoEvaluacion.id = ?", idTipoEvaluacion);
		ApiControllerMethods.all(query);
	}

	static TipoDocumentoAccesible find(Long idTipoEvaluacion, Long id){
		ApiControllerMethods.checkValidationErrors();
		return (TipoDocumentoAccesible)TipoDocumentoAccesible.find("select d from TipoDocumentoAccesible d where d.id = ? and d.tipoEvaluacion.id = ?", id, idTipoEvaluacion).first();		
	}
	
	public static void get(Long idTipoEvaluacion, Long id){
		ApiControllerMethods.get(find(idTipoEvaluacion, id));
	}
	
	public static void delete(Long idTipoEvaluacion, Long id){
		ApiControllerMethods.delete(find(idTipoEvaluacion, id));
	}
	
	public static void post(Long idTipoEvaluacion, String body){
		ApiControllerMethods.checkValidationErrors();
		TipoDocumentoAccesible tipoDocumentoAccesible = ApiControllerMethods.deserialize(body, TipoDocumentoAccesible.class);
		TipoEvaluacion tipoEvaluacion = TipoEvaluacion.findById(idTipoEvaluacion);
		ApiControllerMethods.notFoundIfNull(tipoEvaluacion);
		tipoDocumentoAccesible.tipoEvaluacion = tipoEvaluacion;
		ApiControllerMethods.post(tipoDocumentoAccesible);
	}
	
	public static void put(Long idTipoEvaluacion, Long id, String body){
		ApiControllerMethods.checkValidationErrors();
		TipoDocumentoAccesible tipoDocumentoAccesible = find(idTipoEvaluacion, id);
		ApiControllerMethods.put(tipoDocumentoAccesible, body);
	}
	
}
