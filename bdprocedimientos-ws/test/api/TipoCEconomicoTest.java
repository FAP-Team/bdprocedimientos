package api;

import java.lang.reflect.Type;
import java.util.List;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import models.TipoCEconomico;
import models.TipoCriterio;
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

public class TipoCEconomicoTest extends FunctionalTest{
	
	public static final String TiposEvaluacionesURL = "/tiposevaluaciones";
	
	@Before
	public void setup(){
		Fixtures.deleteDatabase();
	}
	
	public Response CrearEvaluacionJson(String tipoProcedimiento, String nombre, boolean comentariosSolicitante, boolean comentariosAdministracion){
		JsonObject evaluacion = new JsonObject();
		evaluacion.addProperty("tipoProcedimiento", tipoProcedimiento);
		evaluacion.addProperty("nombre", nombre);
		evaluacion.addProperty("comentariosSolicitante", comentariosSolicitante);
		evaluacion.addProperty("comentariosAdministracion", comentariosAdministracion);
		Response post = POST(TiposEvaluacionesURL, "application/json", evaluacion.toString());
		assertIsOk(post);
		return post;
	}
	
	public String cEconomicoJson(String nombre, String clase, String jerarquia){
		JsonObject cEconomico = new JsonObject();
	    cEconomico.addProperty("nombre", nombre);
	    cEconomico.addProperty("clase", clase);
	    cEconomico.addProperty("jerarquia", jerarquia);
	    return cEconomico.toString();
	}
	
	private List<TipoCEconomico> parseAll(Response response){
		Type type = new TypeToken<List<TipoCEconomico>>(){}.getType();
		List<TipoCEconomico> tiposCEconomico= new Gson().fromJson(getContent(response), type);
		return tiposCEconomico;
	}
	
	public List<TipoCEconomico> fetchAll(Long Eid){
		Response get = GET(TiposEvaluacionesURL+"/"+Eid+"/tiposceconomicos");
		assertIsOk(get);
		return parseAll(get);
	}
	
	@Test
	public void all (){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String cEconomico = cEconomicoJson("nombre", "manual", "1.1.1");
		// Insertamos 3
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos", "application/json", cEconomico);
		assertIsOk(post);
		post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos", "application/json", cEconomico);
		assertIsOk(post);
		post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos", "application/json", cEconomico);
		assertIsOk(post);
		
		Response get = GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos");
		assertIsOk(get);
		
		Type type = new TypeToken<List<TipoCEconomico>>(){}.getType();
		List<TipoCEconomico> tiposCEconomico = new Gson().fromJson(getContent(get), type);
		assertNotNull(tiposCEconomico);
		assertEquals(3, tiposCEconomico.size());
		TipoCEconomico first = tiposCEconomico.iterator().next();
		assertNotNull(first);
		assertNotNull(first.jerarquia);
		assertEquals("nombre", first.nombre);
		assertEquals("manual", first.clase.name());
	}
	
