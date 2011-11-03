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
		tipoEvaluacion.nombre = "Recursiva";
		tipoEvaluacion.tipoProcedimiento = "Procedimiento";
		
		TipoCriterio criterio1 = new TipoCriterio();
		criterio1.clase = ClaseCriterio.automatico;
		criterio1.comentariosAdministracion = true;
		criterio1.comentariosSolicitante = false;
		criterio1.descripcion = "Descripción";
		criterio1.instrucciones = "Instrucciones";
		criterio1.jerarquia = "1.1.1";
		criterio1.nombre = "nombre:)";
		criterio1.precision = 3;
		criterio1.tipoValor = TipoValorCriterio.cantidad;
		criterio1.valorMaximo = 3.5;
		criterio1.valorMinimoCorte = 24.3;
		criterio1.transparencia = 1;
		
		tipoEvaluacion.tiposCriterios.add(criterio1);
		tipoEvaluacion.tiposCriterios.add(criterio1);
		tipoEvaluacion.tiposCriterios.add(criterio1);
		
		BDProcedimientosService service = new BDProcedimientosService("http://localhost:9000");
		service.createTipoEvaluacionRecusive(tipoEvaluacion);
	}
	
}
