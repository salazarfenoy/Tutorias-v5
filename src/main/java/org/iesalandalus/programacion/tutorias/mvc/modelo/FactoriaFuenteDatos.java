package org.iesalandalus.programacion.tutorias.mvc.modelo;

import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ficheros.FactoriaFuenteDatosFicheros;

public enum FactoriaFuenteDatos {

	FICHEROS {
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosFicheros();
		}
	},
	MONGODB {
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosMongoDB();
		}
	};

	public abstract IFuenteDatos crear();

}
