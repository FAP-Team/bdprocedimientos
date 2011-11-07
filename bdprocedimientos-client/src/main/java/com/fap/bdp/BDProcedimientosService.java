package com.fap.bdp;

import java.util.List;

import com.fap.bdp.domain.CriterioListaValor;
import com.fap.bdp.domain.TipoAreaEvaluacion;
import com.fap.bdp.domain.TipoCEconomico;
import com.fap.bdp.domain.TipoCriterio;
import com.fap.bdp.domain.TipoDocumentoAccesible;
import com.fap.bdp.domain.TipoEvaluacion;

public class BDProcedimientosService {

	private RestTemplate restTemplate;

	public BDProcedimientosService(String baseUrl) {
		restTemplate = new RestTemplate(baseUrl);
	}

	public List<TipoEvaluacion> getTiposEvaluaciones() throws Exception {
		return restTemplate.getList(Routes.getUrlTiposEvaluaciones(), TipoEvaluacion.class);
	}

	public List<TipoCriterio> getTiposCriterios(Long idTipoEvaluacion) throws Exception {
		return restTemplate.getList(Routes.getUrlTiposCriterios(idTipoEvaluacion), TipoCriterio.class);
	}
	
	public List<CriterioListaValor> getCriterioListaValores(Long idTipoEvaluacion, Long idTipoCriterio) throws Exception {
		return restTemplate.getList(Routes.getUrlCriterioListaValor(idTipoEvaluacion, idTipoCriterio), CriterioListaValor.class);
	}
	
	public List<TipoCEconomico> getTiposCEconomicos(Long idTipoEvaluacion) throws Exception {
		return restTemplate.getList(Routes.getUrlTiposCEconomicos(idTipoEvaluacion), TipoCEconomico.class);
	}
	
	public List<TipoAreaEvaluacion> getTiposAreasEvaluacion() throws Exception {
		return restTemplate.getList(Routes.getUrlTiposAreasEvaluacion(), TipoAreaEvaluacion.class);
	}
	
	public List<TipoDocumentoAccesible> getTiposDocumentosAccesibles(Long idTipoEvaluacion) throws Exception{
		return restTemplate.getList(Routes.getUrlTiposDocumentosAccesibles(idTipoEvaluacion), TipoDocumentoAccesible.class);
	}
		
	public TipoEvaluacion getTipoEvaluacion(Long idTipoEvaluacion) throws Exception {
		return restTemplate.get(Routes.getUrlTiposEvaluaciones(idTipoEvaluacion), TipoEvaluacion.class);
	}

	public TipoCriterio getTipoCriterio(Long idTipoEvaluacion, Long idTipoCriterio) throws Exception {
		return restTemplate.get(Routes.getUrlTiposCriterios(idTipoEvaluacion, idTipoCriterio), TipoCriterio.class);
	}
	
	public CriterioListaValor getCriterioListaValor(Long idTipoEvaluacion, Long idTipoCriterio, Long idCriterioListaValor) throws Exception {
		return restTemplate.get(Routes.getUrlCriterioListaValor(idTipoEvaluacion, idTipoCriterio, idCriterioListaValor), CriterioListaValor.class);
	}
	
	public TipoCEconomico getTipoCEconomico(Long idTipoEvaluacion, Long idTipoCEcoonmico) throws Exception {
		return restTemplate.get(Routes.getUrlTiposCEconomicos(idTipoEvaluacion, idTipoCEcoonmico), TipoCEconomico.class);
	}
	
	public TipoDocumentoAccesible getTipoDocumentoAccesible(Long idTipoEvaluacion, Long idTipoDocumentoAccesible) throws Exception {
		return restTemplate.get(Routes.getUrlTiposDocumentosAccesibles(idTipoEvaluacion, idTipoDocumentoAccesible), TipoDocumentoAccesible.class);
	}
		
	public TipoAreaEvaluacion getTipoAreaEvaluacion(Long idTipoAreaEvaluacion) throws Exception {
		return restTemplate.get(Routes.getUrlTiposAreasEvaluacion(idTipoAreaEvaluacion), TipoAreaEvaluacion.class);
	}
	
	public TipoEvaluacion createTipoEvaluacionRecusive(TipoEvaluacion tipoEvaluacion) throws Exception {
		TipoEvaluacion tipoEvaluacionCreated = createTipoEvaluacion(tipoEvaluacion);
		for(TipoCriterio tipoCriterio : tipoEvaluacion.getTiposCriterios()){
			TipoCriterio tipoCriterioCreated = createTipoCriterio(tipoEvaluacionCreated.getId(), tipoCriterio);
			for(CriterioListaValor criterioListaValor : tipoCriterio.getListaValores()){
				createCriterioListaValor(tipoEvaluacionCreated.getId(), tipoCriterioCreated.getId(), criterioListaValor);
			}
		}
		for(TipoCEconomico tipoCEconomico : tipoEvaluacion.getTiposCEconomicos()){
			createTipoCEconomico(tipoEvaluacionCreated.getId(), tipoCEconomico);
		}
		for(TipoDocumentoAccesible tipoDocumentoAccesible : tipoEvaluacion.getTiposDocumentosAccesibles()){
			createTipoDocumentoAccesible(tipoEvaluacionCreated.getId(), tipoDocumentoAccesible);
		}
		return getTipoEvaluacion(tipoEvaluacionCreated.getId());
	}
	
