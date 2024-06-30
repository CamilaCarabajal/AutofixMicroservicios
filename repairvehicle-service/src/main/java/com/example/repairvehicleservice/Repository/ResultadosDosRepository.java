package com.example.repairvehicleservice.Repository;

import com.example.repairvehicleservice.Entity.ResultadosDosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadosDosRepository extends JpaRepository<ResultadosDosEntity,Long> {
}
