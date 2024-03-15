package ru.pio.aclij.documents;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;

public class TestController {
    @FXML
    private ButtonBar hiButtonBar;
    @FXML
    private Label hiText;

    @FXML
    protected void onHi(){
        hiText.setText("HI");
    }



}
