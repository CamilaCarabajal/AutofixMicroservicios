package com.example.repairvehicleservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resultados_dos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadosDosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private int tipo_reparacion;
    private int cantidad_reparaciones;
    private int monto_total_reparaciones;
    private int mes;
    private int ano;
    private double variacion_cantidad;
    private double variacion_monto;
}