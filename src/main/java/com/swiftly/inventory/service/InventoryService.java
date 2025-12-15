package com.swiftly.inventory.service;

import com.swiftly.inventory.model.Inventory;
import com.swiftly.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepostory;
    @Autowired
    AuditService auditService;

    public void reserveItem(String sku,Integer qty) {
        Inventory item = inventoryRepostory.findBySku(sku);
        if (item.getAvailableQuantity() - qty> 0) {
            item.setAvailableQuantity(item.getAvailableQuantity() - qty);
            item.setReservedQuantity(item.getReservedQuantity() + qty);
            inventoryRepostory.save(item);
            restockItem(sku,item);
            auditService.setLogs(sku,"RESERVE",qty);
        } else {
            throw new RuntimeException("Item out of stock");
        }
    }

    public void restockItem(String sku,Inventory item) {
        if(item.getAvailableQuantity() < 5)
            item.setAvailableQuantity(item.getAvailableQuantity()+10);
            inventoryRepostory.save(item);
            auditService.setLogs(sku,"STOCK",10);
    }

    public void addStock(String sku, int qty) {
        Inventory item = inventoryRepostory.findBySku(sku);
        item.setAvailableQuantity(item.getAvailableQuantity()+qty);
        inventoryRepostory.save(item);
        auditService.setLogs(sku,"NEWLY ADDED",qty);
    }
}
