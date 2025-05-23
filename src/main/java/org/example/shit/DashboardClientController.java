package org.example.shit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardClientController {

    @FXML
    private AnchorPane mainAnchor;
    @FXML
    protected void viewDigitalArtists(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("digital-view.fxml"));
            Parent pane = loader.load(); 
            mainAnchor.getChildren().clear();
            mainAnchor.getChildren().add(pane);

        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Failed to load login-view.fxml", exception);
        }
    }

    @FXML
    protected void viewPortraitArtists(ActionEvent e)
    {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("portrait-view.fxml"));
            Parent pane = loader.load();
            mainAnchor.getChildren().clear();
            mainAnchor.getChildren().add(pane);

        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Failed to load login-view.fxml", exception);
        }
    }
    @FXML
    protected void viewLandscapeArtists(ActionEvent e)
    {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("landscape-view.fxml"));
            Parent pane = loader.load();
            mainAnchor.getChildren().clear();
            mainAnchor.getChildren().add(pane);

        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Failed to load login-view.fxml", exception);
        }
    }
    @FXML
    protected void viewCharcoalArtists(ActionEvent e)
    {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("charcoal-view.fxml"));
            Parent pane = loader.load();
            mainAnchor.getChildren().clear();
            mainAnchor.getChildren().add(pane);

        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Failed to load login-view.fxml", exception);
        }
    }
}
