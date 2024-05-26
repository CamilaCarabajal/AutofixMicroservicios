package com.example.repairvehicleservice.Repository;

import com.example.repairvehicleservice.Entity.RecargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecargoRepository extends JpaRepository<RecargoEntity,Long> {
}
