package com.example.reparacionservice.Controller;

import com.example.reparacionservice.Entity.ReparacionEntity;
import com.example.reparacionservice.Service.ReparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reparacion")
public class ReparacionController {

    @Autowired
    private ReparacionService reparacionService;

    @PostMapping("/guardar")
    public ResponseEntity<ReparacionEntity> guardarReparacion(@RequestBody ReparacionEntity reparacion, @RequestParam String motor) {
        ReparacionEntity nuevaReparacion = reparacionService.guardarReparacion(reparacion, motor);
        return ResponseEntity.ok(nuevaReparacion);
    }

    @GetMapping("/{id_reparacion}")
    public ResponseEntity<ReparacionEntity> obtenerReparacionPorId(@PathVariable Long id_reparacion) {
        ReparacionEntity reparacion = reparacionService.obtenerReparacionPorId(id_reparacion);
        if (reparacion != null) {
            return ResponseEntity.ok(reparacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ReparacionEntity> actualizarReparacion(@RequestBody ReparacionEntity reparacion) {
        ReparacionEntity reparacionActualizada = reparacionService.actualizarReparacion(reparacion);
        return ResponseEntity.ok(reparacionActualizada);
    }

    @DeleteMapping("/{id_reparacion}")
    public ResponseEntity<Void> eliminarReparacion(@PathVariable Long id_reparacion) {
        reparacionService.eliminarReparacion(id_reparacion);
        return ResponseEntity.noContent().build();
    }
}