	@Test
	public void allPaginate(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String cEconomico = cEconomicoJson("nombre", "manual", "1.1.1");
		// Insertamos 3
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos", "application/json", cEconomico);
		assertIsOk(post);
		cEconomico = cEconomicoJson("nombre2", "manual", "1.1.2");
		post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos", "application/json", cEconomico);
		assertIsOk(post);
		cEconomico = cEconomicoJson("nombre3", "manual", "1.1.3");
		post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos", "application/json", cEconomico);
		assertIsOk(post);
		
		List<TipoCEconomico> all = fetchAll(evaluacion.id);
		assertEquals(3, all.size());
		
		List<TipoCEconomico> limit1 = parseAll(GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos" + "?pageSize=1"));
		assertEquals(1, limit1.size());
		assertEquals(all.get(0), limit1.get(0));

		List<TipoCEconomico> limit1Start2 = parseAll(GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos" + "?pageSize=1&pageStartIndex=2"));
		assertEquals(1, limit1Start2.size());
		assertEquals(all.get(2), limit1Start2.get(0));
	}
	
	@Test
	public void get(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String cEconomico = cEconomicoJson("nombre", "manual", "1.1.1");
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos", "application/json", cEconomico);
		assertIsOk(post);

		TipoCEconomico tipoCEconomico = new Gson().fromJson(getContent(post), TipoCEconomico.class);
		Response get = GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos/" + tipoCEconomico.id);
		assertIsOk(get);
		tipoCEconomico = new Gson().fromJson(getContent(get), TipoCEconomico.class);
		assertNotNull(tipoCEconomico);
		assertNotNull(tipoCEconomico.id);
		assertEquals("nombre",tipoCEconomico.nombre);
		assertEquals("1.1.1", tipoCEconomico.jerarquia);
	}
	
	@Test
	public void post(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String cEconomico = cEconomicoJson("nombre", "manual", "1.1.1");
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos", "application/json", cEconomico);
		assertIsOk(post);
		
		TipoCEconomico tipoCEconomico = new Gson().fromJson(getContent(post), TipoCEconomico.class);
		assertNotNull(tipoCEconomico.id);
		assertEquals("nombre", tipoCEconomico.nombre);
		assertEquals("1.1.1", tipoCEconomico.jerarquia);
	}
	
	@Test
	public void delete(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String cEconomico = cEconomicoJson("nombre", "manual", "1.1.1");
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos", "application/json", cEconomico);
		assertIsOk(post);
		
		TipoCEconomico tipoCEconomico = new Gson().fromJson(getContent(post), TipoCEconomico.class);
		assertNotNull(tipoCEconomico);
		assertNotNull(tipoCEconomico.id);
		
		List<TipoCEconomico> all = fetchAll(evaluacion.id);
		assertNotNull(all);
		assertEquals(1, all.size());
		
		Response delete = DELETE(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos/"+tipoCEconomico.id);
		assertIsOk(delete);
		
		all = fetchAll(evaluacion.id);
		assertNotNull(all);
		assertEquals(0, all.size());
	}
	
	@Test
	public void put(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String cEconomico = cEconomicoJson("nombre", "manual", "1.1.1");
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos", "application/json", cEconomico);
		assertIsOk(post);
				
		TipoCEconomico tipoCEconomico = new Gson().fromJson(getContent(post), TipoCEconomico.class);

		cEconomico = cEconomicoJson("nombre2", "automatico", "1.2.1");
		
		Response put = PUT(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos/"+tipoCEconomico.id, "application/json", cEconomico);
		assertIsOk(put);
		tipoCEconomico = new Gson().fromJson(getContent(put), TipoCEconomico.class);
		assertNotNull(tipoCEconomico);
		assertEquals("nombre2", tipoCEconomico.nombre);
		assertEquals("1.2.1", tipoCEconomico.jerarquia);
	}
	
	@Test
	public void notFound(){
		int randomId = 25;
		Response put = PUT(TiposEvaluacionesURL + "/" + randomId + "/tiposceconomicos/" + randomId, "application/json", "");
		assertIsNotFound(put);
		
		Response get = GET(TiposEvaluacionesURL + "/" + randomId + "/tiposceconomicos/" + randomId);
		assertIsNotFound(get);
		
		Response delete = DELETE(TiposEvaluacionesURL + "/" + randomId + "/tiposceconomicos/" + randomId);
		assertIsNotFound(delete);
	}
	
	public ValidationErrors checkValidationErrors(Response response){
		assertEquals((int)Http.StatusCode.BAD_REQUEST, (int)response.status);
		ValidationErrors errors = new Gson().fromJson(getContent(response), ValidationErrors.class);
		return errors;
	}
	
	/*@Test
	public void badRequestPost(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String cEconomico = cEconomicoJson(null, null, "1.2.1");
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos", "application/json", cEconomico);
		ValidationErrors errores = checkValidationErrors(post);
		assertTrue(errores.contains("tipoCEconomico.nombre", "Required"));
		//assertTrue(errores.contains("tipoCEconomicos.clase", "Required"));
		//assertTrue(errores.contains("tipoCEconomicos.tipoValor", "Enumarated"));
	}*/
	
	@Test
	public void badRequestPut(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String cEconomicos = cEconomicoJson("nombre", "manual", "1.1.1");
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos", "application/json", cEconomicos);
		assertIsOk(post);
		TipoCEconomico tipoCEconomico = new Gson().fromJson(getContent(post), TipoCEconomico.class);
		
		cEconomicos = cEconomicoJson(null, "manual", null);
		Response put = PUT(TiposEvaluacionesURL + "/" + evaluacion.id + "/tiposceconomicos/" + tipoCEconomico.id, "application/json", cEconomicos);
		ValidationErrors errores = checkValidationErrors(put);				
		assertTrue(errores.contains("tipoCEconomico.nombre", "Required"));
		assertTrue(errores.contains("tipoCEconomico.jerarquia", "Required"));
	}
	
	public void badRequestWithIncorrectId(Response response){
		ValidationErrors errores = checkValidationErrors(response);
		assertTrue(errores.contains("id", "Formato incorrecto"));
	}
	
	@Test
	public void badRequestGet(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		badRequestWithIncorrectId(GET(TiposEvaluacionesURL + "/" + evaluacion.id +  "/tiposceconomicos/notAValidId"));
		badRequestWithIncorrectId(PUT(TiposEvaluacionesURL + "/" + evaluacion.id +  "/tiposceconomicos/notAValidId", "application/json", ""));
		badRequestWithIncorrectId(DELETE(TiposEvaluacionesURL + "/" + evaluacion.id +  "/tiposceconomicos/notAValidId"));
	}
	
	@Test
	public void badRequestAllPaginate(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		Response get = GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposceconomicos" + "?pageSize=a&pageStartIndex=b");
		ValidationErrors errores = checkValidationErrors(get);
		assertTrue(errores.contains("pageSize", "Formato incorrecto"));
		assertTrue(errores.contains("pageStartIndex", "Formato incorrecto"));
	}

}
