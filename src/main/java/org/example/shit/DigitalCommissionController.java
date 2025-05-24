package org.example.shit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class DigitalCommissionController implements DatabaseConnection {

    @FXML
    private TitledPane requestTo;
    @FXML
    private TextField clientName;
    @FXML
    private TextField email;

    @FXML
    private TextArea description;

    @FXML
    private ChoiceBox <String> materialsBox;
    @FXML
    private DatePicker date;


    @FXML
    public void initialize() {
        materialsBox.getItems().addAll(
                "Anime/Manga",
                "Cartoon",
                "Chibi",
                "Pixel Art",
                "Abstract"
        );
        requestTo.setText("");
    }
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
        }else
        {
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
