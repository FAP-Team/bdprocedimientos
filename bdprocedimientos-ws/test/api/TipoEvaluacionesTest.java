package api;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import models.TipoEvaluacion;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import play.Logger;
import play.mvc.Http;
import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;
import responses.ValidationErrors;

public class TipoEvaluacionesTest extends FunctionalTest {

	public static final String TiposEvaluacionesURL = "/tiposevaluaciones";
	
	@Before
	public void setup(){
		Fixtures.deleteDatabase();
	}
	
	public String evaluacionJson(String tipoProcedimiento, String nombre, boolean comentariosSolicitante, boolean comentariosAdministracion){
		JsonObject evaluacion = new JsonObject();
		evaluacion.addProperty("tipoProcedimiento", tipoProcedimiento);
		evaluacion.addProperty("nombre", nombre);
		evaluacion.addProperty("comentariosSolicitante", comentariosSolicitante);
		evaluacion.addProperty("comentariosAdministracion", comentariosAdministracion);
		return evaluacion.toString();
	}
		
	@Test
	public void all(){
		String evaluacion = evaluacionJson("procedimiento", "nombre", true, false);
		
		Response post = POST(TiposEvaluacionesURL, "application/json", evaluacion);
		assertIsOk(post);
		
		post = POST(TiposEvaluacionesURL, "application/json", evaluacion);
		assertIsOk(post);
		
		post = POST(TiposEvaluacionesURL, "application/json", evaluacion);
		assertIsOk(post);
		
		Response get = GET(TiposEvaluacionesURL);
		assertIsOk(get);
		
		Type type = new TypeToken<Collection<TipoEvaluacion>>(){}.getType();
		Collection<TipoEvaluacion> tiposEvaluaciones = new Gson().fromJson(getContent(get), type);
		assertNotNull(tiposEvaluaciones);
		assertEquals(3, tiposEvaluaciones.size());
		TipoEvaluacion first = tiposEvaluaciones.iterator().next();
		assertNotNull(first);
		assertNotNull(first.id);
		assertEquals("procedimiento", first.tipoProcedimiento);
		assertEquals("nombre", first.nombre);
	}
	
	private Collection<TipoEvaluacion> fetchAll(){
		Response get = GET(TiposEvaluacionesURL);
		assertIsOk(get);
		Type type = new TypeToken<Collection<TipoEvaluacion>>(){}.getType();
		Collection<TipoEvaluacion> tiposEvaluaciones = new Gson().fromJson(getContent(get), type);
		return tiposEvaluaciones;
	}
	
	@Test
	public void get(){
		String evaluacion = evaluacionJson("procedimiento", "nombre", true, false);
		Response post = POST(TiposEvaluacionesURL, "application/json", evaluacion);
		assertIsOk(post);
		TipoEvaluacion tipoEvaluacion = new Gson().fromJson(getContent(post), TipoEvaluacion.class);
		Response get = GET(TiposEvaluacionesURL+ "/" + tipoEvaluacion.id);
		assertIsOk(get);
		tipoEvaluacion = new Gson().fromJson(getContent(get), TipoEvaluacion.class);
		assertNotNull(tipoEvaluacion);
		assertNotNull(tipoEvaluacion.id);
		assertEquals("procedimiento",tipoEvaluacion.tipoProcedimiento);
		assertEquals("nombre", tipoEvaluacion.nombre);
		assertEquals(true, tipoEvaluacion.comentariosSolicitante);
		assertEquals(false, tipoEvaluacion.comentariosAdministracion);
	}
	
	@Test
	public void post(){
		String evaluacion = evaluacionJson("procedimiento", "nombre", true, false);
		Response post = POST(TiposEvaluacionesURL, "application/json", evaluacion);
		assertIsOk(post);
		TipoEvaluacion tipoEvaluacion = new Gson().fromJson(getContent(post), TipoEvaluacion.class);
		assertNotNull(tipoEvaluacion);
		assertNotNull(tipoEvaluacion.id);
		assertEquals("procedimiento", tipoEvaluacion.tipoProcedimiento);
		assertEquals("nombre", tipoEvaluacion.nombre);
		assertEquals(true, tipoEvaluacion.comentariosSolicitante);
		assertEquals(false, tipoEvaluacion.comentariosAdministracion);
	}
	
