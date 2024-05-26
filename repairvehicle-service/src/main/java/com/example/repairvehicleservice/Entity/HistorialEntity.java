package com.example.repairvehicleservice.Entity;

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
@Table(name = "historial")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class HistorialEntity {

    @Id
    @Column(unique = true,nullable = false)
    private Long id_historial;
    private Long id_reparacion;
    private String patente;
    private Long id_descuento;
    private Long id_recargo;
    private Long id_costo;
    private LocalDate fecha_ingreso;
    private LocalTime hora_ingreso;
    private int monto_total;
    private double monto_recargo;
    private double monto_descuento;
    private double monto_iva;
    private double costo_total;
    private LocalDate fecha_salida;
    private LocalTime hora_salida;
    private LocalDate fecha_cliente;
    private LocalTime hora_cliente;
}
