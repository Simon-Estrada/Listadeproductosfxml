package co.edu.uniquindio.fx10.controllers;

import co.edu.uniquindio.fx10.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class DashboardController {

    @FXML
    private VBox contenedorPrincipal;

    @FXML
    private Label lblTitulo;

    @FXML
    public void initialize() {
        cargarListadoProductos();
    }

    public void cargarListadoProductos() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/fx10/vista/ListadoProducto.fxml"));
            Parent listado = loader.load();

            ListadoProductoController controller = loader.getController();
            controller.setContenedorPrincipal(contenedorPrincipal);

            contenedorPrincipal.getChildren().clear();
            contenedorPrincipal.getChildren().add(listado);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public VBox getContenedorPrincipal() {
        return contenedorPrincipal;
    }
}