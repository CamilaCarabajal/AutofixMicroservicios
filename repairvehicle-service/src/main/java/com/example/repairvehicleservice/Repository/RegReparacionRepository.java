package com.example.repairvehicleservice.Repository;

import com.example.repairvehicleservice.Entity.RegReparacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RegReparacionRepository extends JpaRepository<RegReparacionEntity,Long> {

    @Query("SELECT r FROM RegReparacionEntity r WHERE r.patente = :patente")
    List<RegReparacionEntity> findListByPatente(@Param("patente") String patente);

    @Modifying
    @Transactional
    @Query("DELETE FROM RegReparacionEntity r WHERE r.patente = :patente")
    void deleteByPatente(@Param("patente") String patente);
}
