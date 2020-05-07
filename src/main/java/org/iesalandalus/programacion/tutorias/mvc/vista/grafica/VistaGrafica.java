package org.iesalandalus.programacion.tutorias.mvc.vista.grafica;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.vista.IVista;
import org.iesalandalus.programacion.tutorias.mvc.vista.grafica.controladoriugrafica.ControladorPrincipal;
import org.iesalandalus.programacion.utilidades.Dialogos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class VistaGrafica extends Application implements IVista {


	double x, y;
	private static IControlador controladorMVC = null;




	private void addDragListeners(final Node n, Stage primaryStage){

		n.setOnMousePressed((MouseEvent mouseEvent) -> {
			this.x = n.getScene().getWindow().getX() - mouseEvent.getScreenX();
			this.y = n.getScene().getWindow().getY() - mouseEvent.getScreenY();
		});

		n.setOnMouseDragged((MouseEvent mouseEvent) -> {
			primaryStage.setX(mouseEvent.getScreenX() + this.x);
			primaryStage.setY(mouseEvent.getScreenY() + this.y);
		});
	}



	@Override
	public void start(Stage escenarioPrincipal) {
		try {

			FXMLLoader cargadorPrincipal = new FXMLLoader(getClass().getResource("../vistas/VentanaPrincipal.fxml"));
			BorderPane raiz = cargadorPrincipal.load();	


			ControladorPrincipal cPrincipal = cargadorPrincipal.getController();
			cPrincipal.setControlador(controladorMVC);
			cPrincipal.actualizaTablas();
			Scene escena = new Scene(raiz);
			escenarioPrincipal.setScene(escena);
			escenarioPrincipal.setOnCloseRequest(e -> confirmarSalida(escenarioPrincipal, e));
			escenarioPrincipal.initStyle(StageStyle.UNDECORATED);
			addDragListeners(raiz,escenarioPrincipal);
			escenarioPrincipal.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir?", null)) {
			controladorMVC.terminar();
			escenarioPrincipal.close();


		} else {
			e.consume();
		}

	}
	@Override
	public void setControlador(IControlador controlador) {
		controladorMVC = controlador;

	}


	@Override
	public void comenzar() {
		launch(this.getClass());

	}

	@Override
	public void terminar() {

		controladorMVC.terminar();
	}



}
