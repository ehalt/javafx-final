//module com.example.bms {
//    requires java.desktop;
//}

module com.example.bms {
    requires java.desktop;        // For AWT
    requires javafx.fxml;         // For JavaFX FXML
    requires javafx.controls;
    requires java.sql;     // For JavaFX controls

    exports com.example.bms;      // Add this line to export your package
    opens com.example.bms to javafx.fxml;
}