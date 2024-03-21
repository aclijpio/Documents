package ru.pio.aclij.documents.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import ru.pio.aclij.documents.controllers.helpers.DocumentLoader;
import ru.pio.aclij.documents.financial.customcontrols.entityScene.DocumentScene;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.document.Invoice;
import ru.pio.aclij.documents.financial.document.Payment;
import ru.pio.aclij.documents.financial.document.PaymentRequest;
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
        service.createSceneByDocumentAndShow(new Invoice());
    }
    @FXML
    public void showPaymentForm() {
        service.createSceneByDocumentAndShow(new Payment());
    }
    @FXML
    public void  showPaymentRequest() {
        service.createSceneByDocumentAndShow(new PaymentRequest());
    }
}
