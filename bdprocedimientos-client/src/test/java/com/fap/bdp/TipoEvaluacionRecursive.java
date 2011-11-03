package com.fap.bdp;

import org.junit.Test;

import com.fap.bdp.domain.ClaseCriterio;
import com.fap.bdp.domain.TipoCriterio;
import com.fap.bdp.domain.TipoEvaluacion;
import com.fap.bdp.domain.TipoValorCriterio;

public class TipoEvaluacionRecursive {

	@Test
	public void createRecursive() throws Exception {
		TipoEvaluacion tipoEvaluacion = new TipoEvaluacion();
		tipoEvaluacion.setNombre("Recursiva");
		tipoEvaluacion.setTipoProcedimiento("Procedimiento");
		
		TipoCriterio criterio1 = new TipoCriterio();
		criterio1.setClase(ClaseCriterio.automatico);
		criterio1.setComentariosAdministracion(true);
		criterio1.setComentariosSolicitante(false);
		criterio1.setDescripcion("Descripción");
		criterio1.setInstrucciones("Instrucciones");
		criterio1.setJerarquia("1.1.1");
		criterio1.setNombre("nombre");
		criterio1.setPrecision(3);
		criterio1.setTipoValor(TipoValorCriterio.cantidad);
		criterio1.setValorMaximo(3.5);
		criterio1.setValorMinimoCorte(24.3);
		criterio1.setTransparencia(1);
		
		tipoEvaluacion.getTiposCriterios().add(criterio1);
		tipoEvaluacion.getTiposCriterios().add(criterio1);
		tipoEvaluacion.getTiposCriterios().add(criterio1);
		
		BDProcedimientosService service = new BDProcedimientosService("http://localhost:9000");
		service.createTipoEvaluacionRecusive(tipoEvaluacion);
	}
	
}
