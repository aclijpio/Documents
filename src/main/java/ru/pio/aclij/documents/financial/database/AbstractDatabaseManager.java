package ru.pio.aclij.documents.financial.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Table;
import jakarta.persistence.TypedQuery;
import ru.pio.aclij.documents.financial.document.Document;

import java.util.List;

public abstract class AbstractDatabaseManager<T> {
    public final EntityManagerFactory entityManagerFactory;

    public AbstractDatabaseManager(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public String getTableName(Class<? extends T> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        return table != null ? table.name() : clazz.getSimpleName();
    }

    public Long save(T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
        return ((Document) entity).getId();
    }

    public T findById(Class<? extends T> clazz, Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        T entity = entityManager.find(clazz, id);
        entityManager.close();
        return entity;
    }
    public Long update(T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        T mergedEntity = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
        return ((Document) mergedEntity).getId();
    }

    public void delete(T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}