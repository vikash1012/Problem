package com.olx.inventoryManagementSystem.repository;

import com.olx.inventoryManagementSystem.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAInventoryRepository extends JpaRepository<Inventory, Long> {

}
