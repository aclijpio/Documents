package ru.pio.aclij.documents.financial.customcontrols.financialControls;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.document.money.CurrencyCode;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class FinancialControlsFactory {

    public static TextField createUneditableTextField(String text){
        TextField textField = new TextField();
        textField.setText(text);
        textField.setEditable(false);
        return textField;
    }
    public static AlertWrapper createAlertWrapper(String header, String content){
        return new AlertWrapper(header, content);
    }

    public static ComboBox<CurrencyCode> createCurrencyCodeComboBox(FinancialDatabaseManager databaseManager, CurrencyCode code, TextField rateTextField){
        return new CurrencyCodeComboBox(databaseManager, code, rateTextField);
    }
    public static ValidatingTextField createValidationTextField(Predicate<String> validation, Button button, AlertWrapper alertWrapper){
        return new ValidatingTextField(validation, button, alertWrapper);
    }
    public static DatePicker createCurrentDatePicker(){
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        return datePicker;
    }
    public static ComboBox<String> getStringComboBox(List<String> suggestions, TextField textField){
        return new StringComboBox(suggestions, textField);
    }
}
