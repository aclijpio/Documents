package ru.pio.aclij.documents.financial.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDatabaseManager {
    public final EntityManagerFactory entityManagerFactory;

    public AbstractDatabaseManager(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private <T> String getTableName(Class<? extends T> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        return table != null ? table.name() : clazz.getSimpleName();
    }

    public <T> T save(T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
        return entity;
    }

    public <T> T findById(Class<? extends T> clazz, Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        T entity = entityManager.find(clazz, id);
        entityManager.close();
        return entity;
    }
    public <T> T update(T entity) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            T mergedEntity = entityManager.merge(entity);
            entityManager.getTransaction().commit();
            return mergedEntity;
        }
    }

    public <T> void delete(T entity) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
            entityManager.getTransaction().commit();
        }
    }
    public <T> Optional<T> findByName(Class<T> clazz, String name) {

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(clazz);
            Root<T> root = cq.from(clazz);
            cq.select(root).where(cb.equal(root.get("name"), name));

            return Optional.of(entityManager.createQuery(cq).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public <T> List<T> findAllByClass(Class<T> clazz){

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(clazz);
            Root<T> root = cq.from(clazz);
            cq.select(root);

            return entityManager.createQuery(cq).getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }
}