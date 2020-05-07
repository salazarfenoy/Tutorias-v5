package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;

public interface IProfesores {

	int getTamano();

	void insertar(Profesor profesor) throws OperationNotSupportedException;

	Profesor buscar(Profesor profesor);

	void borrar(Profesor profesor) throws OperationNotSupportedException;

	List<Profesor> get();

	void comenzar();

	void terminar();

}