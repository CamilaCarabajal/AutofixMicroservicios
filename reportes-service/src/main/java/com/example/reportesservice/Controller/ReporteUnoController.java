package com.example.reportesservice.Controller;

import com.example.reportesservice.Service.ReporteUnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ReporteUnoController {
    @Autowired
    ReporteUnoService reporteUnoService;

    @GetMapping("/reparaciones/reporteuno")
    public ResponseEntity<Map<String, Map<String, Object>>> getReporte(
            @RequestParam int mes,
            @RequestParam int ano) {
        Map<String, Map<String, Object>> reporte = reporteUnoService.getReporte(mes, ano);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/reparaciones/reportedos")
    public ResponseEntity<Map<String, Map<String, Object>>> getComparativo(
            @RequestParam int mes,
            @RequestParam int ano) {
        Map<String, Map<String, Object>> comparativo = reporteUnoService.getComparativoReparaciones(mes, ano);
        return ResponseEntity.ok(comparativo);
    }
}
