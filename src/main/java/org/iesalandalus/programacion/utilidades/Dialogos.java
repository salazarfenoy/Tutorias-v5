package org.iesalandalus.programacion.utilidades;

import java.util.Optional;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Dialogos {

	private static final String CSS = "/org/iesalandalus/programacion/tutorias/mvc/vista/estilos/estilo1.css";
	private static final String ID_BT_ACEPTAR = "btAceptar";
	private static final String ID_BT_CANCELAR = "btCancelar";
	private static final String ID_BT_BORRAR = "btBorrar";

	private Dialogos() {
		// Evitamos que se puedan instanciar objetos
	}

	public static void mostrarDialogoError(String titulo, String contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.ERROR);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.getDialogPane().getStyleClass().add("salir");
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		dialogo.initStyle(StageStyle.UNDECORATED);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}
		dialogo.showAndWait();
		if (propietario != null)
			propietario.close();
	}

	public static void mostrarDialogoError(String titulo, String contenido) {
		Dialogos.mostrarDialogoError(titulo, contenido, null);
	}

	public static void mostrarDialogoInformacion(String titulo, String contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.INFORMATION);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.getDialogPane().getStyleClass().add("salir");
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		dialogo.initStyle(StageStyle.UNDECORATED);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}
		dialogo.showAndWait();
		if (propietario != null)
			propietario.close();
	}

	public static void mostrarDialogoInformacion(String titulo, String contenido) {
		Dialogos.mostrarDialogoInformacion(titulo, contenido, null);
	}

	public static void mostrarDialogoInformacionPersonalizado(String titulo, Pane contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.INFORMATION);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.getDialogPane().getStyleClass().add("salir");
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(null);
		dialogo.getDialogPane().setHeader(contenido);
		dialogo.initStyle(StageStyle.UNDECORATED);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}
		dialogo.showAndWait();
		if (propietario != null)
			propietario.close();
	}

	public static void mostrarDialogoInformacionCita(String titulo, Pane contenido) {
		Alert dialogo = new Alert(AlertType.INFORMATION);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.getDialogPane().getStyleClass().add("panelVerCita");
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(null);
		dialogo.getDialogPane().setHeader(contenido);
		dialogo.initStyle(StageStyle.UNDECORATED);

		dialogo.showAndWait();

	}

	public static void mostrarDialogoInformacionPersonalizado(String titulo, Pane contenido) {
		Dialogos.mostrarDialogoInformacionPersonalizado(titulo, contenido, null);
	}

	public static void mostrarDialogoAdvertencia(String titulo, String contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.WARNING);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.getDialogPane().getStyleClass().add("salir");
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		dialogo.initStyle(StageStyle.UNDECORATED);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}
		dialogo.showAndWait();
		if (propietario != null)
			propietario.close();
	}

	public static void mostrarDialogoAdvertencia(String titulo, String contenido) {
		Dialogos.mostrarDialogoAdvertencia(titulo, contenido, null);
	}

	public static String mostrarDialogoTexto(String titulo, String contenido) {
		TextInputDialog dialogo = new TextInputDialog();
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());

		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.CANCEL)).setId(ID_BT_CANCELAR);
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);

		Optional<String> respuesta = dialogo.showAndWait();
		return (respuesta.isPresent() ? respuesta.get() : null);
	}

	public static boolean mostrarDialogoConfirmacion(String titulo, String contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.CONFIRMATION);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.CANCEL)).setId(ID_BT_CANCELAR);
		dialogo.getDialogPane().getStyleClass().add("salir");
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		dialogo.initStyle(StageStyle.TRANSPARENT);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}

		Optional<ButtonType> respuesta = dialogo.showAndWait();
		return (respuesta.isPresent() && respuesta.get() == ButtonType.OK);
	}

	public static boolean mostrarDialogoConfirmacion(String titulo, Pane contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.CONFIRMATION);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.CANCEL)).setId(ID_BT_CANCELAR);
		dialogo.getDialogPane().getStyleClass().add("salir");
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(null);
		dialogo.getDialogPane().setHeader(contenido);
		dialogo.initStyle(StageStyle.UNDECORATED);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}

		Optional<ButtonType> respuesta = dialogo.showAndWait();
		return (respuesta.isPresent() && respuesta.get() == ButtonType.OK);
	}

	public static boolean mostrarDialogoBuscado(String titulo, Pane contenido) {
		Alert dialogo = new Alert(AlertType.CONFIRMATION);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.CANCEL)).setId(ID_BT_BORRAR);
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Borrar");
		dialogo.getDialogPane().getStyleClass().add("salir");
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(null);
		dialogo.getDialogPane().setHeader(contenido);
		dialogo.initStyle(StageStyle.UNDECORATED);

		Optional<ButtonType> respuesta = dialogo.showAndWait();
		return (respuesta.isPresent() && respuesta.get() == ButtonType.OK);
	}
}
