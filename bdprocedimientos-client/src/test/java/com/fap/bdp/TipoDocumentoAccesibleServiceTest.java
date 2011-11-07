package com.fap.bdp;

import org.junit.Assert;
import org.junit.Test;

import com.fap.bdp.domain.TipoDocumentoAccesible;
import com.fap.bdp.domain.TipoEvaluacion;
import com.fap.bdp.exceptions.BDPNotFoundException;
import com.fap.bdp.exceptions.BDPValidationException;

public class TipoDocumentoAccesibleServiceTest {
	
	private static final String endPoint = "http://localhost:9000";
    private BDProcedimientosService service = new BDProcedimientosService(endPoint);
    
    public TipoDocumentoAccesible crearTipoDocumentoAccesible (String uri){
    	TipoDocumentoAccesible tipoDocumentoAccesible = new TipoDocumentoAccesible();
    	tipoDocumentoAccesible.setUri(uri);
    	return tipoDocumentoAccesible;
    }
    
    public boolean borrarTipoDocumentoAccesible(long eId, long dId) throws Exception{
    	service.deleteTipoDocumentoAccesible(eId, dId);
    	try {
    		service.getTipoDocumentoAccesible(eId, dId);
    		Assert.assertTrue(false);
    		return true;
    	}catch(BDPNotFoundException e){
    		//Not found
    		return false;
    	}
    }
    
    @Test
    public void getTipoDocumentoAccesible() throws Exception {   
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	TipoDocumentoAccesible tipoDocumentoAccesible = crearTipoDocumentoAccesible("uri");
    	tipoDocumentoAccesible = service.createTipoDocumentoAccesible(tipoE.getId(), tipoDocumentoAccesible);
    	Assert.assertNotNull(tipoDocumentoAccesible);
    	
    	tipoDocumentoAccesible = service.getTipoDocumentoAccesible(tipoE.getId(), tipoDocumentoAccesible.getId());
        Assert.assertNotNull(tipoDocumentoAccesible);
        Assert.assertNotNull(tipoDocumentoAccesible.getId());
        Assert.assertNotNull(tipoDocumentoAccesible.getUri());
        Assert.assertEquals("uri", tipoDocumentoAccesible.getUri());
       
        // BORRAR para que no quede la BBDD con basura
        borrarTipoDocumentoAccesible(tipoE.getId(), tipoDocumentoAccesible.getId());
        TE.deleteTipoEvaluacion(tipoE);
    }
    
    @Test
    public void putTipoDocumentoAccesible() throws Exception {   
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	TipoDocumentoAccesible tipoDocumentoAccesible = crearTipoDocumentoAccesible("uri");
    	tipoDocumentoAccesible = service.createTipoDocumentoAccesible(tipoE.getId(), tipoDocumentoAccesible);
    	Assert.assertNotNull(tipoDocumentoAccesible);
    	
    	TipoDocumentoAccesible tipoDocumentoAccesibleS = crearTipoDocumentoAccesible("uri 2");
    	tipoDocumentoAccesibleS.setId(tipoDocumentoAccesible.getId());
    	
    	tipoDocumentoAccesibleS = service.updateTipoDocumentoAccesible(tipoE.getId(), tipoDocumentoAccesibleS);
        Assert.assertNotNull(tipoDocumentoAccesibleS);
        Assert.assertNotNull(tipoDocumentoAccesibleS.getId());
        Assert.assertNotNull(tipoDocumentoAccesibleS.getUri());
        Assert.assertEquals("uri 2", tipoDocumentoAccesibleS.getUri());
        
        // BORRAR para que no quede la BBDD con basura
        borrarTipoDocumentoAccesible(tipoE.getId(), tipoDocumentoAccesibleS.getId());
        TE.deleteTipoEvaluacion(tipoE);
    }
    
    @Test(expected=BDPNotFoundException.class)
    public void getTipoDocumentoAccesibleNotFound() throws Exception {
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	service.getTipoDocumentoAccesible(tipoE.getId(), 1234l);
    	
    	TE.deleteTipoEvaluacion(tipoE);
    }
    
    @Test(expected=BDPValidationException.class)
    public void badRequestPutTipoDocumentoAccesible() throws Exception{
    	TipoEvaluacionServiceTest TE = new TipoEvaluacionServiceTest();
    	// Insertamos Evaluacion DESPUES BORRAR!!
    	TipoEvaluacion tipoE = TE.dummyTipoEvaluacion();
    	tipoE = service.createTipoEvaluacion(tipoE);
    	Assert.assertNotNull(tipoE);
    	
    	TipoDocumentoAccesible tipoDocumentoAccesible = crearTipoDocumentoAccesible("uri");
    	tipoDocumentoAccesible = service.createTipoDocumentoAccesible(tipoE.getId(), tipoDocumentoAccesible);
    	Assert.assertNotNull(tipoDocumentoAccesible);
    	
    	TipoDocumentoAccesible tipoDocumentoAccesibleS = crearTipoDocumentoAccesible(null);
    	tipoDocumentoAccesibleS.setId(tipoDocumentoAccesible.getId());
    	
    	service.updateTipoDocumentoAccesible(tipoE.getId(), tipoDocumentoAccesibleS);
    	
    	TE.deleteTipoEvaluacion(tipoE);
    }

}
