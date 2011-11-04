package api;

import java.lang.reflect.Type;
import java.util.List;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import models.TipoAreaEvaluacion;
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

public class TipoAreasEvaluacionesTest extends FunctionalTest{
	
	public static final String TiposAreasEvaluacionURL = "/tiposareasevaluacion";
	
	@Before
	public void setup(){
		Fixtures.deleteDatabase();
	}
	
	public String areasEvaluacionJson(String codigo, String descripcion){
		JsonObject areaEvaluacion = new JsonObject();
		areaEvaluacion.addProperty("codigo", codigo);
		areaEvaluacion.addProperty("descripcion", descripcion);
		return areaEvaluacion.toString();
	}
	
	@Test
	public void all(){
		String areaEvaluacion = areasEvaluacionJson("codigo", "descripcion");
		
		Response post = POST(TiposAreasEvaluacionURL, "application/json", areaEvaluacion);
		System.out.println(getContent(post));
		assertIsOk(post);
		
		post = POST(TiposAreasEvaluacionURL, "application/json", areaEvaluacion);
		assertIsOk(post);
		
		post = POST(TiposAreasEvaluacionURL, "application/json", areaEvaluacion);
		assertIsOk(post);
		
		Response get = GET(TiposAreasEvaluacionURL);
		assertIsOk(get);
		
		Type type = new TypeToken<List<TipoAreaEvaluacion>>(){}.getType();
		List<TipoAreaEvaluacion> tiposAreasEvaluacion = new Gson().fromJson(getContent(get), type);
		assertNotNull(tiposAreasEvaluacion);
		assertEquals(3, tiposAreasEvaluacion.size());
		TipoAreaEvaluacion first = tiposAreasEvaluacion.iterator().next();
		assertNotNull(first);
		assertNotNull(first.id);
		assertEquals("codigo", first.codigo);
		assertEquals("descripcion", first.descripcion);
	}
	
	private List<TipoAreaEvaluacion> fetchAll(){
		Response get = GET(TiposAreasEvaluacionURL);
		assertIsOk(get);
		return parseAll(get);
	}
	
	private List<TipoAreaEvaluacion> parseAll(Response response){
		Type type = new TypeToken<List<TipoAreaEvaluacion>>(){}.getType();
		List<TipoAreaEvaluacion> tiposAreasEvaluacion = new Gson().fromJson(getContent(response), type);
		return tiposAreasEvaluacion;
	}
	
	@Test
	public void allPaginate(){
		String areaEvaluacion = areasEvaluacionJson("codigo", "descripcion");
		Response post = POST(TiposAreasEvaluacionURL, "application/json", areaEvaluacion);
		assertIsOk(post);
		areaEvaluacion = areasEvaluacionJson("codigo1", "codigo 1");
		post = POST(TiposAreasEvaluacionURL, "application/json", areaEvaluacion);
		assertIsOk(post);
		areaEvaluacion = areasEvaluacionJson("codigo2", "codigo 2");
		post = POST(TiposAreasEvaluacionURL, "application/json", areaEvaluacion);
		assertIsOk(post);
		
		List<TipoAreaEvaluacion> all = fetchAll();
		assertEquals(3, all.size());
		
		List<TipoAreaEvaluacion> limit1 = parseAll(GET(TiposAreasEvaluacionURL + "?pageSize=1"));
		assertEquals(1, limit1.size());
		assertEquals(all.get(0), limit1.get(0));

		List<TipoAreaEvaluacion> limit1Start2 = parseAll(GET(TiposAreasEvaluacionURL + "?pageSize=1&pageStartIndex=2"));
		assertEquals(1, limit1Start2.size());
		assertEquals(all.get(2), limit1Start2.get(0));
	}
	
	@Test
	public void get(){
		String areaEvaluacion = areasEvaluacionJson("codigo", "descripcion");
		Response post = POST(TiposAreasEvaluacionURL, "application/json", areaEvaluacion);
		assertIsOk(post);
		TipoAreaEvaluacion tipoAreaEvaluacion = new Gson().fromJson(getContent(post), TipoAreaEvaluacion.class);
		Response get = GET(TiposAreasEvaluacionURL+ "/" + tipoAreaEvaluacion.id);
		assertIsOk(get);
		tipoAreaEvaluacion = new Gson().fromJson(getContent(get), TipoAreaEvaluacion.class);
		assertNotNull(tipoAreaEvaluacion);
		assertNotNull(tipoAreaEvaluacion.id);
		assertEquals("codigo",tipoAreaEvaluacion.codigo);
		assertEquals("descripcion", tipoAreaEvaluacion.descripcion);
	}
	
	@Test
	public void post(){
		String areaEvaluacion = areasEvaluacionJson("codigo", "descripcion");
		Response post = POST(TiposAreasEvaluacionURL, "application/json", areaEvaluacion);
		assertIsOk(post);
		TipoAreaEvaluacion tipoAreaEvaluacion = new Gson().fromJson(getContent(post), TipoAreaEvaluacion.class);
		assertNotNull(tipoAreaEvaluacion);
		assertNotNull(tipoAreaEvaluacion.id);
		assertEquals("codigo", tipoAreaEvaluacion.codigo);
		assertEquals("descripcion", tipoAreaEvaluacion.descripcion);
	}
	
