package co.edu.uniquindio.fx10.controllers;

import co.edu.uniquindio.fx10.App;
import co.edu.uniquindio.fx10.models.Producto;
import co.edu.uniquindio.fx10.repositories.ProductoRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

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
    private ListadoProductoController listadoProductoController;
    private VBox contenedorPrincipal;

    @FXML
    public void initialize() {
        productoRepository = ProductoRepository.getInstancia();
    }

    public void setListadoProductoController(ListadoProductoController listadoProductoController) {
        this.listadoProductoController = listadoProductoController;
    }

    public void setContenedorPrincipal(VBox contenedorPrincipal) {
        this.contenedorPrincipal = contenedorPrincipal;
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
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            Producto nuevoProducto = new Producto(codigo, nombre, descripcion, precio, stock);
            productoRepository.agregarProducto(nuevoProducto);

            mostrarAlerta("Éxito", "Producto creado correctamente", Alert.AlertType.INFORMATION);

            if (listadoProductoController != null) {
                listadoProductoController.cargarProductos();
            }

            volverAListadoProductos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "El precio y/o stock no tienen un formato válido.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onCancelar() {
        volverAListadoProductos();
    }

    private void volverAListadoProductos() {
        if (contenedorPrincipal == null) {
            mostrarAlerta("Error de Navegación", "Contenedor principal no inyectado.", Alert.AlertType.ERROR);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/fx10/vista/ListadoProducto.fxml"));
            Parent listado = loader.load();

            ListadoProductoController controller = loader.getController();
            controller.setContenedorPrincipal(contenedorPrincipal);

            contenedorPrincipal.getChildren().clear();
            contenedorPrincipal.getChildren().add(listado);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo volver a la lista de productos.", Alert.AlertType.ERROR);
            e.printStackTrace();
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