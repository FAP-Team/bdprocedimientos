package com.fap.bdp;

import org.junit.Assert;
import org.junit.Test;

import com.fap.bdp.domain.TipoAreaEvaluacion;
import com.fap.bdp.exceptions.BDPNotFoundException;
import com.fap.bdp.exceptions.BDPValidationException;

public class TipoAreaEvaluacionServiceTest {
	
	private static final String endPoint = "http://localhost:9000";
    private BDProcedimientosService service = new BDProcedimientosService(endPoint);
    
    public TipoAreaEvaluacion crearTipoAreaEvaluacion (String codigo, String descripcion){
    	TipoAreaEvaluacion tipoAreaEvaluacion = new TipoAreaEvaluacion();
    	tipoAreaEvaluacion.setCodigo(codigo);
    	tipoAreaEvaluacion.setDescripcion(descripcion);
    	return tipoAreaEvaluacion;
    }
    
    public boolean borrarTipoAreaEvaluacion(long aeId) throws Exception{
    	service.deleteTipoAreaEvaluacion(aeId);
    	try {
    		service.getTipoAreaEvaluacion(aeId);
    		Assert.assertTrue(false);
    		return true;
    	}catch(BDPNotFoundException e){
    		//Not found
    		return false;
    	}
    }
    
    @Test
    public void getTipoAreaEvaluacion() throws Exception{
    	TipoAreaEvaluacion tipoAreaEvaluacion = crearTipoAreaEvaluacion("A316", "descripcion");
    	tipoAreaEvaluacion = service.createTipoAreaEvaluacion(tipoAreaEvaluacion);
    	Assert.assertNotNull(tipoAreaEvaluacion);
    	
    	tipoAreaEvaluacion = service.getTipoAreaEvaluacion(tipoAreaEvaluacion.getId());

        Assert.assertNotNull(tipoAreaEvaluacion);
        Assert.assertNotNull(tipoAreaEvaluacion.getId());
        Assert.assertNotNull(tipoAreaEvaluacion.getCodigo());
        Assert.assertEquals("descripcion", tipoAreaEvaluacion.getDescripcion());
        Assert.assertEquals("A316", tipoAreaEvaluacion.getCodigo());
        
        // BORRAR para que no quede la BBDD con basura
        borrarTipoAreaEvaluacion(tipoAreaEvaluacion.getId());
    }
    
    @Test
    public void putTipoAreaEvaluacion() throws Exception {   
    	TipoAreaEvaluacion tipoAreaEvaluacion = crearTipoAreaEvaluacion("A316", "descripcion");
    	tipoAreaEvaluacion = service.createTipoAreaEvaluacion(tipoAreaEvaluacion);
    	Assert.assertNotNull(tipoAreaEvaluacion);
    	
    	TipoAreaEvaluacion tipoAreaEvaluacionS = crearTipoAreaEvaluacion("O815", "descripcion 2");
    	tipoAreaEvaluacionS.setId(tipoAreaEvaluacion.getId());
    	
    	tipoAreaEvaluacionS = service.updateTipoAreaEvaluacion(tipoAreaEvaluacionS);
        Assert.assertNotNull(tipoAreaEvaluacionS);
        Assert.assertNotNull(tipoAreaEvaluacionS.getId());
        Assert.assertNotNull(tipoAreaEvaluacionS.getCodigo());
        Assert.assertEquals("O815", tipoAreaEvaluacionS.getCodigo());
        Assert.assertEquals("descripcion 2", tipoAreaEvaluacionS.getDescripcion());
        
        // BORRAR para que no quede la BBDD con basura
        borrarTipoAreaEvaluacion(tipoAreaEvaluacionS.getId());
    }
    
    @Test(expected=BDPNotFoundException.class)
    public void getTipoAreaEvaluacionNotFound() throws Exception {
    	
    	service.getTipoAreaEvaluacion(1234l);
    }
    
    @Test(expected=BDPValidationException.class)
    public void badRequestPutTipoAreaEvaluacion() throws Exception{
    	
    	TipoAreaEvaluacion tipoAreaEvaluacion = crearTipoAreaEvaluacion("A316", "descripcion");
    	tipoAreaEvaluacion = service.createTipoAreaEvaluacion(tipoAreaEvaluacion);
    	Assert.assertNotNull(tipoAreaEvaluacion);
    	
    	TipoAreaEvaluacion tipoAreaEvaluacionS = crearTipoAreaEvaluacion(null, null);
    	tipoAreaEvaluacionS.setId(tipoAreaEvaluacion.getId());
    	
    	service.updateTipoAreaEvaluacion(tipoAreaEvaluacionS);
    	
    	// BORRAR para que no quede la BBDD con basura
        borrarTipoAreaEvaluacion(tipoAreaEvaluacionS.getId());
    }

}
