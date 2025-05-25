package org.example.shit;

import javafx.fxml.FXML;

public class PortraitCommissionController extends CommonController{
    @Override
    @FXML
    public void initialize() {
        materialsBox.getItems().addAll(
                "Graphite pencils",
                "Regular Colored Pencils",
                "Highlighter",
                "Ballpoint"
        );
        requestTo.setText("");
    }
}
