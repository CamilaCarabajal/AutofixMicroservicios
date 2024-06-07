package com.example.reportesservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "regReparacion")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegReparacionModel {

    @Id
    @Column(unique = true,nullable = false)
    private Long id_regrepair;
    private String patente;
    private int tipo_reparacion;
    private LocalDate fecha_reparacion;
    private LocalTime hora_reparacion;
    private int monto_reparacion;
}