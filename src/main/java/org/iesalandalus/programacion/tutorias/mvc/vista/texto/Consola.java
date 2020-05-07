package org.iesalandalus.programacion.tutorias.mvc.vista.texto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private Consola() {

	}

	public static void mostrarMenu() {
		System.out.println("Menú para reserva de tutorías");
		for (Opcion opcion : Opcion.values()) {
			System.out.println(opcion);
		}

	}

	public static void mostrarCabecera(String mensaje) {
		System.out.printf("%n%s%n", mensaje);
		String formatoStr = "%0" + mensaje.length() + "d%n";
		System.out.println(String.format(formatoStr, 0).replace("0", "-"));
	}

	public static int elegirOpcion() {
		int ordinalOpcion;
		do {
			System.out.print("\nElige una opción: ");
			ordinalOpcion = Entrada.entero();
		} while (!Opcion.esOrdinalValido(ordinalOpcion));
		return ordinalOpcion;

	}

	public static Alumno leerAlumno() {
		System.out.print("Introduzca el nombre del alumno: ");
		String nombre = Entrada.cadena();
		System.out.print("Introduzca el correo del alumno: ");
		String correo = Entrada.cadena();

		return new Alumno(nombre, correo);

	}

	public static Alumno leerAlumnoFicticio() {
		System.out.print("Introduzca el correo del alumno: ");
		String correo = Entrada.cadena();
		return Alumno.getAlumnoFicticio(correo);

	}

	public static Profesor leerProfesor() {
		System.out.print("Introduzca el nombre del profesor: ");
		String nombre = Entrada.cadena();
		System.out.print("Introduzca el dni del profesor: ");
		String dni = Entrada.cadena();
		System.out.print("Introduzca el correo del profesor: ");
		String correo = Entrada.cadena();

		return new Profesor(nombre, dni, correo);
	}

	public static Profesor leerProfesorFicticio() {
		System.out.print("Introduzca el dni del profesor: ");
		String dni = Entrada.cadena();

		return Profesor.getProfesorFicticio(dni);
	}

	public static Tutoria leerTutoria() {
		System.out.print("Introduzca el nombre de la tutoría: ");
		String nombreTutoria = Entrada.cadena();

		return new Tutoria(leerProfesorFicticio(), nombreTutoria);

	}

	public static Sesion leerSesion() {
		String formatoCadena = "dd/MM/yyyy";
		DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(formatoCadena);
		LocalDate fecha = null;

		boolean fechaValida = false;
		do {

			try {
				System.out.print("Introduzca la fecha de la sesión (dd/MM/yyyy): ");

				fecha = LocalDate.parse(Entrada.cadena(), formatoFecha);
				fechaValida = true;
			} catch (DateTimeParseException e) {

				System.out.println("El formato de la fecha no es válido.");
				fechaValida = false;

			}
		} while (!fechaValida);

		DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime horaInicio = null;
		boolean horaValida = false;

		do {
			try {

				System.out.print("Introduzca una hora de inicio de la sesión (HH:mm): ");

				horaInicio = LocalTime.parse(Entrada.cadena(), formatoHora);
				horaValida = true;
			} catch (DateTimeParseException e) {
				System.out.println("ERROR: El formato de la hora de Inicio no es correcto.");
				horaValida = false;
			}
		} while (!horaValida);

		LocalTime horaFin = null;
		horaValida = false;

		do {
			try {

				System.out.print("Introduzca una hora de fin de la sesión (HH:mm): ");

				horaFin = LocalTime.parse(Entrada.cadena(), formatoHora);
				horaValida = true;
			} catch (DateTimeParseException e) {
				System.out.println("ERROR: El formato de la hora de Inicio no es correcto.");
				horaValida = false;
			}
		} while (!horaValida);

		System.out.print("Introduzca minutos de duración de la sesión: ");
		int minutosDuracion = Entrada.entero();

		return new Sesion(leerTutoria(), fecha, horaInicio, horaFin, minutosDuracion);

	}

	public static Sesion leerSesionFicticia() {
		String formatoCadena = "dd/MM/yyyy";
		DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(formatoCadena);
		LocalDate fecha = null;

		boolean fechaValida = false;
		do {

			try {
				System.out.print("Introduzca la fecha de la sesión (dd/MM/yyyy): ");

				fecha = LocalDate.parse(Entrada.cadena(), formatoFecha);
				fechaValida = true;
			} catch (DateTimeParseException e) {

				System.out.println("El formato de la fecha no es válido.");
				fechaValida = false;

			}
		} while (!fechaValida);
		return Sesion.getSesionFicticia(leerTutoria(), fecha);

	}

	public static Cita leerCita() {
		Alumno alumno = leerAlumnoFicticio();
		Sesion sesion = leerSesionFicticia();

		DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime horaCita = null;
		boolean horaValida = false;

		do {
			try {
				System.out.print("Introduzca la hora de reserva de la cita (HH:mm): ");

				horaCita = LocalTime.parse(Entrada.cadena(), formatoHora);
				horaValida = true;
			} catch (DateTimeParseException e) {
				System.out.println("ERROR: El formato de la hora no es correcto.");
				horaValida = false;
			}
		} while (!horaValida);

		return new Cita(alumno, sesion, horaCita);

	}
}
