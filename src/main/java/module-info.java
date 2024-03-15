module ru.pio.aclij.documents {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires org.postgresql.jdbc;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires com.fasterxml.jackson.databind;

    opens ru.pio.aclij.documents to javafx.fxml;
    exports ru.pio.aclij.documents;
    exports ru.pio.aclij.documents.financial.document;
    opens ru.pio.aclij.documents.financial.document to javafx.fxml;
    exports ru.pio.aclij.documents.financial.document.money;
    opens ru.pio.aclij.documents.financial.document.money to javafx.fxml;
    exports ru.pio.aclij.documents.financial.document.clients;
    opens ru.pio.aclij.documents.financial.document.clients to javafx.fxml;
    exports ru.pio.aclij.documents.financial.config;
    opens ru.pio.aclij.documents.financial.config to javafx.fxml;
}