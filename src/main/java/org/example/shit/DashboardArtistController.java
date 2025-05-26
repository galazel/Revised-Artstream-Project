package org.example.shit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DashboardArtistController implements DatabaseConnection{

    @FXML
    private Label artistName;
    @FXML
    private Label typeOfArtist;
    @FXML
    private Label countClients;
    @FXML
    private Label commissionLabel;
    @FXML
    private Button addArtwork;
    @FXML
    private FlowPane artworks;
    @FXML
    private AnchorPane anchorPane;


    @FXML
    public void initialize()
    {
        artistName.setText(Artist.name);
        typeOfArtist.setText(Artist.typeOfArtist);
        String query = "SELECT * FROM request_commissions WHERE req_Artist = ?";
        try(Connection connection = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,artistName.toString());
            countClients.setText(Integer.toString(preparedStatement.executeUpdate()));

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        if(artworks.getChildren().isEmpty())
            artworks.getChildren().add(new Label("No uploads yet"));
        initializePending();
        initializeAccepted();
        initializeDone();

    }
    protected void initializePending()
    {

    }
    protected void initializeAccepted()
    {

    }
    protected void initializeDone()
    {

    }

    @FXML
    protected void setMenuItemPending(ActionEvent event)
    {
        commissionLabel.setText("Pending Commissions");
        artworks.getChildren().clear();
    }
    @FXML
    protected void setMenuItemAccepted(ActionEvent event)
    {
        commissionLabel.setText("Accepted Commissions");
        artworks.getChildren().clear();
    }@FXML
    protected void setMenuItemDone(ActionEvent event)
    {
        commissionLabel.setText("Done Commissions");
        artworks.getChildren().clear();
    }

    @FXML
    protected void setAddArtwork(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("add-artwork-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Upload");
            stage.show();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    protected void setLogoutBtn(ActionEvent event)
    {
        Logout.logoutButton(event);
    }


    public boolean checkCredentials(String name, String email){
        return false;
    }




}
