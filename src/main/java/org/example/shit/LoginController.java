package org.example.shit;
import com.mysql.cj.xdevapi.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class LoginController implements DatabaseConnection {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;


    public void initialize()
    {

    }

    @FXML
    public void loginButton(ActionEvent e)
    {
        String name = username.getText();
        String pass = password.getText();

        if(checkCredentials(name,pass))
        {
                if(checkArtist(name,pass).equalsIgnoreCase("Client"))
                {
                    try {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Login Successfully!");
                        alert.showAndWait();

                        try(BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Projects in Java\\Shit\\src\\main\\java\\org\\example\\shit\\LoginHistory")))
                        {
                            LocalDateTime date = LocalDateTime.now();
                            writer.write(name);
                            writer.newLine();
                            writer.write(String.valueOf(date));
                            writer.newLine();

                        }catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("client-view.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.setTitle("Welcome!");
                        stage.show();
                    }catch(Exception exception)
                    {
                        throw new RuntimeException();
                    }
                }else
                {
                    try {
                        Artist.name = name.toUpperCase();
                        Artist.typeOfArtist = checkArtist(name,pass);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Login Successfully!");
                        alert.showAndWait();

                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("artist-view.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.setTitle("Welcome!");

                        stage.show();
                    }catch(Exception exception)
                    {
                        throw new RuntimeException();
                    }
                }

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Invalid credentials!");
            alert.showAndWait();
            return;
        }

    }
    @Override
    public boolean checkCredentials(String name, String password)
    {
        String query = "SELECT * FROM users where username = ? AND pass = ? ";
        try(Connection connection = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,name);
            statement.setString(2,password);

            ResultSet result = statement.executeQuery();
            return result.next();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String checkArtist(String name, String password)
    {
        String query = "SELECT * FROM users where username = ? AND pass = ? ";
        try(Connection connection = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,name);
            statement.setString(2,password);

            ResultSet result = statement.executeQuery();
            if (result.next())
                return result.getString("type_of_artist");
              else
                return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void gobackButton(ActionEvent e)
    {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Welcome!");
            stage.show();
        }catch(Exception exception)
        {
            throw new RuntimeException();
        }
    }





}
