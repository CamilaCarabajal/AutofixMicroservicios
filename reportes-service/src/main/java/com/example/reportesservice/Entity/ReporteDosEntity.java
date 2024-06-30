package com.example.reportesservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reporte_dos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_reportedos;
    private int tipo_reparacion;
    private int cantidad_reparaciones;
    private int monto_total_reparaciones;
    private int mes;
    private int ano;
    private double variacion_cantidad;
    private double variacion_monto;
}