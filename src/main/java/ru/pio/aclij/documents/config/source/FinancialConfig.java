package ru.pio.aclij.documents.config.source;

import lombok.Getter;
import ru.pio.aclij.documents.financial.documents.money.CurrencyCode;

public class FinancialConfig {

    private String defaultCurrency;
    @Getter
    private double commission;

    public CurrencyCode getDefaultCurrency() {
        return CurrencyCode.valueOf(defaultCurrency);
    }
}
