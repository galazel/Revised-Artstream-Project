package org.example.shit;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class CommonDashboard implements DatabaseConnection{

    @FXML
    protected FlowPane flowPane;
    @FXML
    protected AnchorPane mainAnchor;

    @FXML
    protected TextField textField;

    @FXML
    protected void searchArtist(KeyEvent e) {

    }
    @FXML
    public abstract void initialize();

    @Override
    public boolean checkCredentials(String name, String email) {
        return false;
    }
    @FXML
    public void returnToDashboardClient(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("client-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Dashboard Client");
            stage.show();
        }catch(Exception exception)
        {
            throw new RuntimeException();
        }
    }

    @FXML
    public void logout(ActionEvent e)
    {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) mainAnchor.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        }catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }


}
