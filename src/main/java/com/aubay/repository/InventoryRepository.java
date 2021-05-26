package com.aubay.repository;

import com.aubay.entity.Inventory;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    Optional<Inventory> findByProduct(@NotNull String product);

    Inventory save(@NotBlank String product, @NotNull Long quantity);

    void delete(@NotNull String product);

    List<Inventory> findAll();

    int update(@NotBlank String product, @NotNull Long quantity);

    @Transactional
    Boolean reserve(String product, Long quantity);
}