	@Test
	public void delete(){
		String areaEvaluacion = areasEvaluacionJson("codigo", "descripcion");
		Response post = POST(TiposAreasEvaluacionURL, "application/json", areaEvaluacion);
		assertIsOk(post);
		TipoAreaEvaluacion tipoAreaEvaluacion = new Gson().fromJson(getContent(post), TipoAreaEvaluacion.class);
		assertNotNull(tipoAreaEvaluacion);
		assertNotNull(tipoAreaEvaluacion.id);
		
		List<TipoAreaEvaluacion> all = fetchAll();
		assertNotNull(all);
		assertEquals(1, all.size());
		
		Response delete = DELETE(TiposAreasEvaluacionURL + "/" + tipoAreaEvaluacion.id);
		assertIsOk(delete);
		
		all = fetchAll();
		assertNotNull(all);
		assertEquals(0, all.size());
	}

	@Test
	public void put(){
		String areaEvaluacion = areasEvaluacionJson("codigo", "descripcion");
		Response post = POST(TiposAreasEvaluacionURL, "application/json", areaEvaluacion);
		assertIsOk(post);		
		TipoAreaEvaluacion tipoAreaEvaluacion = new Gson().fromJson(getContent(post), TipoAreaEvaluacion.class);

		areaEvaluacion = areasEvaluacionJson("codigo1", "descripcion1");
		Response put = PUT(TiposAreasEvaluacionURL + "/" + tipoAreaEvaluacion.id, "application/json", areaEvaluacion);
		assertIsOk(put);
		tipoAreaEvaluacion = new Gson().fromJson(getContent(put), TipoAreaEvaluacion.class);
		assertNotNull(tipoAreaEvaluacion);
		assertEquals("codigo1", tipoAreaEvaluacion.codigo);
		assertEquals("descripcion1", tipoAreaEvaluacion.descripcion);
	}
	
	@Test
	public void notFound(){
		int randomId = 25;
		Response put = PUT(TiposAreasEvaluacionURL + "/" + randomId, "application/json", "");
		assertIsNotFound(put);
		
		Response get = GET(TiposAreasEvaluacionURL + "/" + randomId);
		assertIsNotFound(get);
		
		Response delete = DELETE(TiposAreasEvaluacionURL + "/" + randomId);
		assertIsNotFound(delete);
	}

	public ValidationErrors checkValidationErrors(Response response){
		assertEquals((int)Http.StatusCode.BAD_REQUEST, (int)response.status);
		ValidationErrors errors = new Gson().fromJson(getContent(response), ValidationErrors.class);
		return errors;
	}
	
	@Test
	public void badRequestPost(){
		String areaEvaluacion = areasEvaluacionJson(null, null);
		Response post = POST(TiposAreasEvaluacionURL, "application/json", areaEvaluacion);
		
		ValidationErrors errores = checkValidationErrors(post);
		assertTrue(errores.contains("tipoAreaEvaluacion.codigo", "Required"));
		assertTrue(errores.contains("tipoAreaEvaluacion.descripcion", "Required"));
	}
	

	@Test
	public void badRequestPut(){
		String areaEvaluacion = areasEvaluacionJson("a", "b");
		Response post = POST(TiposAreasEvaluacionURL, "application/json", areaEvaluacion);
		assertIsOk(post);
		TipoAreaEvaluacion tipoAreaEvaluacion = new Gson().fromJson(getContent(post), TipoAreaEvaluacion.class);
		
		areaEvaluacion = areasEvaluacionJson(null, null);
		Response put = PUT(TiposAreasEvaluacionURL + "/" + tipoAreaEvaluacion.id, "application/json", areaEvaluacion);
		ValidationErrors errores = checkValidationErrors(put);				
		assertTrue(errores.contains("tipoAreaEvaluacion.codigo", "Required"));
		assertTrue(errores.contains("tipoAreaEvaluacion.descripcion", "Required"));		
	}
	
	public void badRequestWithIncorrectId(Response response){
		ValidationErrors errores = checkValidationErrors(response);
		assertTrue(errores.contains("id", "Formato incorrecto"));
	}
	
	@Test
	public void badRequestGet(){
		badRequestWithIncorrectId(GET(TiposAreasEvaluacionURL + "/notAValidId"));
		badRequestWithIncorrectId(PUT(TiposAreasEvaluacionURL + "/notAValidId", "application/json", ""));
		badRequestWithIncorrectId(DELETE(TiposAreasEvaluacionURL + "/notAValidId"));
	}
	
	@Test
	public void badRequestAllPaginate(){
		Response get = GET(TiposAreasEvaluacionURL + "?pageSize=a&pageStartIndex=b");
		ValidationErrors errores = checkValidationErrors(get);
		assertTrue(errores.contains("pageSize", "Formato incorrecto"));
		assertTrue(errores.contains("pageStartIndex", "Formato incorrecto"));
	}

}
