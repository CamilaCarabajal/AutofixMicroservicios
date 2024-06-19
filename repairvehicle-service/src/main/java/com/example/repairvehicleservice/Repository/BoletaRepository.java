package com.example.repairvehicleservice.Repository;

import com.example.repairvehicleservice.Entity.BoletaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface BoletaRepository extends JpaRepository<BoletaEntity,Long> {
    @Query("SELECT b FROM BoletaEntity b WHERE b.patente = :patente ORDER BY b.id_boleta DESC")
    BoletaEntity findTopByPatenteOrderByIdBoletaDesc(String patente);
}
