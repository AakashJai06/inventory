package com.swiftly.inventory.service;

import com.swiftly.inventory.model.Inventory;
import com.swiftly.inventory.model.RestockRequest;
import com.swiftly.inventory.repository.InventoryRepository;
import com.swiftly.inventory.repository.RestockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RestockService {

    @Autowired
    RestockRepository restockRepository;
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    AuditService auditService; // Uses your existing audit service

    // 1. Called automatically when stock is low
    public void createAutoRequest(String sku, int quantityNeeded) {
        RestockRequest req = RestockRequest.builder()
                .sku(sku)
                .requestedQuantity(quantityNeeded)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        restockRepository.save(req);
    }

    // 2. Get list for Admin
    public List<RestockRequest> getPendingRequests() {
        return restockRepository.findByStatus("PENDING");
    }

    // 3. Admin clicks "Approve"
    public void approveRequest(Long id) {
        RestockRequest req = restockRepository.findById(id).orElseThrow();
        req.setStatus("ORDERED");
        restockRepository.save(req);
    }

    // 4. Items arrive at warehouse
    public void receiveItems(Long id) {
        RestockRequest req = restockRepository.findById(id).orElseThrow();
        
        if (!"ORDERED".equals(req.getStatus())) {
            throw new RuntimeException("Request must be ORDERED before receiving");
        }

        // Update Inventory
        Inventory item = inventoryRepository.findBySku(req.getSku())
                .orElseThrow(() -> new RuntimeException("SKU not found"));
        
        item.setAvailableQuantity(item.getAvailableQuantity() + req.getRequestedQuantity());
        inventoryRepository.save(item);

        // Update Request Status
        req.setStatus("RECEIVED");
        restockRepository.save(req);

        // Log it
        auditService.setLogs(req.getSku(), "RESTOCK_RECEIVED", req.getRequestedQuantity(), "WAREHOUSE_ADMIN");
    }
}
