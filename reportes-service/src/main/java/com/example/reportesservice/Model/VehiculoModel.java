package com.example.reportesservice.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehiculo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoModel {

    @Id
    @Column(unique = true,nullable = false)
    private String patente;
    private String marca;
    private String modelo;
    private int ano_fabricacion;
    private String motor;
    private int asientos;
    private int kilometraje;
    private int cantidad_reparaciones;
}
