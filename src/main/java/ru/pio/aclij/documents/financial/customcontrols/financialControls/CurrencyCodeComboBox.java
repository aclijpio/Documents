package ru.pio.aclij.documents.financial.customcontrols.financialControls;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.documents.money.Currency;
import ru.pio.aclij.documents.financial.documents.money.CurrencyCode;

import java.util.Optional;

public class CurrencyCodeComboBox extends ComboBox<CurrencyCode> {

    public CurrencyCodeComboBox(FinancialDatabaseManager databaseManager, CurrencyCode code, TextField rateTextField) {

        if (code == null)
            throw new IllegalArgumentException("Currency code cannot be null");

        this.getItems().addAll(
                CurrencyCode.getCurrencies()
        );

        this.getSelectionModel().select(code);

        this.setOnAction(e -> {
            CurrencyCode currentCode = this.getSelectionModel().getSelectedItem();
            Optional<Currency> currency = databaseManager.findCurrencyByCurrencyCode(currentCode);
            currency.ifPresent(value -> rateTextField.setText(String.valueOf(value.getRate())));
        });
    }
}
