package ru.pio.aclij.documents.financial.customcontrols.financialControls;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.AlertWrapper;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.ValidatingTextField;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.document.money.Currency;
import ru.pio.aclij.documents.financial.document.money.CurrencyCode;

import java.util.function.Predicate;

public class FinancialControls {

    public static TextField createUneditableTextField(String text){
        TextField textField = new TextField();
        textField.setText(text);
        textField.setEditable(false);
        return textField;
    }
    public static AlertWrapper createAlertWrapper(String header, String content){
        return new AlertWrapper(header, content);
    }

    public static ComboBox<CurrencyCode> getCurrencyCodeComboBox(FinancialDatabaseManager databaseManager, CurrencyCode code, TextField rateTextField){
        return new CurrencyCodeComboBox(databaseManager, code, rateTextField);
    }
    public static ValidatingTextField getValidationTextField(Predicate<String> validation, Button button, AlertWrapper alertWrapper){
        return new ValidatingTextField(validation, button, alertWrapper);
    }
}
