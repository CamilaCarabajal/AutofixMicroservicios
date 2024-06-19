package com.example.repairvehicleservice.Controller;

import com.example.repairvehicleservice.Entity.HistorialEntity;
import com.example.repairvehicleservice.Service.HistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/repairvehicle")
@RestController
public class HistorialController {

    @Autowired
    HistorialService historialService;
    @PostMapping("/historial/vehiculo/{patente}")
    public ResponseEntity<HistorialEntity> guardarHistorial(@PathVariable String patente) {
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
