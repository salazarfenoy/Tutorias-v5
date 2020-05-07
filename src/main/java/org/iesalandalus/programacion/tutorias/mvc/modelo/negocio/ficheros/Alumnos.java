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

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IAlumnos;

public class Alumnos implements IAlumnos {

	private List<Alumno> coleccionAlumnos;

	private static final String NOMBRE_FICHERO_ALUMNOS = "ficheros/alumnos.dat";

	public Alumnos() {

		coleccionAlumnos = new ArrayList<>();

	}

	@Override
	public int getTamano() {
		return coleccionAlumnos.size();
	}

	private List<Alumno> copiaProfundaAlumnos() {
		List<Alumno> copiaAlumnos = new ArrayList<>();
		for (Alumno alumno : coleccionAlumnos) {
			copiaAlumnos.add(new Alumno(alumno));
		}

		return copiaAlumnos;

	}

	@Override
	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
		}

		int indice = coleccionAlumnos.indexOf(alumno);

		if (indice == -1) {
			coleccionAlumnos.add(new Alumno(alumno));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese expediente.");
		}

	}

	@Override
	public Alumno buscar(Alumno alumno) {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un alumno nulo.");
		}
		int indice = coleccionAlumnos.indexOf(alumno);

		if (indice == -1) {
			return null;
		} else {
			return new Alumno(coleccionAlumnos.get(indice));
		}

	}

	@Override
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un alumno nulo.");
		}
		int indice = coleccionAlumnos.indexOf(alumno);

		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alumno con ese expediente.");
		} else {
			coleccionAlumnos.remove(indice);
		}

	}

	@Override
	public List<Alumno> get() {
		List<Alumno> alumnosOrdenados = copiaProfundaAlumnos();
		alumnosOrdenados.sort(Comparator.comparing(Alumno::getCorreo));

		return alumnosOrdenados;
	}

	@Override
	public void comenzar() {
		File fichero = new File(NOMBRE_FICHERO_ALUMNOS);
		Alumno alumno;
		int identificadorFichero = 0;
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(fichero))) {
			try {
				while ((alumno = (Alumno) entrada.readObject()) != null) {
					coleccionAlumnos.add(alumno);

					String[] cadena = alumno.getExpediente().split("_");
					if (Integer.parseInt(cadena[2]) > identificadorFichero) {
						identificadorFichero = Integer.parseInt(cadena[2]);
					}
				}
			} catch (EOFException eo) {
				Alumno.asignarIdentificadorFichero(identificadorFichero);
				System.out.println("Fichero de alumnos leído satisfactoriamente.");
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
		File fichero = new File(NOMBRE_FICHERO_ALUMNOS);

		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(fichero))) {
			for (Alumno alumno : coleccionAlumnos) {

				salida.writeObject(new Alumno(alumno));
			}
			System.out.println("Fichero de alumnos escrito satisfactoriamente");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de salida");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida");
		}
	}

}
