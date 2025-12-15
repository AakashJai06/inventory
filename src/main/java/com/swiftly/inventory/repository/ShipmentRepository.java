package com.swiftly.inventory.repository;

import com.swiftly.inventory.entity.ShipmentStatus;
import com.swiftly.inventory.model.Shipment;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment,Integer> {
    List<Shipment> findByStatus(ShipmentStatus status);
}
