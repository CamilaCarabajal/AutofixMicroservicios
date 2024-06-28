package com.example.repairvehicleservice.Repository;

import com.example.repairvehicleservice.Entity.ResultadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoRepository extends JpaRepository<ResultadoEntity,Long> {
}
