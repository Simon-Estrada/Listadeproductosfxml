package co.edu.uniquindio.fx10.controllers;

import co.edu.uniquindio.fx10.App;
import co.edu.uniquindio.fx10.models.Moto;
import co.edu.uniquindio.fx10.repositories.MotoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;

public class ListadoMotoController {

    @FXML
    private TableView<Moto> tablaMotos;

    @FXML
    private TableColumn<Moto, String> colPlaca;

    @FXML
    private TableColumn<Moto, String> colMarca;

    @FXML
    private TableColumn<Moto, String> colModelo;

    private MotoRepository motoRepository;
    private ObservableList<Moto> listaMotos;
    private Stage mainStage;

    @FXML
    public void initialize() {
        motoRepository = MotoRepository.getInstancia();

        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));



        cargarMotos();
    }

    public void setMainStage(Stage stage) {
        this.mainStage = stage;
    }

    public void cargarMotos() {
        listaMotos = FXCollections.observableArrayList(motoRepository.getProductos());
        tablaMotos.setItems(listaMotos);
    }

    @FXML
    private void onCrearProducto() {
        cargarEscena("/co/edu/uniquindio/fx10/vista/FormularioMoto.fxml", "Registrar Moto");
    }

    @FXML
    private void onVolverDashboard() {
        cargarEscena("/co/edu/uniquindio/fx10/vista/Dashboard.fxml", "Sistema de Gestión de Motos");
    }

    @FXML
    private void onEliminarProducto() {
        Moto motoSeleccionado = tablaMotos.getSelectionModel().getSelectedItem();

        if (motoSeleccionado == null) {
            mostrarAlerta("Advertencia", "Por favor seleccione una moto para eliminar", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar la Moto?");
        confirmacion.setContentText("Moto: " + motoSeleccionado.getPlaca());

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                motoRepository.eliminarMoto(motoSeleccionado);
                cargarMotos();
                mostrarAlerta("Éxito", "Moto eliminada correctamente", Alert.AlertType.INFORMATION);
            }
        });
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
            } else if (controller instanceof DashboardController) {
                ((DashboardController) controller).setMainStage(mainStage);
            }

            Scene scene = new Scene(root, 900, 600);
            mainStage.setScene(scene);
            mainStage.setTitle(titulo);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la escena", Alert.AlertType.ERROR);
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