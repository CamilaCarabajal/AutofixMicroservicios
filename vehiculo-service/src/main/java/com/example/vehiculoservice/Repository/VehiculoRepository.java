package com.example.vehiculoservice.Repository;

import com.example.vehiculoservice.Entity.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity,String>{

    @Query("select e from VehiculoEntity e where e.patente = :patente")
    VehiculoEntity findByPatente(@Param("patente")String patente);

    @Transactional
    @Modifying
    @Query("DELETE FROM VehiculoEntity v WHERE v.patente = :patente")
    void deleteByPatente(String patente);
}
