package controllers;

import models.TipoCriterio;
import models.TipoEvaluacion;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.Controller;

public class ApiTipoEvaluacion extends Controller {

	public static void all(){
		JPAQuery query = TipoEvaluacion.all();
		ApiControllerMethods.all(query);
	}
	
	public static void get(Long id){
		ApiControllerMethods.checkValidationErrors();
		ApiControllerMethods.get(TipoEvaluacion.findById(id));
	}

	public static void delete(Long id){
		ApiControllerMethods.checkValidationErrors();
		ApiControllerMethods.delete((TipoEvaluacion)TipoEvaluacion.findById(id));
	}
	
	public static void post(String body){
		TipoEvaluacion tipoEvaluacion = ApiControllerMethods.deserialize(body, TipoEvaluacion.class);		
		ApiControllerMethods.post(tipoEvaluacion);
	}
	
	public static void put(Long id, String body){
		ApiControllerMethods.checkValidationErrors();
		TipoEvaluacion tipoEvaluacion = TipoEvaluacion.findById(id);
		ApiControllerMethods.put(tipoEvaluacion, body);		
	}
	
}
