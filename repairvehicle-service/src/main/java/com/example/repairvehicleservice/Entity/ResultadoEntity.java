package com.example.repairvehicleservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resultado")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_resultado;
    private String patente;
    private String modelo;
    private int tipo_reparacion;
    private int costo_reparacion;
    private int conteo_reparaciones;  // Nuevo campo para el conteo de reparaciones
}
