package org.iesalandalus.programacion.tutorias.mvc.vista;

import org.iesalandalus.programacion.tutorias.mvc.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tutorias.mvc.vista.texto.VistaTexto;

public enum FactoriaVista {

	TEXTO {
		public IVista crear() {
			return new VistaTexto();
		}
	},
	GRAFICA {
		
		@Override
		public IVista crear() {
			
			return new VistaGrafica();
		}
	}
	;

	public abstract IVista crear();
}
