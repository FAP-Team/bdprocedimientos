package api;

import java.lang.reflect.Type;
import java.util.List;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import models.TipoCriterio;
import models.TipoEvaluacion;
import models.CriterioListaValor;

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

public class CriterioListaValorTest extends FunctionalTest{
	
    public static final String TiposEvaluacionesURL = "/tiposevaluaciones";
    
    private TipoEvaluacion TE;
    private TipoCriterio TC;
	
	@Before
	public void setup(){
		Fixtures.deleteDatabase();
	}
	
	private void CrearDependencias(String eProcedimiento, String eNombre, boolean eComentariosSolicitante, boolean eComentariosAdministracion, String cNombre, String cClase, String cJerarquia, String cTipoValor, Integer cTransparencia, boolean cComentariosAdministracion, boolean cComentariosSolicitante){
		// Creamos evaluacion
		TipoEvaluacionesTest tTE = new TipoEvaluacionesTest();
		String evaluacion = tTE.evaluacionJson(eProcedimiento, eNombre, eComentariosSolicitante, eComentariosAdministracion);
		Response post = POST(TiposEvaluacionesURL, "application/json", evaluacion);
		assertIsOk(post);
		TE = new Gson().fromJson(getContent(post), TipoEvaluacion.class);
		// Creamos el criterio
		TipoCriteriosTest tTC = new TipoCriteriosTest();
		String criterio = tTC.criterioJson(cNombre, cClase, cJerarquia, cTipoValor, cTransparencia, cComentariosAdministracion, cComentariosSolicitante);
		post = POST(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios", "application/json", criterio);
		assertIsOk(post);
	    TC = new Gson().fromJson(getContent(post), TipoCriterio.class);
	}
	
	public String listaValorJson(double valor, String descripcion){
		JsonObject listaValor = new JsonObject();
	    listaValor.addProperty("valor", valor);
	    listaValor.addProperty("descripcion", descripcion);
		return listaValor.toString();
	}
	
	private List<CriterioListaValor> parseAll(Response response){
		Type type = new TypeToken<List<CriterioListaValor>>(){}.getType();
		List<CriterioListaValor> criteriosListaValor= new Gson().fromJson(getContent(response), type);
		return criteriosListaValor;
	}
	
	public List<CriterioListaValor> fetchAll(Long Eid, Long Cid){
		Response get = GET(TiposEvaluacionesURL+"/"+Eid+"/tiposcriterios/"+Cid+"/listavalores");
		assertIsOk(get);
		return parseAll(get);
	}
	
	@Test
	public void all (){
		
		CrearDependencias("procedimiento", "Nombre", true, false, "nombre", "manual", "1.1.1", "lista", 8, true, false);
		
		// Creamos el tipo de valor
		String listaValor = listaValorJson(4.815, "descripcion");
		Response post = POST(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores", "application/json", listaValor);
		assertIsOk(post);
		post = POST(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores", "application/json", listaValor);
		assertIsOk(post);
		post = POST(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores", "application/json", listaValor);
		assertIsOk(post);
		
		Response get = GET(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores");
		assertIsOk(get);
		
		Type type = new TypeToken<List<CriterioListaValor>>(){}.getType();
		List<CriterioListaValor> criterioListaValor = new Gson().fromJson(getContent(get), type);
		assertNotNull(criterioListaValor);
		assertEquals(3, criterioListaValor.size());
		CriterioListaValor first = criterioListaValor.iterator().next();
		assertNotNull(first);
		assertNotNull(first.valor);
		assertEquals(String.valueOf(4.815), String.valueOf(first.valor));
		assertEquals("descripcion", first.descripcion);
	}
	
	@Test
	public void allPaginate(){
		
		CrearDependencias("procedimiento", "Nombre", true, false, "nombre", "manual", "1.1.1", "lista", 8, true, false);
		
		// Creamos el tipo de valor
		String listaValor = listaValorJson(4.815, "descripcion");
		Response post = POST(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores", "application/json", listaValor);
		assertIsOk(post);
		post = POST(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores", "application/json", listaValor);
		assertIsOk(post);
		post = POST(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores", "application/json", listaValor);
		assertIsOk(post);
		
		List<CriterioListaValor> all = fetchAll(TE.id, TC.id);
		assertEquals(3, all.size());
		
		List<CriterioListaValor> limit1 = parseAll(GET(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores" + "?pageSize=1"));
		assertEquals(1, limit1.size());
		assertEquals(all.get(0), limit1.get(0));

		List<CriterioListaValor> limit1Start2 = parseAll(GET(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores" + "?pageSize=1&pageStartIndex=2"));
		assertEquals(1, limit1Start2.size());
		assertEquals(all.get(2), limit1Start2.get(0));
	}
	
	@Test
	public void get(){
		CrearDependencias("procedimiento", "Nombre", true, false, "nombre", "manual", "1.1.1", "lista", 8, true, false);
		
		// Creamos el tipo de valor
		String listaValor = listaValorJson(4.815, "descripcion");
		Response post = POST(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores", "application/json", listaValor);
		assertIsOk(post);

		CriterioListaValor criterioListaValor= new Gson().fromJson(getContent(post), CriterioListaValor.class);
		
		Response get = GET(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores/" + criterioListaValor.id);
		assertIsOk(get);
		criterioListaValor = new Gson().fromJson(getContent(get), CriterioListaValor.class);
		assertNotNull(criterioListaValor);
		assertNotNull(criterioListaValor.id);
		assertEquals("4.815", String.valueOf(criterioListaValor.valor));
		assertEquals("descripcion", criterioListaValor.descripcion);
	}
	
	@Test
	public void post(){
		
		CrearDependencias("procedimiento", "Nombre", true, false, "nombre", "manual", "1.1.1", "lista", 8, true, false);
		
		// Creamos el tipo de valor
		String listaValor = listaValorJson(4.815, "descripcion");
		Response post = POST(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores", "application/json", listaValor);
		assertIsOk(post);

		CriterioListaValor criterioListaValor= new Gson().fromJson(getContent(post), CriterioListaValor.class);
		
		assertNotNull(criterioListaValor.id);
		assertEquals("4.815", criterioListaValor.valor.toString());
		assertEquals("descripcion", criterioListaValor.descripcion);
	}
	
	@Test
	public void delete(){
		
		CrearDependencias("procedimiento", "Nombre", true, false, "nombre", "manual", "1.1.1", "lista", 8, true, false);
		
		// Creamos el tipo de valor
		String listaValor = listaValorJson(4.815, "descripcion");
		Response post = POST(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores", "application/json", listaValor);
		assertIsOk(post);

		CriterioListaValor criterioListaValor= new Gson().fromJson(getContent(post), CriterioListaValor.class);
		
		assertNotNull(criterioListaValor);
		assertNotNull(criterioListaValor.id);
		
		List<CriterioListaValor> all = fetchAll(TE.id, TC.id);
		assertNotNull(all);
		assertEquals(1, all.size());
		
		Response delete = DELETE(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores/"+criterioListaValor.id);
		assertIsOk(delete);
		
		all = fetchAll(TE.id, TC.id);
		assertNotNull(all);
		assertEquals(0, all.size());
	}
	
	@Test
	public void put(){
		CrearDependencias("procedimiento", "Nombre", true, false, "nombre", "manual", "1.1.1", "lista", 8, true, false);
		
		// Creamos el tipo de valor
		String listaValor = listaValorJson(4.815, "descripcion");
		Response post = POST(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores", "application/json", listaValor);
		assertIsOk(post);

		CriterioListaValor criterioListaValor= new Gson().fromJson(getContent(post), CriterioListaValor.class);

		listaValor = listaValorJson(16.234, "descripcion 2");
		
		Response put = PUT(TiposEvaluacionesURL+"/"+TE.id+"/tiposcriterios/"+TC.id+"/listavalores/"+criterioListaValor.id, "application/json", listaValor);
		assertIsOk(put);
		criterioListaValor = new Gson().fromJson(getContent(put), CriterioListaValor.class);
		
		assertNotNull(criterioListaValor);
		assertEquals("16.234", criterioListaValor.valor.toString());
		assertEquals("descripcion 1", criterioListaValor.descripcion);
	}

}
