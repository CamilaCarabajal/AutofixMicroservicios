package com.example.repairvehicleservice.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recargo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecargoEntity {

    @Id
    @Column(unique = true,nullable = false)
    private Long id_recargo;
    private Long id_reparacion;
    private String patente;
    private double monto_total;
}
