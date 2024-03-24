package ru.pio.aclij.documents;

import jakarta.persistence.EntityManagerFactory;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ru.pio.aclij.documents.config.ConfigLoader;
import ru.pio.aclij.documents.config.source.AppConfig;
import ru.pio.aclij.documents.controllers.FinancialMenuController;
import ru.pio.aclij.documents.controllers.helpers.DocumentHelper;
import ru.pio.aclij.documents.controllers.helpers.DocumentLoader;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.database.JpaUtil;

public class FinancialApplication extends Application{

    @FXML
    private ListView<String> documentList;
    @Override
    public void start(Stage stage) throws Exception {

        AppConfig appConfig = ConfigLoader.loadConfig();

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory(appConfig.getDatasource());




        FinancialDatabaseManager databaseManager = new FinancialDatabaseManager(entityManagerFactory);

        FXMLLoader fxmlLoader = new FXMLLoader(FinancialApplication.class.getResource(appConfig.getFiles().getMenu()));


        DocumentLoader documentLoader = new DocumentLoader(new DocumentHelper(databaseManager, appConfig.getFinancial()), appConfig.getFiles());

        fxmlLoader.setController(new FinancialMenuController(databaseManager, documentLoader, stage));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Financial");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

}
