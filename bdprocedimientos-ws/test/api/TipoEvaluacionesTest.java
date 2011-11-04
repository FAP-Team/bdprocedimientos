package api;

import java.lang.reflect.Type;
import java.util.List;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import models.TipoDocumentoAccesible;
import models.TipoEvaluacion;
import models.TipoCriterio;
import models.TipoCEconomico;

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
		
		Type type = new TypeToken<List<TipoEvaluacion>>(){}.getType();
		List<TipoEvaluacion> tiposEvaluaciones = new Gson().fromJson(getContent(get), type);
		assertNotNull(tiposEvaluaciones);
		assertEquals(3, tiposEvaluaciones.size());
		TipoEvaluacion first = tiposEvaluaciones.iterator().next();
		assertNotNull(first);
		assertNotNull(first.id);
		assertEquals("procedimiento", first.tipoProcedimiento);
		assertEquals("nombre", first.nombre);
	}
	
	@Test
	public void allPaginate(){
		String evaluacion = evaluacionJson("procedimiento", "nombre", true, false);
		Response post = POST(TiposEvaluacionesURL, "application/json", evaluacion);
		assertIsOk(post);
		evaluacion = evaluacionJson("procedimiento1", "nombre1", true, false);
		post = POST(TiposEvaluacionesURL, "application/json", evaluacion);
		assertIsOk(post);
		evaluacion = evaluacionJson("procedimiento2", "nombre2", true, false);
		post = POST(TiposEvaluacionesURL, "application/json", evaluacion);
		assertIsOk(post);
		
		List<TipoEvaluacion> all = fetchAll();
		assertEquals(3, all.size());
		
		List<TipoEvaluacion> limit1 = parseAll(GET(TiposEvaluacionesURL + "?pageSize=1"));
		assertEquals(1, limit1.size());
		assertEquals(all.get(0), limit1.get(0));

		List<TipoEvaluacion> limit1Start2 = parseAll(GET(TiposEvaluacionesURL + "?pageSize=1&pageStartIndex=2"));
		assertEquals(1, limit1Start2.size());
		assertEquals(all.get(2), limit1Start2.get(0));
	}
	
	private List<TipoEvaluacion> fetchAll(){
		Response get = GET(TiposEvaluacionesURL);
		assertIsOk(get);
		return parseAll(get);
	}
	
	private List<TipoEvaluacion> parseAll(Response response){
		Type type = new TypeToken<List<TipoEvaluacion>>(){}.getType();
		List<TipoEvaluacion> tiposEvaluaciones = new Gson().fromJson(getContent(response), type);
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
		
		List<TipoEvaluacion> all = fetchAll();
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
	public void deleteCascade (){
		// Creamos la evaluacion
		String evaluacion = evaluacionJson("procedimiento", "nombre", true, false);
		Response post = POST(TiposEvaluacionesURL, "application/json", evaluacion);
		assertIsOk(post);		
		TipoEvaluacion tipoEvaluacion = new Gson().fromJson(getContent(post), TipoEvaluacion.class);
		
		// Creamos un tipo criterio asociado a la evaluacion
		TipoCriteriosTest TCriterio = new TipoCriteriosTest();
		String criterio = TCriterio.criterioJson("nombre", "manual", "1.1.1", "lista", 8, true, false);
		post = POST(TiposEvaluacionesURL+"/"+tipoEvaluacion.id+"/tiposcriterios", "application/json", criterio);
		assertIsOk(post);
		   // Creamos otro criterio, para probar con 2, y que los 2 despues se borren en cascada
		post = POST(TiposEvaluacionesURL+"/"+tipoEvaluacion.id+"/tiposcriterios", "application/json", criterio);
		assertIsOk(post);
		
		// Creamos un tipo CEconomico asociado a la evaluacion
		TipoCEconomicoTest TCEconomico = new TipoCEconomicoTest();
		String cEconomico = TCEconomico.cEconomicoJson("nombre", "manual", "1.1.1");
		post = POST(TiposEvaluacionesURL+"/"+tipoEvaluacion.id+"/tiposceconomicos", "application/json", cEconomico);
		assertIsOk(post);
		
		// Creamos un tipo Documento Accesible asociado a la evaluacion
		TipoDocumentoAccesibleTest TDocAcc = new TipoDocumentoAccesibleTest();
		String documentoAccesible = TDocAcc.documentoAccesibleJson("uri");
		post = POST(TiposEvaluacionesURL+"/"+tipoEvaluacion.id+"/tiposdocumentosaccesibles", "application/json", documentoAccesible);
		assertIsOk(post);
		
		// Borramos la evaluacion
		Response delete = DELETE(TiposEvaluacionesURL + "/" + tipoEvaluacion.id);
		assertIsOk(delete);
		List<TipoEvaluacion> all = fetchAll();
		assertNotNull(all);
		assertEquals(0, all.size());
		
		// Comprobamos que se borraron en cascada los elementos asociadas a la evaluacion borrada
		List<TipoCEconomico> allCE = TCEconomico.fetchAll(tipoEvaluacion.id);
		assertNotNull(allCE);
		assertEquals(0, allCE.size());
		
		List<TipoDocumentoAccesible> allDocAcc = TDocAcc.fetchAll(tipoEvaluacion.id);
		assertNotNull(allDocAcc);
		assertEquals(0, allDocAcc.size());
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
	
	@Test
	public void badRequestAllPaginate(){
		Response get = GET(TiposEvaluacionesURL + "?pageSize=a&pageStartIndex=b");
		ValidationErrors errores = checkValidationErrors(get);
		assertTrue(errores.contains("pageSize", "Formato incorrecto"));
		assertTrue(errores.contains("pageStartIndex", "Formato incorrecto"));
	}
	
}
