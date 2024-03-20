package ru.pio.aclij.documents.financial.customcontrols.financialControls;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.function.Predicate;

public class ValidatingTextField extends TextField{
    private final BooleanProperty isValidProperty = new SimpleBooleanProperty();

    public ValidatingTextField(Predicate<String> validation, Button button, AlertWrapper alertWrapper) {
        this.textProperty().addListener((observableValue, s, newText) -> {
            boolean valid = validation.test(newText);
            isValidProperty.setValue(valid);
        });
        button.setOnMouseClicked(actionEvent -> {
            if (isValidProperty.get())
                new TextFieldWarningAlert(alertWrapper).execute();
        });
    }
    private static class TextFieldWarningAlert extends Alert {
        public TextFieldWarningAlert(AlertWrapper alertWrapper) {
            super(AlertType.WARNING);
            this.setTitle("Warning");
            this.setHeaderText(alertWrapper.header());
            this.setContentText(alertWrapper.content());
        }
        public void execute(){
            this.showAndWait();
        }

    }
}
