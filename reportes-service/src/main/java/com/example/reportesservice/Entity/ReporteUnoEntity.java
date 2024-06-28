package com.example.reportesservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reporte_uno")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteUnoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_reporteuno;
    private String patente;
    private String modelo;
    private int tipo_reparacion;
    private int costo_reparacion;
    private int conteo_reparaciones;  // Nuevo campo para el conteo de reparaciones
}