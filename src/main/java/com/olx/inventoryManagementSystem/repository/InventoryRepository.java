package com.olx.inventoryManagementSystem.repository;

import com.olx.inventoryManagementSystem.exceptions.InventoryNotFoundException;
import com.olx.inventoryManagementSystem.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public class InventoryRepository {
    JPAInventoryRepository jpaInventoryRepository;

    @Autowired
    public InventoryRepository(JPAInventoryRepository jpaInventoryRepository) {
        this.jpaInventoryRepository = jpaInventoryRepository;
    }

    // TODO: rename methods, remove suffixes.
    public String createInventory(Inventory inventory) {
        return this.jpaInventoryRepository.save(inventory).getSku();
    }

    public Inventory findInventory(String sku) throws InventoryNotFoundException {
        Optional<Inventory> optionalInventory = this.jpaInventoryRepository.findById(sku);
        if (optionalInventory.isEmpty()) {
            throw new InventoryNotFoundException("Inventory not found for sku - " + sku);
        }
        return optionalInventory.get();
    }

    public Page<Inventory> fetchInventories(Pageable pageable) {
        return this.jpaInventoryRepository.findAll(pageable);
    }

    public void saveInventory(Inventory inventory) {
        this.jpaInventoryRepository.save(inventory);
    }
}
