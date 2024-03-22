package ru.pio.aclij.documents.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import ru.pio.aclij.documents.controllers.FinancialMenuController;
import ru.pio.aclij.documents.controllers.helpers.DocumentItem;
import ru.pio.aclij.documents.controllers.helpers.DocumentLoader;
import ru.pio.aclij.documents.financial.customcontrols.entityScene.DocumentScene;
import ru.pio.aclij.documents.financial.customcontrols.stage.DocumentStage;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.documents.Document;

import java.util.stream.Collectors;

public class FinancialMenuService {

    private final FinancialDatabaseManager databaseManager;
    private final DocumentLoader loader;
    private final FinancialMenuController controller;

    public FinancialMenuService(FinancialDatabaseManager databaseManager, DocumentLoader loader, FinancialMenuController controller) {
        this.databaseManager = databaseManager;
        this.loader = loader;
        this.controller = controller;
    }


    public ObservableList<DocumentItem> findAllDocuments(){
        return FXCollections.observableList(
                databaseManager.findAllDocuments()
                        .stream()
                        .map(DocumentItem::new)
                        .peek(System.out::println)
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


}
