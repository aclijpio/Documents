package ru.pio.aclij.documents.financial.customcontrols.entityScene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.DocumentHelper;

import java.io.IOException;

public class DocumentScene extends Scene {

    public DocumentScene(FXMLLoader loader) throws IOException {
        super(loader.load());
    }

    public DocumentScene loadEntity(ParentDocument document, VBox parent, DocumentHelper helper){
        parent.getChildren().addAll(
            document.toParent(helper)
        );
        return this;
    }
    public void show(Stage stage){
        stage.setScene(this);
        stage.show();
    }
}

