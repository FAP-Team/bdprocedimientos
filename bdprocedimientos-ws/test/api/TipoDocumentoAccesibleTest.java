package api;

import java.lang.reflect.Type;
import java.util.List;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import models.TipoDocumentoAccesible;
import models.TipoDocumentoAccesible;
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

public class TipoDocumentoAccesibleTest extends FunctionalTest{
	
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

	
	public String documentoAccesibleJson(String uri){
		JsonObject documentoAccesible = new JsonObject();
		documentoAccesible.addProperty("uri", uri);
	    return documentoAccesible.toString();
	}
	
	private List<TipoDocumentoAccesible> parseAll(Response response){
		Type type = new TypeToken<List<TipoDocumentoAccesible>>(){}.getType();
		List<TipoDocumentoAccesible> tiposDocumentosAccesible= new Gson().fromJson(getContent(response), type);
		return tiposDocumentosAccesible;
	}
	
	public List<TipoDocumentoAccesible> fetchAll(Long Eid){
		Response get = GET(TiposEvaluacionesURL+"/"+Eid+"/tiposdocumentosaccesibles");
		assertIsOk(get);
		return parseAll(get);
	}
	
	@Test
	public void all (){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String documentoAccesible = documentoAccesibleJson("uri");
		// Insertamos 3
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles", "application/json", documentoAccesible);
		assertIsOk(post);
		post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles", "application/json", documentoAccesible);
		assertIsOk(post);
		post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles", "application/json", documentoAccesible);
		assertIsOk(post);
		
		Response get = GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles");
		assertIsOk(get);
		
		Type type = new TypeToken<List<TipoDocumentoAccesible>>(){}.getType();
		List<TipoDocumentoAccesible> tipoDocumentoAccesible = new Gson().fromJson(getContent(get), type);
		assertNotNull(tipoDocumentoAccesible);
		assertEquals(3, tipoDocumentoAccesible.size());
		TipoDocumentoAccesible first = tipoDocumentoAccesible.iterator().next();
		assertNotNull(first);
		assertEquals("uri", first.uri);
	}
	
