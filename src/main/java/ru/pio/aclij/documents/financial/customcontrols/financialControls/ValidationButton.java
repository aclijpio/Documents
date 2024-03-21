package ru.pio.aclij.documents.financial.customcontrols.financialControls;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.pio.aclij.documents.controllers.DocumentController;

import java.util.ArrayList;
import java.util.List;

public abstract class ValidationButton extends Button {

    List<ValidatingTextField> fields = new ArrayList<>();

    public ValidationButton(String name) {
        super(name);

        this.setOnAction(actionEvent -> {
            for (ValidatingTextField textField : fields) {
                if (!textField.applyPredicate()) {
                    textField.createAlert().execute();
                    actionEvent.consume();
                    break;
                }
            }
            executeEvent();
        });
    }
    public abstract void executeEvent();
    public void add(ValidatingTextField textField){
        fields.add(textField);
    }



}
