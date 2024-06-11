package com.example.reparacionservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "reparacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReparacionEntity {
    @Id
    @Column(unique = true,nullable = false)
    private Long id_reparacion;
    private int tipo_reparacion;
    private String tipo_motor;
    private int monto_reparacion;
}
