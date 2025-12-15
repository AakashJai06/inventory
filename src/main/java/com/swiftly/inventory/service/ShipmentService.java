package com.swiftly.inventory.service;

import com.swiftly.inventory.entity.ShipmentStatus;
import com.swiftly.inventory.model.Shipment;
import com.swiftly.inventory.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShipmentService {
    @Autowired
    ShipmentRepository shipmentRepository;

    public void createShipment(int orderItemId,String sku,int qty){
        Shipment shipment = new Shipment();
        shipment.setStatus(ShipmentStatus.PENDING);
        shipment.setQuantity(qty);
        shipment.setSku(sku);
        shipment.setOrderItemId(orderItemId);
        shipment.setDispatchedAt(LocalDateTime.now());
        shipmentRepository.save(shipment);
    }

    public List<Shipment> getPickingList() {
        return shipmentRepository.findByStatus(ShipmentStatus.PICKING);
    }

    public void packShipment(Integer id) {
        Shipment shipment = shipmentRepository.findById(id).orElseThrow();
        if(shipment.getStatus() != ShipmentStatus.PENDING){
            throw new RuntimeException("Item not in PENDING");
        }
        shipment.setStatus(ShipmentStatus.PACKED);
        shipmentRepository.save(shipment);
    }

    public void dispatchShipment(Integer id) {
        Shipment shipment = shipmentRepository.findById(id).orElseThrow();
        if(shipment.getStatus() != ShipmentStatus.PACKED){
            throw new RuntimeException("Item not yet PACKED");
        }
        shipment.setDispatchedAt(LocalDateTime.now());
        shipment.setStatus(ShipmentStatus.DISPATCHED);
        shipmentRepository.save(shipment);
    }
}
