package co.edu.uniquindio.fx10.controllers;

import co.edu.uniquindio.fx10.App;
import co.edu.uniquindio.fx10.models.Moto;
import co.edu.uniquindio.fx10.repositories.MotoRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class FormularioMotoController {

    @FXML
    private TextField txtPlaca;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtModelo;

    private MotoRepository motoRepository;
    private Stage mainStage;

    @FXML
    public void initialize() {
        motoRepository = MotoRepository.getInstancia();
    }

    public void setMainStage(Stage stage) {
        this.mainStage = stage;
    }

    @FXML
    private void onGuardarProducto() {
        if (!validarCampos()) {
            return;
        }

        try {
            String placa = txtPlaca.getText();
            String marca = txtMarca.getText();
            String modelo = txtModelo.getText();

            Moto nuevoMoto = new Moto(placa, marca, modelo);
            motoRepository.agregarMoto(nuevoMoto);

            mostrarAlerta("Éxito", "Moto registrada correctamente", Alert.AlertType.INFORMATION);

            volverAlDashboard();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "El precio y/o stock deben ser números válidos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onCancelar() {
        volverAlDashboard();
    }

    private void volverAlDashboard() {
        if (mainStage == null) {
            mostrarAlerta("Error", "No se puede volver al Dashboard.", Alert.AlertType.ERROR);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/fx10/vista/Dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setMainStage(mainStage);

            Scene scene = new Scene(root, 900, 600);
            mainStage.setScene(scene);
            mainStage.setTitle("Sistema de registro de motos");

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar el Dashboard.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        if (txtPlaca.getText().trim().isEmpty() ||
                txtMarca.getText().trim().isEmpty() ||
                txtModelo.getText().trim().isEmpty())
        {
            mostrarAlerta("Error de validación", "Todos los campos son obligatorios.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}