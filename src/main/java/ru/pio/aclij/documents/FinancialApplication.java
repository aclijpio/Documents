package ru.pio.aclij.documents;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.pio.aclij.documents.financial.config.ConfigLoader;

public class FinancialApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        var config = ConfigLoader.loadConfig();

        System.out.println(config);
    }
    public static void main(String[] args) {
        launch();
    }
}
