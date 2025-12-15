package com.swiftly.inventory.controller;

import com.swiftly.inventory.model.Inventory;
import com.swiftly.inventory.model.InventoryAudit;
import com.swiftly.inventory.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    @Autowired
    AuditService auditService;

    @PostMapping("/reconcile")
    public String reconcile(@RequestParam String sku,@RequestParam Integer physicalCount){
        return auditService.reconcile(sku,physicalCount);
    }

}
