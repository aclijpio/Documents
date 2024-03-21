package ru.pio.aclij.documents.controllers.helpers;

import lombok.Getter;
import ru.pio.aclij.documents.config.source.FinancialConfig;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;

@Getter
public class DocumentHelper {

    private final FinancialDatabaseManager databaseManager;
    private final FinancialConfig config;

    public DocumentHelper(FinancialDatabaseManager databaseManager, FinancialConfig config) {
        this.databaseManager = databaseManager;
        this.config = config;
    }



}
