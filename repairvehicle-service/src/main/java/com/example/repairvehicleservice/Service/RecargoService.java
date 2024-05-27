package com.example.repairvehicleservice.Service;

import com.example.repairvehicleservice.Entity.RecargoEntity;
import com.example.repairvehicleservice.Entity.RegReparacionEntity;
import com.example.repairvehicleservice.Model.VehiculoModel;
import com.example.repairvehicleservice.Repository.RecargoRepository;
import com.example.repairvehicleservice.Repository.RegReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class RecargoService {
    @Autowired
    RecargoRepository recargoRepository;

    @Autowired
    RegReparacionRepository regReparacionRepository;

    @Autowired
    private RestTemplate restTemplate;

    public VehiculoModel getVehiculo(String patente) {
        ResponseEntity<VehiculoModel> responseEntity = restTemplate.exchange(
                "http://vehiculo-service/vehiculo/" + patente,
                HttpMethod.GET,
                null,
                VehiculoModel.class
        );
        return responseEntity.getBody();
    }

    public double calcularRecargoKilometraje(String patente){
        double recargoKilometraje = 0.0;
        VehiculoModel vehiculo = getVehiculo(patente);
        int kilometraje = vehiculo.getKilometraje();
        String modelo = vehiculo.getModelo();

        if (kilometraje>=0 && kilometraje <=5000){
            recargoKilometraje = 0.0;
        } else if (kilometraje>=5001 && kilometraje<=12000) {
            if(modelo == "Sedan" || modelo == "Hatchback"){
                recargoKilometraje = 0.03;
            } else if (modelo == "SUV" || modelo == "Pickup" || modelo == "Furgoneta") {
                recargoKilometraje = 0.05;
            }
        } else if (kilometraje>=12001 && kilometraje<=25000) {
            if(modelo == "Sedan" || modelo == "Hatchback"){
                recargoKilometraje = 0.07;
            } else if (modelo == "SUV" || modelo == "Pickup" || modelo == "Furgoneta") {
                recargoKilometraje = 0.09;
            }

        } else if (kilometraje>=25001 && kilometraje<=40000) {
            recargoKilometraje = 0.12;

        } else if (kilometraje>=40001) {
            recargoKilometraje = 0.2;
        }
        return recargoKilometraje;
    }

    public double calculoRecargoAntiguedad(String patente){
        double recargoAntiguedad = 0.0;
        int anoActual = 2024;
        VehiculoModel vehiculo = getVehiculo(patente);
        int anoFabricacion = vehiculo.getAno_fabricacion();
        int antiguedad = anoActual - anoFabricacion;
        String modelo = vehiculo.getModelo();

        if(antiguedad >=0 && antiguedad<=5){
            recargoAntiguedad = 0.0;
        } else if (antiguedad>=6 && antiguedad<=10) {
            if(modelo == "Sedan" || modelo == "Hatchback"){
                recargoAntiguedad = 0.05;
            } else if (modelo == "SUV" || modelo == "Pickup" || modelo == "Furgoneta") {
                recargoAntiguedad = 0.07;
            }
        } else if (antiguedad>=11 && antiguedad<=15) {
            if(modelo == "Sedan" || modelo == "Hatchback"){
                recargoAntiguedad = 0.09;
            } else if (modelo == "SUV" || modelo == "Pickup" || modelo == "Furgoneta") {
                recargoAntiguedad = 0.11;
            }
        } else if (antiguedad>=16) {
            if(modelo == "Sedan" || modelo == "Hatchback"){
                recargoAntiguedad = 0.15;
            } else if (modelo == "SUV" || modelo == "Pickup" || modelo == "Furgoneta") {
                recargoAntiguedad = 0.2;
            }
        }
        return recargoAntiguedad;
    }
    public double calcularRecargoPorRetraso(Long idReparacion, LocalDate fecha_reparacion, LocalDate fecha_cliente) {
        RegReparacionEntity reparacion = regReparacionRepository.findById(idReparacion).orElse(null);
        if (reparacion == null) {
            // Manejo de error si no se encuentra la reparación
            return 0.0;
        }

        // Calcula la cantidad de días de retraso
        long diasRetraso = ChronoUnit.DAYS.between(fecha_reparacion, fecha_cliente);

        if (diasRetraso > 0) {
            // Calcula el recargo por retraso (5% por cada día de retraso)
            double recargo = reparacion.getMonto_reparacion() * 0.05 * diasRetraso;

            // Crea un nuevo recargo en la base de datos
            RecargoEntity nuevoRecargo = new RecargoEntity();
            nuevoRecargo.setId_reparacion(idReparacion);
            nuevoRecargo.setPatente(reparacion.getPatente());
            nuevoRecargo.setMonto_total(recargo);

            // Guarda el nuevo recargo en la base de datos
            recargoRepository.save(nuevoRecargo);

            return recargo;
        } else {
            // No hay retraso, no se aplica recargo
            return 0.0;
        }
    }

}
