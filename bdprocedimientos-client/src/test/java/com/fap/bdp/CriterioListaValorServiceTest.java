package com.fap.bdp;

import org.junit.Assert;
import org.junit.Test;

import com.fap.bdp.domain.ClaseCriterio;
import com.fap.bdp.domain.CriterioListaValor;
import com.fap.bdp.domain.TipoCriterio;
import com.fap.bdp.domain.TipoEvaluacion;
import com.fap.bdp.domain.TipoValorCriterio;
import com.fap.bdp.exceptions.BDPNotFoundException;
import com.fap.bdp.exceptions.BDPValidationException;

public class CriterioListaValorServiceTest {

	private static final String endPoint = "http://localhost:9000";
    private BDProcedimientosService service = new BDProcedimientosService(endPoint);
    
    public CriterioListaValor crearCriterioListaValor (Double valor, String descripcion){
    	CriterioListaValor criterioListaValor = new CriterioListaValor();
    	criterioListaValor.setValor(valor);
    	criterioListaValor.setDescripcion(descripcion);
    	return criterioListaValor;
    }
    
    public boolean borrarCriterioListaValor(long eId, long cId, long clvId) throws Exception{
    	service.deleteCriterioListaValor(eId, cId, clvId);
    	try {
    		service.getCriterioListaValor(eId, cId, clvId);
    		Assert.assertTrue(false);
    		return true;
    	}catch(BDPNotFoundException e){
    		//Not found
    		return false;
    	}
    }
    
    @Test
    public void getCriterioListaValor() throws Exception {   
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	TipoCriterioServiceTest TC = new TipoCriterioServiceTest();
    	TipoCriterio tipoCriterio = TC.crearTipoCriterio("nombre", ClaseCriterio.manual, "1.1.1", TipoValorCriterio.cantidad, 8, true, false);
    	tipoCriterio = service.createTipoCriterio(tipoE.getId(), tipoCriterio);
    	Assert.assertNotNull(tipoCriterio);
    	
    	CriterioListaValor criterioListaValor = crearCriterioListaValor(4.815, "descripcion");
    	criterioListaValor = service.createCriterioListaValor(tipoE.getId(), tipoCriterio.getId(), criterioListaValor);
    	Assert.assertNotNull(criterioListaValor);
    	
        criterioListaValor = service.getCriterioListaValor(tipoE.getId(), tipoCriterio.getId(), criterioListaValor.getId());
        Assert.assertNotNull(criterioListaValor);
        Assert.assertNotNull(criterioListaValor.getId());
        Assert.assertNotNull(criterioListaValor.getValor());
        Assert.assertEquals("4.815", criterioListaValor.getValor().toString());
        Assert.assertEquals("descripcion", criterioListaValor.getDescripcion());
        
        // BORRAR para que no quede la BBDD con basura
        borrarCriterioListaValor(tipoE.getId(), tipoCriterio.getId(), criterioListaValor.getId());
        TE.deleteTipoEvaluacion(tipoE);
    }
    
    @Test
    public void putCriterioListaValor() throws Exception {
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	TipoCriterioServiceTest TC = new TipoCriterioServiceTest();
    	TipoCriterio tipoCriterio = TC.crearTipoCriterio("nombre", ClaseCriterio.manual, "1.1.1", TipoValorCriterio.cantidad, 8, true, false);
    	tipoCriterio = service.createTipoCriterio(tipoE.getId(), tipoCriterio);
    	Assert.assertNotNull(tipoCriterio);
    	
    	CriterioListaValor criterioListaValor = crearCriterioListaValor(4.815, "descripcion");
    	criterioListaValor = service.createCriterioListaValor(tipoE.getId(), tipoCriterio.getId(), criterioListaValor);
    	Assert.assertNotNull(criterioListaValor);
    	
    	CriterioListaValor criterioListaValorS = crearCriterioListaValor(16.234, "descripcion 2");
    	criterioListaValorS.setId(criterioListaValor.getId());
    	
    	criterioListaValorS = service.updateCriterioListaValor(tipoE.getId(), tipoCriterio.getId(), criterioListaValorS);
    	
    	Assert.assertNotNull(criterioListaValorS);
        Assert.assertNotNull(criterioListaValorS.getId());
        Assert.assertNotNull(criterioListaValorS.getValor());
        Assert.assertEquals("descripcion 2", criterioListaValorS.getDescripcion());
        Assert.assertEquals("16.234", criterioListaValorS.getValor().toString());
        
        // BORRAR para que no quede la BBDD con basura
        borrarCriterioListaValor(tipoE.getId(), tipoCriterio.getId(), criterioListaValorS.getId());
        TE.deleteTipoEvaluacion(tipoE);
    }
    
    @Test(expected=BDPNotFoundException.class)
    public void getTipoCriterioNotFound() throws Exception {
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	TipoCriterioServiceTest TC = new TipoCriterioServiceTest();
    	TipoCriterio tipoCriterio = TC.crearTipoCriterio("nombre", ClaseCriterio.manual, "1.1.1", TipoValorCriterio.cantidad, 8, true, false);
    	tipoCriterio = service.createTipoCriterio(tipoE.getId(), tipoCriterio);
    	Assert.assertNotNull(tipoCriterio);
    	
    	service.getCriterioListaValor(tipoE.getId(), tipoCriterio.getId(), 1234l);
    	
    	TE.deleteTipoEvaluacion(tipoE);
    }
    
    @Test(expected=BDPValidationException.class)
    public void badRequestPutTipoCriterio() throws Exception{
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	TipoCriterioServiceTest TC = new TipoCriterioServiceTest();
    	TipoCriterio tipoCriterio = TC.crearTipoCriterio("nombre", ClaseCriterio.manual, "1.1.1", TipoValorCriterio.cantidad, 8, true, false);
    	tipoCriterio = service.createTipoCriterio(tipoE.getId(), tipoCriterio);
    	Assert.assertNotNull(tipoCriterio);
    	
    	CriterioListaValor criterioListaValor = crearCriterioListaValor(4.815, "descripcion");
    	criterioListaValor = service.createCriterioListaValor(tipoE.getId(), tipoCriterio.getId(), criterioListaValor);
    	Assert.assertNotNull(criterioListaValor);
    	
    	CriterioListaValor criterioListaValorS = crearCriterioListaValor(16.234, null);
    	criterioListaValorS.setId(criterioListaValor.getId());
    	
    	service.updateCriterioListaValor(tipoE.getId(), tipoCriterio.getId(), criterioListaValorS);
    	
    	TE.deleteTipoEvaluacion(tipoE);
    }
	
}
