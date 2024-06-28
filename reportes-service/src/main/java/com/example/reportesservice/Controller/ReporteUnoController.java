package com.example.reportesservice.Controller;

import com.example.reportesservice.Entity.ReporteUnoEntity;
import com.example.reportesservice.Service.ReporteUnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/reportes")
@RestController
public class ReporteUnoController {
    @Autowired
    private ReporteUnoService reporteUnoService;

    @PostMapping("/calcular-costos-reparacion")
    public ResponseEntity<List<ReporteUnoEntity>> calcularCostosReparacion(@RequestParam int mes, @RequestParam int ano) {
        List<ReporteUnoEntity> resultados = reporteUnoService.calcularCostosReparacion(mes, ano);
        if (!resultados.isEmpty()) {
            return ResponseEntity.ok(resultados);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
