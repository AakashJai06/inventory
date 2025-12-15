package com.swiftly.inventory.service;

import com.swiftly.inventory.model.Inventory;
import com.swiftly.inventory.model.RestockRequest;
import com.swiftly.inventory.repository.InventoryRepository;
import com.swiftly.inventory.repository.RestockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    RestockRepository restockRepository;
    @Autowired
    AuditService auditService;

    public void reserveItem(String sku, Integer qty) {
        Inventory item = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("SKU not found: " + sku));

        // Fix: Use >= to allow buying the last item
        if (item.getAvailableQuantity() - qty >= 0) {
            item.setAvailableQuantity(item.getAvailableQuantity() - qty);
            item.setReservedQuantity(item.getReservedQuantity() + qty);
            inventoryRepository.save(item);
            
            auditService.setLogs(sku, "RESERVE", -qty, "SYSTEM");
            
            // Check if we need to restock now
            checkAndTriggerRestock(item);
        } else {
            throw new RuntimeException("Item out of stock");
        }
    }

    // New Logic: Create a request, don't just add stock magically
    private void checkAndTriggerRestock(Inventory item) {
        if (item.getAvailableQuantity() < item.getMinStockThreshold()) {
            RestockRequest request = RestockRequest.builder()
                    .sku(item.getSku())
                    .requestedQuantity(item.getMinStockThreshold() * 2) // Simple prediction
                    .status("PENDING")
                    .createdAt(LocalDateTime.now())
                    .build();
            restockRepository.save(request);
            System.out.println("Restock Request Created for " + item.getSku());
        }
    }

    public void addStock(String sku, int qty) {
        Inventory item = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("SKU not found"));
        
        item.setAvailableQuantity(item.getAvailableQuantity() + qty);
        inventoryRepository.save(item);
        auditService.setLogs(sku, "NEWLY_ADDED", qty, "ADMIN");
    }
}
