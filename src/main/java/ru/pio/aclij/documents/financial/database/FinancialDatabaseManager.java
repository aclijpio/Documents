package ru.pio.aclij.documents.financial.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import ru.pio.aclij.documents.financial.document.Document;
import ru.pio.aclij.documents.financial.document.clients.User;
import ru.pio.aclij.documents.financial.document.money.Currency;
import ru.pio.aclij.documents.financial.document.money.CurrencyCode;

import java.util.List;

public class FinancialDatabaseManager extends AbstractDatabaseManager<Document> {
    public FinancialDatabaseManager(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }
    public List<? extends Document> findAllDocuments(){
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        TypedQuery<? extends Document> query = entityManager.createQuery(
                "SELECT d FROM Document d WHERE TYPE(d) IN (Invoice, Payment, PaymentRequest)", Document.class);
        List<? extends Document> documents = query.getResultList();
        entityManager.close();
        return documents;
    }

    public void deleteDocumentById(Long id ){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Document document = entityManager.find(Document.class, id);
        if (document != null) {
            entityManager.remove(document);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Currency findCurrencyByCurrencyCode(CurrencyCode code){
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(
                "SELECT c FROM Currency c WHERE c.currencyCode = :currencyCodeValue"
        );
        query.setParameter("currencyCodeValue", code);
        Currency currency = (Currency) query.getSingleResult();
        query.getSingleResult();
        return currency;
    }
    public List<User> findAllUsers(){
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u",
                User.class
        );
        List<User> users = query.getResultList();
        entityManager.close();
        return users;
    }
    public <T> User findUserByUsername(String username){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username"
        );
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }
}
