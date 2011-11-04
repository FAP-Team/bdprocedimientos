package api;

import java.lang.reflect.Type;
import java.util.List;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import models.TipoCriterio;
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

public class TipoCriteriosTest extends FunctionalTest {
	
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

	
	public String criterioJson(String nombre, String clase, String jerarquia, String tipoValor, Integer transparencia, boolean comentariosAdministracion, boolean comentariosSolicitante){
		JsonObject criterio = new JsonObject();
	    criterio.addProperty("nombre", nombre);
	    criterio.addProperty("clase", clase);
	    criterio.addProperty("jerarquia", jerarquia);
	    criterio.addProperty("tipoValor", tipoValor);
	    criterio.addProperty("transparencia", transparencia);
		criterio.addProperty("comentariosSolicitante", comentariosSolicitante);
		criterio.addProperty("comentariosAdministracion", comentariosAdministracion);
		return criterio.toString();
	}
	
	private List<TipoCriterio> parseAll(Response response){
		Type type = new TypeToken<List<TipoCriterio>>(){}.getType();
		List<TipoCriterio> tiposCriterios= new Gson().fromJson(getContent(response), type);
		return tiposCriterios;
	}
	
	private List<TipoCriterio> fetchAll(Long Eid){
		Response get = GET(TiposEvaluacionesURL+"/"+Eid+"/tiposcriterios");
		assertIsOk(get);
		return parseAll(get);
	}
	
	@Test
	public void all (){
		// Creamos la evaluacion que ira unida al tipo criterio, para conocer su ID
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		// Creamos el tipo de criterio
		String criterio = criterioJson("nombre", "manual", "1.1.1", "lista", 8, true, false);
		// Insertamos 3
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios", "application/json", criterio);
		assertIsOk(post);
		post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios", "application/json", criterio);
		assertIsOk(post);
		post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios", "application/json", criterio);
		assertIsOk(post);
		
		Response get = GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios");
		assertIsOk(get);
		
		Type type = new TypeToken<List<TipoCriterio>>(){}.getType();
		List<TipoCriterio> tiposCriterios = new Gson().fromJson(getContent(get), type);
		assertNotNull(tiposCriterios);
		assertEquals(3, tiposCriterios.size());
		TipoCriterio first = tiposCriterios.iterator().next();
		assertNotNull(first);
		assertNotNull(first.jerarquia);
		assertEquals("nombre", first.nombre);
		assertEquals("manual", first.clase.name());
		assertEquals("lista", first.tipoValor.name());
	}
	
