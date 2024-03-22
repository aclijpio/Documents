package ru.pio.aclij.documents.financial.customcontrols.entityScene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class DocumentScene extends Scene {


    public DocumentScene(FXMLLoader loader) throws IOException {
        super(loader.load());
    }

}

