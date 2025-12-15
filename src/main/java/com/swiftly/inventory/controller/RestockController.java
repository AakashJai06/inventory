package com.swiftly.inventory.controller;

import com.swiftly.inventory.model.RestockRequest;
import com.swiftly.inventory.service.RestockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restock")
public class RestockController {

    @Autowired
    RestockService restockService;

    // 1. View all alerts (What do we need to buy?)
    @GetMapping("/alerts")
    public List<RestockRequest> getPendingRequests() {
        return restockService.getPendingRequests();
    }

    // 2. Admin approves the order (Send to Supplier)
    @PostMapping("/approve/{id}")
    public String approveRequest(@PathVariable Long id) {
        restockService.approveRequest(id);
        return "Request Approved & Sent to Supplier";
    }

    // 3. Truck arrives with items (Update Inventory)
    @PostMapping("/receive/{id}")
    public String receiveItems(@PathVariable Long id) {
        restockService.receiveItems(id);
        return "Items Received & Inventory Updated";
    }
}
