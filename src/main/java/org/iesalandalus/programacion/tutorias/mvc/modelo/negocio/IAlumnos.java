package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;

public interface IAlumnos {

	int getTamano();

	void insertar(Alumno alumno) throws OperationNotSupportedException;

	Alumno buscar(Alumno alumno);

	void borrar(Alumno alumno) throws OperationNotSupportedException;

	List<Alumno> get();

	void comenzar();

	void terminar();

}