package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;

public interface ITutorias {

	int getTamano();

	void insertar(Tutoria tutoria) throws OperationNotSupportedException;

	Tutoria buscar(Tutoria tutoria);

	void borrar(Tutoria tutoria) throws OperationNotSupportedException;

	List<Tutoria> get(Profesor profesor);

	List<Tutoria> get();

	void comenzar();

	void terminar();

}