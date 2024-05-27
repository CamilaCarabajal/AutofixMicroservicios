package com.example.repairvehicleservice.Service;

import com.example.repairvehicleservice.Entity.DescuentoEntity;
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

    public double calcularDescuentoReparacion(String patente){
        double descuentoReparacion = 0.0;
        VehiculoModel vehiculo = getVehiculo(patente);
        int cantReparaciones = vehiculo.getCantidad_reparaciones();
        String tipoMotor = vehiculo.getMotor();

        if(tipoMotor == "Gasolina") {
            if(cantReparaciones>=1 && cantReparaciones<=2){
                descuentoReparacion = 0.05;
            } else if (cantReparaciones>=3 && cantReparaciones<=5) {
                descuentoReparacion = 0.1;
            } else if (cantReparaciones>=6 && cantReparaciones<=9) {
                descuentoReparacion = 0.15;
            } else if (cantReparaciones>=10) {
                descuentoReparacion = 0.2;
            }
        } else if (tipoMotor == "Diesel") {
            if(cantReparaciones>=1 && cantReparaciones<=2){
                descuentoReparacion = 0.07;
            } else if (cantReparaciones>=3 && cantReparaciones<=5) {
                descuentoReparacion = 0.12;
            } else if (cantReparaciones>=6 && cantReparaciones<=9) {
                descuentoReparacion = 0.17;
            } else if (cantReparaciones>=10) {
                descuentoReparacion = 0.22;
            }

        } else if (tipoMotor == "Hibrido") {
            if(cantReparaciones>=1 && cantReparaciones<=2){
                descuentoReparacion = 0.1;
            } else if (cantReparaciones>=3 && cantReparaciones<=5) {
                descuentoReparacion = 0.15;
            } else if (cantReparaciones>=6 && cantReparaciones<=9) {
                descuentoReparacion = 0.2;
            } else if (cantReparaciones>=10) {
                descuentoReparacion = 0.25;
            }

        } else if(tipoMotor == "Electrico"){
            if(cantReparaciones>=1 && cantReparaciones<=2){
                descuentoReparacion = 0.08;
            } else if (cantReparaciones>=3 && cantReparaciones<=5) {
                descuentoReparacion = 0.13;
            } else if (cantReparaciones>=6 && cantReparaciones<=9) {
                descuentoReparacion = 0.18;
            } else if (cantReparaciones>=10) {
                descuentoReparacion = 0.23;
            }
        }
        return descuentoReparacion;
    }

    public double calcularDescuentoBono(String patente){
        double descuentoBono = 0.0;
        VehiculoModel vehiculo = getVehiculo(patente);
        String marcaVehiculo = vehiculo.getMarca();
        List<Integer> precioBono = List.of(70000,50000,30000,40000);
        List<Integer> cantBonos = new ArrayList<>(List.of(5,2,1,7));

        if(cantBonos.get(0)>=1 && marcaVehiculo == "Toyota"){
            descuentoBono = precioBono.get(0);
            cantBonos.set(0,cantBonos.get(0)-1);
        } else if (cantBonos.get(1)>=1 && marcaVehiculo == "Ford"){
            descuentoBono = precioBono.get(1);
            cantBonos.set(1,cantBonos.get(1)-1);
        }else if (cantBonos.get(2)>=1 && marcaVehiculo == "Hyundai"){
            descuentoBono = precioBono.get(2);
            cantBonos.set(2,cantBonos.get(2)-1);
        }else if (cantBonos.get(3)>=1 && marcaVehiculo == "Honda"){
            descuentoBono = precioBono.get(3);
            cantBonos.set(3,cantBonos.get(3)-1);
        }else {
            descuentoBono = 0.0;
        }
        return descuentoBono;
    }

    public double calculoDescuentoDia(Long id_reparacion) {
        // Obtener la reparación por su id
        RegReparacionEntity reparacion = entityManager.find(RegReparacionEntity.class, id_reparacion);

        if (reparacion != null) {
            if (reparacion.getFecha_reparacion().getDayOfWeek().equals(DayOfWeek.MONDAY) || reparacion.getFecha_reparacion().getDayOfWeek().equals(DayOfWeek.THURSDAY)) {
                LocalTime horaReparacion = reparacion.getHora_reparacion();
                if (horaReparacion.isAfter(LocalTime.of(9, 0)) && horaReparacion.isBefore(LocalTime.of(12, 0))) {
                    // Calcular el monto con el descuento del 10%
                    double montoConDescuento = reparacion.getMonto_reparacion() * 0.9;

                    // Crear un nuevo registro en la tabla DescuentoEntity
                    DescuentoEntity descuento = new DescuentoEntity();
                    descuento.setId_reparacion(id_reparacion);
                    descuento.setPatente(reparacion.getPatente());
                    descuento.setMonto_total(montoConDescuento);

                    // Guardar el registro en la base de datos
                    entityManager.persist(descuento);

                    return reparacion.getMonto_reparacion() - montoConDescuento; // Retornar el monto del descuento realizado
                }
            }
        }

        return 0; // Retornar 0 si no se realizó ningún descuento
    }


}
