package com.example.repairvehicleservice.Service;

import com.example.repairvehicleservice.Entity.RegReparacionEntity;
import com.example.repairvehicleservice.Model.VehiculoModel;
import com.example.repairvehicleservice.Repository.DescuentoRepository;
import com.example.repairvehicleservice.Repository.RegReparacionRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class DescuentoService {

    @Autowired
    DescuentoRepository descuentoRepository;

    @Autowired
    RegReparacionRepository reparacionRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EntityManager entityManager;

    public VehiculoModel getVehiculo(String patente) {
        ResponseEntity<VehiculoModel> responseEntity = restTemplate.exchange(
                "http://vehiculo-service/vehiculo/" + patente,
                HttpMethod.GET,
                null,
                VehiculoModel.class
        );
        return responseEntity.getBody();
    }

    public double calcularDescuentoReparacion(String patente) {
        double descuentoReparacion = 0.0;
        VehiculoModel vehiculo = getVehiculo(patente);

        // Verificar si el vehículo es null
        if (vehiculo == null) {
            return 0.0;
        }

        int cantReparaciones = vehiculo.getCantidad_reparaciones();
        String tipoMotor = vehiculo.getMotor();

        // Usar constantes para los tipos de motor
        final String GASOLINA = "Gasolina";
        final String DIESEL = "Diesel";
        final String HIBRIDO = "Hibrido";
        final String ELECTRICO = "Electrico";

        switch (tipoMotor) {
            case GASOLINA:
                if (cantReparaciones >= 1 && cantReparaciones <= 2) {
                    descuentoReparacion = 0.05;
                } else if (cantReparaciones >= 3 && cantReparaciones <= 5) {
                    descuentoReparacion = 0.1;
                } else if (cantReparaciones >= 6 && cantReparaciones <= 9) {
                    descuentoReparacion = 0.15;
                } else if (cantReparaciones >= 10) {
                    descuentoReparacion = 0.2;
                }
                break;
            case DIESEL:
                if (cantReparaciones >= 1 && cantReparaciones <= 2) {
                    descuentoReparacion = 0.07;
                } else if (cantReparaciones >= 3 && cantReparaciones <= 5) {
                    descuentoReparacion = 0.12;
                } else if (cantReparaciones >= 6 && cantReparaciones <= 9) {
                    descuentoReparacion = 0.17;
                } else if (cantReparaciones >= 10) {
                    descuentoReparacion = 0.22;
                }
                break;
            case HIBRIDO:
                if (cantReparaciones >= 1 && cantReparaciones <= 2) {
                    descuentoReparacion = 0.1;
                } else if (cantReparaciones >= 3 && cantReparaciones <= 5) {
                    descuentoReparacion = 0.15;
                } else if (cantReparaciones >= 6 && cantReparaciones <= 9) {
                    descuentoReparacion = 0.2;
                } else if (cantReparaciones >= 10) {
                    descuentoReparacion = 0.25;
                }
                break;
            case ELECTRICO:
                if (cantReparaciones >= 1 && cantReparaciones <= 2) {
                    descuentoReparacion = 0.08;
                } else if (cantReparaciones >= 3 && cantReparaciones <= 5) {
                    descuentoReparacion = 0.13;
                } else if (cantReparaciones >= 6 && cantReparaciones <= 9) {
                    descuentoReparacion = 0.18;
                } else if (cantReparaciones >= 10) {
                    descuentoReparacion = 0.23;
                }
                break;
            default:
                descuentoReparacion = 0.0; // En caso de un tipo de motor desconocido
                break;
        }

        return descuentoReparacion;
    }


    public double calcularDescuentoBono(String patente) {
        double descuentoBono = 0.0;
        VehiculoModel vehiculo = getVehiculo(patente);

        // Verificar si el vehículo es null
        if (vehiculo == null) {
            return 0.0;
        }

        String marcaVehiculo = vehiculo.getMarca();
        List<Integer> precioBono = new ArrayList<>(List.of(70000, 50000, 30000, 40000)); // Convertir a lista mutable
        List<Integer> cantBonos = new ArrayList<>(List.of(5, 2, 1, 7)); // Lista mutable

        if (cantBonos.get(0) >= 1 && "Toyota".equals(marcaVehiculo)) {
            descuentoBono = precioBono.get(0);
            cantBonos.set(0, cantBonos.get(0) - 1);
        } else if (cantBonos.get(1) >= 1 && "Ford".equals(marcaVehiculo)) {
            descuentoBono = precioBono.get(1);
            cantBonos.set(1, cantBonos.get(1) - 1);
        } else if (cantBonos.get(2) >= 1 && "Hyundai".equals(marcaVehiculo)) {
            descuentoBono = precioBono.get(2);
            cantBonos.set(2, cantBonos.get(2) - 1);
        } else if (cantBonos.get(3) >= 1 && "Honda".equals(marcaVehiculo)) {
            descuentoBono = precioBono.get(3);
            cantBonos.set(3, cantBonos.get(3) - 1);
        } else {
            descuentoBono = 0.0;
        }

        return descuentoBono;
    }


    public double calculoDescuentoDia(Long id_reparacion) {
        // Obtener la reparación por su id
        RegReparacionEntity reparacion = entityManager.find(RegReparacionEntity.class, id_reparacion);

        if (reparacion != null) {
            LocalDate fechaReparacion = reparacion.getFecha_reparacion();
            LocalTime horaReparacion = reparacion.getHora_reparacion();

            // Verificar que fechaReparacion y horaReparacion no sean null
            if (fechaReparacion != null && horaReparacion != null) {
                // Verificar si la reparación se realizó un lunes o jueves entre 9:00 y 12:00
                if ((fechaReparacion.getDayOfWeek().equals(DayOfWeek.MONDAY) ||
                        fechaReparacion.getDayOfWeek().equals(DayOfWeek.THURSDAY)) &&
                        horaReparacion.isAfter(LocalTime.of(9, 0)) &&
                        horaReparacion.isBefore(LocalTime.of(12, 0))) {

                    // Calcular el monto con el descuento del 10%
                    double montoConDescuento = reparacion.getMonto_reparacion() * 0.9;

                    return montoConDescuento; // Retornar el monto con el descuento aplicado
                }
            }
        }

        return 0; // Retornar 0 si no se realizó ningún descuento
    }



}
