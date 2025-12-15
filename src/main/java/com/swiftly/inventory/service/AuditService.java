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
    InventoryRepository inventoryRepostory;
    @Autowired
    AuditRepository auditRepostory;
    public void setLogs(String sku, String action,Integer numOfChanges) {
        InventoryAudit inventoryAudit = new InventoryAudit();
        inventoryAudit.setSku(sku);
        inventoryAudit.setActionType(action);
        inventoryAudit.setQuantityChanged(numOfChanges);
        auditRepostory.save(inventoryAudit);
    }

    public String reconcile(String sku, Integer physicalCount) {
        Inventory systemItem = inventoryRepostory.findBySku(sku);
        int systemCount = systemItem.getAvailableQuantity() + systemItem.getReservedQuantity();
        if(systemCount == physicalCount)    return "MATCH: SYSTEM AND PHYSICAL COUNTS AGREE";
        else{
            int diff = physicalCount - systemCount;
            setLogs(sku,"AUDIT_CORRECTION",diff);
            return  "MISMATCHED DETECTED: System : "+systemCount+" Physical : "+physicalCount;
        }
    }
}
