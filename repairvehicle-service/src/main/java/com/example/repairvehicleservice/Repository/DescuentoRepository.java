package com.example.repairvehicleservice.Repository;

import com.example.repairvehicleservice.Entity.DescuentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoRepository extends JpaRepository<DescuentoEntity,Long> {
}
