package ru.pio.aclij.documents;

import jakarta.persistence.EntityManagerFactory;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ru.pio.aclij.documents.config.AppConfig;
import ru.pio.aclij.documents.config.ConfigLoader;
import ru.pio.aclij.documents.controllers.DocumentController;
import ru.pio.aclij.documents.controllers.FinancialMenuController;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.database.JpaUtil;
import ru.pio.aclij.documents.financial.document.Document;
import ru.pio.aclij.documents.financial.document.Invoice;
import ru.pio.aclij.documents.financial.document.Payment;
import ru.pio.aclij.documents.financial.document.clients.Employee;
import ru.pio.aclij.documents.financial.document.clients.User;
import ru.pio.aclij.documents.financial.document.money.Currency;
import ru.pio.aclij.documents.financial.document.money.CurrencyCode;
import ru.pio.aclij.documents.financial.document.money.Product;

import java.time.Clock;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class FinancialApplication extends Application{

    @FXML
    private ListView<String> documentList;
    @Override
    public void start(Stage stage) throws Exception {

        AppConfig appConfig = ConfigLoader.loadConfig();

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory(appConfig.getDatasource());



        Invoice invoice = new Invoice(
                "1",
                LocalDate.now(Clock.systemDefaultZone()),
                new User("user"),
                12.1,
                new Currency(CurrencyCode.RUB, 45.12),
                new Product("Milk", 12)
        );
        Payment payment = new Payment(
                "2",
                LocalDate.now(Clock.systemDefaultZone()),
                new User("user2"),
                12.21,
                new Employee("employee")
        );
        FinancialDatabaseManager databaseManager = new FinancialDatabaseManager(entityManagerFactory);
        databaseManager.save(payment);
        databaseManager.save(invoice);
        System.out.println(
                databaseManager.findById(Invoice.class, 1L)
        );
        System.out.println(
                databaseManager.findAll()
                        .stream()
                        .map(Document::toString)
                        .collect(Collectors.joining(", "))
        );
        FXMLLoader fxmlLoader = new FXMLLoader(FinancialApplication.class.getResource(appConfig.getFiles().getMenu()));
        FXMLLoader entityFxmlLoader = new FXMLLoader(FinancialApplication.class.getResource(appConfig.getFiles().getEntity()));

        fxmlLoader.setController(new FinancialMenuController(stage, entityFxmlLoader,databaseManager));
        entityFxmlLoader.setController(new DocumentController(databaseManager));

        Scene scene = new Scene(fxmlLoader.load());


        stage.setTitle("Financial");
        stage.setScene(scene);
        stage.show();
/*
        System.out.println(databaseManager.findAll().stream()
                .map(Document::getId)
                        .map(String::valueOf)
                .collect(Collectors.joining(", ")));

*/


    }
    public static void main(String[] args) {
        launch();
    }

}
