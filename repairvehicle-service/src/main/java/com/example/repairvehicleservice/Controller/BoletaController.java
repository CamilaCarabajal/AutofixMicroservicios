package com.example.repairvehicleservice.Controller;

import com.example.repairvehicleservice.Entity.BoletaEntity;
import com.example.repairvehicleservice.Service.BoletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RequestMapping("/repairvehicle")
@RestController

public class BoletaController {
    @Autowired
    BoletaService boletaService;
    @PostMapping("/boleta/vehiculo/{patente}")
    public ResponseEntity<BoletaEntity> generarBoleta(
            @PathVariable String patente,
            @RequestParam(name = "fechaReparacion") LocalDate fechaReparacion,
            @RequestParam(name = "fechaCliente") LocalDate fechaCliente,
            @RequestParam(name = "horaReparacion") LocalTime horaReparacion,
            @RequestParam(name = "horaCliente") LocalTime horaCliente
    ) {
        // Aquí puedes validar los parámetros según tus necesidades
        if (fechaReparacion == null || fechaCliente == null || horaReparacion == null || horaCliente == null) {
            throw new IllegalArgumentException("Faltan parámetros obligatorios.");
        }

        BoletaEntity boleta = boletaService.generarBoleta(patente, fechaReparacion, fechaCliente, horaReparacion, horaCliente);
        return ResponseEntity.ok(boleta);
    }


    @DeleteMapping("/boleta/eliminar/{id}")
    public ResponseEntity<String> eliminarBoleta(@PathVariable Long id) {
        boolean eliminado = boletaService.eliminarBoleta(id);
        if (eliminado) {
            return ResponseEntity.ok("Boleta eliminada exitosamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Boleta no encontrada");
    }

    @GetMapping("/boleta/{id}")
    public BoletaEntity obtenerBoletaPorId(@PathVariable Long id) {
        return boletaService.obtenerBoletaPorId(id);
    }
}
