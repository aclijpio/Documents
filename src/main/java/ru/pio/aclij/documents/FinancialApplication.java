package ru.pio.aclij.documents;

import jakarta.persistence.EntityManagerFactory;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ru.pio.aclij.documents.config.source.AppConfig;
import ru.pio.aclij.documents.config.ConfigLoader;
import ru.pio.aclij.documents.controllers.DocumentController;
import ru.pio.aclij.documents.controllers.DocumentLoader;
import ru.pio.aclij.documents.controllers.FinancialMenuController;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.DocumentHelper;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.database.JpaUtil;
import ru.pio.aclij.documents.financial.document.Invoice;
import ru.pio.aclij.documents.financial.document.Payment;
import ru.pio.aclij.documents.financial.document.clients.Employee;
import ru.pio.aclij.documents.financial.document.clients.User;
import ru.pio.aclij.documents.financial.document.money.Currency;
import ru.pio.aclij.documents.financial.document.money.CurrencyCode;
import ru.pio.aclij.documents.financial.document.money.Product;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

public class FinancialApplication extends Application{

    @FXML
    private ListView<String> documentList;
    @Override
    public void start(Stage stage) throws Exception {

        AppConfig appConfig = ConfigLoader.loadConfig();

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory(appConfig.getDatasource());




        FinancialDatabaseManager databaseManager = new FinancialDatabaseManager(entityManagerFactory);

        FXMLLoader fxmlLoader = new FXMLLoader(FinancialApplication.class.getResource(appConfig.getFiles().getMenu()));
        FXMLLoader entityFxmlLoader = new FXMLLoader(FinancialApplication.class.getResource(appConfig.getFiles().getEntity()));


        DocumentController documentController = new DocumentController(databaseManager, appConfig.getFinancial());

        DocumentLoader documentLoader = new DocumentLoader(documentController.createHelper(), entityFxmlLoader, stage);

        
                
        entityFxmlLoader.setController(documentController);


        fxmlLoader.setController(new FinancialMenuController(databaseManager, documentLoader));





        Scene scene = new Scene(fxmlLoader.load());


        stage.setTitle("Financial");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

}
