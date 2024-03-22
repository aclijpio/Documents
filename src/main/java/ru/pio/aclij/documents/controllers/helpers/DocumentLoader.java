package ru.pio.aclij.documents.controllers.helpers;

import javafx.fxml.FXMLLoader;
import lombok.Getter;
import ru.pio.aclij.documents.FinancialApplication;
import ru.pio.aclij.documents.config.exceptions.FailedToLoadFileNameException;
import ru.pio.aclij.documents.config.source.Files;
import ru.pio.aclij.documents.controllers.DocumentController;
import ru.pio.aclij.documents.financial.customcontrols.entityScene.DocumentScene;
import ru.pio.aclij.documents.financial.customcontrols.stage.DocumentStage;

import java.io.IOException;

@Getter
public class DocumentLoader {
    private final DocumentHelper helper;
    private final Files files;


    public DocumentLoader(DocumentHelper helper, Files files) {
        this.helper = helper;
        this.files = files;
    }

    public DocumentScene loadByDocument(DocumentStage stage){
        try {

            FXMLLoader loader = new FXMLLoader(FinancialApplication.class.getResource(files.getEntity()));

            DocumentController controller = new DocumentController(helper, stage);
            loader.setController(controller);
            return new DocumentScene(loader);
        } catch (IOException e)
        {
            throw new FailedToLoadFileNameException("The form file cannot be loaded.", e);
        }
    }
}
