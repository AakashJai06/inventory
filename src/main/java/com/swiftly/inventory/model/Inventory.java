package com.swiftly.inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String sku;
    private Double price;
    private Double gst;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer minStockThreshold;
    private Integer returnableDays;
    private String warehouseLocation;

    public Inventory() {
    }
}
