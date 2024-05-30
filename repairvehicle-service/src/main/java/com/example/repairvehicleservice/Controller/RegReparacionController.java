package com.example.repairvehicleservice.Controller;

import com.example.repairvehicleservice.Entity.RegReparacionEntity;
import com.example.repairvehicleservice.Service.RegReparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/regrepair")
@RestController
public class RegReparacionController {
    @Autowired
    RegReparacionService regReparacionService;

    @PostMapping
    public ResponseEntity<RegReparacionEntity> crearReparacion(@RequestBody RegReparacionEntity reparacion) {
        RegReparacionEntity nuevaReparacion = regReparacionService.crearReparacion(reparacion);
        return ResponseEntity.ok(nuevaReparacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegReparacionEntity> obtenerReparacionPorId(@PathVariable Long id) {
        RegReparacionEntity reparacion = regReparacionService.obtenerReparacionPorId(id);
        if (reparacion != null) {
            return ResponseEntity.ok(reparacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegReparacionEntity> actualizarReparacion(@PathVariable Long id, @RequestBody RegReparacionEntity reparacion) {
        RegReparacionEntity reparacionActualizada = regReparacionService.actualizarReparacion(id, reparacion);
        if (reparacionActualizada != null) {
            return ResponseEntity.ok(reparacionActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReparacion(@PathVariable Long id) {
        regReparacionService.eliminarReparacion(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/vehiculo/{patente}")
    public ResponseEntity<RegReparacionEntity> crearReparacionVehiculo(@PathVariable String patente, @RequestBody RegReparacionEntity reparacion) {
        RegReparacionEntity nuevaReparacion = regReparacionService.crearReparacionVehiculo(patente, reparacion);

        if (nuevaReparacion != null) {
            return ResponseEntity.ok(nuevaReparacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<List<RegReparacionEntity>> listarReparaciones() {
        List<RegReparacionEntity> reparaciones = regReparacionService.listarReparaciones();

        if (!reparaciones.isEmpty()) {
            return ResponseEntity.ok(reparaciones);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{patente}")
    public List<RegReparacionEntity> obtenerReparacionPorPatente(@PathVariable String patente) {
        return regReparacionService.obtenerReparacionesPatente(patente);
    }


}
