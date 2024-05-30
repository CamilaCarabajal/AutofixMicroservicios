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
@Table(name = "reporte_uno")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteUnoEntity {
    @Id
    @Column(unique = true,nullable = false)
    private Long id_reporteuno;
    private Long id_regrepair;
    private String patente;
    private int tipo_reparacion;
    private String tipo_modelo;
    private int monto_total;
    private int reparacion_vehiculo;
    private LocalDate fecha_reporte;
}
