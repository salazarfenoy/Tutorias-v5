package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores {

	private List<Profesor> coleccionProfesores;

	private static final String NOMBRE_FICHERO_PROFESORES = "ficheros/profesores.dat";

	public Profesores() {

		coleccionProfesores = new ArrayList<>();

	}

	@Override
	public int getTamano() {
		return coleccionProfesores.size();
	}

	private List<Profesor> copiaProfundaProfesores() {
		List<Profesor> copiaProfesores = new ArrayList<>();
		for (Profesor profesor : coleccionProfesores) {
			copiaProfesores.add(new Profesor(profesor));
		}
		return copiaProfesores;

	}

	@Override
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}

		int indice = coleccionProfesores.indexOf(profesor);

		if (indice == -1) {
			coleccionProfesores.add(new Profesor(profesor));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese DNI.");
		}

	}

	@Override
	public Profesor buscar(Profesor profesor) {
		if (profesor == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un profesor nulo.");
		}
		int indice = coleccionProfesores.indexOf(profesor);

		if (indice == -1) {
			return null;
		} else {
			return new Profesor(coleccionProfesores.get(indice));
		}

	}

	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un profesor nulo.");
		}
		int indice = coleccionProfesores.indexOf(profesor);

		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese DNI.");
		} else {
			coleccionProfesores.remove(indice);
		}
	}

	@Override
	public List<Profesor> get() {
		List<Profesor> profesoresOrdenados = copiaProfundaProfesores();
		profesoresOrdenados.sort(Comparator.comparing(Profesor::getDni));
		return profesoresOrdenados;
	}

	@Override
	public void comenzar() {
		File fichero = new File(NOMBRE_FICHERO_PROFESORES);
		Profesor profesor;
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(fichero))) {
			try {
				while ((profesor = (Profesor) entrada.readObject()) != null) {
					coleccionProfesores.add(profesor);
				}
			} catch (EOFException eo) {
				System.out.println("Fichero de profesores leído satisfactoriamente.");
			} catch (ClassNotFoundException e) {
				System.out.println("No puedo encontrar la clase que tengo que leer.");
			} catch (IOException e) {
				System.out.println("Error inesperado de Entrada/Salida.");
			}
		} catch (IOException e) {
			System.out.println("No puedo abrir el fihero de entrada.");
		}
	}

	@Override
	public void terminar() {
		File fichero = new File(NOMBRE_FICHERO_PROFESORES);

		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(fichero))) {
			for (Profesor profesor : coleccionProfesores) {

				salida.writeObject(new Profesor(profesor));
			}
			System.out.println("Fichero de profesores escrito satisfactoriamente");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de salida");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida");
		}
	}
}
