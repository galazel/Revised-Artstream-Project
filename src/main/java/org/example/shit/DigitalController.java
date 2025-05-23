package org.example.shit;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DigitalController implements DatabaseConnection {

    @FXML
    private FlowPane flowPane;

    @FXML
    private TextField textField;

    @FXML
    protected void searchArtist(KeyEvent e) {
        // To be implemented
    }

    @FXML
    public void initialize() {
        String query = "SELECT * FROM users WHERE type_of_artist = 'Digital Artist'";
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, "root", "galagar")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Load image
                ImageView image = new ImageView(new Image("file:D:/Projects in Java/Shit/src/main/resources/Images/profile.jpg"));
                image.setPreserveRatio(true);
                image.setFitWidth(100); // Set desired width

                // Name label
                Label name = new Label(resultSet.getString("fullname"));
                name.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                // Button
                Button requestButton = new Button("Request Commission");
                requestButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

                // VBox setup
                VBox box = new VBox(10); // spacing between children
                box.setAlignment(Pos.CENTER);
                box.setPrefWidth(200);
                box.setPrefHeight(250);
                box.setStyle("""
                    -fx-background-color: #ffffff;
                    -fx-border-color: #cccccc;
                    -fx-border-width: 1;
                    -fx-border-radius: 10;
                    -fx-background-radius: 10;
                    -fx-padding: 15;
                    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0.5, 0, 4);
                """);

                // Add components to VBox
                box.getChildren().addAll(image, name, requestButton);

                // Add VBox to FlowPane
                flowPane.getChildren().add(box);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkCredentials(String name, String email) {
        return false;
    }
}
