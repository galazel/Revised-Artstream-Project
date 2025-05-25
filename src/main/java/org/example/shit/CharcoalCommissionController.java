package org.example.shit;

import javafx.fxml.FXML;

public class CharcoalCommissionController extends CommonController{
    @Override
    @FXML
    public void initialize() {
        materialsBox.getItems().addAll(
                "Vine charcoal",
                "Compressed charcoal",
                "Charcoal pencils"
        );
        requestTo.setText("");
    }

}
