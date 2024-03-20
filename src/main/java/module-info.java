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
    requires jakarta.transaction;
    requires jakarta.cdi;

    opens ru.pio.aclij.documents to javafx.fxml;

    exports ru.pio.aclij.documents;
    exports ru.pio.aclij.documents.financial.document;

    opens ru.pio.aclij.documents.financial.document to javafx.fxml, org.hibernate.orm.core;
    exports ru.pio.aclij.documents.financial.document.money;
    opens ru.pio.aclij.documents.financial.document.money to javafx.fxml, org.hibernate.orm.core;
    exports ru.pio.aclij.documents.financial.document.clients;
    opens ru.pio.aclij.documents.financial.document.clients to javafx.fxml, org.hibernate.orm.core;
    exports ru.pio.aclij.documents.config;
    opens ru.pio.aclij.documents.config to javafx.fxml;
    exports ru.pio.aclij.documents.controllers;
    opens ru.pio.aclij.documents.controllers to javafx.fxml;
    exports ru.pio.aclij.documents.config.source;
    opens ru.pio.aclij.documents.config.source to javafx.fxml, com.fasterxml.jackson.databind;
}