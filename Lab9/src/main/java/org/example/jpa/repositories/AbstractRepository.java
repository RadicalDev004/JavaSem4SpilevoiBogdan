package org.example.jpa.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public abstract class AbstractRepository<T, ID> {

    protected final EntityManager em;
    private final Class<T> entityClass;

    protected AbstractRepository(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(entity);
        tx.commit();
    }

    public T findById(ID id) {
        return em.find(entityClass, id);
    }

    public void delete(T entity) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        tx.commit();
    }

    public List<T> findAll() {
        return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    public void update(T entity) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(entity);
        tx.commit();
    }

    public void close() {
        em.close();
    }
}
