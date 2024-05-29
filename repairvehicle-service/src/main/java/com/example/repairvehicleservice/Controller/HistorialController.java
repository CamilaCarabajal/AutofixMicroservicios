package com.example.repairvehicleservice.Controller;

import com.example.repairvehicleservice.Entity.HistorialEntity;
import com.example.repairvehicleservice.Entity.RegReparacionEntity;
import com.example.repairvehicleservice.Service.HistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public ResponseEntity<HistorialEntity> guardarHistorial(@RequestBody String patente) {
        HistorialEntity nuevoHistorial = historialService.guardarHistorial(patente);
        return ResponseEntity.ok(nuevoHistorial);
    }

    @GetMapping("/listaHistorial")
    public ResponseEntity<List<HistorialEntity>> listaHistorial() {
        List<HistorialEntity> historial = historialService.listaHistorial();

        if (!historial.isEmpty()) {
            return ResponseEntity.ok(historial);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
