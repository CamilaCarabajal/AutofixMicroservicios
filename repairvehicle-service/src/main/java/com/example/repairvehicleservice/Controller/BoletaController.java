package com.example.repairvehicleservice.Controller;

import com.example.repairvehicleservice.Entity.BoletaEntity;
import com.example.repairvehicleservice.Service.BoletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RequestMapping("/repairvehicle")
@RestController

public class BoletaController {
    @Autowired
    BoletaService boletaService;
    @PostMapping("/boleta/generar")
    public ResponseEntity<BoletaEntity> generarBoleta(
            @RequestParam String patente,
            @RequestParam LocalDate fechaReparacion,
            @RequestParam LocalDate fechaCliente,
            @RequestParam LocalTime horaReparacion,
            @RequestParam LocalTime horaCliente) {

        // Validar la patente según el formato requerido (4 letras y 2 números)
        if (!patente.matches("[A-Z]{4}[0-9]{2}")) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            BoletaEntity boleta = boletaService.generarBoleta(patente, fechaReparacion, fechaCliente, horaReparacion, horaCliente);
            return new ResponseEntity<>(boleta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
