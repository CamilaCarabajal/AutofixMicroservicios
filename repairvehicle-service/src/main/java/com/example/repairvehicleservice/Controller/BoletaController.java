package com.example.repairvehicleservice.Controller;

import com.example.repairvehicleservice.Entity.BoletaEntity;
import com.example.repairvehicleservice.Service.BoletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RequestMapping("/repairvehicle")
@RestController

public class BoletaController {
    @Autowired
    BoletaService boletaService;

    public BoletaEntity generarBoleta(
            @PathVariable String patente,
            @RequestBody BoletaEntity boleta,
            @RequestParam(name = "fechaReparacion") LocalDate fechaReparacion,
            @RequestParam(name = "fechaCliente") LocalDate fechaCliente,
            @RequestParam(name = "horaReparacion") LocalTime horaReparacion,
            @RequestParam(name = "horaCliente") LocalTime horaCliente
    ) {
        return boletaService.generarBoleta(patente, boleta, fechaReparacion, fechaCliente, horaReparacion, horaCliente);
    }
}
