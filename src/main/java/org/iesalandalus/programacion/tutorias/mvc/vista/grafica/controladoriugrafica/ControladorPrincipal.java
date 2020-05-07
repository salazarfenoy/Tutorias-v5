package org.iesalandalus.programacion.tutorias.mvc.vista.grafica.controladoriugrafica;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.utilidades.Dialogos;

import javafx.beans.property.SimpleIntegerProperty;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControladorPrincipal implements Initializable {

	private IControlador controladorMVC;

	// ER
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

	private static final String ER_OBLIGATORIO = ".+";

	private static final String ER_CORREO = "([\\w\\.]+[^.])@[\\w^\\_]+\\.[a-z]{2,3}";
	private static final String ER_NOMBRE = "([a-zA-ZÁÉÍÓÚáéíóú]+)(\\s+([a-zA-ZÁÉÍÓÚáéíóú]+))+";

	private static final String ER_DNI = "\\d{8}[A-Za-z]";

	// Observable y filtered list

	private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
	private FilteredList<Alumno> alumnosFiltrados = new FilteredList<>(alumnos, p -> true);

	private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
	private FilteredList<Profesor> profesoresFiltrados = new FilteredList<>(profesores, p -> true);

	private ObservableList<Tutoria> tutorias = FXCollections.observableArrayList();
	private FilteredList<Tutoria> tutoriasFiltradas = new FilteredList<>(tutorias, p -> true);

	private ObservableList<Sesion> sesiones = FXCollections.observableArrayList();
	private FilteredList<Sesion> sesionesFiltradas = new FilteredList<>(sesiones, p -> true);

	private ObservableList<Cita> citas = FXCollections.observableArrayList();
	private FilteredList<Cita> citasFiltradas = new FilteredList<>(citas, p -> true);

	// TableViews
	@FXML private TableView<Alumno> tvAlumnado;
	@FXML private TableView<Profesor> tvProfesorado;
	@FXML private TableView<Tutoria> tvTutorias;
	@FXML private TableView<Sesion> tvSesiones;
	@FXML private TableView<Cita> tvCitas;

	// tc Alumnado
	@FXML private TableColumn<Alumno, String> tcNombre;
	@FXML private TableColumn<Alumno, String> tcCorreo;
	@FXML private TableColumn<Alumno, String> tcExpediente;

	// tc Profesores
	@FXML private TableColumn<Profesor, String> tcNombreP;
	@FXML private TableColumn<Profesor, String> tcCorreoP;
	@FXML private TableColumn<Profesor, String> tcDni;

	// tc Tutorías
	@FXML private TableColumn<Tutoria, String> tcProfesorT;
	@FXML private TableColumn<Tutoria, String> tcNombreTutoria;

	// tc Sesiones
	@FXML private TableColumn<Sesion, String> tcProfesorS;
	@FXML private TableColumn<Sesion, String> tcNombreTutoriaS;
	@FXML private TableColumn<Sesion, String> tcFecha;
	@FXML private TableColumn<Sesion, String> tcHora;
	@FXML private TableColumn<Sesion, Integer> tcDuracion;

	// tc Citas

	@FXML private TableColumn<Cita, String> tcNombreTutoriaC;
	@FXML private TableColumn<Cita, String> tcSesionC;
	@FXML private TableColumn<Cita, String> tcHoraC;
	@FXML private TableColumn<Cita, String> tcAlumnoC;

	// TextFields

	// Crear alumno
	@FXML private TextField tfNombreAlumno;
	@FXML private TextField tfCorreo;

	// Crear profesor

	@FXML private TextField tfNombreProfesor;
	@FXML private TextField tfDni;
	@FXML private TextField tfCorreoProfesor;

	// Crear tutoría

	@FXML private TextField tfNombreTutoria;
	@FXML private ComboBox<Profesor> cajaProfesorTutoria;

	// Crear sesión

	@FXML private DatePicker tfFecha;
	@FXML private TextField tfHoraInicio;
	@FXML private TextField tfHoraFin;
	@FXML private TextField tfDuracion;
	@FXML private ComboBox<Tutoria> cajaTutoriaSesion;

	// Crear cita
	@FXML private ComboBox<Alumno> cajaAlumnoCita;
	@FXML private ComboBox<Tutoria> cajaTutoriaCita;
	@FXML private ComboBox<Sesion> cajaSesionCita;
	@FXML private ComboBox<LocalTime> cajaHoraCita;


	// Filtrar
	@FXML private TextField buscarAlumno;
	@FXML private TextField buscarProfesor;
	@FXML private TextField buscarTutoria;
	@FXML private TextField buscarSesion;
	@FXML private DatePicker filtrarSesionFecha;
	@FXML private TextField filtrarTutoriaC;
	@FXML private TextField filtrarAlumnoC;
	@FXML private DatePicker filtrarFechaC;
	@FXML private TextField filtrarPorDni;
	@FXML private TextField filtrarPorCorreo;



	// Botones y Paneles
	@FXML private ImageView botonVolverAlumnado, botonVolverProfesorado, botonVolverTutorias, botonVolverSesion,
	botonVolverCita;
	@FXML private Button botonSalir, botonAlumnado, botonProfesorado, botonTutorias, botonSesiones, botonCitas, botonAcercaDe,
	botonAceptarAlumno, botonAceptarProfesor, botonCrearProfesor, botonCrearAlumno, botonCrearTutoria,
	botonAceptarTutoria, botonCrearSesion, botonAceptarSesion, botonAceptarCita, botonCrearCita;
	@FXML private BorderPane panelRaiz;
	@FXML private AnchorPane panelAlumnado;
	@FXML private AnchorPane panelCrearAlumno;
	@FXML private AnchorPane panelProfesorado;
	@FXML private AnchorPane panelCrearProfesor;
	@FXML private AnchorPane panelTutorias;
	@FXML private AnchorPane panelCrearTutoria;
	@FXML private AnchorPane panelSesiones;
	@FXML private AnchorPane panelCrearSesion;
	@FXML private AnchorPane panelCitas;
	@FXML private AnchorPane panelCrearCita;
	@FXML private AnchorPane panelAcercaDe;


	public void setControlador(IControlador controlador) {
		this.controladorMVC = controlador;

	}

	public void actualizaTablas() {

		actualizaAlumnos();
		actualizaProfesores();
		actualizaTutorias();
		actualizaSesiones();
		actualizaCitas();

		tvAlumnado.setPlaceholder(new Label("No hay alumnos para mostrar."));

		tvProfesorado.setPlaceholder(new Label("No hay profesores para mostrar."));

		tvTutorias.setPlaceholder(new Label("No hay tutorías para mostrar."));

		tvSesiones.setPlaceholder(new Label("No hay sesiones para mostrar."));

		tvCitas.setPlaceholder(new Label("No hay citas para mostrar."));

	}

	private void filtrarTablas() {
		// filtrar alumnos

		buscarAlumno.textProperty().addListener((prop, old, text) -> {
			alumnosFiltrados.setPredicate(alumno -> {
				if (text == null || text.isEmpty())
					return true;

				String nombre = alumno.getNombre().toLowerCase();
				return nombre.contains(text.toLowerCase());
			});

		});

		filtrarPorCorreo.textProperty().addListener((prop, old, text) -> {
			alumnosFiltrados.setPredicate(alumno -> {
				if (text == null || text.isEmpty())
					return true;

				String correo = alumno.getCorreo().toLowerCase();
				return correo.contains(text.toLowerCase());
			});

		});

		// filtrar profesores

		buscarProfesor.textProperty().addListener((prop, old, text) -> {
			profesoresFiltrados.setPredicate(profesor -> {
				if (text == null || text.isEmpty())
					return true;

				String nombre = profesor.getNombre().toLowerCase();
				return nombre.contains(text.toLowerCase());
			});

		});

		filtrarPorDni.textProperty().addListener((prop, old, text) -> {
			profesoresFiltrados.setPredicate(profesor -> {
				if (text == null || text.isEmpty())
					return true;

				String dni = profesor.getDni().toLowerCase();
				return dni.contains(text.toLowerCase());
			});

		});

		//filtrar tutorías

		buscarTutoria.textProperty().addListener((prop, old, text) -> {
			tutoriasFiltradas.setPredicate(tutoria -> {
				if (text == null || text.isEmpty())
					return true;

				String nombre = tutoria.getNombre().toLowerCase();
				return nombre.contains(text.toLowerCase());
			});

		});

		//filtrar sesiones

		buscarSesion.textProperty().addListener((prop, old, text) -> {
			sesionesFiltradas.setPredicate(sesion -> {
				if (text == null || text.isEmpty())
					return true;

				String nombre = sesion.getTutoria().getNombre().toLowerCase();
				return nombre.contains(text.toLowerCase());
			});

		});

		filtrarSesionFecha.valueProperty().addListener((prop, old, text) -> {
			sesionesFiltradas.setPredicate(sesion -> {
				if (text == null)
					return true;

				LocalDate fecha = sesion.getFecha();
				return fecha.isEqual(text);
			});

		});

		//filtrar citas

		filtrarFechaC.valueProperty().addListener((prop, old, text) -> {
			citasFiltradas.setPredicate(cita -> {
				if (text == null)
					return true;

				LocalDate fecha = cita.getSesion().getFecha();
				return fecha.isEqual(text);
			});

		});

		filtrarTutoriaC.textProperty().addListener((prop, old, text) -> {
			citasFiltradas.setPredicate(cita -> {
				if (text == null || text.isEmpty())
					return true;

				String nombre = cita.getSesion().getTutoria().getNombre().toLowerCase();
				return nombre.contains(text.toLowerCase());
			});

		});

		filtrarAlumnoC.textProperty().addListener((prop, old, text) -> {
			citasFiltradas.setPredicate(cita -> {
				if (text == null || text.isEmpty())
					return true;

				String nombre = cita.getAlumno().getNombre().toLowerCase();
				return nombre.contains(text.toLowerCase());
			});

		});

	}

	private void limpiaFormulario() {
		//tf alumnos
		tfNombreAlumno.setText("");
		tfCorreo.setText("");
		buscarAlumno.setText("");
		filtrarPorCorreo.setText("");

		//tf profesores
		tfCorreoProfesor.setText("");
		tfDni.setText("");
		tfNombreProfesor.setText("");
		buscarProfesor.setText("");
		filtrarPorDni.setText("");

		//tf tutorías
		tfNombreTutoria.setText("");
		cajaProfesorTutoria.valueProperty().set(null);
		buscarTutoria.setText("");

		//tf sesiones
		cajaTutoriaSesion.valueProperty().set(null);
		tfFecha.valueProperty().set(null);
		tfHoraFin.setText("");
		tfHoraInicio.setText("");
		tfDuracion.setText("");
		filtrarSesionFecha.valueProperty().set(null);
		buscarSesion.setText("");

		// tf citas
		cajaAlumnoCita.valueProperty().set(null);
		cajaTutoriaCita.valueProperty().set(null);
		cajaSesionCita.valueProperty().set(null);
		cajaHoraCita.valueProperty().set(null);
		filtrarFechaC.valueProperty().set(null);
		filtrarTutoriaC.setText("");
		filtrarAlumnoC.setText("");

	}

	// Menú Principal

	@FXML
	private void irACrear(ActionEvent e) {
		if (botonCrearAlumno == (Button) e.getSource())
			panelCrearAlumno.toFront();
		if (botonCrearProfesor == (Button) e.getSource())
			panelCrearProfesor.toFront();
		if (botonCrearTutoria == (Button) e.getSource())
			panelCrearTutoria.toFront();
		if (botonCrearSesion == (Button) e.getSource())
			panelCrearSesion.toFront();
		if (botonCrearCita == (Button) e.getSource())
			panelCrearCita.toFront();

	}

	@FXML
	private void salir(ActionEvent e) {

		Stage escenarioPrincipal = (Stage) botonSalir.getScene().getWindow();

		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir?", null)) {
			controladorMVC.terminar();
			escenarioPrincipal.close();

		} else {
			e.consume();
		}

	}

	private void cambiarEstilo(Button botonElegido) {
		Button[] botones = { botonAlumnado, botonProfesorado, botonTutorias, botonSesiones, botonCitas, botonAcercaDe };

		for (Button boton : botones) {
			if (boton != botonElegido) {
				boton.setId("botonMenu");
			}
		}
	}

	@FXML
	private void elegirBotonMenu(ActionEvent evento) {
		Button botonPulsado = (Button) evento.getSource();

		if (botonPulsado == botonAlumnado) {
			botonAlumnado.setId("botonPulsado");
			cambiarEstilo(botonAlumnado);
			panelAlumnado.toFront();
			limpiaFormulario();

		}

		if (botonPulsado == botonProfesorado) {
			botonProfesorado.setId("botonPulsado");
			cambiarEstilo(botonProfesorado);

			panelProfesorado.toFront();
			limpiaFormulario();
		}

		if (botonPulsado == botonTutorias) {
			botonTutorias.setId("botonPulsado");
			cambiarEstilo(botonTutorias);
			tutorias.setAll(controladorMVC.getTutorias());
			actualizaTutorias();
			panelTutorias.toFront();
			limpiaFormulario();
		}

		if (botonPulsado == botonSesiones) {
			botonSesiones.setId("botonPulsado");
			cambiarEstilo(botonSesiones);
			sesiones.setAll(controladorMVC.getSesiones());
			actualizaSesiones();
			panelSesiones.toFront();
			limpiaFormulario();
		}

		if (botonPulsado == botonCitas) {
			botonCitas.setId("botonPulsado");
			cambiarEstilo(botonCitas);
			actualizaCitas();
			panelCitas.toFront();
			limpiaFormulario();
		}

		if (botonPulsado == botonAcercaDe) {
			botonAcercaDe.setId("botonPulsado");
			cambiarEstilo(botonAcercaDe);
			panelAcercaDe.toFront();
		}
	}

	@FXML
	private void volver(MouseEvent evento) {
		ImageView botonPulsado = (ImageView) evento.getSource();

		if (botonPulsado == botonVolverAlumnado) {
			limpiaFormulario();
			panelAlumnado.toFront();
		}

		if (botonPulsado == botonVolverProfesorado) {
			limpiaFormulario();
			panelProfesorado.toFront();
		}

		if (botonPulsado == botonVolverTutorias) {
			limpiaFormulario();
			panelTutorias.toFront();
		}
		if (botonPulsado == botonVolverSesion) {
			limpiaFormulario();
			panelSesiones.toFront();
		}
		if (botonPulsado == botonVolverCita) {
			limpiaFormulario();
			panelCitas.toFront();
		}
	}

	// Creación objetos

	@FXML
	private void aceptarYCrear(ActionEvent evento) {
		Button botonPulsado = (Button) evento.getSource();

		if (botonPulsado == botonAceptarAlumno) {
			crearAlumno();
		}

		if (botonPulsado == botonAceptarProfesor) {
			crearProfesor();
		}
		if (botonPulsado == botonAceptarTutoria) {
			crearTutoria();
		}
		if (botonPulsado == botonAceptarSesion) {
			crearSesion();
		}
		if (botonPulsado == botonAceptarCita) {
			crearCita();
		}
	}

	private void compruebaCampoTexto(String er, TextField campoTexto) {
		String texto = campoTexto.getText();
		if (texto.matches(er)) {
			campoTexto.setStyle("-fx-border-color: green;");
		} else if (texto.matches("")) {
			campoTexto.setStyle("-fx-border-color: gray;");
		} else {
			campoTexto.setStyle("-fx-border-color: red;");
		}
	}

	private void observarTextFields() {
		tfNombreAlumno.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_NOMBRE, tfNombreAlumno));
		tfCorreo.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_CORREO, tfCorreo));
		tfCorreoProfesor.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_CORREO, tfCorreoProfesor));
		tfNombreProfesor.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_NOMBRE, tfNombreProfesor));
		tfDni.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_DNI, tfDni));
		tfNombreTutoria.textProperty()
		.addListener((ob, ov, nv) -> compruebaCampoTexto(ER_OBLIGATORIO, tfNombreTutoria));

	}

	// Alumnos

	private void actualizaAlumnos() {

		alumnos.setAll(controladorMVC.getAlumnos());
	}

	private void iniciarTablaAlumnos() {
		tcNombre.setCellValueFactory(alumno -> new SimpleStringProperty(alumno.getValue().getNombre()));
		tcCorreo.setCellValueFactory(alumno -> new SimpleStringProperty(alumno.getValue().getCorreo()));
		tcExpediente.setCellValueFactory(alumno -> new SimpleStringProperty(alumno.getValue().getExpediente()));

		SortedList<Alumno> alumnosOrdenados = new SortedList<>(alumnosFiltrados);
		alumnosOrdenados.comparatorProperty().bind(tvAlumnado.comparatorProperty());
		tvAlumnado.setItems(alumnosOrdenados);

	}

	private void crearAlumno() {
		Alumno alumno = null;
		try {
			alumno = getAlumno();
			controladorMVC.insertar(alumno);
			Dialogos.mostrarDialogoInformacion("", "Alumno añadido satisfactoriamente.", null);
			alumnos.setAll(controladorMVC.getAlumnos());
			limpiaFormulario();
			panelAlumnado.toFront();
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("", e.getMessage());
		}
	}

	@FXML
	private void borrarAlumno() throws OperationNotSupportedException {
		Alumno alumno = (Alumno) tvAlumnado.getSelectionModel().getSelectedItem();
		if (alumno == null) {
			Dialogos.mostrarDialogoAdvertencia("", "Debes seleccionar un alumno de la tabla.");
		} else {
			String nombre = alumno.getNombre();
			if (Dialogos.mostrarDialogoConfirmacion("",
					"¿Estás seguro de que quieres eliminar al alumno " + nombre + "?", null)) {

				controladorMVC.borrar(alumno);
				actualizaAlumnos();
				Dialogos.mostrarDialogoAdvertencia("", "Alumno borrado satisfactoriamente");

			}
		}
	}

	@FXML
	private void verAlumno() {

		Alumno alumno = (Alumno) tvAlumnado.getSelectionModel().getSelectedItem();
		if (alumno == null) {
			Dialogos.mostrarDialogoAdvertencia("", "Debes seleccionar un alumno de la tabla.");
		} else {
			GridPane panelVerAlumno = new GridPane();
			panelVerAlumno.getStyleClass().add("gridPaneAlert");
			TextField nombre = new TextField();
			TextField correo = new TextField();
			TextField expediente = new TextField();
			nombre.setEditable(false);
			correo.setEditable(false);
			expediente.setEditable(false);
			nombre.setText(alumno.getNombre());
			correo.setText(alumno.getCorreo());
			expediente.setText(alumno.getExpediente());
			nombre.getStyleClass().addAll("textoPlano", "textoVer");
			correo.getStyleClass().addAll("textoPlano", "textoVer");
			expediente.getStyleClass().addAll("textoPlano", "textoVer");
			panelVerAlumno.add(new Text("Nombre:"), 1, 0);
			panelVerAlumno.add(nombre, 2, 0);
			panelVerAlumno.add(new Text("Correo:"), 1, 1);
			panelVerAlumno.add(correo, 2, 1);
			panelVerAlumno.add(new Text("Expediente:"), 1, 2);
			panelVerAlumno.add(expediente, 2, 2);

			Dialogos.mostrarDialogoInformacionPersonalizado("Alumno", panelVerAlumno);

		}
	}

	private Alumno getAlumno() {
		Alumno alumno = null;
		try {
			String nombre = tfNombreAlumno.getText();
			String correo = tfCorreo.getText();
			alumno = new Alumno(nombre, correo);
		} catch (NumberFormatException e) {
			Dialogos.mostrarDialogoError("Añadir alumno", e.getMessage());
		}
		return alumno;
	}

	// Profesorado

	@FXML
	private void actualizaProfesores() {
		profesores.setAll(controladorMVC.getProfesores());
	}

	private void iniciarTablaProfesores() {
		tcNombreP.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getNombre()));
		tcCorreoP.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getCorreo()));
		tcDni.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getDni()));

		SortedList<Profesor> profesoresOrdenados = new SortedList<>(profesoresFiltrados);
		profesoresOrdenados.comparatorProperty().bind(tvProfesorado.comparatorProperty());
		tvProfesorado.setItems(profesoresOrdenados);

	}

	private void crearProfesor() {
		Profesor profesor = null;
		try {
			profesor = getProfesor();
			controladorMVC.insertar(profesor);
			Dialogos.mostrarDialogoInformacion("", "Profesor añadido satisfactoriamente.", null);
			limpiaFormulario();
			actualizaProfesores();
			panelProfesorado.toFront();
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("", e.getMessage());
		}
	}

	@FXML
	private void borrarProfesor() throws OperationNotSupportedException {
		Profesor profesor = (Profesor) tvProfesorado.getSelectionModel().getSelectedItem();
		if (profesor == null) {
			Dialogos.mostrarDialogoAdvertencia("", "Debes seleccionar un profesor de la tabla.");
		} else {
			String nombre = profesor.getNombre();
			if (Dialogos.mostrarDialogoConfirmacion("",
					"¿Estás seguro de que quieres eliminar al profesor " + nombre + "?", null)) {
				controladorMVC.borrar(profesor);
				actualizaProfesores();
				Dialogos.mostrarDialogoAdvertencia("", "Profesor borrado satisfactoriamente");

			}
		}
	}

	@FXML
	private void verProfesor() {

		Profesor profesor = (Profesor) tvProfesorado.getSelectionModel().getSelectedItem();
		if (profesor == null) {
			Dialogos.mostrarDialogoAdvertencia("", "Debes seleccionar un profesor de la tabla.");
		} else {
			GridPane panelVerProfesor = new GridPane();
			panelVerProfesor.getStyleClass().add("gridPaneAlert");
			TextField nombre = new TextField();
			TextField correo = new TextField();
			TextField dni = new TextField();
			nombre.setEditable(false);
			correo.setEditable(false);
			dni.setEditable(false);
			nombre.setText(profesor.getNombre());
			correo.setText(profesor.getCorreo());
			dni.setText(profesor.getDni());
			nombre.getStyleClass().addAll("textoPlano", "textoVer");
			correo.getStyleClass().addAll("textoPlano", "textoVer");
			dni.getStyleClass().addAll("textoPlano", "textoVer");
			panelVerProfesor.add(new Text("Nombre:"), 1, 0);
			panelVerProfesor.add(nombre, 2, 0);
			panelVerProfesor.add(new Text("Dni:"), 1, 1);
			panelVerProfesor.add(dni, 2, 1);
			panelVerProfesor.add(new Text("Correo:"), 1, 2);
			panelVerProfesor.add(correo, 2, 2);

			Dialogos.mostrarDialogoInformacionPersonalizado("", panelVerProfesor);

		}
	}

	private Profesor getProfesor() {
		Profesor profesor = null;
		try {
			String nombre = tfNombreProfesor.getText();
			String correo = tfCorreoProfesor.getText();
			String dni = tfDni.getText();
			profesor = new Profesor(nombre, dni, correo);
		} catch (NumberFormatException e) {
			Dialogos.mostrarDialogoError("", e.getMessage());
		}
		return profesor;
	}

	// Tutorías

	@FXML
	private void actualizaTutorias() {
		tutorias.setAll(controladorMVC.getTutorias());
		cajaProfesorTutoria.getItems().clear();
		cajaProfesorTutoria.getItems().addAll(profesores);

	}

	@FXML

	private void iniciarTablaTutorias() {
		tcNombreTutoria.setCellValueFactory(tutoria -> new SimpleStringProperty(tutoria.getValue().getNombre()));
		tcProfesorT
		.setCellValueFactory(tutoria -> new SimpleStringProperty(tutoria.getValue().getProfesor().getNombre()));

		SortedList<Tutoria> tutoriasOrdenadas = new SortedList<>(tutoriasFiltradas);
		tutoriasOrdenadas.comparatorProperty().bind(tvTutorias.comparatorProperty());
		tvTutorias.setItems(tutoriasOrdenadas);
		Callback<ListView<Profesor>, ListCell<Profesor>> factory = lv -> new ListCell<Profesor>() {

			@Override
			protected void updateItem(Profesor profesor, boolean empty) {
				super.updateItem(profesor, empty);
				setText(empty ? "" : profesor.getNombre());
			}

		};

		cajaProfesorTutoria.setCellFactory(factory);
		cajaProfesorTutoria.setButtonCell(factory.call(null));

	}

	private void crearTutoria() {
		Tutoria tutoria = null;
		try {
			tutoria = getTutoria();
			controladorMVC.insertar(tutoria);
			Dialogos.mostrarDialogoInformacion("", "Tutoría añadida satisfactoriamente.", null);
			limpiaFormulario();
			actualizaTutorias();
			panelTutorias.toFront();
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("", e.getMessage());
		}
	}

	@FXML
	private void borrarTutoria() throws OperationNotSupportedException {
		Tutoria tutoria = (Tutoria) tvTutorias.getSelectionModel().getSelectedItem();
		if (tutoria == null) {
			Dialogos.mostrarDialogoAdvertencia("", "Debes seleccionar una tutoría de la tabla.");
		} else {
			String nombre = tutoria.getNombre();
			if (Dialogos.mostrarDialogoConfirmacion("",
					"¿Estás seguro de que quieres eliminar la tutoría " + nombre + "?", null)) {
				controladorMVC.borrar(tutoria);
				actualizaTutorias();
				Dialogos.mostrarDialogoAdvertencia("", "Tutoría borrada satisfactoriamente.");

			}
		}
	}

	@FXML
	private void verTutoria() {

		Tutoria tutoria = (Tutoria) tvTutorias.getSelectionModel().getSelectedItem();
		if (tutoria == null) {
			Dialogos.mostrarDialogoAdvertencia("", "Debes seleccionar un a tutoría de la tabla.");
		} else {
			GridPane panelVerTutoria = new GridPane();
			panelVerTutoria.getStyleClass().add("gridPaneAlert");
			TextField nombre = new TextField();
			TextField nombreP = new TextField();
			TextField dniP = new TextField();
			TextField correoP = new TextField();
			nombre.setEditable(false);
			nombreP.setEditable(false);
			dniP.setEditable(false);
			correoP.setEditable(false);
			nombre.setText(tutoria.getNombre());
			nombreP.setText(tutoria.getProfesor().getNombre());
			dniP.setText(tutoria.getProfesor().getDni());
			correoP.setText(tutoria.getProfesor().getCorreo());
			nombre.getStyleClass().addAll("textoPlano", "textoVer");
			nombreP.getStyleClass().addAll("textoPlano", "textoVer");
			correoP.getStyleClass().addAll("textoPlano", "textoVer");
			dniP.getStyleClass().addAll("textoPlano", "textoVer");
			panelVerTutoria.add(new Text("Tutoría:"), 1, 0);
			panelVerTutoria.add(nombre, 2, 0);
			panelVerTutoria.add(new Text("Profesor:"), 1, 1);
			panelVerTutoria.add(nombreP, 2, 1);
			panelVerTutoria.add(new Text("DNI:"), 1, 2);
			panelVerTutoria.add(dniP, 2, 2);
			panelVerTutoria.add(new Text("Correo:"), 1, 3);
			panelVerTutoria.add(correoP, 2, 3);

			Dialogos.mostrarDialogoInformacionPersonalizado("", panelVerTutoria);

		}
	}

	private Tutoria getTutoria() {
		Tutoria tutoria = null;
		try {
			String nombre = tfNombreTutoria.getText();
			Profesor profesor = cajaProfesorTutoria.getValue();
			tutoria = new Tutoria(profesor, nombre);
		} catch (NumberFormatException e) {
			Dialogos.mostrarDialogoError("", e.getMessage());
		}
		return tutoria;
	}

	// Sesiones

	@FXML
	private void actualizaSesiones() {
		sesiones.setAll(controladorMVC.getSesiones());
		cajaTutoriaSesion.getItems().clear();
		cajaTutoriaSesion.getItems().addAll(tutorias);

	}

	@FXML

	private void iniciarTablaSesiones() {
		tcNombreTutoriaS
		.setCellValueFactory(sesion -> new SimpleStringProperty(sesion.getValue().getTutoria().getNombre()));
		tcProfesorS.setCellValueFactory(
				sesion -> new SimpleStringProperty(sesion.getValue().getTutoria().getProfesor().getNombre()));
		tcFecha.setCellValueFactory(
				sesion -> new SimpleStringProperty(FORMATO_FECHA.format(sesion.getValue().getFecha())));
		tcHora.setCellValueFactory(
				sesion -> new SimpleStringProperty(FORMATO_HORA.format(sesion.getValue().getHoraInicio()).concat(" - ")
						.concat(FORMATO_HORA.format(sesion.getValue().getHoraFin()))));
		tcDuracion.setCellValueFactory(
				sesion -> new SimpleIntegerProperty(sesion.getValue().getMinutosDuracion()).asObject());

		SortedList<Sesion> sesionesOrdenadas = new SortedList<>(sesionesFiltradas);
		sesionesOrdenadas.comparatorProperty().bind(tvSesiones.comparatorProperty());
		tvSesiones.setItems(sesionesOrdenadas);

		Callback<ListView<Tutoria>, ListCell<Tutoria>> factory = lv -> new ListCell<Tutoria>() {

			@Override
			protected void updateItem(Tutoria tutoria, boolean empty) {
				super.updateItem(tutoria, empty);
				setText(empty ? "" : tutoria.getNombre().concat(" - ").concat(tutoria.getProfesor().getNombre()));
			}

		};

		cajaTutoriaSesion.setCellFactory(factory);
		cajaTutoriaSesion.setButtonCell(factory.call(null));

	}

	private void crearSesion() {
		Sesion sesion = null;
		try {
			sesion = getSesion();
			controladorMVC.insertar(sesion);
			Dialogos.mostrarDialogoInformacion("", "Sesión añadida satisfactoriamente.", null);
			limpiaFormulario();
			sesiones.setAll(controladorMVC.getSesiones());
			panelSesiones.toFront();
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("", e.getMessage());
		}
	}

	@FXML
	private void borrarSesion() throws OperationNotSupportedException {
		Sesion sesion = (Sesion) tvSesiones.getSelectionModel().getSelectedItem();
		if (sesion == null) {
			Dialogos.mostrarDialogoAdvertencia("", "Debes seleccionar una sesión de la tabla.");
		} else {

			if (Dialogos.mostrarDialogoConfirmacion("", "¿Estás seguro de que quieres eliminar esta sesión?", null)) {
				controladorMVC.borrar(sesion);
				actualizaSesiones();
				Dialogos.mostrarDialogoAdvertencia("", "Sesión borrada satisfactoriamente.");

			}
		}
	}

	@FXML
	private void verSesion() {

		Sesion sesion = (Sesion) tvSesiones.getSelectionModel().getSelectedItem();
		if (sesion == null) {
			Dialogos.mostrarDialogoAdvertencia("", "Debes seleccionar una sesión de la tabla.");
		} else {

			GridPane panelVerSesion = new GridPane();
			panelVerSesion.getStyleClass().add("gridPaneAlert");
			TextField tutoria = new TextField();
			TextField nombreP = new TextField();
			TextField dniP = new TextField();
			TextField correoP = new TextField();
			TextField fecha = new TextField();
			TextField hora = new TextField();
			TextField duracion = new TextField();
			tutoria.setEditable(false);
			nombreP.setEditable(false);
			dniP.setEditable(false);
			correoP.setEditable(false);
			fecha.setEditable(false);
			hora.setEditable(false);
			duracion.setEditable(false);
			tutoria.setText(sesion.getTutoria().getNombre());
			nombreP.setText(sesion.getTutoria().getProfesor().getNombre());
			dniP.setText(sesion.getTutoria().getProfesor().getDni());
			correoP.setText(sesion.getTutoria().getProfesor().getCorreo());
			fecha.setText(FORMATO_FECHA.format(sesion.getFecha()));
			hora.setText(FORMATO_HORA.format(sesion.getHoraInicio()).concat(" - ")
					.concat(FORMATO_HORA.format(sesion.getHoraFin())));
			duracion.setText(String.valueOf(sesion.getMinutosDuracion()).concat(" minutos"));
			tutoria.getStyleClass().addAll("textoPlano", "textoVer");
			nombreP.getStyleClass().addAll("textoPlano", "textoVer");
			correoP.getStyleClass().addAll("textoPlano", "textoVer");
			dniP.getStyleClass().addAll("textoPlano", "textoVer");
			fecha.getStyleClass().addAll("textoPlano", "textoVer");
			hora.getStyleClass().addAll("textoPlano", "textoVer");
			duracion.getStyleClass().addAll("textoPlano", "textoVer");
			panelVerSesion.add(new Text("Tutoría:"), 1, 0);
			panelVerSesion.add(tutoria, 2, 0);
			panelVerSesion.add(new Text("Profesor:"), 1, 1);
			panelVerSesion.add(nombreP, 2, 1);
			panelVerSesion.add(new Text("DNI:"), 1, 2);
			panelVerSesion.add(dniP, 2, 2);
			panelVerSesion.add(new Text("Correo:"), 1, 3);
			panelVerSesion.add(correoP, 2, 3);
			panelVerSesion.add(new Text("Fecha:"), 1, 4);
			panelVerSesion.add(fecha, 2, 4);
			panelVerSesion.add(new Text("Hora:"), 1, 5);
			panelVerSesion.add(hora, 2, 5);
			panelVerSesion.add(new Text("Duración:"), 1, 6);
			panelVerSesion.add(duracion, 2, 6);

			Dialogos.mostrarDialogoInformacionPersonalizado("", panelVerSesion);

		}
	}

	private Sesion getSesion() {
		Sesion sesion = null;
		try {
			Tutoria tutoria = cajaTutoriaSesion.getSelectionModel().getSelectedItem();

			LocalDate fecha = tfFecha.getValue();
			LocalTime horaI = LocalTime.parse(tfHoraInicio.getText(), FORMATO_HORA);
			LocalTime horaF = LocalTime.parse(tfHoraFin.getText(), FORMATO_HORA);
			int duracion = Integer.parseInt(tfDuracion.getText());

			sesion = new Sesion(tutoria, fecha, horaI, horaF, duracion);
		} catch (NumberFormatException e) {
			Dialogos.mostrarDialogoError("", e.getMessage());
		}
		return sesion;
	}

	// Citas

	@FXML
	private void actualizaCitas() {
		citas.setAll(controladorMVC.getCitas());
		cajaAlumnoCita.getItems().clear();
		cajaAlumnoCita.getItems().addAll(alumnos);
		cajaTutoriaCita.getItems().clear();
		cajaTutoriaCita.getItems().addAll(tutorias);

	}

	@FXML
	private void rellenar() {
		Tutoria tutoria = cajaTutoriaCita.getSelectionModel().getSelectedItem();
		cajaSesionCita.getItems().clear();
		if (tutoria == null) {
			cajaSesionCita.valueProperty().set(null);
		} else {
			cajaSesionCita.getItems()
			.addAll(controladorMVC.getSesiones(cajaTutoriaCita.getSelectionModel().getSelectedItem()));
		}
	}

	@FXML
	private void horasDisponibles() {
		Sesion sesion = cajaSesionCita.getSelectionModel().getSelectedItem();
		cajaHoraCita.getItems().clear();
		LocalTime horaAux;
		if (sesion == null) {
			cajaHoraCita.valueProperty().set(null);
		} else {
			List<LocalTime> horasDisponibles = new ArrayList<>();
			List<Cita> citasPorSesion = new ArrayList<>();
			citasPorSesion.addAll(controladorMVC.getCitas(sesion));
			List<LocalTime> horasReservadas = new ArrayList<>();
			for (Cita cita : citasPorSesion) {
				horasReservadas.add(cita.getHora());
			}
			horaAux = sesion.getHoraInicio();

			do {

				horasDisponibles.add(horaAux);

				horaAux = horaAux.plusMinutes(sesion.getMinutosDuracion());
			} while (horaAux.isBefore(sesion.getHoraFin()));

			horasDisponibles.removeAll(horasReservadas);
			cajaHoraCita.getItems().addAll(horasDisponibles);

		}
	}

	@FXML
	private void limpiaSesion() {
		cajaSesionCita.valueProperty().set(null);
	}

	@FXML

	private void iniciarTablaCitas() {
		tcNombreTutoriaC.setCellValueFactory(
				cita -> new SimpleStringProperty(cita.getValue().getSesion().getTutoria().getNombre()));
		tcSesionC.setCellValueFactory(
				cita -> new SimpleStringProperty(FORMATO_FECHA.format(cita.getValue().getSesion().getFecha())
						.concat(" ").concat(FORMATO_HORA.format(cita.getValue().getSesion().getHoraInicio())
								.concat(" - ").concat(FORMATO_HORA.format(cita.getValue().getSesion().getHoraFin())))));
		tcHoraC.setCellValueFactory(cita -> new SimpleStringProperty(FORMATO_HORA.format(cita.getValue().getHora())));
		tcAlumnoC.setCellValueFactory(cita -> new SimpleStringProperty(cita.getValue().getAlumno().getNombre()));

		SortedList<Cita> citasOrdenadas = new SortedList<>(citasFiltradas);
		citasOrdenadas.comparatorProperty().bind(tvCitas.comparatorProperty());
		tvCitas.setItems(citasOrdenadas);

		Callback<ListView<Tutoria>, ListCell<Tutoria>> factory = lv -> new ListCell<Tutoria>() {

			@Override
			protected void updateItem(Tutoria tutoria, boolean empty) {
				super.updateItem(tutoria, empty);
				setText(empty ? "" : tutoria.getNombre().concat(" - ").concat(tutoria.getProfesor().getNombre()));
			}

		};

		Callback<ListView<Sesion>, ListCell<Sesion>> factoryS = lv -> new ListCell<Sesion>() {

			@Override
			protected void updateItem(Sesion sesion, boolean empty) {
				super.updateItem(sesion, empty);
				setText(empty ? ""
						: FORMATO_FECHA.format(sesion.getFecha()).concat("  ")
						.concat(FORMATO_HORA.format(sesion.getHoraInicio()).concat(" - ")
								.concat(FORMATO_HORA.format(sesion.getHoraFin()))));
			}

		};

		Callback<ListView<Alumno>, ListCell<Alumno>> factoryA = lv -> new ListCell<Alumno>() {

			@Override
			protected void updateItem(Alumno alumno, boolean empty) {
				super.updateItem(alumno, empty);
				setText(empty ? "" : alumno.getNombre().concat(" - ").concat(alumno.getCorreo()));
			}

		};
		cajaTutoriaCita.setCellFactory(factory);
		cajaTutoriaCita.setButtonCell(factory.call(null));
		cajaSesionCita.setCellFactory(factoryS);
		cajaSesionCita.setButtonCell(factoryS.call(null));
		cajaAlumnoCita.setCellFactory(factoryA);
		cajaAlumnoCita.setButtonCell(factoryA.call(null));

	}

	private void crearCita() {
		Cita cita = null;
		try {
			cita = getCita();
			controladorMVC.insertar(cita);
			Dialogos.mostrarDialogoInformacion("", "Cita añadida satisfactoriamente.", null);
			limpiaFormulario();
			citas.setAll(controladorMVC.getCitas());
			panelCitas.toFront();
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("", e.getMessage());
		}
	}

	@FXML
	private void borrarCita() throws OperationNotSupportedException {
		Cita cita = tvCitas.getSelectionModel().getSelectedItem();
		if (cita == null) {
			Dialogos.mostrarDialogoAdvertencia("", "Debes seleccionar una cita de la tabla.");
		} else {

			if (Dialogos.mostrarDialogoConfirmacion("", "¿Estás seguro de que quieres eliminar esta cita?", null)) {
				controladorMVC.borrar(cita);
				actualizaCitas();
				Dialogos.mostrarDialogoAdvertencia("", "Cita borrada satisfactoriamente.");

			}
		}
	}

	@FXML
	private void verCita() {

		Cita cita = tvCitas.getSelectionModel().getSelectedItem();
		if (cita == null) {
			Dialogos.mostrarDialogoAdvertencia("", "Debes seleccionar una cita de la tabla.");
		} else {

			HBox panelTotal = new HBox();
			panelTotal.getStyleClass().add("panelVer");
			VBox panel = new VBox();
			VBox panelDos = new VBox();
			panel.getStyleClass().add("vBoxCita");
			panelDos.getStyleClass().add("vBoxCita");
			GridPane panelVerSesion = new GridPane();
			panelVerSesion.getStyleClass().add("gridPaneAlert");
			GridPane panelVerSesionDos = new GridPane();
			panelVerSesionDos.getStyleClass().add("gridPaneAlert");
			GridPane panelVerSesionTres = new GridPane();
			panelVerSesionTres.getStyleClass().add("gridPaneAlert");
			TextField tutoria = new TextField();
			TextField nombreP = new TextField();
			TextField dniP = new TextField();
			TextField correoP = new TextField();
			TextField fecha = new TextField();
			TextField hora = new TextField();
			TextField duracion = new TextField();
			TextField citaH = new TextField();
			TextField alumnoN = new TextField();
			TextField alumnoC = new TextField();
			TextField alumnoE = new TextField();
			tutoria.setEditable(false);
			nombreP.setEditable(false);
			dniP.setEditable(false);
			correoP.setEditable(false);
			fecha.setEditable(false);
			hora.setEditable(false);
			duracion.setEditable(false);
			citaH.setEditable(false);
			alumnoN.setEditable(false);
			alumnoC.setEditable(false);
			alumnoE.setEditable(false);
			tutoria.setText(cita.getSesion().getTutoria().getNombre());
			nombreP.setText(cita.getSesion().getTutoria().getProfesor().getNombre());
			dniP.setText(cita.getSesion().getTutoria().getProfesor().getDni());
			correoP.setText(cita.getSesion().getTutoria().getProfesor().getCorreo());
			fecha.setText(FORMATO_FECHA.format(cita.getSesion().getFecha()));
			hora.setText(FORMATO_HORA.format(cita.getSesion().getHoraInicio()).concat(" - ")
					.concat(FORMATO_HORA.format(cita.getSesion().getHoraFin())));
			duracion.setText(String.valueOf(cita.getSesion().getMinutosDuracion()).concat(" minutos"));
			citaH.setText(FORMATO_HORA.format(cita.getHora()));
			alumnoN.setText(cita.getAlumno().getNombre());
			alumnoC.setText(cita.getAlumno().getCorreo());
			alumnoE.setText(cita.getAlumno().getExpediente());
			tutoria.getStyleClass().addAll("textoPlano", "textoVer");
			nombreP.getStyleClass().addAll("textoPlano", "textoVer");
			correoP.getStyleClass().addAll("textoPlano", "textoVer");
			dniP.getStyleClass().addAll("textoPlano", "textoVer");
			fecha.getStyleClass().addAll("textoPlano", "textoVer");
			hora.getStyleClass().addAll("textoPlano", "textoVer");
			duracion.getStyleClass().addAll("textoPlano", "textoVer");
			citaH.getStyleClass().addAll("textoPlano", "textoVer");
			alumnoN.getStyleClass().addAll("textoPlano", "textoVer");
			alumnoC.getStyleClass().addAll("textoPlano", "textoVer");
			alumnoE.getStyleClass().addAll("textoPlano", "textoVer");
			panelVerSesion.add(new Text("Tutoría:"), 1, 0);
			panelVerSesion.add(tutoria, 2, 0);
			panelVerSesion.add(new Text("Profesor:"), 1, 1);
			panelVerSesion.add(nombreP, 2, 1);
			panelVerSesion.add(new Text("DNI:"), 1, 2);
			panelVerSesion.add(dniP, 2, 2);
			panelVerSesion.add(new Text("Correo:"), 1, 3);
			panelVerSesion.add(correoP, 2, 3);

			panelVerSesionDos.add(new Text("Nombre:"), 1, 0);
			panelVerSesionDos.add(alumnoN, 2, 0);
			panelVerSesionDos.add(new Text("Correo:"), 1, 1);
			panelVerSesionDos.add(alumnoC, 2, 1);
			panelVerSesionDos.add(new Text("Expediente:"), 1, 2);
			panelVerSesionDos.add(alumnoE, 2, 2);

			panelVerSesionTres.add(new Text("Fecha:"), 1, 0);
			panelVerSesionTres.add(fecha, 2, 0);
			panelVerSesionTres.add(new Text("Hora:"), 1, 1);
			panelVerSesionTres.add(hora, 2, 1);
			panelVerSesionTres.add(new Text("Duración:"), 1, 2);
			panelVerSesionTres.add(duracion, 2, 2);
			panelVerSesionTres.add(new Text("Hora de la cita:"), 1, 3);
			panelVerSesionTres.add(citaH, 2, 3);

			panelTotal.getChildren().addAll(panel, panelDos);
			panel.getChildren().addAll(new Label("Tutoría"),panelVerSesion,new Label("Alumno"),panelVerSesionDos);
			panelDos.getChildren().addAll(new Label("Cita"),panelVerSesionTres);

			Dialogos.mostrarDialogoInformacionCita("", panelTotal);

		}
	}

	private Cita getCita() {
		Cita cita = null;
		try {
			Sesion sesion = cajaSesionCita.getSelectionModel().getSelectedItem();
			Alumno alumno = cajaAlumnoCita.getSelectionModel().getSelectedItem();
			LocalTime hora = cajaHoraCita.getSelectionModel().getSelectedItem();
			cita = new Cita(alumno, sesion, hora);
		} catch (NumberFormatException e) {
			Dialogos.mostrarDialogoError("", e.getMessage());
		}
		return cita;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		iniciarTablaAlumnos();
		iniciarTablaProfesores();
		iniciarTablaTutorias();
		iniciarTablaSesiones();
		iniciarTablaCitas();
		observarTextFields();
		filtrarTablas();

	}

}
