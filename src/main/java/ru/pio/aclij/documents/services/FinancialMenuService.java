package ru.pio.aclij.documents.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import ru.pio.aclij.documents.controllers.helpers.DocumentLoader;
import ru.pio.aclij.documents.financial.customcontrols.entityScene.DocumentScene;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.document.Document;

import java.util.stream.Collectors;

public class FinancialMenuService {

    private final FinancialDatabaseManager databaseManager;
    private final DocumentLoader loader;

    public FinancialMenuService(FinancialDatabaseManager databaseManager, DocumentLoader loader) {
        this.databaseManager = databaseManager;
        this.loader = loader;
    }


    public ObservableList<String> findAllDocuments(){
        return FXCollections.observableList(
                databaseManager.findAllDocuments()
                        .stream()
                        .map(Document::toString)
                        .peek(System.out::println)
                        .collect(Collectors.toList())
        );
    }

    public DocumentScene createSceneByDocument(Document document, Stage stage){
        return loader.loadByDocument(document, stage);
    }
    public DocumentScene createSceneByDocumentAndShow(Document document){
        Stage stage = new Stage();
        DocumentScene scene = createSceneByDocument(document, stage);
        scene.show(stage);
        return scene;
    }
}
