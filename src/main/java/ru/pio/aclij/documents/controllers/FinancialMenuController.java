package ru.pio.aclij.documents.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;
import ru.pio.aclij.documents.controllers.helpers.DocumentItem;
import ru.pio.aclij.documents.controllers.helpers.DocumentLoader;
import ru.pio.aclij.documents.financial.customcontrols.stage.DocumentStage;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.documents.Document;
import ru.pio.aclij.documents.financial.documents.Invoice;
import ru.pio.aclij.documents.financial.documents.Payment;
import ru.pio.aclij.documents.financial.documents.PaymentRequest;
import ru.pio.aclij.documents.services.FinancialMenuService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class
FinancialMenuController implements Initializable {
    private final FinancialDatabaseManager databaseManager;
    private final FinancialMenuService service;
    private final DocumentLoader documentLoader;

    @FXML
    private ListView<DocumentItem> documentList;
    @FXML
    private Button invoiceButton;

    private VBox form;

    public FinancialMenuController(FinancialDatabaseManager databaseManager, DocumentLoader documentLoader) {
        this.databaseManager = databaseManager;
        this.documentLoader = documentLoader;

        service = new FinancialMenuService(databaseManager, documentLoader, this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        documentList.setCellFactory(CheckBoxListCell.forListView(new Callback<DocumentItem, ObservableValue<Boolean>>() {
             @Override
             public ObservableValue<Boolean> call(DocumentItem documentItem) {
                 BooleanProperty observable = new SimpleBooleanProperty();
                 observable.addListener((obs, wasSelected, isNowSelected) -> {
                     documentItem.select();
                 });
                 return observable;
             }
         }, new StringConverter<DocumentItem>() {
             @Override
             public String toString(DocumentItem documentItem) {
                 return documentItem.getString();
             }

             @Override
             public DocumentItem fromString(String s) {
                 return null;
             }
         }
        ));


        documentList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        documentList.setItems(
                service.findAllDocuments()
        );
    }




    @FXML
    private void showInvoiceForm() {
        service.createSceneByDocumentAndShow(new Invoice());
    }
    @FXML
    private void showPaymentForm() {
        service.createSceneByDocumentAndShow(new Payment());
    }
    @FXML
    private void  showPaymentRequest() {
        service.createSceneByDocumentAndShow(new PaymentRequest());
    }
    @FXML
    private void save() throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("document.json");

        File selectedFile = fileChooser.showSaveDialog(null);



        try(FileWriter fileWriter = new FileWriter(selectedFile)) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            Iterator<DocumentItem> iterator = documentList.getItems().iterator();
            while (iterator.hasNext()) {
                DocumentItem documentItem = iterator.next();
                if (documentItem.isSelected()) {
                    objectMapper.writeValue(fileWriter, documentItem.getDocument());
                }
            }
        }


    }
    @FXML
    private void load(){

    }
    @FXML
    public void showDocument(){
        DocumentItem documentItem = documentList.getSelectionModel().getSelectedItem();
        service.createSceneByDocumentAndShow(documentItem.getDocument());
    }
    @FXML
    public void delete(){

        Iterator<DocumentItem> iterator = documentList.getItems().iterator();
        while (iterator.hasNext()){
            DocumentItem documentItem = iterator.next();
            if (documentItem.isSelected()){
                iterator.remove();
                databaseManager.delete(documentItem.getDocument());
            }
        }
    }

    public void executeAction(DocumentStage stage, FinancialDatabaseManager databaseManager){
        switch (stage.getAction()){
            case ADD -> {
                Document document = databaseManager.findById(Document.class, stage.getDocument().getId());

                documentList.getItems().add(0, new DocumentItem(document));
            }
            case UPDATE -> {
                long id = stage.getDocument().getId();
                Document document = databaseManager.findById(Document.class, id);
                    Iterator<DocumentItem> iterator = documentList.getItems().iterator();

                    while(iterator.hasNext()){
                        if (iterator.next().getId() == id){
                            iterator.remove();
                            documentList.getItems().add(0, new DocumentItem(document));
                            break;
                        }
                    }
                }
        }
    }
}
