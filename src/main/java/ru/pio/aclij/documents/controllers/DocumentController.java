package ru.pio.aclij.documents.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.Getter;
import ru.pio.aclij.documents.config.source.FinancialConfig;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.DocumentHelper;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.services.DocumentService;
import ru.pio.aclij.documents.services.exceptions.TextFieldNotFoundException;

public class DocumentController {

    private final FinancialDatabaseManager databaseManager;
    private final FinancialConfig config;
    private final DocumentService service;

    @Getter
    @FXML
    private VBox form;

    @Getter
    @FXML
    private Button persist;


    public DocumentController(FinancialDatabaseManager databaseManager, FinancialConfig config) {
        this.databaseManager = databaseManager;
        this.config = config;
        this.service = new DocumentService(databaseManager);
    }
    public DocumentHelper createHelper(){
        return new DocumentHelper(databaseManager, this, config);
    }
    @FXML
    public void save(){

    }
    @FXML
    public void update(){

    }

    @FXML
    public void delete(){
        TextField textFieldById = (TextField) form.lookup("documentId");
        if (textFieldById == null)
            throw new TextFieldNotFoundException("TextField not found on the form.");
        Long id  = Long.valueOf(textFieldById.getId());
        this.service.delete(id);
    }

}
