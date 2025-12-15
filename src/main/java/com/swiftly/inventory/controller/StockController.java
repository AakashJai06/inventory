package com.swiftly.inventory.controller;

import com.swiftly.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory") 
public class StockController {
    @Autowired
    InventoryService inventoryService;

    @PostMapping("/reserve/{sku}")
    public String reserveItem(@PathVariable String sku, @RequestParam int qty){
        inventoryService.reserveItem(sku, qty);
        return "Stock Reserved";
    }

    @PostMapping("/add/{sku}")
    public String addStock(@PathVariable String sku, @RequestParam int qty){
        inventoryService.addStock(sku, qty);
        return "Stock Added";
    }
}
