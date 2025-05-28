package org.example.shit;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

public abstract class CommonController implements DatabaseConnection {
    @FXML
    protected TitledPane requestTo;
    @FXML
    protected TextField clientName;
    @FXML
    protected TextField email;
    @FXML
    protected TextArea description;

    @FXML
    protected ChoiceBox<String> materialsBox;
    @FXML
    protected DatePicker date;

    @FXML
    public abstract void initialize();

    @FXML
    protected void sendRequest(ActionEvent e)
    {
        String reqArtist = Artist.name;
        String name = clientName.getText();
        String emailText = email.getText();
        String desc = description.getText();
        String material = materialsBox.getValue();
        LocalDate localDate = date.getValue();

        if(reqArtist.isEmpty() || name.isEmpty() || emailText.isEmpty() || desc.isEmpty() || material == null || localDate == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all of the fields!");
            alert.showAndWait();
            return;
        }
        else
        {

            String query1 = "SELECT * FROM request_commissions WHERE client_name = ?";
            try(Connection connection = DriverManager.getConnection(CONNECTION_STRING, "root","galagar"))
            {
                PreparedStatement preparedStatement = connection.prepareStatement(query1);
                preparedStatement.setString(1,name);
                ResultSet compare = preparedStatement.executeQuery();
                if(compare.next())
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("You already requested this person.");
                    alert.showAndWait();
                    clientName.clear();
                    email.clear();
                    description.clear();
                    materialsBox.setValue(null);
                    date.setValue(null);
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.close();
                    return;
                }
            }catch (Exception exception)
            {
                exception.printStackTrace();
            }

            Date date1 = Date.valueOf(localDate);
            String query = "INSERT INTO request_commissions(req_Artist,client_name,email,description_art,material,completion_date)" +
                    "VALUES(?,?,?,?,?,?)";
            try(Connection connection = DriverManager.getConnection(CONNECTION_STRING, "root","galagar"))
            {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,reqArtist);
                preparedStatement.setString(2,name);
                preparedStatement.setString(3,emailText);
                preparedStatement.setString(4,desc);
                preparedStatement.setString(5,material);
                preparedStatement.setString(6, String.valueOf(date1));

                int row = preparedStatement.executeUpdate();
                if(row > 0)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Request Sent to "+reqArtist);
                    alert.showAndWait();
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.close();
                }
            }catch (Exception exception)
            {
                exception.printStackTrace();
            }

        }
    }
    public boolean checkCredentials(String name, String email)
    {
        return false;
    }



}
