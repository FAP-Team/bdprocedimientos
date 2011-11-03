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
    	tipoEvaluacion.setNombre("Evaluacion a");
    	tipoEvaluacion.setTipoProcedimiento("Procedimiento a");
    	tipoEvaluacion.setComentariosSolicitante(true);
    	tipoEvaluacion.setComentariosAdministracion(true);
    	return tipoEvaluacion;
    }
    
    private void deleteTipoEvaluacion(TipoEvaluacion tipoEvaluacion) throws Exception {
    	service.deleteTipoEvaluacion(tipoEvaluacion.getId());
    	try {
    		service.getTipoEvaluacion(tipoEvaluacion.getId());
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
    	Assert.assertNotNull(createdTipoEvaluacion.getId());
    	
    	deleteTipoEvaluacion(createdTipoEvaluacion);
    }
    
    @Test
    public void createAndUpdateAndDeleteTipoEvaluacion() throws Exception {
    	TipoEvaluacion tipoEvaluacion = dummyTipoEvaluacion();
    	TipoEvaluacion createdTipoEvaluacion = service.createTipoEvaluacion(tipoEvaluacion);
    	Assert.assertNotNull(createdTipoEvaluacion);
    	Assert.assertNotNull(createdTipoEvaluacion.getId());
    	
    	createdTipoEvaluacion.setNombre("nombre modificado");
    	TipoEvaluacion updatedTipoEvaluacion = service.updateTipoEvaluacion(createdTipoEvaluacion);
    	
    	Assert.assertNotNull(updatedTipoEvaluacion);
    	Assert.assertNotNull(updatedTipoEvaluacion.getId());
    	Assert.assertEquals(createdTipoEvaluacion.getId(), updatedTipoEvaluacion.getId());
    	Assert.assertFalse(!createdTipoEvaluacion.getNombre().equals(updatedTipoEvaluacion.getNombre()));
    	
    	deleteTipoEvaluacion(createdTipoEvaluacion);	
    }
    
}
