package ru.pio.aclij.documents.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ru.pio.aclij.documents.config.Files;
import ru.pio.aclij.documents.config.exceptions.FailedToLoadFileNameException;
import ru.pio.aclij.documents.controllers.DocumentController;
import ru.pio.aclij.documents.financial.customcontrols.entityScene.DocumentScene;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.document.Document;
import ru.pio.aclij.documents.financial.document.Invoice;

import java.io.IOException;
import java.util.stream.Collectors;

public class FinancialMenuService {

    private final FinancialDatabaseManager databaseManager;

    public FinancialMenuService(FinancialDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


    public ObservableList<String> findAllDocuments(){
        return FXCollections.observableList(
                databaseManager.findAll()
                        .stream()
                        .map(Document::toString)
                        .peek(System.out::println)
                        .collect(Collectors.toList())
        );
    }

    public DocumentScene createSceneByDocument(Document document, FXMLLoader fxmlLoader){
        try {
            DocumentController controller = fxmlLoader.getController();
            return new DocumentScene(fxmlLoader)
                    .loadEntity(document, controller.getForm());
        } catch (IOException e)
        {
            throw new FailedToLoadFileNameException("The form file cannot be loaded.", e);
        }
    }

}