	@Test
	public void delete(){
		String evaluacion = evaluacionJson("procedimiento", "nombre", true, false);
		Response post = POST(TiposEvaluacionesURL, "application/json", evaluacion);
		assertIsOk(post);
		TipoEvaluacion tipoEvaluacion = new Gson().fromJson(getContent(post), TipoEvaluacion.class);
		assertNotNull(tipoEvaluacion);
		assertNotNull(tipoEvaluacion.id);
		
		Collection<TipoEvaluacion> all = fetchAll();
		assertNotNull(all);
		assertEquals(1, all.size());
		
		Response delete = DELETE(TiposEvaluacionesURL + "/" + tipoEvaluacion.id);
		assertIsOk(delete);
		
		all = fetchAll();
		assertNotNull(all);
		assertEquals(0, all.size());
	}

	@Test
	public void put(){
		String evaluacion = evaluacionJson("procedimiento", "nombre", true, false);
		Response post = POST(TiposEvaluacionesURL, "application/json", evaluacion);
		assertIsOk(post);		
		TipoEvaluacion tipoEvaluacion = new Gson().fromJson(getContent(post), TipoEvaluacion.class);

		evaluacion = evaluacionJson("procedimiento1", "nombre1", true, false);
		Response put = PUT(TiposEvaluacionesURL + "/" + tipoEvaluacion.id, "application/json", evaluacion);
		assertIsOk(put);
		tipoEvaluacion = new Gson().fromJson(getContent(put), TipoEvaluacion.class);
		assertNotNull(tipoEvaluacion);
		assertEquals("procedimiento1", tipoEvaluacion.tipoProcedimiento);
		assertEquals("nombre1", tipoEvaluacion.nombre);
		assertEquals(true, tipoEvaluacion.comentariosSolicitante);
		assertEquals(false, tipoEvaluacion.comentariosAdministracion);
	}
	
	@Test
	public void notFound(){
		int randomId = 25;
		Response put = PUT(TiposEvaluacionesURL + "/" + randomId, "application/json", "");
		assertIsNotFound(put);
		
		Response get = GET(TiposEvaluacionesURL + "/" + randomId);
		assertIsNotFound(get);
		
		Response delete = DELETE(TiposEvaluacionesURL + "/" + randomId);
		assertIsNotFound(delete);
	}

	public ValidationErrors checkValidationErrors(Response response){
		assertEquals((int)Http.StatusCode.BAD_REQUEST, (int)response.status);
		ValidationErrors errors = new Gson().fromJson(getContent(response), ValidationErrors.class);
		return errors;
	}
	
	@Test
	public void badRequestPost(){
		String evaluacion = evaluacionJson(null, null, true, false);
		Response post = POST(TiposEvaluacionesURL, "application/json", evaluacion);
		
		ValidationErrors errores = checkValidationErrors(post);
		assertTrue(errores.contains("tipoEvaluacion.nombre", "Required"));
		assertTrue(errores.contains("tipoEvaluacion.tipoProcedimiento", "Required"));
	}
	

	@Test
	public void badRequestPut(){
		String evaluacion = evaluacionJson("a", "b", true, false);
		Response post = POST(TiposEvaluacionesURL, "application/json", evaluacion);
		assertIsOk(post);
		TipoEvaluacion tipoEvaluacion = new Gson().fromJson(getContent(post), TipoEvaluacion.class);
		
		evaluacion = evaluacionJson(null, null, true, false);
		Response put = PUT(TiposEvaluacionesURL + "/" + tipoEvaluacion.id, "application/json", evaluacion);
		ValidationErrors errores = checkValidationErrors(put);				
		assertTrue(errores.contains("tipoEvaluacion.nombre", "Required"));
		assertTrue(errores.contains("tipoEvaluacion.tipoProcedimiento", "Required"));		
	}	
	
	public void badRequestWithIncorrectId(Response response){
		ValidationErrors errores = checkValidationErrors(response);
		assertTrue(errores.contains("id", "Formato incorrecto"));
	}
	
	@Test
	public void badRequestGet(){
		badRequestWithIncorrectId(GET(TiposEvaluacionesURL + "/notAValidId"));
		badRequestWithIncorrectId(PUT(TiposEvaluacionesURL + "/notAValidId", "application/json", ""));
		badRequestWithIncorrectId(DELETE(TiposEvaluacionesURL + "/notAValidId"));
	}
}
