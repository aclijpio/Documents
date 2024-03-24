package ru.pio.aclij.documents.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import ru.pio.aclij.documents.controllers.helpers.DocumentItem;
import ru.pio.aclij.documents.controllers.helpers.DocumentLoader;
import ru.pio.aclij.documents.financial.customcontrols.stage.DocumentActionCode;
import ru.pio.aclij.documents.financial.customcontrols.stage.DocumentStage;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.entities.Document;
import ru.pio.aclij.documents.financial.entities.Invoice;
import ru.pio.aclij.documents.financial.entities.Payment;
import ru.pio.aclij.documents.financial.entities.PaymentRequest;
import ru.pio.aclij.documents.services.FinancialMenuService;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

public class
FinancialMenuController implements Initializable {
    private final FinancialDatabaseManager databaseManager;
    private final FinancialMenuService service;
    private boolean mainCheckbox = false;
    private final Stage stage;
    @FXML
    private ListView<DocumentItem> documentList;
    @FXML
    private Button invoiceButton;



    public FinancialMenuController(FinancialDatabaseManager databaseManager, DocumentLoader documentLoader, Stage stage) {
        this.databaseManager = databaseManager;
        this.stage = stage;
        service = new FinancialMenuService(databaseManager, documentLoader, this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        

        documentList.setCellFactory(CheckBoxListCell.forListView(
                new Callback<DocumentItem, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(DocumentItem documentItem) {
                        // Получаем существующий BooleanProperty из объекта DocumentItem
                        BooleanProperty observable = documentItem.selectedProperty();

                        // Добавляем слушатель, если нужно
                        observable.addListener((obs, wasSelected, isNowSelected) -> {
                            // Действия при изменении состояния флажка
                        });

                        return observable;
                    }
                }, new StringConverter<DocumentItem>() {
                    @Override
                    public String toString(DocumentItem documentItem) {
                        return documentItem.getDocument().toString();
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
    private void close(){
        this.stage.close();
    }
    @FXML
    private void checkbox(){
        if (mainCheckbox){
            documentList.getItems()
                    .forEach(DocumentItem::unselect);

        } else {
            documentList.getItems()
                    .forEach(DocumentItem::select);
        }
        mainCheckbox = !mainCheckbox;
    }
    public void selectAll(){

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
    private void save() {
        this.service.saveToJsonFile(
                this.service.findSelectedDocumentItems(this.documentList)
        );
    }
    @FXML
    private void load(){
        Map<DocumentItem, DocumentActionCode> documentItems =  this.service.getReduceListLoad(
                this.service.loadDocumentsFromJsonFile(),
                this.documentList
        );
        this.service.addToDatabaseAndListView(documentItems, documentList);
    }
    @FXML
    public void showDocument(){
        DocumentItem documentItem = documentList.getSelectionModel().getSelectedItem();
        service.createSceneByDocumentAndShow(documentItem.getDocument());
    }
    @FXML
    public void delete(){
        int count= 0;
        if (!documentList.getItems().isEmpty()) {
            Iterator<DocumentItem> iterator = documentList.getItems().iterator();

            while (iterator.hasNext()) {
                count++;
                DocumentItem documentItem = iterator.next();
                if (documentItem.isSelected()) {
                    iterator.remove();
                    databaseManager.delete(documentItem.getDocument());
                }
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
                        if (iterator.next().getDocument().getId() == id){
                            iterator.remove();
                            documentList.getItems().add(0, new DocumentItem(document));
                            break;
                        }
                    }
                }
        }
    }
}
