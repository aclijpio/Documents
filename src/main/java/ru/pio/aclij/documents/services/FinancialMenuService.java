package ru.pio.aclij.documents.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import ru.pio.aclij.documents.controllers.FinancialMenuController;
import ru.pio.aclij.documents.controllers.helpers.DocumentItem;
import ru.pio.aclij.documents.controllers.helpers.DocumentLoader;
import ru.pio.aclij.documents.financial.customcontrols.entityScene.DocumentScene;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.replace.ReplaceAlert;
import ru.pio.aclij.documents.financial.customcontrols.stage.DocumentActionCode;
import ru.pio.aclij.documents.financial.customcontrols.stage.DocumentStage;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.documents.Document;
import ru.pio.aclij.documents.financial.documents.Invoice;
import ru.pio.aclij.documents.financial.documents.Payment;
import ru.pio.aclij.documents.financial.documents.PaymentRequest;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FinancialMenuService {

    private final FinancialDatabaseManager databaseManager;
    private final DocumentLoader loader;
    private final FinancialMenuController controller;
    private final ObjectMapper objectMapper;

    public FinancialMenuService(FinancialDatabaseManager databaseManager, DocumentLoader loader, FinancialMenuController controller) {
        this.databaseManager = databaseManager;
        this.loader = loader;
        this.controller = controller;
        this.objectMapper = createStandartObjectMapper();
    }
    private ObjectMapper createStandartObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerSubtypes(Invoice.class, Payment.class, PaymentRequest.class);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        return objectMapper;
    }

    public ObservableList<DocumentItem> findAllDocuments(){
        return FXCollections.observableList(
                databaseManager.findAllDocuments()
                        .stream()
                        .map(DocumentItem::new)
                        .collect(Collectors.toList())
        );
    }

    public DocumentScene createSceneByDocument(DocumentStage stage){
        return loader.loadByDocument(stage);
    }

    public DocumentStage createSceneByDocumentAndShow(Document document){
        DocumentStage stage= new DocumentStage(document);
        DocumentScene scene = createSceneByDocument(stage);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                controller.executeAction(stage, loader.getHelper().getDatabaseManager());
            }
        });
        
        return stage;
    }
    public List<Document> findSelectedDocumentItems(ListView<DocumentItem> documentList){
        return documentList.getItems().stream()
                .filter(DocumentItem::isSelected)
                .map(DocumentItem::getDocument)
                .collect(Collectors.toList());
    }

    public void saveToJsonFile(List<Document> documentList){
        if (!documentList.isEmpty()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("documents.json");

            File createdFile = fileChooser.showSaveDialog(null);

            if (createdFile != null) {
                try (FileWriter fileWriter = new FileWriter(createdFile)) {

                    this.objectMapper.writeValue(fileWriter, documentList);
                } catch (IOException e) {
                    callErrorAlert("Не удалось сохранить в файл.");
                }
            }
        }
    }

    public List<Document> loadDocumentsFromJsonFile(){

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null && selectedFile.exists()){
            try (FileReader fileReader = new FileReader(selectedFile)){
                List<Object> objects = objectMapper.readValue(fileReader, new TypeReference<List<Object>>() {});
                List<Document> documents = new ArrayList<>();
                for (Object obj : objects) {
                    Document document = (Document) obj;
                    documents.add(document);
                }

                return documents;

            }
            catch (IOException e){
                callErrorAlert("Не удалось загрузить файл.");
            }
        }
        return List.of();
    }
    public Map<DocumentItem, DocumentActionCode> getReduceListLoad(List<Document> documentList, ListView<DocumentItem> list){
        Iterator<Document> documentIterator = documentList.iterator();
        HashMap<DocumentItem, DocumentActionCode> addingDocument = new HashMap<>();
        boolean withAlert = true;
        while (documentIterator.hasNext()){
            Document document = documentIterator.next();
            Optional<DocumentItem> hasReplace = getIfExists(document, list);
            if (hasReplace.isPresent()){
                DocumentItem item = hasReplace.get();
                if (withAlert) {
                    ReplaceAlert replaceAlert = new ReplaceAlert(item);
                    switch (replaceAlert.getCurrentReplaceCode()) {
                        case ALL -> withAlert = false;
                        case CLOSE -> {
                            return Map.of();
                        }
                    }
                }
                addingDocument.put(item, DocumentActionCode.UPDATE);
            } else {
                DocumentItem documentItem = new DocumentItem(document);
                documentItem.select();
                addingDocument.put(documentItem, DocumentActionCode.ADD);
            }

        }
        System.out.println(addingDocument);
        return addingDocument;
    }
    public Optional<DocumentItem> getIfExists(Document document, ListView<DocumentItem> list){
        return list.getItems().stream()
                .filter(documentItem -> documentItem.getDocument().getNumber().equals(document.getNumber()))
                .findAny();
    }
    public void addToDatabaseAndListView(Map<DocumentItem, DocumentActionCode> map, ListView<DocumentItem> list){
        map.forEach((key, item) -> {
            switch (item) {
                case UPDATE -> {
                    System.out.println("U");
                    removeDocumentItemById(key.getId(), list);
                    list.getItems().add(key);
                    databaseManager.update(key.getDocument());
                }
                case ADD -> {
                    Document document = databaseManager.update(key.getDocument());
                    list.getItems().add(new DocumentItem(document));

                }
            }
        });
    }

    private void callErrorAlert(String s){
       Alert alert =  new Alert(Alert.AlertType.ERROR, s);
       alert.show();
    }

    private void removeDocumentItemById(Long id, ListView<DocumentItem> list){
        list.getItems().removeIf(documentItem -> documentItem.getId() == id);

    }



}
