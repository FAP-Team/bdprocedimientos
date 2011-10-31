package com.fap.bdp;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpStatus;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;

import com.fap.bdp.domain.CriterioListaValor;
import com.fap.bdp.domain.TipoCEconomico;
import com.fap.bdp.domain.TipoCriterio;
import com.fap.bdp.domain.TipoDocumentoAccesible;
import com.fap.bdp.domain.TipoEvaluacion;
import com.fap.bdp.exceptions.BDPException;
import com.fap.bdp.exceptions.BDPNotFoundException;
import com.fap.bdp.exceptions.BDPValidationException;

public class BDProcedimientosService {

	private String endPoint;

	public BDProcedimientosService(String endPoint) {
		this.endPoint = endPoint;
	}

	public List<TipoEvaluacion> getTiposEvaluaciones() throws Exception {
		return getList(Routes.getUrlTiposEvaluaciones(), TipoEvaluacion.class);
	}

	public List<TipoCriterio> getTiposCriterios(Long idTipoEvaluacion) throws Exception {
		return getList(Routes.getUrlTiposCriterios(idTipoEvaluacion), TipoCriterio.class);
	}
	
	public List<CriterioListaValor> getCriterioListaValores(Long idTipoEvaluacion, Long idTipoCriterio) throws Exception {
		return getList(Routes.getUrlCriterioListaValor(idTipoEvaluacion, idTipoCriterio), CriterioListaValor.class);
	}
	
	public List<TipoCEconomico> getTiposCEconomicos(Long idTipoEvaluacion) throws Exception {
		return getList(Routes.getUrlTiposCEconomicos(idTipoEvaluacion), TipoCEconomico.class);
	}
	
	public List<TipoDocumentoAccesible> getTiposDocumentosAccesibles(Long idTipoEvaluacion) throws Exception{
		return getList(Routes.getUrlTiposDocumentosAccesibles(idTipoEvaluacion), TipoDocumentoAccesible.class);
	}
	
	private <T> List<T> getList(String url, Class<T> clazz) throws Exception {
		ClientRequest request = getRequest(url);
		GenericType<List<T>> type = new GenericType<List<T>>() {};
		ClientResponse<List<T>> response = request.get(type);
		return resultOrError(response);
	}
	
	public TipoEvaluacion getTipoEvaluacion(Long idTipoEvaluacion) throws Exception {
		return get(Routes.getUrlTiposEvaluaciones(idTipoEvaluacion), TipoEvaluacion.class);
	}

	public TipoCriterio getTipoCriterio(Long idTipoEvaluacion, Long idTipoCriterio) throws Exception {
		return get(Routes.getUrlTiposCriterios(idTipoEvaluacion, idTipoCriterio), TipoCriterio.class);
	}
	
	public CriterioListaValor getCriterioListaValor(Long idTipoEvaluacion, Long idTipoCriterio, Long idCriterioListaValor) throws Exception {
		return get(Routes.getUrlCriterioListaValor(idTipoEvaluacion, idTipoCriterio, idCriterioListaValor), CriterioListaValor.class);
	}
	
	public TipoCEconomico getTipoCEconomico(Long idTipoEvaluacion, Long idTipoCEcoonmico) throws Exception {
		return get(Routes.getUrlTiposCEconomicos(idTipoEvaluacion, idTipoCEcoonmico), TipoCEconomico.class);
	}
	
	public TipoDocumentoAccesible getTipoDocumentoAccesible(Long idTipoEvaluacion, Long idTipoDocumentoAccesible) throws Exception {
		return get(Routes.getUrlTiposCEconomicos(idTipoEvaluacion, idTipoDocumentoAccesible), TipoDocumentoAccesible.class);
	}
	
	private <T> T get(String url, Class<T> clazz) throws Exception {
		ClientRequest request = getRequest(url);
		ClientResponse<T> response = request.get(clazz);
		return resultOrError(response);
	}
	
	public TipoEvaluacion createTipoEvaluacion(TipoEvaluacion tipoEvaluacion) throws Exception {
		return create(Routes.getUrlTiposEvaluaciones(), tipoEvaluacion, TipoEvaluacion.class);
	}
	
	public TipoCriterio createTipoCriterio(Long idTipoEvaluacion, TipoCriterio tipoCriterio) throws Exception {
		return create(Routes.getUrlTiposCriterios(idTipoEvaluacion), tipoCriterio, TipoCriterio.class);
	}
	
	public CriterioListaValor createCriterioListaValor(Long idTipoEvaluacion, Long idTipoCriterio, CriterioListaValor criterioListaValor) throws Exception {
		return create(Routes.getUrlCriterioListaValor(idTipoEvaluacion, idTipoCriterio), criterioListaValor, CriterioListaValor.class);
	}
	
	public TipoCEconomico createTipoCEconomico(Long idTipoEvaluacion, TipoCEconomico tipoCEconomico) throws Exception {
		return create(Routes.getUrlTiposCEconomicos(idTipoEvaluacion), tipoCEconomico, TipoCEconomico.class);
	}
	
