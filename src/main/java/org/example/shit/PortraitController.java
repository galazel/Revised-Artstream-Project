package org.example.shit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class PortraitController extends CommonDashboard{

    @FXML
    public void initialize() {
        String query = "SELECT * FROM users WHERE type_of_artist = 'Portrait Artist'";
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, "root", "galagar")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ImageView image = new ImageView(new Image("D:\\Projects in Java\\Shit\\src\\main\\resources\\org\\example\\shit\\Images\\profile.jpg"));
                image.setPreserveRatio(true);
                image.setFitWidth(100); // Set desired width
                Label name = new Label(resultSet.getString("fullname"));
                name.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                Button requestButton = new Button("Request Commission");
                requestButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                requestButton.setOnAction(event ->
                {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("portrait-commission-view.fxml"));
                        Parent root = fxmlLoader.load();
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("Request Commission");
                        Artist.name = name.getText();
                        stage.showAndWait();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                VBox box = new VBox(10);
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
                box.getChildren().addAll(image, name, requestButton);

                flowPane.getChildren().add(box);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
