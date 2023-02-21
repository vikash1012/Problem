package com.olx.inventoryManagementSystem.repository;

import com.olx.inventoryManagementSystem.exceptions.InventoryNotFoundException;
import com.olx.inventoryManagementSystem.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    public Inventory findInventory(String sku) throws InventoryNotFoundException {
        //Integer InventoryId = Integer.parseInt(id);

        Optional<Inventory> optionalCar = this.jpaInventoryRepository.findById(sku);
        if (optionalCar.isEmpty()) {
            throw new InventoryNotFoundException("Inventory not found for sku - " + sku);
        }
        return optionalCar.get();
    }

    public Page<Inventory> fetchInventories(Pageable pageable) {
        return this.jpaInventoryRepository.findAll(pageable);
    }
}
