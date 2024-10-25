package com.example.bms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class bsmController {
    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button close;

    // Database connection variables
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private double x = 0;
    private double y = 0;

    public void loginAdmin() throws SQLException {
        // Validate user input before database interaction
        if (username.getText().trim().isEmpty() || password.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error halted!");
            alert.setHeaderText(null);
            alert.setContentText("Username or password is empty!");
            alert.showAndWait();
            return; // Exit the method if inputs are invalid
        }

        // Attempt to connect to the database
        connect = Database.getConnection();

        // Check if connection was successful
        if (connect == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection Error!");
            alert.setHeaderText(null);
            alert.setContentText("Could not connect to the database.");
            alert.showAndWait();
            return; // Exit the method if connection fails
        }

        String sql = "SELECT * FROM admin WHERE username=? AND password=?";

        try (PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());
            try (ResultSet result = prepare.executeQuery()) {
                if (result.next()) {

                    getData.username = username.getText();

                    // Load the dashboard if login is successful
                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    // Mouse event handling for dragging the window
                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });
                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();
                } else {
                    // Alert for invalid credentials
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error halted!");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username or password!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while logging in.");
            alert.showAndWait();
        } finally {
            // Close the connection if necessary
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void loginBtn() {
        try {
            loginAdmin();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

