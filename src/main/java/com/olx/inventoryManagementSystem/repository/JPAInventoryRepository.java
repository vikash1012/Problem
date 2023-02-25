package com.olx.inventoryManagementSystem.repository;

import com.olx.inventoryManagementSystem.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JPAInventoryRepository extends JpaRepository<Inventory, Integer>  {
    @Query("select t from Inventory t where t.sku=:sku")
    Optional<Inventory> findById(String sku);

}
