package com.swiftly.inventory.model;

import com.swiftly.inventory.entity.ShipmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trackingId;
    private Integer orderItemId;
    private String sku;
    private  Integer quantity;
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
    private LocalDateTime dispatchedAt;

}
