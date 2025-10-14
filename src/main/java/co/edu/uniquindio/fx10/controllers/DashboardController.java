package co.edu.uniquindio.fx10.controllers;

import co.edu.uniquindio.fx10.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController {

    private Stage mainStage;

    public void setMainStage(Stage stage) {
        this.mainStage = stage;
    }

    @FXML
    private void onShowlist(){
        cargarEscena("/co/edu/uniquindio/fx10/vista/ListadoMoto.fxml", "Listado de Motos");
    }

    @FXML
    private void onCrearProducto() {
        cargarEscena("/co/edu/uniquindio/fx10/vista/FormularioMoto.fxml", "Nueva Moto");
    }

    private void cargarEscena(String fxmlPath, String titulo) {
        if (mainStage == null) {
            mostrarAlerta("Error", "La ventana principal no está configurada.", Alert.AlertType.ERROR);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();

            if (controller instanceof FormularioMotoController) {
                ((FormularioMotoController) controller).setMainStage(mainStage);
            } else if (controller instanceof ListadoMotoController) {
                ((ListadoMotoController) controller).setMainStage(mainStage);
            }

            Scene scene = new Scene(root, 900, 600);
            mainStage.setScene(scene);
            mainStage.setTitle(titulo);

        } catch (IOException e) {
            mostrarAlerta("Error de Carga", "No se pudo cargar la escena: " + fxmlPath, Alert.AlertType.ERROR);
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