	public TipoDocumentoAccesible createTipoDocumentoAccesible(Long idTipoEvaluacion, TipoDocumentoAccesible tipoDocumentoAccesible) throws Exception {
		return create(Routes.getUrlTiposDocumentosAccesibles(idTipoEvaluacion), tipoDocumentoAccesible, TipoDocumentoAccesible.class);
	}
	
	public <T> T create(String url, T tipoEvaluacion, Class<T> clazz) throws Exception {
		ClientRequest request = getRequest(url);
		request.body(MediaType.APPLICATION_JSON, tipoEvaluacion);
		ClientResponse<T> response = request.post(clazz);
		return resultOrError(response);
	}	
	
	public void deleteTipoEvaluacion(Long idTipoEvaluacion) throws Exception {
		delete(Routes.getUrlTiposEvaluaciones(idTipoEvaluacion));
	}
	
	public void deleteTipoCriterio(Long idTipoEvaluacion, Long idTipoCriterio) throws Exception {
		delete(Routes.getUrlTiposCriterios(idTipoEvaluacion, idTipoCriterio));
	}
	
	public void deleteCriterioListaValor(Long idTipoEvaluacion, Long idTipoCriterio, Long idCriterioListaValor) throws Exception {
		delete(Routes.getUrlCriterioListaValor(idTipoEvaluacion, idTipoCriterio, idCriterioListaValor));
	}
	
	public void deleteTipoCEconomico(Long idTipoEvaluacion, Long idTipoCEconomico) throws Exception {
		delete(Routes.getUrlTiposCEconomicos(idTipoEvaluacion, idTipoCEconomico));
	}
	
	public void deleteTipoDocumentoAccesible(Long idTipoEvaluacion, Long idTipoDocumentoAccesible) throws Exception {
		delete(Routes.getUrlTiposDocumentosAccesibles(idTipoEvaluacion, idTipoDocumentoAccesible));
	}

	public void delete(String url) throws Exception {
		ClientRequest request = getRequest(url);
		ClientResponse<?> response = request.delete();
		isOk(response);
	}
	
	public TipoEvaluacion updateTipoEvaluacion(TipoEvaluacion tipoEvaluacion) throws Exception {
		return update(Routes.getUrlTiposEvaluaciones(tipoEvaluacion.id), tipoEvaluacion, TipoEvaluacion.class);
	}
	
	public TipoCriterio updateTipoCriterio(Long idEvaluacion, TipoCriterio tipoCriterio) throws Exception {
		return update(Routes.getUrlTiposCriterios(idEvaluacion, tipoCriterio.id), tipoCriterio, TipoCriterio.class);
	}
	
	public CriterioListaValor updateCriterioListaValor(Long idTipoEvaluacion, Long idTipoCriterio, CriterioListaValor criterioListaValor) throws Exception {
		return update(Routes.getUrlCriterioListaValor(idTipoEvaluacion, idTipoCriterio, criterioListaValor.id), criterioListaValor, CriterioListaValor.class);
	}
	
	public TipoCEconomico updateTipoCEconomico(Long idEvaluacion, TipoCEconomico tipoCEconomico) throws Exception {
		return update(Routes.getUrlTiposCriterios(idEvaluacion, tipoCEconomico.id), tipoCEconomico, TipoCEconomico.class);
	}
	
	public TipoCriterio updateTipoDocumentoAccesible(Long idEvaluacion, TipoCriterio tipoCriterio) throws Exception {
		return update(Routes.getUrlTiposCriterios(idEvaluacion, tipoCriterio.id), tipoCriterio, TipoCriterio.class);
	}
	
	public <T> T update(String url, T o, Class<T> clazz) throws Exception {
		ClientRequest request = getRequest(url);
		request.body(MediaType.APPLICATION_JSON, o);
		ClientResponse<T> response = request.put(clazz);
		return resultOrError(response);
	}

	public <T> T resultOrError(ClientResponse<T> response) throws BDPException {
		if(isOk(response)){
			return response.getEntity();
		}
		return null;
	}
		
	private ClientRequest getRequest(String url){
		ClientRequest request = new ClientRequest(endPoint + url);
		request.accept(MediaType.APPLICATION_JSON);
		return request;
	}
		
	protected boolean isOk(ClientResponse<?> response) throws BDPException{
		if(response.getStatus() == HttpStatus.SC_OK){
			return true;
		}
		createError(response);
		return false;
	}
	
	protected void createError(ClientResponse<?> response) throws BDPException {
		String body = response.getEntity(String.class);
		if(response.getStatus() == HttpStatus.SC_NOT_FOUND){
			throw new BDPNotFoundException(body);
		}else if(response.getStatus() == HttpStatus.SC_BAD_REQUEST){
			throw new BDPValidationException(body);
		}
		throw new BDPException(body);
	}
		
}
