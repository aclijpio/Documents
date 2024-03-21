package ru.pio.aclij.documents.controllers.helpers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import lombok.Getter;
import ru.pio.aclij.documents.FinancialApplication;
import ru.pio.aclij.documents.config.exceptions.FailedToLoadFileNameException;
import ru.pio.aclij.documents.config.source.Files;
import ru.pio.aclij.documents.controllers.DocumentController;
import ru.pio.aclij.documents.controllers.helpers.DocumentHelper;
import ru.pio.aclij.documents.financial.customcontrols.entityScene.DocumentScene;
import ru.pio.aclij.documents.financial.document.Document;

import java.io.IOException;

@Getter
public class DocumentLoader {
    private final DocumentHelper helper;
    private final Files files;


    public DocumentLoader(DocumentHelper helper, Files files) {
        this.helper = helper;
        this.files = files;
    }

    public DocumentScene loadByDocument(Document document, Stage stage){
        try {

            FXMLLoader loader = new FXMLLoader(FinancialApplication.class.getResource(files.getEntity()));

            DocumentController controller = new DocumentController(helper, document, stage);
            loader.setController(controller);
            DocumentScene scene = new DocumentScene(loader);
            return scene;
        } catch (IOException e)
        {
            throw new FailedToLoadFileNameException("The form file cannot be loaded.", e);
        }
    }
}