	@Test
	public void allPaginate(){
		// Creamos la evaluacion que ira unida al tipo criterio, para conocer su ID
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		// Creamos el tipo de criterio
		String criterio = criterioJson("nombre", "manual", "1.1.1", "lista", 8, true, false);
		// Insertamos 3
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios", "application/json", criterio);
		assertIsOk(post);
		criterio = criterioJson("nombre2", "manual", "1.1.2", "lista", 8, true, false);
		post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios", "application/json", criterio);
		assertIsOk(post);
		criterio = criterioJson("nombre3", "manual", "1.1.3", "lista", 8, true, false);
		post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios", "application/json", criterio);
		assertIsOk(post);
		
		List<TipoCriterio> all = fetchAll(evaluacion.id);
		assertEquals(3, all.size());
		
		List<TipoCriterio> limit1 = parseAll(GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios" + "?pageSize=1"));
		assertEquals(1, limit1.size());
		assertEquals(all.get(0), limit1.get(0));

		List<TipoCriterio> limit1Start2 = parseAll(GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios" + "?pageSize=1&pageStartIndex=2"));
		assertEquals(1, limit1Start2.size());
		assertEquals(all.get(2), limit1Start2.get(0));
	}
	
	@Test
	public void get(){
		// Creamos la evaluacion que ira unida al tipo criterio, para conocer su ID
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		// Creamos el tipo de criterio
		String criterio = criterioJson("nombre", "manual", "1.1.1", "lista", 8, true, false);
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios", "application/json", criterio);
		assertIsOk(post);


		TipoCriterio tipoCriterio = new Gson().fromJson(getContent(post), TipoCriterio.class);
		Response get = GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios/" + tipoCriterio.id);
		assertIsOk(get);
		tipoCriterio = new Gson().fromJson(getContent(get), TipoCriterio.class);
		assertNotNull(tipoCriterio);
		assertNotNull(tipoCriterio.id);
		assertEquals("nombre",tipoCriterio.nombre);
		assertEquals("1.1.1", tipoCriterio.jerarquia);
		assertEquals(false, tipoCriterio.comentariosSolicitante);
		assertEquals(true, tipoCriterio.comentariosAdministracion);
	}
	
	@Test
	public void post(){
		// Creamos la evaluacion que ira unida al tipo criterio, para conocer su ID
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		// Creamos el tipo de criterio
		String criterio = criterioJson("nombre", "manual", "1.1.1", "lista", 8, true, false);
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios", "application/json", criterio);
		assertIsOk(post);
		
		TipoCriterio tipoCriterio = new Gson().fromJson(getContent(post), TipoCriterio.class);
		assertNotNull(tipoCriterio.id);
		assertEquals("nombre", tipoCriterio.nombre);
		assertEquals("8", tipoCriterio.transparencia.toString());
		assertEquals(false, tipoCriterio.comentariosSolicitante);
		assertEquals(true, tipoCriterio.comentariosAdministracion);
	}
	
	@Test
	public void delete(){
		// Creamos la evaluacion que ira unida al tipo criterio, para conocer su ID
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		// Creamos el tipo de criterio
		String criterio = criterioJson("nombre", "manual", "1.1.1", "lista", 8, true, false);
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios", "application/json", criterio);
		assertIsOk(post);
		
		TipoCriterio tipoCriterio = new Gson().fromJson(getContent(post), TipoCriterio.class);
		assertNotNull(tipoCriterio);
		assertNotNull(tipoCriterio.id);
		
		List<TipoCriterio> all = fetchAll(evaluacion.id);
		assertNotNull(all);
		assertEquals(1, all.size());
		
		Response delete = DELETE(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios/"+tipoCriterio.id);
		assertIsOk(delete);
		
		all = fetchAll(evaluacion.id);
		assertNotNull(all);
		assertEquals(0, all.size());
	}
	
	@Test
	public void put(){
		// Creamos la evaluacion que ira unida al tipo criterio, para conocer su ID
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		// Creamos el tipo de criterio
		String criterio = criterioJson("nombre", "manual", "1.1.1", "lista", 8, true, false);
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios", "application/json", criterio);
		assertIsOk(post);
				
		TipoCriterio tipoCriterio = new Gson().fromJson(getContent(post), TipoCriterio.class);

		criterio = criterioJson("nombre2", "automatico", "1.2.1", "lista", 9, true, false);
		
		Response put = PUT(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios/"+tipoCriterio.id, "application/json", criterio);
		assertIsOk(put);
		tipoCriterio = new Gson().fromJson(getContent(put), TipoCriterio.class);
		assertNotNull(tipoCriterio);
		assertEquals("nombre2", tipoCriterio.nombre);
		assertEquals("1.2.1", tipoCriterio.jerarquia);
		assertEquals(false, tipoCriterio.comentariosSolicitante);
		assertEquals(true, tipoCriterio.comentariosAdministracion);
	}
	
	@Test
	public void notFound(){
		int randomId = 25;
		Response put = PUT(TiposEvaluacionesURL + "/" + randomId + "/tiposcriterios/" + randomId, "application/json", "");
		assertIsNotFound(put);
		
		Response get = GET(TiposEvaluacionesURL + "/" + randomId + "/tiposcriterios/" + randomId);
		assertIsNotFound(get);
		
		Response delete = DELETE(TiposEvaluacionesURL + "/" + randomId + "/tiposcriterios/" + randomId);
		assertIsNotFound(delete);
	}
	
	public ValidationErrors checkValidationErrors(Response response){
		assertEquals((int)Http.StatusCode.BAD_REQUEST, (int)response.status);
		ValidationErrors errors = new Gson().fromJson(getContent(response), ValidationErrors.class);
		return errors;
	}
	
	/*@Test
	public void badRequestPost(){
		// Creamos la evaluacion que ira unida al tipo criterio, para conocer su ID
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String criterio = criterioJson(null, null, "1.2.1", null, 9, true, false);
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios", "application/json", criterio);
		System.out.println(getContent(post));
		ValidationErrors errores = checkValidationErrors(post);
		assertTrue(errores.contains("tipoCriterio.nombre", "Required"));
		//assertTrue(errores.contains("tipoCriterio.clase", "Required"));
		//assertTrue(errores.contains("tipoCriterio.tipoValor", "Enumarated"));
	}*/
	
	@Test
	public void badRequestPut(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String criterio = criterioJson("nombre", "manual", "1.1.1", "lista", 8, true, false);
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios", "application/json", criterio);
		assertIsOk(post);
		TipoCriterio tipoCriterio = new Gson().fromJson(getContent(post), TipoCriterio.class);
		
		criterio = criterioJson(null, "manual", null, "lista", 8, true, false);
		Response put = PUT(TiposEvaluacionesURL + "/" + evaluacion.id + "/tiposcriterios/" + tipoCriterio.id, "application/json", criterio);
		ValidationErrors errores = checkValidationErrors(put);				
		assertTrue(errores.contains("tipoCriterio.nombre", "Required"));
		assertTrue(errores.contains("tipoCriterio.jerarquia", "Required"));
	}
	
	public void badRequestWithIncorrectId(Response response){
		ValidationErrors errores = checkValidationErrors(response);
		assertTrue(errores.contains("id", "Formato incorrecto"));
	}
	
	@Test
	public void badRequestGet(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		badRequestWithIncorrectId(GET(TiposEvaluacionesURL + "/" + evaluacion.id +  "/tiposcriterios/notAValidId"));
		badRequestWithIncorrectId(PUT(TiposEvaluacionesURL + "/" + evaluacion.id +  "/tiposcriterios/notAValidId", "application/json", ""));
		badRequestWithIncorrectId(DELETE(TiposEvaluacionesURL + "/" + evaluacion.id +  "/tiposcriterios/notAValidId"));
	}
	
	@Test
	public void badRequestAllPaginate(){
		// Creamos la evaluacion que ira unida al tipo criterio, para conocer su ID
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		Response get = GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposcriterios" + "?pageSize=a&pageStartIndex=b");
		ValidationErrors errores = checkValidationErrors(get);
		assertTrue(errores.contains("pageSize", "Formato incorrecto"));
		assertTrue(errores.contains("pageStartIndex", "Formato incorrecto"));
	}

}
