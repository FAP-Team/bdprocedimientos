package com.fap.bdp;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fap.bdp.domain.TipoEvaluacion;
import com.fap.bdp.exceptions.BDPNotFoundException;

public class TipoEvaluacionServiceTest {
    
    private static final String endPoint = "http://localhost:9000";
    private BDProcedimientosService service = new BDProcedimientosService(endPoint);
    
    @Test
    public void getTipoEvaluacion() throws Exception {    
        TipoEvaluacion tipoEvaluacion = service.getTipoEvaluacion(1l);
        Assert.assertNotNull(tipoEvaluacion);
    }
   
    @Test(expected=BDPNotFoundException.class)
    public void getTipoEvaluacionNotFound() throws Exception {
    	service.getTipoEvaluacion(1234l);
    }
    
    @Test
    public void getTiposEvaluaciones() throws Exception {
        List<TipoEvaluacion> tiposEvaluaciones = service.getTiposEvaluaciones();
        Assert.assertNotNull(tiposEvaluaciones);
    }
    
    
    private TipoEvaluacion dummyTipoEvaluacion(){
    	TipoEvaluacion tipoEvaluacion = new TipoEvaluacion();
    	tipoEvaluacion.nombre = "Evaluacion a";
    	tipoEvaluacion.tipoProcedimiento = "Procedimiento a";
    	tipoEvaluacion.comentariosSolicitante = true;
    	tipoEvaluacion.comentariosAdministracion = true;
    	return tipoEvaluacion;
    }
    
    private void deleteTipoEvaluacion(TipoEvaluacion tipoEvaluacion) throws Exception {
    	service.deleteTipoEvaluacion(tipoEvaluacion.id);
    	try {
    		service.getTipoEvaluacion(tipoEvaluacion.id);
    		Assert.assertTrue(false);
    	}catch(BDPNotFoundException e){
    		//Not found
    	}
    }
    
    @Test
    public void createAndDeleteTipoEvaluacion() throws Exception {
    	TipoEvaluacion tipoEvaluacion = dummyTipoEvaluacion();
    	TipoEvaluacion createdTipoEvaluacion = service.createTipoEvaluacion(tipoEvaluacion);
    	
    	Assert.assertNotNull(createdTipoEvaluacion);
    	Assert.assertNotNull(createdTipoEvaluacion.id);
    	
    	deleteTipoEvaluacion(createdTipoEvaluacion);
    }
    
    @Test
    public void createAndUpdateAndDeleteTipoEvaluacion() throws Exception {
    	TipoEvaluacion tipoEvaluacion = dummyTipoEvaluacion();
    	TipoEvaluacion createdTipoEvaluacion = service.createTipoEvaluacion(tipoEvaluacion);
    	Assert.assertNotNull(createdTipoEvaluacion);
    	Assert.assertNotNull(createdTipoEvaluacion.id);
    	
    	createdTipoEvaluacion.nombre = "nombre modificado";
    	TipoEvaluacion updatedTipoEvaluacion = service.updateTipoEvaluacion(createdTipoEvaluacion);
    	
    	Assert.assertNotNull(updatedTipoEvaluacion);
    	Assert.assertNotNull(updatedTipoEvaluacion.id);
    	Assert.assertEquals(createdTipoEvaluacion.id, updatedTipoEvaluacion.id);
    	Assert.assertFalse(!createdTipoEvaluacion.nombre.equals(updatedTipoEvaluacion.nombre));
    	
    	deleteTipoEvaluacion(createdTipoEvaluacion);	
    }
    
}
