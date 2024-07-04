package com.example.repairvehicleservice.Controller;

import com.example.repairvehicleservice.Entity.RegReparacionEntity;
import com.example.repairvehicleservice.Entity.ResultadoEntity;
import com.example.repairvehicleservice.Entity.ResultadosDosEntity;
import com.example.repairvehicleservice.Service.BoletaService;
import com.example.repairvehicleservice.Service.RegReparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/repairvehicle")
@RestController
public class RegReparacionController {
    @Autowired
    RegReparacionService regReparacionService;
    @Autowired
    BoletaService boletaService;

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
    public ResponseEntity<RegReparacionEntity> crearReparacionVehiculo(@PathVariable String patente, @RequestBody RegReparacionEntity reparacion) {
        try {
            RegReparacionEntity nuevaReparacion = regReparacionService.crearReparacionVehiculo(patente, reparacion);
            if (nuevaReparacion != null) {
                return ResponseEntity.ok(nuevaReparacion);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Logging the exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/regrepair/calcular-monto")
    public ResponseEntity<Integer> calcularMonto(@RequestParam String patente, @RequestParam int tipoReparacion) {
        RegReparacionEntity reparacion = new RegReparacionEntity();
        reparacion.setTipo_reparacion(tipoReparacion);
        int monto = regReparacionService.calcularCostoReparacion(patente, reparacion);
        return ResponseEntity.ok(monto);
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



    @GetMapping("/regrepair/lista/{patente}")
    public ResponseEntity<List<RegReparacionEntity>> getReparacionesPorPatente(@PathVariable String patente) {
        List<RegReparacionEntity> reparaciones = regReparacionService.getReparacionesPorPatente(patente);
        if (reparaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(reparaciones);
        }
    }

    @DeleteMapping("/regrepair/vehiculo/{patente}")
    public ResponseEntity<Void> eliminarReparacionesPorPatente(@PathVariable String patente) {
        regReparacionService.eliminarReparacionesPorPatente(patente);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/regrepair/calcularCostos")
    public ResponseEntity<List<ResultadoEntity>> calcularCostos(@RequestParam int mes, @RequestParam int ano) {
        List<ResultadoEntity> resultados = regReparacionService.calcularCostosReparacion(mes, ano);
        if (!resultados.isEmpty()) {
            return ResponseEntity.ok(resultados);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/regrepair/calcular-reparaciones")
    public ResponseEntity<List<ResultadosDosEntity>> calcularReparacionesPorMes(
            @RequestParam int mes, @RequestParam int ano) {

        List<ResultadosDosEntity> resultados = regReparacionService.calcularReparacionesPorMes(mes, ano);

        if (!resultados.isEmpty()) {
            return ResponseEntity.ok(resultados);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/regrepair/calcularVariaciones")
    public List<ResultadosDosEntity> calcularVariacionesEndpoint(@RequestBody List<ResultadosDosEntity> resultados) {
        regReparacionService.calcularVariaciones(resultados); // Llama a tu función existente

        // Puedes devolver los resultados actualizados si es necesario
        return resultados;
    }

}
