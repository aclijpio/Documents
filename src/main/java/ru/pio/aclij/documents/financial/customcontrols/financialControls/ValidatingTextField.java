package ru.pio.aclij.documents.financial.customcontrols.financialControls;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.function.Predicate;

public class ValidatingTextField extends TextField {

    private final Predicate<String> validation;
    private final AlertWrapper alertWrapper;

    public ValidatingTextField(Predicate<String> validation, AlertWrapper alertWrapper) {
        this.validation = validation;
        this.alertWrapper = alertWrapper;
    }

    public TextFieldWarningAlert createAlert(){
        return new TextFieldWarningAlert(this.alertWrapper);
    }
    public boolean applyPredicate(){
        return validation.test(this.getText());
    }

    public static class TextFieldWarningAlert extends Alert {
        public TextFieldWarningAlert(AlertWrapper alertWrapper) {
            super(AlertType.WARNING);
            this.setTitle("Warning");
            this.setHeaderText(alertWrapper.header());
            this.setContentText(alertWrapper.content());
        }
        public void execute() {
            this.showAndWait();
        }
    }
}