	@Test
	public void allPaginate(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String documentoAccesible = documentoAccesibleJson("uri");
		// Insertamos 3
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles", "application/json", documentoAccesible);
		assertIsOk(post);
		documentoAccesible = documentoAccesibleJson("uri");
		post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles", "application/json", documentoAccesible);
		assertIsOk(post);
		documentoAccesible = documentoAccesibleJson("uri");
		post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles", "application/json", documentoAccesible);
		assertIsOk(post);
		
		List<TipoDocumentoAccesible> all = fetchAll(evaluacion.id);
		assertEquals(3, all.size());
		
		List<TipoDocumentoAccesible> limit1 = parseAll(GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles" + "?pageSize=1"));
		assertEquals(1, limit1.size());
		assertEquals(all.get(0), limit1.get(0));

		List<TipoDocumentoAccesible> limit1Start2 = parseAll(GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles" + "?pageSize=1&pageStartIndex=2"));
		assertEquals(1, limit1Start2.size());
		assertEquals(all.get(2), limit1Start2.get(0));
	}
	
	@Test
	public void get(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String documentoAccesible = documentoAccesibleJson("uri");
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles", "application/json", documentoAccesible);
		assertIsOk(post);

		TipoDocumentoAccesible tipoDocumentoAccesible = new Gson().fromJson(getContent(post), TipoDocumentoAccesible.class);
		Response get = GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles/" + tipoDocumentoAccesible.id);
		assertIsOk(get);
		tipoDocumentoAccesible = new Gson().fromJson(getContent(get), TipoDocumentoAccesible.class);
		assertNotNull(tipoDocumentoAccesible);
		assertNotNull(tipoDocumentoAccesible.id);
		assertEquals("uri",tipoDocumentoAccesible.uri);
	}
	
	@Test
	public void post(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String documentoAccesible = documentoAccesibleJson("uri");
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles", "application/json", documentoAccesible);
		assertIsOk(post);
		
		TipoDocumentoAccesible tipoDocumentoAccesible = new Gson().fromJson(getContent(post), TipoDocumentoAccesible.class);
		assertNotNull(tipoDocumentoAccesible.id);
		assertEquals("uri", tipoDocumentoAccesible.uri);
	}
	
	@Test
	public void delete(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String documentoAccesible = documentoAccesibleJson("uri");
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles", "application/json", documentoAccesible);
		assertIsOk(post);
		
		TipoDocumentoAccesible tipoDocumentoAccesible = new Gson().fromJson(getContent(post), TipoDocumentoAccesible.class);
		assertNotNull(tipoDocumentoAccesible);
		assertNotNull(tipoDocumentoAccesible.id);
		
		List<TipoDocumentoAccesible> all = fetchAll(evaluacion.id);
		assertNotNull(all);
		assertEquals(1, all.size());
		
		Response delete = DELETE(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles/"+tipoDocumentoAccesible.id);
		assertIsOk(delete);
		
		all = fetchAll(evaluacion.id);
		assertNotNull(all);
		assertEquals(0, all.size());
	}
	
	@Test
	public void put(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String documentoAccesible = documentoAccesibleJson("uri");
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles", "application/json", documentoAccesible);
		assertIsOk(post);
				
		TipoDocumentoAccesible tipoDocumentoAccesible = new Gson().fromJson(getContent(post), TipoDocumentoAccesible.class);

		documentoAccesible = documentoAccesibleJson("uri2");
		
		Response put = PUT(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles/"+tipoDocumentoAccesible.id, "application/json", documentoAccesible);
		assertIsOk(put);
		tipoDocumentoAccesible = new Gson().fromJson(getContent(put), TipoDocumentoAccesible.class);
		assertNotNull(tipoDocumentoAccesible);
		assertEquals("uri2", tipoDocumentoAccesible.uri);
	}
	
	@Test
	public void notFound(){
		int randomId = 25;
		Response put = PUT(TiposEvaluacionesURL + "/" + randomId + "/tiposdocumentosaccesibles/" + randomId, "application/json", "");
		assertIsNotFound(put);
		
		Response get = GET(TiposEvaluacionesURL + "/" + randomId + "/tiposdocumentosaccesibles/" + randomId);
		assertIsNotFound(get);
		
		Response delete = DELETE(TiposEvaluacionesURL + "/" + randomId + "/tiposdocumentosaccesibles/" + randomId);
		assertIsNotFound(delete);
	}
	
	public ValidationErrors checkValidationErrors(Response response){
		assertEquals((int)Http.StatusCode.BAD_REQUEST, (int)response.status);
		ValidationErrors errors = new Gson().fromJson(getContent(response), ValidationErrors.class);
		return errors;
	}
	
	@Test
	public void badRequestPost(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String documentoAccesible = documentoAccesibleJson(null);
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles", "application/json", documentoAccesible);
		ValidationErrors errores = checkValidationErrors(post);
		assertTrue(errores.contains("tipoDocumentoAccesible.uri", "Required"));
	}
	
	@Test
	public void badRequestPut(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		String documentoAccesible = documentoAccesibleJson("uri");
		// Insertamos
		Response post = POST(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles", "application/json", documentoAccesible);
		assertIsOk(post);
		TipoDocumentoAccesible tipoDocumentoAccesible = new Gson().fromJson(getContent(post), TipoDocumentoAccesible.class);
		
		documentoAccesible = documentoAccesibleJson(null);
		Response put = PUT(TiposEvaluacionesURL + "/" + evaluacion.id + "/tiposdocumentosaccesibles/" + tipoDocumentoAccesible.id, "application/json", documentoAccesible);
		ValidationErrors errores = checkValidationErrors(put);				
		assertTrue(errores.contains("tipoDocumentoAccesible.uri", "Required"));
	}
	
	public void badRequestWithIncorrectId(Response response){
		ValidationErrors errores = checkValidationErrors(response);
		assertTrue(errores.contains("id", "Formato incorrecto"));
	}
	
	@Test
	public void badRequestGet(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		badRequestWithIncorrectId(GET(TiposEvaluacionesURL + "/" + evaluacion.id +  "/tiposdocumentosaccesibles/notAValidId"));
		badRequestWithIncorrectId(PUT(TiposEvaluacionesURL + "/" + evaluacion.id +  "/tiposdocumentosaccesibles/notAValidId", "application/json", ""));
		badRequestWithIncorrectId(DELETE(TiposEvaluacionesURL + "/" + evaluacion.id +  "/tiposdocumentosaccesibles/notAValidId"));
	}
	
	@Test
	public void badRequestAllPaginate(){
		Response evalPost = CrearEvaluacionJson("procedimiento", "nombre", true, false);
		TipoEvaluacion evaluacion = new Gson().fromJson(getContent(evalPost), TipoEvaluacion.class);
		Response get = GET(TiposEvaluacionesURL+"/"+evaluacion.id+"/tiposdocumentosaccesibles" + "?pageSize=a&pageStartIndex=b");
		ValidationErrors errores = checkValidationErrors(get);
		assertTrue(errores.contains("pageSize", "Formato incorrecto"));
		assertTrue(errores.contains("pageStartIndex", "Formato incorrecto"));
	}

}
