package com.swiftly.inventory.repository;

import com.swiftly.inventory.model.InventoryAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<InventoryAudit,Integer>{
}
