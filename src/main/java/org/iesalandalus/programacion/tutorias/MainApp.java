package org.iesalandalus.programacion.tutorias;

import org.iesalandalus.programacion.tutorias.mvc.controlador.Controlador;
import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.IModelo;
import org.iesalandalus.programacion.tutorias.mvc.modelo.Modelo;
import org.iesalandalus.programacion.tutorias.mvc.vista.FactoriaVista;
import org.iesalandalus.programacion.tutorias.mvc.vista.IVista;

public class MainApp {

	public static void main(String[] args) {

		IModelo modelo = new Modelo(procesarArgumentosModelo(args));
		IVista vista = procesarArgumentosVista(args);
		IControlador controlador = new Controlador(modelo, vista);
		controlador.comenzar();

	}

	private static IVista procesarArgumentosVista(String[] args) {
		IVista vista = FactoriaVista.GRAFICA.crear();
		for (String argumento : args) {
			if (argumento.equalsIgnoreCase("-vgrafica")) {
				vista = FactoriaVista.GRAFICA.crear();
			} else if (argumento.equalsIgnoreCase("-vtexto")) {
				vista = FactoriaVista.TEXTO.crear();
			}
		}
		return vista;
	}

	private static IFuenteDatos procesarArgumentosModelo(String[] args) {
		IFuenteDatos fuenteDeDatos = FactoriaFuenteDatos.MONGODB.crear();
		for (String argumento : args) {
			if (argumento.equalsIgnoreCase("-fdficheros")) {
				fuenteDeDatos = FactoriaFuenteDatos.FICHEROS.crear();
			} else if (argumento.equalsIgnoreCase("-fdmongodb")) {
				fuenteDeDatos = FactoriaFuenteDatos.MONGODB.crear();
			}
		}
		return fuenteDeDatos;
	}

}
