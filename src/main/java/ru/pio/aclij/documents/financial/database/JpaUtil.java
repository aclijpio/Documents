package ru.pio.aclij.documents.financial.database;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceProvider;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import ru.pio.aclij.documents.config.ConfigLoader;
import ru.pio.aclij.documents.config.Datasource;

import java.util.Properties;

public class JpaUtil {

    private static final String PERSISTENCE_UNIT_NAME = "unit";
    private static EntityManagerFactory entityManagerFactory;


    public static EntityManagerFactory getEntityManagerFactory(Datasource datasource) {
        if (entityManagerFactory == null) {
            PersistenceProvider persistenceProvider = new HibernatePersistenceProvider();
            entityManagerFactory = persistenceProvider
                    .createEntityManagerFactory(PERSISTENCE_UNIT_NAME, getConfig(datasource));
        }
        return entityManagerFactory;
    }

    private static Properties getConfig(Datasource datasource) {
        Properties properties = new Properties();

        properties.put(AvailableSettings.JAKARTA_JDBC_DRIVER, datasource.getDriver());
        properties.put(AvailableSettings.JAKARTA_JDBC_URL, datasource.getUrl());
        properties.put(AvailableSettings.JAKARTA_JDBC_USER, datasource.getUsername());
        properties.put(AvailableSettings.JAKARTA_JDBC_PASSWORD, datasource.getPassword());
        return properties;
    }
}
