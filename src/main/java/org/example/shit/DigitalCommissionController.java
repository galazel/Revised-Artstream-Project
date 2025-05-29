package org.example.shit;

import javafx.fxml.FXML;


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
