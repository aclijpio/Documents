package ru.pio.aclij.documents.controllers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import lombok.Getter;
import ru.pio.aclij.documents.config.exceptions.FailedToLoadFileNameException;
import ru.pio.aclij.documents.financial.customcontrols.entityScene.DocumentScene;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.DocumentHelper;
import ru.pio.aclij.documents.financial.document.Document;

import java.io.IOException;

@Getter
public class DocumentLoader {
    private final DocumentHelper helper;
    private final FXMLLoader loader;
    private final Stage stage;

    public DocumentLoader(DocumentHelper helper, FXMLLoader loader, Stage stage) {
        this.helper = helper;
        this.loader = loader;
        this.stage = stage;
    }

    public DocumentScene loadByDocument(Document document){
        try {
            DocumentController controller = loader.getController();
            return new DocumentScene(loader)
                    .loadEntity(document, controller.getForm(), helper);
        } catch (IOException e)
        {
            throw new FailedToLoadFileNameException("The form file cannot be loaded.", e);
        }
    }
}
