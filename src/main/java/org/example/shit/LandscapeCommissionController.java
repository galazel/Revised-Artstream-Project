package org.example.shit;

import javafx.fxml.FXML;

public class LandscapeCommissionController extends  CommonController{
    @Override
    @FXML
    public void initialize() {
        materialsBox.getItems().addAll(
                "Water Color",
                "Color Pastels",
                "Poster Paint"
        );
        requestTo.setText("");
    }
}
