package ru.pio.aclij.documents.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.pio.aclij.documents.config.source.FinancialConfig;
import ru.pio.aclij.documents.financial.customcontrols.entityScene.DocumentScene;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.DocumentHelper;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.document.Invoice;
import ru.pio.aclij.documents.services.FinancialMenuService;

import java.net.URL;
import java.util.ResourceBundle;

public class
FinancialMenuController implements Initializable {
    private final FinancialDatabaseManager databaseManager;
    private final FinancialMenuService service;
    private final DocumentLoader documentLoader;

    @FXML
    private ListView<String> documentList;
    @FXML
    private Button invoiceButton;

    private VBox form;
    public FinancialMenuController(FinancialDatabaseManager databaseManager, DocumentLoader documentLoader) {
        this.databaseManager = databaseManager;
        this.documentLoader = documentLoader;

        service = new FinancialMenuService(databaseManager, documentLoader);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        documentList.getItems().addAll(
                service.findAllDocuments()
        );

    }
    @FXML
    public void showInvoiceForm() {
        DocumentScene scene = service.createSceneByDocument(new Invoice());
        scene.show(documentLoader.getStage());
    }
}
