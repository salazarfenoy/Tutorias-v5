package org.iesalandalus.programacion.tutorias.mvc.vista.texto;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.vista.IVista;

import java.util.List;

public class VistaTexto implements IVista {

	private IControlador controlador;

	public VistaTexto() {
		Opcion.setVista(this);
	}

	@Override
	public void setControlador(IControlador controlador) {
		this.controlador = controlador;
	}

	@Override
	public void comenzar() {
		Consola.mostrarCabecera("Gestión de Tutorías del IES Al-Ándalus");
		int ordinalOpcion;
		do {
			Consola.mostrarMenu();
			ordinalOpcion = Consola.elegirOpcion();
			Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinalOpcion);
			opcion.ejecutar();
		} while (ordinalOpcion != Opcion.SALIR.ordinal());
	}

	@Override
	public void terminar() {
		controlador.terminar();
	}

	public void insertarAlumno() {
		Consola.mostrarCabecera("Insertar Alumno");
		try {
			controlador.insertar(Consola.leerAlumno());
			System.out.println("Alumno insertado correctamente.");
			System.out.println("");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarAlumno() {
		Consola.mostrarCabecera("Buscar Alumno");
		Alumno alumno;
		try {
			alumno = controlador.buscar(Consola.leerAlumnoFicticio());
			String mensaje = (alumno != null) ? alumno.toString() : "No existe dicho alumno.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarAlumno() {
		Consola.mostrarCabecera("Borrar Alumno");
		try {
			controlador.borrar(Consola.leerAlumnoFicticio());
			System.out.println("Alumno borrado satisfactoriamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlumnos() {
		Consola.mostrarCabecera("Listado de Alumnos");
		List<Alumno> alumnos = controlador.getAlumnos();
		if (!alumnos.isEmpty()) {
			for (Alumno alumno : alumnos) {
				System.out.println(alumno);
			}
		} else {
			System.out.println("No hay alumnos que mostrar.");
		}
	}

	public void insertarProfesor() {
		Consola.mostrarCabecera("Insertar Profesor");
		try {
			controlador.insertar(Consola.leerProfesor());
			System.out.println("Profesor insertado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarProfesor() {
		Consola.mostrarCabecera("Buscar Profesor");
		Profesor profesor;
		try {
			profesor = controlador.buscar(Consola.leerProfesorFicticio());
			String mensaje = (profesor != null) ? profesor.toString() : "No existe dicho profesor.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarProfesor() {
		Consola.mostrarCabecera("Borrar Profesor");
		try {
			controlador.borrar(Consola.leerProfesorFicticio());
			System.out.println("Profesor borrado satisfactoriamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarProfesores() {
		Consola.mostrarCabecera("Listado de Profesores");
		List<Profesor> profesores = controlador.getProfesores();
		if (!profesores.isEmpty()) {
			for (Profesor profesor : profesores) {
				System.out.println(profesor);
			}
		} else {
			System.out.println("No hay profesores que mostrar.");
		}
	}

	public void insertarTutoria() {
		Consola.mostrarCabecera("Insertar Tutoría");
		try {
			Tutoria tutoria = Consola.leerTutoria();
			controlador.insertar(tutoria);
			System.out.println("Tutoría insertada correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarTutoria() {
		Consola.mostrarCabecera("Buscar Tutoría");
		Tutoria tutoria;
		try {
			tutoria = controlador.buscar(Consola.leerTutoria());
			String mensaje = (tutoria != null) ? tutoria.toString() : "No existe dicha tutoría.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarTutoria() {
		Consola.mostrarCabecera("Borrar Tutoría");
		try {
			controlador.borrar(Consola.leerTutoria());
			System.out.println("Tutoría borrada satisfactoriamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarTutorias() {
		Consola.mostrarCabecera("Listado de Tutorías");
		List<Tutoria> tutorias = controlador.getTutorias();
		if (!tutorias.isEmpty()) {
			for (Tutoria tutoria : tutorias) {
				System.out.println(tutoria);
			}
		} else {
			System.out.println("No hay tutorías que mostrar.");
		}
	}

	public void listarTutoriasProfesor() {
		Consola.mostrarCabecera("Listado de Tutorías por Profesor");
		List<Tutoria> tutorias = controlador.getTutorias(Consola.leerProfesorFicticio());
		if (!tutorias.isEmpty()) {
			for (Tutoria tutoria : tutorias) {
				System.out.println(tutoria);
			}
		} else {
			System.out.println("No hay tutorías que mostrar para ese profesor.");
		}
	}

	public void insertarSesion() {
		Consola.mostrarCabecera("Insertar Sesión");
		try {
			controlador.insertar(Consola.leerSesion());
			System.out.println("Sesión insertada correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarSesion() {
		Consola.mostrarCabecera("Buscar Sesión");
		Sesion sesion;
		try {
			sesion = controlador.buscar(Consola.leerSesionFicticia());
			String mensaje = (sesion != null) ? sesion.toString() : "No existe dicha sesión.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarSesion() {
		Consola.mostrarCabecera("Borrar Tutoría");
		try {
			controlador.borrar(Consola.leerSesionFicticia());
			System.out.println("Tutoría borrada satisfactoriamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarSesiones() {
		Consola.mostrarCabecera("Listado de Sesiones");
		List<Sesion> sesiones = controlador.getSesiones();
		if (!sesiones.isEmpty()) {
			for (Sesion sesion : sesiones) {
				System.out.println(sesion);
			}
		} else {
			System.out.println("No hay sesiones que mostrar.");
		}
	}

	public void listarSesionesTutoria() {
		Consola.mostrarCabecera("Listado de Sesiones por Tutoría");
		List<Sesion> sesiones = controlador.getSesiones(Consola.leerTutoria());
		if (!sesiones.isEmpty()) {
			for (Sesion sesion : sesiones) {
				System.out.println(sesion);
			}
		} else {
			System.out.println("No hay sesiones que mostrar para esa tutoría.");
		}
	}

	public void insertarCita() {
		Consola.mostrarCabecera("Insertar Cita");
		try {
			controlador.insertar(Consola.leerCita());
			System.out.println("Cita insertada correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarCita() {
		Consola.mostrarCabecera("Buscar Cita");
		Cita cita;
		try {
			cita = controlador.buscar(Consola.leerCita());
			String mensaje = (cita != null) ? cita.toString() : "No existe dicha cita.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarCita() {
		Consola.mostrarCabecera("Borrar Cita");
		try {
			controlador.borrar(Consola.leerCita());
			System.out.println("Cita borrada satisfactoriamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarCitas() {
		Consola.mostrarCabecera("Listado de Citas");
		List<Cita >citas = controlador.getCitas();
		if (!citas.isEmpty()) {
			for (Cita cita : citas) {
				System.out.println(cita);
			}
		} else {
			System.out.println("No hay citas que mostrar.");
		}
	}

	public void listarCitasSesion() {
		Consola.mostrarCabecera("Listado de Citas por Sesión");
		List<Cita> citas = controlador.getCitas(Consola.leerSesionFicticia());
		if (!citas.isEmpty()) {
			for (Cita cita : citas) {
				System.out.println(cita);
			}
		} else {
			System.out.println("No hay citas que mostrar para esa sesión.");
		}
	}

	public void listarCitasAlumno() {
		Consola.mostrarCabecera("Listado de Citas por Alumno");
		List<Cita> citas = controlador.getCitas(Consola.leerAlumnoFicticio());
		if (!citas.isEmpty()) {
			for (Cita cita : citas) {
				System.out.println(cita);
			}
		} else {
			System.out.println("No hay citas que mostrar para ese alumno.");
		}
	}
}
