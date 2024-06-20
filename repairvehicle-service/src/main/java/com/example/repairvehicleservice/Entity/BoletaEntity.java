package com.example.repairvehicleservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "boleta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoletaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id_boleta;
    private String patente;
    private int monto_total;
    private double descuento;
    private double recargo;
    private double iva;
    private double costo_total;
    private LocalDate fecha_salida;
    private LocalTime hora_salida;
    private LocalDate fecha_cliente;
    private LocalTime hora_cliente;
}
