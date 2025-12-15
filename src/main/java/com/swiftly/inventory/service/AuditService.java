package com.swiftly.inventory.service;

import com.swiftly.inventory.model.Inventory;
import com.swiftly.inventory.model.InventoryAudit;
import com.swiftly.inventory.repository.AuditRepository;
import com.swiftly.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    AuditRepository auditRepository;

    public void setLogs(String sku, String action, Integer qty, String user) {
        InventoryAudit log = InventoryAudit.builder()
                .sku(sku)
                .actionType(action)
                .quantityChanged(qty)
                .triggeredBy(user)
                .build();
        auditRepository.save(log);
    }

    public String reconcile(String sku, Integer physicalCount) {
        Inventory systemItem = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("SKU Not Found"));
                
        int systemCount = systemItem.getAvailableQuantity() + systemItem.getReservedQuantity();
        
        if (systemCount == physicalCount) {
            return "MATCH: System and Physical counts agree.";
        } else {
            int diff = physicalCount - systemCount;
            setLogs(sku, "AUDIT_CORRECTION", diff, "AUDIT_ADMIN");
            return "MISMATCH DETECTED: System=" + systemCount + " Physical=" + physicalCount;
        }
    }
}
