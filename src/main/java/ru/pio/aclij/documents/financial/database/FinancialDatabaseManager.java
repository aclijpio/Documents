package ru.pio.aclij.documents.financial.database;

import jakarta.persistence.*;
import ru.pio.aclij.documents.financial.documents.Document;
import ru.pio.aclij.documents.financial.documents.money.Currency;
import ru.pio.aclij.documents.financial.documents.money.CurrencyCode;

import java.util.List;
import java.util.Optional;


public class FinancialDatabaseManager extends AbstractDatabaseManager {
    public FinancialDatabaseManager(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }
    public List<? extends Document> findAllDocuments(){
        try(EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            TypedQuery<? extends Document> query = entityManager.createQuery(
                    "SELECT d FROM Document d WHERE TYPE(d) IN (Invoice, Payment, PaymentRequest)",
                    Document.class);
            return query.getResultList();
        }
    }
    public boolean checkUniqueNumber(String number){
        try(EntityManager entityManager = this.entityManagerFactory.createEntityManager()){
            TypedQuery<? extends Document> query = entityManager.createQuery(
                    "SELECT d FROM Document d WHERE TYPE(d) IN (Invoice, Payment, PaymentRequest) AND d.number = :currentNumber",
                    Document.class);
            query.setParameter("currentNumber", number);
            return query.getSingleResult() == null;

        } catch (NoResultException e){
          return true;
        }
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

    public Optional<Currency> findCurrencyByCurrencyCode(CurrencyCode code){
        try(EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            Query query = entityManager.createQuery(
                    "SELECT c FROM Currency c WHERE c.currencyCode = :currencyCodeValue"
            );
            query.setParameter("currencyCodeValue", code);
            Currency currency = (Currency) query.getSingleResult();
            query.getSingleResult();
            return Optional.of(currency);
        }catch (NoResultException e){
            return Optional.empty();
        }

    }
}
