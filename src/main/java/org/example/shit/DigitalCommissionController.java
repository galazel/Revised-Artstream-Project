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

public class DigitalCommissionController extends CommonController{
    @Override
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
}
