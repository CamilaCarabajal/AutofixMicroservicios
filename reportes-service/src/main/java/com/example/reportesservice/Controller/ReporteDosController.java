package com.example.reportesservice.Controller;

import com.example.reportesservice.Entity.ReporteDosEntity;
import com.example.reportesservice.Service.ReporteDosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/reportes")
@RestController
public class ReporteDosController {
    @Autowired
    private ReporteDosService reporteDosService;

    @PostMapping("/calcular-reparaciones")
    public ResponseEntity<List<ReporteDosEntity>> calcularReparaciones(@RequestParam int mes, @RequestParam int ano) {
        List<ReporteDosEntity> resultados = reporteDosService.calcularReparacionesPorMes(mes, ano);

        if (!resultados.isEmpty()) {
            return ResponseEntity.ok(resultados);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
