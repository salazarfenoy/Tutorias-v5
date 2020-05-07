package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public class Alumno implements Serializable {
	private static final String ER_NOMBRE = "([a-zA-ZÁÉÍÓÚáéíóú]+)(\\s+([a-zA-ZÁÉÍÓÚáéíóú]+))+";
	private static final String PREFIJO_EXPEDIENTE = "SP_";
	private static final String ER_CORREO = "([\\w\\.]+[^.])@[\\w^\\_]+\\.[a-z]{2,3}";
	private static int ultimoIdentificador;
	private String nombre;
	private String correo;
	private String expediente;

	public Alumno(String nombre, String correo) {
		setNombre(nombre);
		setCorreo(correo);
		setExpediente();

	}
	
	public Alumno(String nombre, String correo, int identificador) {
		setNombre(nombre);
		setCorreo(correo);
		setExpediente(identificador);
	}

	public Alumno(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
		}
		setNombre(alumno.nombre);
		setCorreo(alumno.correo);
		this.expediente = alumno.expediente;
	}

	public static Alumno getAlumnoFicticio(String correo) {

		return new Alumno("Nombre Ficticio", correo);

	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {

		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if (!nombre.matches(ER_NOMBRE)) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		this.nombre = formateaNombre(nombre);
	}

	private String formateaNombre(String nombre) {
		nombre = nombre.replaceAll("( )+", " ");
		nombre = nombre.trim();

		String[] palabras = nombre.split(" ");
		StringBuilder copiaNombre = new StringBuilder();
		for (int i = 0; i <= palabras.length - 1; i++) {

			palabras[i] = palabras[i].substring(0, 1).toUpperCase() + palabras[i].substring(1).toLowerCase();

			copiaNombre.append(palabras[i] + " ");
		}

		nombre = copiaNombre.toString();
		return nombre.trim();

	}

	public String getCorreo() {
		return correo;
	}

	private void setCorreo(String correo) {
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo no puede ser nulo.");
		}
		if (!correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El formato del correo no es válido.");
		}
		this.correo = correo;
	}

	public String getExpediente() {
		return expediente;
	}

	private void setExpediente() {
		incrementaUltimoIdentificador();
		StringBuilder expedienteAsignado = new StringBuilder(PREFIJO_EXPEDIENTE);
		expedienteAsignado.append(getIniciales() + "_");
		expedienteAsignado.append(ultimoIdentificador);

		this.expediente = expedienteAsignado.toString();
	}
	
	private void setExpediente(int identificador) {
		
		if(identificador<=0) {
			throw new NullPointerException("ERROR: El identificador tiene que ser mayor que 0.");
		}
		StringBuilder expedienteAsignado = new StringBuilder(PREFIJO_EXPEDIENTE);
		expedienteAsignado.append(getIniciales() + "_");
		expedienteAsignado.append(identificador);
		
		this.expediente = expedienteAsignado.toString();
		
	}

	public static void asignarIdentificadorFichero(int identificadorFichero) {
		ultimoIdentificador = identificadorFichero;
	}

	private static void incrementaUltimoIdentificador() {

		++ultimoIdentificador;
	}

	private String getIniciales() {

		StringBuilder iniciales = new StringBuilder("");
		String[] palabras = getNombre().split(" ");
		for (int i = 0; i <= palabras.length - 1; i++) {

			iniciales.append(palabras[i].charAt(0));
		}

		return iniciales.toString().toUpperCase();

	}

	@Override
	public int hashCode() {
		return Objects.hash(correo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Alumno)) {
			return false;
		}
		Alumno other = (Alumno) obj;
		return Objects.equals(correo, other.correo);
	}

	@Override
	public String toString() {

		return String.format("nombre=%s (%s), correo=%s, expediente=%s", getNombre(), getIniciales(), getCorreo(),
				getExpediente());
	}

}
