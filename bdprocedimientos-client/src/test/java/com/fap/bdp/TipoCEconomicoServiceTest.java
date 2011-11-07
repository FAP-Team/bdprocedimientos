package com.fap.bdp;

import org.junit.Assert;
import org.junit.Test;

import com.fap.bdp.domain.ClaseCEconomico;
import com.fap.bdp.domain.TipoCEconomico;
import com.fap.bdp.domain.TipoEvaluacion;
import com.fap.bdp.exceptions.BDPNotFoundException;
import com.fap.bdp.exceptions.BDPValidationException;

public class TipoCEconomicoServiceTest {
	
	private static final String endPoint = "http://localhost:9000";
    private BDProcedimientosService service = new BDProcedimientosService(endPoint);
	
    public TipoCEconomico crearTipoCEconomico(String nombre, ClaseCEconomico clase, String jerarquia){
    	TipoCEconomico tipoCEconomico = new TipoCEconomico();
    	tipoCEconomico.setNombre(nombre);
    	tipoCEconomico.setClase(clase);
    	tipoCEconomico.setJerarquia(jerarquia);
    	return tipoCEconomico;
    }
    
    public boolean borrarTipoCEconomico(long eId, long ceId) throws Exception{
    	service.deleteTipoCEconomico(eId, ceId);
    	try {
    		service.getTipoCEconomico(eId, ceId);
    		Assert.assertTrue(false);
    		return true;
    	}catch(BDPNotFoundException e){
    		//Not found
    		return false;
    	}
    }
    
//    @Test 
//    public void allTipoCEconomico() throws Exception {
//    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
//    	// Insertamos Evaluacion DESPUES BORRAR!!
//    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
//    	tipoE = service.createTipoEvaluacion(tipoE);
//    	Assert.assertNotNull(tipoE);
//    	
//    	TipoCEconomico tipoCEconomico = crearTipoCEconomico("nombre", ClaseCEconomico.manual, "1.1.1");
//    	tipoCEconomico = service.createTipoCEconomico(tipoE.getId(), tipoCEconomico);
//    	Assert.assertNotNull(tipoCEconomico);
//    	tipoCEconomico = service.createTipoCEconomico(tipoE.getId(), tipoCEconomico);
//    	Assert.assertNotNull(tipoCEconomico);
//    	tipoCEconomico = service.createTipoCEconomico(tipoE.getId(), tipoCEconomico);
//    	Assert.assertNotNull(tipoCEconomico);
//    	
//    	List<TipoCEconomico> tiposCEconomicos = service.getTiposCEconomicos(tipoE.getId());
//    	
//    	Assert.assertNotNull(tiposCEconomicos);
//    	Assert.assertEquals(3, tiposCEconomicos.size());
//    	
//		TipoCEconomico first = tiposCEconomicos.iterator().next();
//		
//		Assert.assertNotNull(first);
//		Assert.assertNotNull(first.getJerarquia());
//		Assert.assertEquals("nombre", first.getJerarquia());
//		Assert.assertEquals(ClaseCEconomico.manual, first.getClase());
//		Assert.assertEquals("1.1.1", first.getJerarquia());
//    	
//		// BORRAR para que no quede la BBDD con basura
//        TE.deleteTipoEvaluacion(tipoE);
//    }
    
    
	@Test
    public void getTipoCEconomico() throws Exception {   
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	TipoCEconomico tipoCEconomico = crearTipoCEconomico("nombre", ClaseCEconomico.automatico, "1.1.1");
    	tipoCEconomico = service.createTipoCEconomico(tipoE.getId(), tipoCEconomico);
    	Assert.assertNotNull(tipoCEconomico);
    	
    	tipoCEconomico = service.getTipoCEconomico(tipoE.getId(), tipoCEconomico.getId());
    	Assert.assertNotNull(tipoCEconomico);
        Assert.assertNotNull(tipoCEconomico.getId());
        Assert.assertNotNull(tipoCEconomico.getNombre());
        Assert.assertEquals(ClaseCEconomico.automatico, tipoCEconomico.getClase());
        Assert.assertEquals("1.1.1", tipoCEconomico.getJerarquia());
        
        // BORRAR para que no quede la BBDD con basura
        borrarTipoCEconomico(tipoE.getId(), tipoCEconomico.getId());
        TE.deleteTipoEvaluacion(tipoE);
	}
	
	@Test
	public void putTipoCEconomico() throws Exception {   
	   	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
	   	// Insertamos Evaluacion DESPUES BORRAR!!
	   	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
	   	tipoE = service.createTipoEvaluacion(tipoE);
	   	Assert.assertNotNull(tipoE);
	   	
	   	TipoCEconomico tipoCEconomico = crearTipoCEconomico("nombre", ClaseCEconomico.manual, "1.2.1");
	   	tipoCEconomico = service.createTipoCEconomico(tipoE.getId(), tipoCEconomico);
	   	Assert.assertNotNull(tipoCEconomico);
	   	
	   	TipoCEconomico tipoCEconomicoS = crearTipoCEconomico("nombre 2", ClaseCEconomico.automatico, "1.2.2");
	   	tipoCEconomicoS.setId(tipoCEconomico.getId());
	   	
	   	tipoCEconomicoS = service.updateTipoCEconomico(tipoE.getId(), tipoCEconomicoS);
	    Assert.assertNotNull(tipoCEconomicoS);
	    Assert.assertNotNull(tipoCEconomicoS.getId());
	    Assert.assertNotNull(tipoCEconomicoS.getNombre());
	    Assert.assertEquals(ClaseCEconomico.automatico, tipoCEconomicoS.getClase());
	    Assert.assertEquals("nombre 2", tipoCEconomicoS.getNombre());
	    Assert.assertEquals("1.2.2", tipoCEconomicoS.getJerarquia());
	      
	    // BORRAR para que no quede la BBDD con basura
	    borrarTipoCEconomico(tipoE.getId(), tipoCEconomicoS.getId());
	    TE.deleteTipoEvaluacion(tipoE);
	}
	
	@Test(expected=BDPNotFoundException.class)
    public void getTipoCEconomicoNotFound() throws Exception {
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	service.getTipoCEconomico(tipoE.getId(), 1234l);
    	
    	TE.deleteTipoEvaluacion(tipoE);
    }
    
    @Test(expected=BDPValidationException.class)
    public void badRequestPutTipoCEconomico() throws Exception{
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	TipoCEconomico tipoCEconomico = crearTipoCEconomico("nombre", ClaseCEconomico.manual, "1.1.1");
    	tipoCEconomico = service.createTipoCEconomico(tipoE.getId(), tipoCEconomico);
    	Assert.assertNotNull(tipoCEconomico);
    	
    	TipoCEconomico tipoCEconomicoS = crearTipoCEconomico(null, ClaseCEconomico.automatico, "1.2.1");
    	tipoCEconomicoS.setId(tipoCEconomico.getId());
    	
    	service.updateTipoCEconomico(tipoE.getId(), tipoCEconomicoS);
    	
    	TE.deleteTipoEvaluacion(tipoE);
    }

}
