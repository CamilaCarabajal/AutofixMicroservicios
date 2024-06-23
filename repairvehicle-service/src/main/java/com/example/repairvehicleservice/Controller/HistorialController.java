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

    @GetMapping("/historial/listaHistorial")
    public ResponseEntity<List<HistorialEntity>> listaHistorial() {
        List<HistorialEntity> historial = historialService.listaHistorial();

        if (!historial.isEmpty()) {
            return ResponseEntity.ok(historial);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/historial/{id}")
    public ResponseEntity<Void> eliminarHistorial(@PathVariable Long id) {
        try {
            historialService.eliminarHistorial(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
