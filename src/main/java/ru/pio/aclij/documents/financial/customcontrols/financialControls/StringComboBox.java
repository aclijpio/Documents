package ru.pio.aclij.documents.financial.customcontrols.financialControls;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class StringComboBox extends ComboBox<String> {

    private boolean updatingComboBox = false;

    public StringComboBox(List<String> suggestions, TextField textField) {
        super();


        this.setOnAction( e -> {
            textField.setText(this.getSelectionModel().getSelectedItem());
        });
        this.setOnMouseClicked(e -> {
            String currentText = textField.getText();
            if (currentText != null) {
                List<String> suit = new ArrayList<>();
                for (String item : suggestions) {
                    if (item != null && item.toLowerCase().startsWith(currentText.toLowerCase())) {
                        suit.add(item);
                    }
                }
                this.setItems(FXCollections.observableArrayList(suit));

            } else
                this.setItems(FXCollections.observableArrayList(suggestions));

        });

    }
}