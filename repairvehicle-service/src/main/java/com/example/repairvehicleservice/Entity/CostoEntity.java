package com.example.repairvehicleservice.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "costo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostoEntity {

    @Id
    @Column(unique = true,nullable = false)
    private Long id_costo;
    private Long id_descuento;
    private Long id_recargo;
    private double iva;
    private double costo_total;
}
