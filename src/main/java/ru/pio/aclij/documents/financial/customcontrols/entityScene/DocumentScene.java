package ru.pio.aclij.documents.financial.customcontrols.entityScene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.pio.aclij.documents.controllers.DocumentController;
import ru.pio.aclij.documents.controllers.helpers.DocumentHelper;
import ru.pio.aclij.documents.financial.document.Document;

import java.io.IOException;

public class DocumentScene extends Scene {


    public DocumentScene(FXMLLoader loader) throws IOException {
        super(loader.load());
    }

    public void show(Stage stage){
        stage.setScene(this);
        stage.show();
    }
}

