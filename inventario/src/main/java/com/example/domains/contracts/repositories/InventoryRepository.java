package com.example.domains.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domains.core.contracts.repositories.RepositoryWithProjections;
import com.example.domains.entities.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer>, RepositoryWithProjections {
}
