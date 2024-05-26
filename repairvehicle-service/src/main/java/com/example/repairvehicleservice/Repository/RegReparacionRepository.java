package com.example.repairvehicleservice.Repository;

import com.example.repairvehicleservice.Entity.RegReparacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegReparacionRepository extends JpaRepository<RegReparacionEntity,Long> {
}
