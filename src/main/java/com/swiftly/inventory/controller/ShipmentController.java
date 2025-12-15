package com.swiftly.inventory.controller;

import com.swiftly.inventory.model.Shipment;
import com.swiftly.inventory.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fulfillment")
@RequiredArgsConstructor
public class ShipmentController {
    @Autowired
    ShipmentService shipmentService;

    @GetMapping("/picking-list")
    public List<Shipment> getPickingList(){
        return shipmentService.getPickingList();
    }
    @PostMapping("/pack/{id}")
    public String packItem(@PathVariable Integer id){
        shipmentService.packShipment(id);
        return "ITEM PACKED";
    }
    @PostMapping("/dispatch/{id}")
    public String dispatchItem(@PathVariable Integer id){
        shipmentService.dispatchShipment(id);
        return "ITEM PACKED";
    }
    @PostMapping("/create")
    public String createShipmentRecord(@RequestParam int orderItemId, @RequestParam String sku, @RequestParam int qty){
        shipmentService.createShipment(orderItemId,sku,qty);
        return "Shipment Record Created";
    }

}
