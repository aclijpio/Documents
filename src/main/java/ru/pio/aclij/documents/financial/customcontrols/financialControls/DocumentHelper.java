package ru.pio.aclij.documents.financial.customcontrols.financialControls;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Getter;
import ru.pio.aclij.documents.config.source.FinancialConfig;
import ru.pio.aclij.documents.controllers.DocumentController;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.document.money.CurrencyCode;

import java.util.function.Predicate;

@Getter
public class DocumentHelper {

    private final FinancialDatabaseManager databaseManager;
    private final DocumentController documentController;
    private final FinancialConfig config;

    public DocumentHelper(FinancialDatabaseManager databaseManager, DocumentController controller, FinancialConfig config) {
        this.databaseManager = databaseManager;
        this.documentController = controller;
        this.config = config;
    }


    public TextField getValidationTextField(Predicate<String> validation, AlertWrapper alertWrapper){
        return FinancialControls.getValidationTextField(validation, documentController.getPersist(), alertWrapper);
    }
    public ComboBox<CurrencyCode> getCurrencyCodeComboBox(CurrencyCode code, TextField rateTextField){
        return FinancialControls.getCurrencyCodeComboBox(databaseManager, code, rateTextField);
    }

}
