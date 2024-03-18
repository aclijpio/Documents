package ru.pio.aclij.documents.financial.customcontrols.entityScene;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DocumentScene extends Scene {

    public DocumentScene(FXMLLoader loader) throws IOException {
        super(loader.load());
    }

    public DocumentScene loadEntity(ParentDocument document, VBox parent){
        parent.getChildren().addAll(
            document.toParent()
        );
        return this;
    }
    public void show(Stage stage){
        stage.setScene(this);
        stage.show();
    }
}

