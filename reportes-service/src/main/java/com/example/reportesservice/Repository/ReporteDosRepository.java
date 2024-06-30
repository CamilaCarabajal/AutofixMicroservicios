package com.example.reportesservice.Repository;

import com.example.reportesservice.Entity.ReporteDosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteDosRepository extends JpaRepository<ReporteDosEntity,Long> {
}
