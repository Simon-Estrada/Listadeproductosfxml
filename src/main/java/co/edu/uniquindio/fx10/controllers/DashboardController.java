package co.edu.uniquindio.fx10.controllers;

import co.edu.uniquindio.fx10.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class DashboardController {

    @FXML
    private VBox contenedorPrincipal;

    @FXML
    private Label lblTitulo;

    @FXML
    private VBox contenedorDinamico;

    @FXML
    public void initialize() {
        if(contenedorPrincipal != null){
            cargarListadoProductos();
        }
    }
    @FXML
    private void onShowList(){
        cargarListadoProductos();
    }
    @FXML
    private void onCrearProducto() {
        if (contenedorDinamico == null) {
            mostrarAlerta("Error", "Contenedor principal no configurado.", Alert.AlertType.ERROR);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/fx10/vista/FormularioProducto.fxml"));
            Parent formulario = loader.load();

            FormularioProductoController controller = loader.getController();
            controller.setDashboardController(this);

            contenedorDinamico.getChildren().clear();
            contenedorDinamico.getChildren().add(formulario);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar el formulario", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void cargarListadoProductos() {

        if( contenedorDinamico == null){
            System.err.println("Error: contenedorDinamico es null");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/fx10/vista/ListadoProducto.fxml"));
            Parent listado = loader.load();


            contenedorDinamico.getChildren().clear();
            contenedorDinamico.getChildren().add(listado);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}