# Servicio web de base de datos de procedimientos


## Entidades

Entidades utilizadas en el servicios web

### TipoEvaluacion

	TipoEvaluacion {
		Long id
		String tipoProcedimiento
		String nombre
		List<TipoDocumentoAccesible> tiposDocumentosAccesibles
		List<TipoCriterio> tiposCriterios
		List<TipoCEconomico> tipoCEconomicos
		boolean comentariosAdministracion
		boolean comentariosSolicitante
		int numeroEvaluaciones			
	}

## TipoCriterio

TipoCriterio {
	Long id
	String nombre
	String descripcion
	String instrucciones
	Enum<ClaseCriterio> clase (automatico, manual, automaticomodificable)
	String jerarquia
	Long precision
	Double valorMaximo
	Double valorMinimoCorte
	Enum<TipoValorCriterio> tipoValor (cantidad, lista)
	List<CriterioListaValor> listaValores
	int transparencia
	boolean comentariosAdministracion
	boolean comentariosSolicitante	
}

## CriterioListaValor

CriterioListaValor {
	double valor
	String descripcion
}
















## Tipos de Criterios

	/tiposcriterios

Parámetros:

* **pageSize** (Opcional) : Tamaño de la página
* **pageStartIndex** (Opcional) : Índice en el cual empezará la página

Respuesta:

Lista de tipos de criterios

### Ejemplos:

Consulta:

	/tipocriterios

Respuesta:

	[
	    {
	        "clase": "manual",
	        "comentariosAdministracion": true,
	        "comentariosSolicitante": false,
	        "descripcion": "descipción bastante larga",
	        "id": 1,
	        "instrucciones": "instrucciones",
	        "jerarquia": "1.2",
	        "listaValores": [
	            {
	                "descripcion": "descValor1",
	                "id": 1,
	                "valor": 123.0
	            },
	            {
	                "descripcion": "desvValor2",
	                "id": 2,
	                "valor": 3.0
	            }
	        ],
	        "nombre": "nombreTipoCriterio1",
	        "precision": 0,
	        "tipoValor": "cantidad",
	        "transparencia": 0,
	        "valorMaximo": 0.0,
	        "valorMinimoCorte": 0.0
	    },
	    {
	        "clase": "manual",
	        "comentariosAdministracion": true,
	        "comentariosSolicitante": true,
	        "descripcion": "Descripcion2",
	        "id": 2,
	        "instrucciones": "Instrucciones2",
	        "jerarquia": "1.4.5",
	        "listaValores": [

	        ],
	        "nombre": "Criterio2",
	        "precision": 0,
	        "tipoValor": "lista",
	        "transparencia": 0,
	        "valorMaximo": 0.0,
	        "valorMinimoCorte": 0.0
	    }
	]
	
Consulta:

	/tipocriterios?pageSize=1&pageStartIndex=1

Respuesta:	
	
	[
	    {
	        "clase": "manual",
	        "comentariosAdministracion": true,
	        "comentariosSolicitante": true,
	        "descripcion": "Descripcion",
	        "id": 2,
	        "instrucciones": "asdfasdfasdf",
	        "jerarquia": "1.4.5",
	        "listaValores": [

	        ],
	        "nombre": "Criterio2",
	        "precision": 0,
	        "tipoValor": "lista",
	        "transparencia": 0,
	        "valorMaximo": 0.0,
	        "valorMinimoCorte": 0.0
	    }
	]