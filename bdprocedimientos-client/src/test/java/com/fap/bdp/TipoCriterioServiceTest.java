package com.fap.bdp;

import org.junit.Assert;
import org.junit.Test;

import com.fap.bdp.domain.ClaseCriterio;
import com.fap.bdp.domain.TipoCriterio;
import com.fap.bdp.domain.TipoEvaluacion;
import com.fap.bdp.domain.TipoValorCriterio;
import com.fap.bdp.exceptions.BDPNotFoundException;
import com.fap.bdp.exceptions.BDPValidationException;

public class TipoCriterioServiceTest {
	
	private static final String endPoint = "http://localhost:9000";
    private BDProcedimientosService service = new BDProcedimientosService(endPoint);
    
    public TipoCriterio crearTipoCriterio(String nombre, ClaseCriterio clase, String jerarquia, TipoValorCriterio tipoValor, Integer transparencia, boolean comentariosAdministracion, boolean comentariosSolicitante){
    	TipoCriterio tipoCriterio = new TipoCriterio();
    	tipoCriterio.setNombre(nombre);
    	tipoCriterio.setClase(clase);
    	tipoCriterio.setJerarquia(jerarquia);
    	tipoCriterio.setTipoValor(tipoValor);
    	tipoCriterio.setTransparencia(transparencia);
    	tipoCriterio.setComentariosSolicitante(comentariosSolicitante);
    	tipoCriterio.setComentariosAdministracion(comentariosAdministracion);
    	return tipoCriterio;
    }
    
    public boolean borrarTipoCriterio(long eId, long cId) throws Exception{
    	service.deleteTipoCriterio(eId, cId);
    	try {
    		service.getTipoCriterio(eId, cId);
    		Assert.assertTrue(false);
    		return true;
    	}catch(BDPNotFoundException e){
    		//Not found
    		return false;
    	}
    }
	
//    @Test 
//    public void allTipoCriterio() throws Exception {
//    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
//    	// Insertamos Evaluacion DESPUES BORRAR!!
//    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
//    	tipoE = service.createTipoEvaluacion(tipoE);
//    	Assert.assertNotNull(tipoE);
//    	
//    	TipoCriterio tipoCriterio = crearTipoCriterio("nombre", ClaseCriterio.manual, "1.1.1", TipoValorCriterio.cantidad, 8, true, false);
//    	tipoCriterio = service.createTipoCriterio(tipoE.getId(), tipoCriterio);
//    	Assert.assertNotNull(tipoCriterio);
//    	tipoCriterio = service.createTipoCriterio(tipoE.getId(), tipoCriterio);
//    	Assert.assertNotNull(tipoCriterio);
//    	tipoCriterio = service.createTipoCriterio(tipoE.getId(), tipoCriterio);
//    	Assert.assertNotNull(tipoCriterio);
//    	
//    	List<TipoCriterio> tiposCriterios = service.getTiposCriterios(tipoE.getId());
//    	
//    	Assert.assertNotNull(tiposCriterios);
//    	Assert.assertEquals(3, tiposCriterios.size());
//    	
//		TipoCriterio first = tiposCriterios.iterator().next();
//		
//		Assert.assertNotNull(first);
//		Assert.assertNotNull(first.getJerarquia());
//		Assert.assertEquals("nombre", first.getNombre());
//		Assert.assertEquals(ClaseCriterio.manual, first.getClase());
//		Assert.assertEquals(TipoValorCriterio.cantidad, first.getTipoValor());
//    	
//		// BORRAR para que no quede la BBDD con basura
//        TE.deleteTipoEvaluacion(tipoE);
//    }
//    
//    @Test
//	public void allPaginateTipoCriterio() throws Exception{
//    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
//    	// Insertamos Evaluacion DESPUES BORRAR!!
//    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
//    	tipoE = service.createTipoEvaluacion(tipoE);
//    	Assert.assertNotNull(tipoE);
//    	
//    	TipoCriterio tipoCriterio = crearTipoCriterio("nombre", ClaseCriterio.manual, "1.1.1", TipoValorCriterio.cantidad, 8, true, false);
//    	tipoCriterio = service.createTipoCriterio(tipoE.getId(), tipoCriterio);
//    	Assert.assertNotNull(tipoCriterio);
//    	tipoCriterio = service.createTipoCriterio(tipoE.getId(), tipoCriterio);
//    	Assert.assertNotNull(tipoCriterio);
//    	tipoCriterio = service.createTipoCriterio(tipoE.getId(), tipoCriterio);
//    	Assert.assertNotNull(tipoCriterio);
//    	
//    	List<TipoCriterio> tiposCriterios = service.getTiposCriterios(tipoE.getId());
//    	
//    	Assert.assertNotNull(tiposCriterios);
//    	Assert.assertEquals(3, tiposCriterios.size());  	
//    }
    
