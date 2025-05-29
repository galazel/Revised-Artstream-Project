package org.example.shit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

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
    protected FlowPane artworks;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize()
    {
        artistName.setText(Artist.name);
        typeOfArtist.setText(Artist.typeOfArtist);

        int count = 0;
        try(Connection connection = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
        {
            String query1 = "SELECT * FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1,artistName.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            String name = null;
            if(resultSet.next())
             name = resultSet.getString("fullname");

            System.out.println(name);

            if(name != null) {
                String query2 = "SELECT COUNT(req_Artist) AS total FROM request_commissions WHERE req_Artist = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                preparedStatement2.setString(1, name);
                ResultSet resultSet1 = preparedStatement2.executeQuery();

                if(resultSet1.next())
                    count = resultSet1.getInt("total");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        countClients.setText(Integer.toString(count));
        if(artworks.getChildren().isEmpty())
            artworks.getChildren().add(new Label("No uploads yet"));

        initializePending();
    }
    protected void initializePending()
    {
        commissionLabel.setText("Pending Commissions");
        artworks.getChildren().clear();

        try(Connection connection = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
        {
            String name = setName();
            if(name != null) {
                String query1 = "SELECT * FROM request_commissions WHERE req_Artist = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                preparedStatement1.setString(1, name);
                ResultSet resultSet1 = preparedStatement1.executeQuery();

                while(resultSet1.next())
                {
                    Label client_name = new Label (resultSet1.getString("client_name"));
                    client_name.getStyleClass().add("label");
                    Label client_email = new Label (resultSet1.getString("email"));
                    client_email.getStyleClass().add("label");
                    Label art_description = new Label (resultSet1.getString("description_art"));
                    art_description.getStyleClass().add("label");
                    Label artMaterial = new Label (resultSet1.getString("material"));
                    artMaterial.getStyleClass().add("label");
                    Label completion_date = new Label (resultSet1.getString("completion_date"));
                    completion_date.getStyleClass().add("label");
                    Button accept = new Button("Accept");
                    accept.getStyleClass().add("button");
                    Button delete = new Button("Delete");
                    delete.getStyleClass().add("button");
                    VBox box = getVbox();
                    box.getStyleClass().add("commission-box");
                    box.getChildren().addAll(client_name,client_email,art_description,artMaterial,completion_date,accept,delete);
                    artworks.getChildren().add(box);
                    accept.setOnAction(e->
                    {
                        try(Connection connection1 = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
                        {

                            String query2 = "INSERT INTO accept_commissions(req_Artist,client_name, client_email, art_description, material, completion_date)" +
                                    "VALUES(?,?,?,?,?,?)";
                            PreparedStatement preparedStatement2 = connection1.prepareStatement(query2);
                            preparedStatement2.setString(1, name);
                            preparedStatement2.setString(2, client_name.getText());
                            preparedStatement2.setString(3, client_email.getText());
                            preparedStatement2.setString(4, art_description.getText());
                            preparedStatement2.setString(5, artMaterial.getText());
                            preparedStatement2.setString(6, completion_date.getText());

                            int num = preparedStatement2.executeUpdate();
                            if(num > 0) {
                                String query3 = "DELETE FROM request_commissions WHERE req_Artist = ? AND client_name = ?";
                                PreparedStatement preparedStatement3 = connection1.prepareStatement(query3);
                                preparedStatement3.setString(1, name);
                                preparedStatement3.setString(2, client_name.getText());
                                int row1 = preparedStatement3.executeUpdate();
                                if (row1 > 0)
                                    artworks.getChildren().remove(box);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    delete.setOnAction(e->
                    {
                        try(Connection connection1 = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
                        {
                            String query2 = "DELETE FROM request_commissions WHERE req_Artist = ? AND client_name = ?";
                            PreparedStatement preparedStatement3 = connection1.prepareStatement(query2);
                            preparedStatement3.setString(1, name);
                            preparedStatement3.setString(2, client_name.getText());
                            int row = preparedStatement3.executeUpdate();
                            if(row > 0)
                                artworks.getChildren().remove(box);

                            try(BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Projects in Java\\Shit\\src\\main\\java\\org\\example\\shit\\CommissionHistory")))
                            {
                                LocalDateTime dateTime = LocalDateTime.now();
                                writer.write("Deleted on: "+dateTime.toString());
                                writer.write(client_name.getText()+"\n");
                                writer.write(client_email.getText()+"\n");
                                writer.write(art_description.getText()+"\n");
                                writer.write(artMaterial.getText()+"\n");
                                writer.write(completion_date.getText()+"\n");
                                writer.flush();
                                writer.close();

                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });


                }

            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    protected void initializeAccepted()
    {

    }
    protected void initializeDone()
    {

    }

    protected String setName()
    {
        try(Connection connection = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
        {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,artistName.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            String name = null;
            if(resultSet.next())
                return name = resultSet.getString("fullname");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    @FXML
    protected void setMenuItemPending(ActionEvent event)
    {
        commissionLabel.setText("Pending Commissions");
        artworks.getChildren().clear();

        try(Connection connection = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
        {
            String name = setName();
            if(name != null) {
                String query1 = "SELECT * FROM request_commissions WHERE req_Artist = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                preparedStatement1.setString(1, name);
                ResultSet resultSet1 = preparedStatement1.executeQuery();

                while(resultSet1.next())
                {
                    Label client_name = new Label (resultSet1.getString("client_name"));
                    Label client_email = new Label (resultSet1.getString("email"));
                    Label art_description = new Label (resultSet1.getString("description_art"));
                    Label artMaterial = new Label (resultSet1.getString("material"));
                    Label completion_date = new Label (resultSet1.getString("completion_date"));
                    Button accept = new Button("Accept");
                    Button delete = new Button("Delete");
                    VBox box = getVbox();
                    box.getChildren().addAll(client_name,client_email,art_description,artMaterial,completion_date,accept,delete);
                    artworks.getChildren().add(box);
                    accept.setOnAction(e->
                    {
                        try(Connection connection1 = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
                        {

                           String query2 = "INSERT INTO accept_commissions(req_Artist,client_name, client_email, art_description, material, completion_date)" +
                                   "VALUES(?,?,?,?,?,?)";
                            PreparedStatement preparedStatement2 = connection1.prepareStatement(query2);
                            preparedStatement2.setString(1, name);
                            preparedStatement2.setString(2, client_name.getText());
                            preparedStatement2.setString(3, client_email.getText());
                            preparedStatement2.setString(4, art_description.getText());
                            preparedStatement2.setString(5, artMaterial.getText());
                            preparedStatement2.setString(6, completion_date.getText());

                           int num = preparedStatement2.executeUpdate();
                            if(num > 0) {
                                String query3 = "DELETE FROM request_commissions WHERE req_Artist = ? AND client_name = ?";
                                PreparedStatement preparedStatement3 = connection1.prepareStatement(query3);
                                preparedStatement3.setString(1, name);
                                preparedStatement3.setString(2, client_name.getText());
                                int row1 = preparedStatement3.executeUpdate();
                                if (row1 > 0)
                                    artworks.getChildren().remove(box);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    delete.setOnAction(e->
                    {
                        try(Connection connection1 = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
                        {
                            String query2 = "DELETE FROM request_commissions WHERE req_Artist = ? AND client_name = ?";
                            PreparedStatement preparedStatement3 = connection1.prepareStatement(query2);
                            preparedStatement3.setString(1, name);
                            preparedStatement3.setString(2, client_name.getText());
                            int row = preparedStatement3.executeUpdate();
                            if(row > 0)
                                artworks.getChildren().remove(box);

                            try(BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Projects in Java\\Shit\\src\\main\\java\\org\\example\\shit\\CommissionHistory")))
                            {
                                LocalDateTime dateTime = LocalDateTime.now();
                                writer.write("Deleted on: "+dateTime.toString());
                                writer.write(client_name.getText()+"\n");
                                writer.write(client_email.getText()+"\n");
                                writer.write(art_description.getText()+"\n");
                                writer.write(artMaterial.getText()+"\n");
                                writer.write(completion_date.getText()+"\n");
                                writer.flush();
                                writer.close();

                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });


                }

            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    protected void setMenuItemAccepted(ActionEvent event)
    {
        commissionLabel.setText("Accepted Commissions");
        artworks.getChildren().clear();

        try(Connection connection = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
        {
            String name = setName();
            if(name != null) {
                String query1 = "SELECT * FROM accept_commissions WHERE req_Artist = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                preparedStatement1.setString(1, name);
                ResultSet resultSet1 = preparedStatement1.executeQuery();

                while(resultSet1.next())
                {
                    Label client_name = new Label (resultSet1.getString("client_name"));
                    Label client_email = new Label (resultSet1.getString("client_email"));
                    Label art_description = new Label (resultSet1.getString("art_description"));
                    Label artMaterial = new Label (resultSet1.getString("material"));
                    Label completion_date = new Label (resultSet1.getString("completion_date"));
                    Button done = new Button("Done");
                    VBox box = getVbox();
                    box.getChildren().addAll(client_name,client_email,art_description,artMaterial,completion_date,done);
                    artworks.getChildren().add(box);
                    done.setOnAction(e->
                    {
                        try(Connection connection1 = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
                        {

                            String query2 = "INSERT INTO done_commissions(req_Artist,client_name, client_email, art_description, material, completion_date)" +
                                    "VALUES(?,?,?,?,?,?)";
                            PreparedStatement preparedStatement2 = connection1.prepareStatement(query2);
                            preparedStatement2.setString(1, name);
                            preparedStatement2.setString(2, client_name.getText());
                            preparedStatement2.setString(3, client_email.getText());
                            preparedStatement2.setString(4, art_description.getText());
                            preparedStatement2.setString(5, artMaterial.getText());
                            preparedStatement2.setString(6, completion_date.getText());

                            int num = preparedStatement2.executeUpdate();
                            if(num > 0) {
                                String query3 = "DELETE FROM accept_commissions WHERE req_Artist = ? AND client_name = ?";
                                PreparedStatement preparedStatement3 = connection1.prepareStatement(query3);
                                preparedStatement3.setString(1, name);
                                preparedStatement3.setString(2, client_name.getText());
                                int row1 = preparedStatement3.executeUpdate();
                                if (row1 > 0)
                                    artworks.getChildren().remove(box);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });



                }

            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }



    }@FXML
    protected void setMenuItemDone(ActionEvent event)
    {
        commissionLabel.setText("Done Commissions");
        artworks.getChildren().clear();

        try(Connection connection = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
        {
            String name = setName();
            if(name != null) {
                String query1 = "SELECT * FROM done_commissions WHERE req_Artist = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                preparedStatement1.setString(1, name);
                ResultSet resultSet1 = preparedStatement1.executeQuery();

                while(resultSet1.next())
                {
                    Label client_name = new Label (resultSet1.getString("client_name"));
                    Label client_email = new Label (resultSet1.getString("client_email"));
                    Label art_description = new Label (resultSet1.getString("art_description"));
                    Label artMaterial = new Label (resultSet1.getString("material"));
                    Label completion_date = new Label (resultSet1.getString("completion_date"));
                    Button delete = new Button("Delete");
                    VBox box = getVbox();

                    box.getChildren().addAll(client_name,client_email,art_description,artMaterial,completion_date,delete);
                    artworks.getChildren().add(box);

                    delete.setOnAction(e->
                    {
                        try(Connection connection1 = DriverManager.getConnection(CONNECTION_STRING,"root","galagar"))
                        {
                            String query2 = "DELETE FROM done_commissions WHERE req_Artist = ? AND client_name = ?";
                            PreparedStatement preparedStatement3 = connection1.prepareStatement(query2);
                            preparedStatement3.setString(1, name);
                            preparedStatement3.setString(2, client_name.getText());
                            int row = preparedStatement3.executeUpdate();
                            if(row > 0)
                                artworks.getChildren().remove(box);

                            try(BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Projects in Java\\Shit\\src\\main\\java\\org\\example\\shit\\CommissionHistory")))
                            {
                                LocalDateTime dateTime = LocalDateTime.now();
                                writer.write("Deleted on: "+dateTime.toString());
                                writer.write(client_name.getText()+"\n");
                                writer.write(client_email.getText()+"\n");
                                writer.write(art_description.getText()+"\n");
                                writer.write(artMaterial.getText()+"\n");
                                writer.write(completion_date.getText()+"\n");
                                writer.flush();
                                writer.close();

                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });


                }

            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

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

    public VBox getVbox()
    {
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
        return box;
    }




}
