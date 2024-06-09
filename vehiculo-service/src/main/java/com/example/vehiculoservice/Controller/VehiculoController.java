package com.example.vehiculoservice.Controller;


import com.example.vehiculoservice.Entity.VehiculoEntity;
import com.example.vehiculoservice.Service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/vehiculo")
public class VehiculoController {

    @Autowired
    VehiculoService vehiculoService;

    @PostMapping
    public VehiculoEntity registroVehiculo(@RequestBody VehiculoEntity vehiculo) {
        return vehiculoService.crearVehiculo(vehiculo);
    }

    @GetMapping("/{patente}")
    public VehiculoEntity obtenerVehiculo(@PathVariable String patente) {
        return vehiculoService.obtenerVehiculoPorPatente(patente);
    }

    @PutMapping("/{patente}")
    public VehiculoEntity actualizarVehiculo(@PathVariable String patente, @RequestBody VehiculoEntity vehiculo) {
        return vehiculoService.actualizarVehiculo(patente, vehiculo);
    }

    @DeleteMapping("/{patente}")
    public void eliminarVehiculo(@PathVariable String patente) {
        vehiculoService.eliminarVehiculo(patente);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<VehiculoEntity>> getVehiculos() {
        List<VehiculoEntity> vehiculos = vehiculoService.obtenerVehiculos();
        if (!vehiculos.isEmpty()) {
            return ResponseEntity.ok(vehiculos);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


}

