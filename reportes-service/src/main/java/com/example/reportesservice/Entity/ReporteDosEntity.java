package com.example.reportesservice.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reporte_dos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDosEntity {

    @Id
    @Column(unique = true,nullable = false)
    private Long id_reportedos;
    private Long id_regrepair;
    private int cantidad_reparacion;
    private int monto_total;
    private LocalDate mes_reparacion;
    private double variacion_reparacion;
    private double variacion_monto;

}
