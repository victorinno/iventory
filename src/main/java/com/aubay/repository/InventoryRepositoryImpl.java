package com.aubay.repository;

import com.aubay.entity.Inventory;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.TransactionalAdvice;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Singleton
public class InventoryRepositoryImpl implements InventoryRepository {

    private final EntityManager entityManager;

    public InventoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @ReadOnly
    public Optional<Inventory> findByProduct(String product) {
        TypedQuery<Inventory> query = entityManager.createQuery("select i from Inventory i where i.product = :product", Inventory.class)
                .setParameter("product", product);
        Inventory inventory = query.getSingleResult();
        return Optional.ofNullable(inventory);
    }

    @Override
    @TransactionalAdvice
    public Inventory save(String product, Long quantity) {
        Inventory inventory = new Inventory(product, quantity);
        entityManager.persist(inventory);
        return inventory;
    }

    @Override
    @TransactionalAdvice
    public void delete(String product) {
        findByProduct(product).ifPresent(entityManager::remove);
    }

    @Override
    @ReadOnly
    public List<Inventory> findAll() {
        TypedQuery<Inventory> query = entityManager.createQuery("select i from Inventory i", Inventory.class);
        return query.getResultList();
    }

    @Override
    @TransactionalAdvice
    public int update(String product, Long quantity) {
        return entityManager.createQuery("update Inventory i set i.quantity = :quantity where i.product = :product")
                .setParameter("product", product)
                .setParameter("quantity", quantity).executeUpdate();
    }

    @Override
    @TransactionalAdvice
    public Boolean reserve(String product, Long quantity) {
        entityManager.createQuery("update Inventory i set i.quantity = i.quantity - :quantity where i.product = :product")
                .setParameter("product", product)
                .setParameter("quantity", quantity).executeUpdate();
        Optional<Inventory> byProduct = findByProduct(product);
        Boolean aBoolean = byProduct.map(Inventory::getQuantity).map(q -> q >= 0).orElse(false);
        if (!aBoolean) {
            entityManager.createQuery("update Inventory i set i.quantity = i.quantity + :quantity where i.product = :product")
                    .setParameter("product", product)
                    .setParameter("quantity", quantity).executeUpdate();
        }
        return aBoolean;
    }


}
