package com.example.reportesservice.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "reg_reparacion")
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

