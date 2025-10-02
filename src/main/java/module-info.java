module co.edu.uniquindio.fx10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens co.edu.uniquindio.fx10 to javafx.fxml;
    opens co.edu.uniquindio.fx10.controllers to javafx.fxml;
    opens co.edu.uniquindio.fx10.models to javafx.base;
    
    exports co.edu.uniquindio.fx10;
    exports co.edu.uniquindio.fx10.controllers;
    exports co.edu.uniquindio.fx10.models;
}

