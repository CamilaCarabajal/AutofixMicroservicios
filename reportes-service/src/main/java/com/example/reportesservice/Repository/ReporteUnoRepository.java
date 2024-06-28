package com.example.reportesservice.Repository;

import com.example.reportesservice.Entity.ReporteUnoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteUnoRepository extends JpaRepository<ReporteUnoEntity,Long> {
}
