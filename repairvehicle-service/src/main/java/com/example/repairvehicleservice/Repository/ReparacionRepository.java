package com.example.repairvehicleservice.Repository;

import com.example.repairvehicleservice.Entity.ReparacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReparacionRepository extends JpaRepository<ReparacionEntity, Long> {
}
