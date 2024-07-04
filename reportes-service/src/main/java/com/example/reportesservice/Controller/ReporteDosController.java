package com.example.reportesservice.Controller;

import com.example.reportesservice.Entity.ReporteDosEntity;
import com.example.reportesservice.Service.ReporteDosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/calcularVariacionPorcentual")
    public List<ReporteDosEntity> calcularVariaciones(@RequestBody List<ReporteDosEntity> resultados) {
        reporteDosService.consumirCalcularVariaciones(resultados); // Llama a tu servicio para calcular las variaciones

        // Puedes devolver los resultados actualizados si es necesario
        return resultados;
    }

}
