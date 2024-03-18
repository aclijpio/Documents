package ru.pio.aclij.documents.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.Getter;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;

public class DocumentController {

    private final FinancialDatabaseManager databaseManager;

    @FXML
    @Getter
    private VBox form;


    public DocumentController(FinancialDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


}
