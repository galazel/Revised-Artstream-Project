package org.example.shit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class RegisterController implements DatabaseConnection {

    @FXML
    private TextField name;
    @FXML
    private TextField username;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField email;
    @FXML
    private ComboBox<String> comboBoxSelect;

    @FXML
    public void initialize() {
        comboBoxSelect.getItems().addAll(
                "Digital Artist",
                "Charcoal Artist",
                "Portrait Artist",
                "Landscape Artist",
                "Client"
        );
    }
    @FXML
    protected void registerButton(ActionEvent e)
    {
        String n = name.getText();
        String user = username.getText();
        String password = passwordField.getText();
        String emailText = email.getText();
        String type = comboBoxSelect.getValue();
//
//
//        User user = new User.Builder()
//                .setName(name.getText())
//                .setUsername(username.getText())
//                .setPass(passwordField.getText())
//                .setEmail(email.getText())
//                .setArtistType(comboBoxSelect.getValue())
//                .build();
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction tx = session.beginTransaction();
//
//        session.persist(user);
//
//        tx.commit();
//        session.close();
//
//        System.out.println("User saved successfully!");

        if(n.isEmpty() || user.isEmpty() || password.isEmpty() || emailText.isEmpty() || type == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all of the fields!");
            alert.showAndWait();
            return;
        }else if(checkCredentials(n,emailText))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Account already existed");
            alert.showAndWait();
            return;
        }
        else
        {
            String query = "INSERT INTO users(fullname,username,pass,email,type_of_artist) VALUES(?,?,?,?,?)";
            try (Connection connect = DriverManager.getConnection(CONNECTION_STRING, "root", "galagar")) {
                PreparedStatement statement = connect.prepareStatement(query);
                statement.setString(1, n);
                statement.setString(2, user);
                statement.setString(3, password);
                statement.setString(4, emailText);
                statement.setString(5, type);

                int exe = statement.executeUpdate();
                if (exe > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully created the account");
                    alert.showAndWait();
                    try {
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.setTitle("Login");
                        stage.show();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }





    }
    public boolean checkCredentials(String name, String email)
    {
        String query = "SELECT * FROM users where fullname = ? AND email = ? ";
        try(Connection connection = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,name);
            statement.setString(2,email);

            ResultSet result = statement.executeQuery();
            return result.next();

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
            exception.printStackTrace();
        }
    }



}
