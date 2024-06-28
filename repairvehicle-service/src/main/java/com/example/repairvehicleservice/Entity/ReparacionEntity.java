package com.example.repairvehicleservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reparacion")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReparacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id_reparacion;
    private String patente;
    private int tipo_reparacion;
    private LocalDate fecha_reparacion;
    private LocalTime hora_reparacion;
    private int monto_reparacion;
}