	public TipoEvaluacion createTipoEvaluacion(TipoEvaluacion tipoEvaluacion) throws Exception {
		return restTemplate.create(Routes.getUrlTiposEvaluaciones(), tipoEvaluacion, TipoEvaluacion.class);
	}
	
	public TipoCriterio createTipoCriterio(Long idTipoEvaluacion, TipoCriterio tipoCriterio) throws Exception {
		return restTemplate.create(Routes.getUrlTiposCriterios(idTipoEvaluacion), tipoCriterio, TipoCriterio.class);
	}
	
	public CriterioListaValor createCriterioListaValor(Long idTipoEvaluacion, Long idTipoCriterio, CriterioListaValor criterioListaValor) throws Exception {
		return restTemplate.create(Routes.getUrlCriterioListaValor(idTipoEvaluacion, idTipoCriterio), criterioListaValor, CriterioListaValor.class);
	}
	
	public TipoCEconomico createTipoCEconomico(Long idTipoEvaluacion, TipoCEconomico tipoCEconomico) throws Exception {
		return restTemplate.create(Routes.getUrlTiposCEconomicos(idTipoEvaluacion), tipoCEconomico, TipoCEconomico.class);
	}
	
	public TipoDocumentoAccesible createTipoDocumentoAccesible(Long idTipoEvaluacion, TipoDocumentoAccesible tipoDocumentoAccesible) throws Exception {
		return restTemplate.create(Routes.getUrlTiposDocumentosAccesibles(idTipoEvaluacion), tipoDocumentoAccesible, TipoDocumentoAccesible.class);
	}

	public TipoAreaEvaluacion createTipoAreaEvaluacion(TipoAreaEvaluacion tipoAreaEvaluacion) throws Exception {
		return restTemplate.create(Routes.getUrlTiposAreasEvaluacion(), tipoAreaEvaluacion, TipoAreaEvaluacion.class);
	}
	
	public void deleteTipoEvaluacion(Long idTipoEvaluacion) throws Exception {
		restTemplate.delete(Routes.getUrlTiposEvaluaciones(idTipoEvaluacion));
	}
	
	public void deleteTipoCriterio(Long idTipoEvaluacion, Long idTipoCriterio) throws Exception {
		restTemplate.delete(Routes.getUrlTiposCriterios(idTipoEvaluacion, idTipoCriterio));
	}
	
	public void deleteCriterioListaValor(Long idTipoEvaluacion, Long idTipoCriterio, Long idCriterioListaValor) throws Exception {
		restTemplate.delete(Routes.getUrlCriterioListaValor(idTipoEvaluacion, idTipoCriterio, idCriterioListaValor));
	}
	
	public void deleteTipoCEconomico(Long idTipoEvaluacion, Long idTipoCEconomico) throws Exception {
		restTemplate.delete(Routes.getUrlTiposCEconomicos(idTipoEvaluacion, idTipoCEconomico));
	}
	
	public void deleteTipoDocumentoAccesible(Long idTipoEvaluacion, Long idTipoDocumentoAccesible) throws Exception {
		restTemplate.delete(Routes.getUrlTiposDocumentosAccesibles(idTipoEvaluacion, idTipoDocumentoAccesible));
	}
	
	public void deleteTipoAreaEvaluacion(Long idTipoAreaEvaluacion) throws Exception {
		restTemplate.delete(Routes.getUrlTiposAreasEvaluacion(idTipoAreaEvaluacion));
	}
	
	public TipoEvaluacion updateTipoEvaluacion(TipoEvaluacion tipoEvaluacion) throws Exception {
		return restTemplate.update(Routes.getUrlTiposEvaluaciones(tipoEvaluacion.getId()), tipoEvaluacion, TipoEvaluacion.class);
	}
	
	public TipoCriterio updateTipoCriterio(Long idEvaluacion, TipoCriterio tipoCriterio) throws Exception {
		return restTemplate.update(Routes.getUrlTiposCriterios(idEvaluacion, tipoCriterio.getId()), tipoCriterio, TipoCriterio.class);
	}
	
	public CriterioListaValor updateCriterioListaValor(Long idTipoEvaluacion, Long idTipoCriterio, CriterioListaValor criterioListaValor) throws Exception {
		return restTemplate.update(Routes.getUrlCriterioListaValor(idTipoEvaluacion, idTipoCriterio, criterioListaValor.getId()), criterioListaValor, CriterioListaValor.class);
	}
	
	public TipoCEconomico updateTipoCEconomico(Long idEvaluacion, TipoCEconomico tipoCEconomico) throws Exception {
		return restTemplate.update(Routes.getUrlTiposCEconomicos(idEvaluacion, tipoCEconomico.getId()), tipoCEconomico, TipoCEconomico.class);
	}
	
	public TipoDocumentoAccesible updateTipoDocumentoAccesible(Long idEvaluacion, TipoDocumentoAccesible tipoDocumentoAccesible) throws Exception {
		return restTemplate.update(Routes.getUrlTiposDocumentosAccesibles(idEvaluacion, tipoDocumentoAccesible.getId()), tipoDocumentoAccesible, TipoDocumentoAccesible.class);
	}
	
	public TipoAreaEvaluacion updateTipoAreaEvaluacion(TipoAreaEvaluacion tipoAreaEvaluacion) throws Exception {
		return restTemplate.update(Routes.getUrlTiposAreasEvaluacion(tipoAreaEvaluacion.getId()), tipoAreaEvaluacion, TipoAreaEvaluacion.class);
	}
}
