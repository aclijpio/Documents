package ru.pio.aclij.documents.financial.customcontrols.financialControls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class StringComboBox extends ComboBox<String> {


    public StringComboBox(List <String> suggestions, TextField textField) {
        super();
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                this.getItems().clear();
                List<String> suit = new ArrayList<>();
                for (String item : suggestions) {
                    if (item.toLowerCase().startsWith(newValue.toLowerCase())) {
                        suit.add(item);
                    }
                }
                if (!suit.isEmpty()) {
                    this.getItems().addAll(suit);
                    this.setValue(suit.get(0));
                }
            }
        });

    }



}
