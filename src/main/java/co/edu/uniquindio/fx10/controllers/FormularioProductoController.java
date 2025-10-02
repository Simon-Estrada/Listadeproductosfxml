package co.edu.uniquindio.fx10.controllers;

import co.edu.uniquindio.fx10.models.Producto;
import co.edu.uniquindio.fx10.repositories.ProductoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;


public class FormularioProductoController {

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    private ProductoRepository productoRepository;
    private DashboardController dashboardController;

    @FXML
    public void initialize() {
        productoRepository = ProductoRepository.getInstancia();
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    private void onGuardarProducto() {
        if (!validarCampos()) {
            return;
        }

        try {
            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            double precio = Double.parseDouble(txtPrecio.getText().trim().replace(",", "."));
            int stock = Integer.parseInt(txtStock.getText().trim());

            Producto nuevoProducto = new Producto(codigo, nombre, descripcion, precio, stock);
            productoRepository.agregarProducto(nuevoProducto);

            mostrarAlerta("Éxito", "Producto creado correctamente", Alert.AlertType.INFORMATION);

            if (dashboardController != null) {
                dashboardController.cargarListadoProductos();
            } else {
                mostrarAlerta("Error de Navegación", "Dashboard Controller no inyectado. No se puede volver al listado.", Alert.AlertType.ERROR);
            }


        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "El precio y/o stock deben ser números válidos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onCancelar() {
        if (dashboardController != null) {
            dashboardController.cargarListadoProductos();
        } else {
            mostrarAlerta("Error", "No se puede volver al listado.", Alert.AlertType.ERROR);
        }
    }


    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty() ||
                txtNombre.getText().trim().isEmpty() ||
                txtDescripcion.getText().trim().isEmpty() ||
                txtPrecio.getText().trim().isEmpty() ||
                txtStock.getText().trim().isEmpty())
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