package com.swiftly.inventory.controller;

import com.swiftly.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class StockController {
    @Autowired
    InventoryService inventoryService;
    @PostMapping("/reserve/{sku}")
    public void reserveItem(@PathVariable String sku, @RequestParam int qty){
        inventoryService.reserveItem(sku,qty);
    }
    @PostMapping("/add/{sku}")
    public  void addStock(@PathVariable String sku,@RequestParam int qty){
        inventoryService.addStock(sku,qty);
    }
}
