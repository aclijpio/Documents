package ru.pio.aclij.documents.financial.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import ru.pio.aclij.documents.financial.document.Document;

import java.util.List;

public class FinancialDatabaseManager extends AbstractDatabaseManager<Document> {
    public FinancialDatabaseManager(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }
    public List<? extends Document> findAll(){
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        TypedQuery<? extends Document> query = entityManager.createQuery(
                "SELECT d FROM Document d WHERE TYPE(d) IN (Invoice, Payment, PaymentRequest)", Document.class);
        List<? extends Document> documents = query.getResultList();
        entityManager.close();

        return documents;
    }

}
