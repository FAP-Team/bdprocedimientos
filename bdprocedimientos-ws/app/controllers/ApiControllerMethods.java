package controllers;

import java.util.List;

import javax.management.Query;

import models.TipoEvaluacion;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import play.data.validation.Validation;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.Model;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Scope.Params;
import play.mvc.results.RenderJson;
import responses.ErrorResponse;
import responses.ResourceNotFoundResponse;
import responses.ValidationErrors;
import responses.ValidationErrorsResponse;
import serializers.AscendentExclusionEstrategy;
import serializers.IdExclusionEstrategy;
import serializers.RelationExclusionStrategy;
import utils.JSonUtils;
import utils.PojoUtils;
import utils.QueryUtils;

public class ApiControllerMethods<T> {

	private static Params getParams(){
		return Params.current();
	}
	
	public static void all(JPAQuery query) {
		Params params = getParams();
		Integer pageSize = params.get("pageSize", Integer.class);
		Integer pageStartIndex = params.get("pageStartIndex", Integer.class);
		checkValidationErrors();
		
		List<Object> result = QueryUtils.fetchPaginate(query, pageSize, pageStartIndex);
		String json = new GsonBuilder()
				.addSerializationExclusionStrategy(new AscendentExclusionEstrategy())
				.serializeNulls()
				.create().toJson(result);
		 throw new RenderJson(json);
	}

	public static void checkValidationErrors(){
		if(Validation.hasErrors()){
			throw new ValidationErrorsResponse(Validation.errors());
		}		
	}
	
	public static String serialize(Object o){
		return new GsonBuilder()
		.addSerializationExclusionStrategy(new AscendentExclusionEstrategy())
		.serializeNulls()
		.create()
		.toJson(o);
	}
	
	public static void serializeAndRender(Object o){
		String json = serialize(o);
		throw new RenderJson(json);
	}
	
	public static void notFoundIfNull(Object o){
		//TODO Cambiar excepcion a generica
		Params params = getParams();
		if(o == null){
			throw new ResourceNotFoundResponse("Recurso", params.get("id", Long.class));			
		}		
	}
	
	public static void get(Object o){
		notFoundIfNull(o);
		serializeAndRender(o);
	}
	
	public static <T extends Model> void delete(T o){
		Params params = getParams();
		if(o == null){
			throw new ResourceNotFoundResponse("Recurso", params.get("id", Long.class));			
		}
		o.delete();
		throw new RenderJson("{\"message\" : \"Borrado correctamente\"}");
	}
	
	public static <T extends Model> T deserialize(String json, Class<T> classOfT){
		try {
			return new GsonBuilder()
				.addDeserializationExclusionStrategy(new IdExclusionEstrategy())
				.addDeserializationExclusionStrategy(new RelationExclusionStrategy())
				.create()
				.fromJson(json, classOfT);
		}catch(JsonSyntaxException e){
			throw new ErrorResponse(e.getMessage(), Http.StatusCode.BAD_REQUEST);			
		}
	}
	
	public static <T extends Model> void post(T o){
		if(o == null)
			throw new ErrorResponse("Empty body", Http.StatusCode.BAD_REQUEST);
		
		o.validateAndCreate();
		checkValidationErrors();
		serializeAndRender(o);
	}
	
	public static <T extends Model> void put(T o, String json){
		notFoundIfNull(o);
		T param = (T)deserialize(json, o.getClass());
		PojoUtils.copyAllSimpleFields(param, o);
		o.validateAndSave();
		checkValidationErrors();
		serializeAndRender(o);
	}
	
}
