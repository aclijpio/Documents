package ru.pio.aclij.documents.config.source;

import ru.pio.aclij.documents.financial.document.money.CurrencyCode;

public class FinancialConfig {

    private String defaultCurrency;

    public CurrencyCode getDefaultCurrency() {
        return CurrencyCode.valueOf(defaultCurrency);
    }
}
