package com.example.repairvehicleservice.Controller;

import com.example.repairvehicleservice.Entity.RegReparacionEntity;
import com.example.repairvehicleservice.Service.HistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HistorialController {

    @Autowired
    HistorialService historialService;
    @GetMapping("/vehiculo/{patente}")
    public ResponseEntity<List<RegReparacionEntity>> listarReparacionesPorPatente(@PathVariable String patente) {
        List<RegReparacionEntity> reparaciones = historialService.listarReparacionesPorPatente(patente);

        if (!reparaciones.isEmpty()) {
            return ResponseEntity.ok(reparaciones);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
