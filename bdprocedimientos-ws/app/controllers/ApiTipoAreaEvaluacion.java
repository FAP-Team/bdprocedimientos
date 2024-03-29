package controllers;

import models.TipoAreaEvaluacion;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.Controller;

public class ApiTipoAreaEvaluacion extends Controller {

	public static void all(){
		JPAQuery query = TipoAreaEvaluacion.all();
		ApiControllerMethods.all(query);
	}
	
	public static void get(Long id){
		ApiControllerMethods.checkValidationErrors();
		ApiControllerMethods.get(TipoAreaEvaluacion.findById(id));
	}

	public static void delete(Long id){
		ApiControllerMethods.checkValidationErrors();
		ApiControllerMethods.delete((TipoAreaEvaluacion)TipoAreaEvaluacion.findById(id));
	}
	
	public static void post(String body){
		TipoAreaEvaluacion tipoAreaEvaluacion = ApiControllerMethods.deserialize(body, TipoAreaEvaluacion.class);		
		ApiControllerMethods.post(tipoAreaEvaluacion);
	}
	
	public static void put(Long id, String body){
		ApiControllerMethods.checkValidationErrors();
		TipoAreaEvaluacion tipoAreaEvaluacion = TipoAreaEvaluacion.findById(id);
		ApiControllerMethods.put(tipoAreaEvaluacion, body);		
	}
	
}
