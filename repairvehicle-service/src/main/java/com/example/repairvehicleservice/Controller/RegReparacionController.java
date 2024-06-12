package com.example.repairvehicleservice.Controller;

import com.example.repairvehicleservice.Entity.RegReparacionEntity;
import com.example.repairvehicleservice.Service.RegReparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/repairvehicle")
@RestController
public class RegReparacionController {
    @Autowired
    RegReparacionService regReparacionService;

    @PostMapping("/regrepair")
    public ResponseEntity<RegReparacionEntity> crearReparacion(@RequestBody RegReparacionEntity reparacion) {
        RegReparacionEntity nuevaReparacion = regReparacionService.crearReparacion(reparacion);
        return ResponseEntity.ok(nuevaReparacion);
    }

    @GetMapping("/regrepair/{id}")
    public ResponseEntity<RegReparacionEntity> obtenerReparacionPorId(@PathVariable Long id) {
        RegReparacionEntity reparacion = regReparacionService.obtenerReparacionPorId(id);
        if (reparacion != null) {
            return ResponseEntity.ok(reparacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/regrepair/{id}")
    public ResponseEntity<RegReparacionEntity> actualizarReparacion(@PathVariable Long id, @RequestBody RegReparacionEntity reparacion) {
        RegReparacionEntity reparacionActualizada = regReparacionService.actualizarReparacion(id, reparacion);
        if (reparacionActualizada != null) {
            return ResponseEntity.ok(reparacionActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/regrepair/{id}")
    public ResponseEntity<Void> eliminarReparacion(@PathVariable Long id) {
        regReparacionService.eliminarReparacion(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/regrepair/vehiculo/{patente}")
    public ResponseEntity<RegReparacionEntity> crearReparacionVehiculo(@PathVariable String patente) {
        RegReparacionEntity nuevaReparacion = regReparacionService.crearReparacionVehiculo(patente);
        if (nuevaReparacion != null) {
            return ResponseEntity.ok(nuevaReparacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/regrepair/lista")
    public ResponseEntity<List<RegReparacionEntity>> listarReparaciones() {
        List<RegReparacionEntity> reparaciones = regReparacionService.listarReparaciones();

        if (!reparaciones.isEmpty()) {
            return ResponseEntity.ok(reparaciones);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/regrepair/{patente}")
    public List<RegReparacionEntity> obtenerReparacionPorPatente(@PathVariable String patente) {
        return regReparacionService.obtenerReparacionesPatente(patente);
    }
}