    @Test
    public void getTipoCriterio() throws Exception {   
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	TipoCriterio tipoCriterio = crearTipoCriterio("nombre", ClaseCriterio.manual, "1.1.1", TipoValorCriterio.cantidad, 8, true, false);
    	tipoCriterio = service.createTipoCriterio(tipoE.getId(), tipoCriterio);
    	Assert.assertNotNull(tipoCriterio);
    	
    	tipoCriterio = service.getTipoCriterio(tipoE.getId(), tipoCriterio.getId());
        Assert.assertNotNull(tipoCriterio);
        Assert.assertNotNull(tipoCriterio.getId());
        Assert.assertNotNull(tipoCriterio.getNombre());
        Assert.assertEquals(ClaseCriterio.manual, tipoCriterio.getClase());
        Assert.assertEquals(TipoValorCriterio.cantidad, tipoCriterio.getTipoValor());
        
        // BORRAR para que no quede la BBDD con basura
        borrarTipoCriterio(tipoE.getId(), tipoCriterio.getId());
        TE.deleteTipoEvaluacion(tipoE);
    }
    
    @Test
    public void putTipoCriterio() throws Exception {   
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	TipoCriterio tipoCriterio = crearTipoCriterio("nombre", ClaseCriterio.manual, "1.1.1", TipoValorCriterio.cantidad, 8, true, false);
    	tipoCriterio = service.createTipoCriterio(tipoE.getId(), tipoCriterio);
    	Assert.assertNotNull(tipoCriterio);
    	
    	TipoCriterio tipoCriterioS = crearTipoCriterio("nombre 2", ClaseCriterio.automatico, "1.2.1", TipoValorCriterio.cantidad, 8, true, false);
    	tipoCriterioS.setId(tipoCriterio.getId());
    	
    	tipoCriterioS = service.updateTipoCriterio(tipoE.getId(), tipoCriterioS);
        Assert.assertNotNull(tipoCriterioS);
        Assert.assertNotNull(tipoCriterioS.getId());
        Assert.assertNotNull(tipoCriterioS.getNombre());
        Assert.assertEquals(ClaseCriterio.automatico, tipoCriterioS.getClase());
        Assert.assertEquals("nombre 2", tipoCriterioS.getNombre());
        
        // BORRAR para que no quede la BBDD con basura
        borrarTipoCriterio(tipoE.getId(), tipoCriterioS.getId());
        TE.deleteTipoEvaluacion(tipoE);
    }
    
    @Test(expected=BDPNotFoundException.class)
    public void getTipoCriterioNotFound() throws Exception {
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	service.getTipoCriterio(tipoE.getId(), 1234l);
    	
    	TE.deleteTipoEvaluacion(tipoE);
    }
    
    @Test(expected=BDPValidationException.class)
    public void badRequestPutTipoCriterio() throws Exception{
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	TipoCriterio tipoCriterio = crearTipoCriterio("nombre", ClaseCriterio.manual, "1.1.1", TipoValorCriterio.cantidad, 8, true, false);
    	tipoCriterio = service.createTipoCriterio(tipoE.getId(), tipoCriterio);
    	Assert.assertNotNull(tipoCriterio);
    	
    	TipoCriterio tipoCriterioS = crearTipoCriterio(null, ClaseCriterio.automatico, "1.2.1", TipoValorCriterio.cantidad, 8, true, false);
    	tipoCriterioS.setId(tipoCriterio.getId());
    	
    	service.updateTipoCriterio(tipoE.getId(), tipoCriterioS);
    	
    	TE.deleteTipoEvaluacion(tipoE);
    }

}
