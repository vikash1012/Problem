package com.olx.inventoryManagementSystem.repository;

import com.olx.inventoryManagementSystem.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryRepository {

    JPAInventoryRepository jpaInventoryRepository;

    @Autowired
    public InventoryRepository(JPAInventoryRepository jpaInventoryRepository) {
        this.jpaInventoryRepository = jpaInventoryRepository;
    }
    public String createInventory(Inventory inventory) {
        Inventory savedInventory = this.jpaInventoryRepository.save(inventory);
        return savedInventory.getSku();
    }
}