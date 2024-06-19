package com.example.repairvehicleservice.Service;

import com.example.repairvehicleservice.Entity.BoletaEntity;
import com.example.repairvehicleservice.Entity.RegReparacionEntity;
import com.example.repairvehicleservice.Model.VehiculoModel;
import com.example.repairvehicleservice.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service

public class BoletaService {
    @Autowired
    BoletaRepository boletaRepository;
    @Autowired
    RecargoRepository recargoRepository;
    @Autowired
    DescuentoRepository descuentoRepository;
    @Autowired
    RegReparacionRepository regReparacionRepository;
    @Autowired
    RecargoService recargoService;
    @Autowired
    DescuentoService descuentoService;
    @Autowired
    RegReparacionService regReparacionService;
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

    public int calcularMontoTotalReparaciones(String patente) {
        List<RegReparacionEntity> reparaciones = regReparacionService.getReparacionesPorPatente(patente);
        int montoTotal = 0;

        for (RegReparacionEntity reparacion : reparaciones) {
            montoTotal += regReparacionService.calcularCostoReparacion(patente,reparacion);
        }

        return montoTotal;
    }
    public double calcularMontoRecargos(String patente, LocalDate fechaReparacion, LocalDate fechaCliente) {
        List<RegReparacionEntity> reparaciones = regReparacionService.getReparacionesPorPatente(patente);


        double montoTotal = calcularMontoTotalReparaciones(patente);

        double recargoPorRetraso = 0.0;
        double recargoPorKilometraje = recargoService.calcularRecargoKilometraje(patente);
        double recargoPorAntiguedad = recargoService.calculoRecargoAntiguedad(patente);

        for (RegReparacionEntity reparacion : reparaciones) {
            recargoPorRetraso += recargoService.calcularRecargoPorRetraso(reparacion.getId_regrepair(), fechaReparacion, fechaCliente);
        }

        double montoRecargos = montoTotal * (recargoPorKilometraje + recargoPorAntiguedad) + recargoPorRetraso;

        return montoRecargos;
    }

    public double calcularMontoTotalDescuento(String patente) {
        // Obtener la lista de reparaciones por patente
        List<RegReparacionEntity> reparaciones = regReparacionService.getReparacionesPorPatente(patente);


        // Calcular el monto total de reparaciones
        double montoTotalReparaciones = calcularMontoTotalReparaciones(patente);

        // Calcular el monto total de descuento sumando los descuentos de reparación, bono y por día de atención para cada reparación
        double montoTotalDescuento = 0.0;
        double descuentoDia = 0.0;
        double descuentoReparacion = descuentoService.calcularDescuentoReparacion(patente);
        double descuentoBono = descuentoService.calcularDescuentoBono(patente);

        for (RegReparacionEntity reparacion : reparaciones) {

            // Aplicar el descuento por día de atención si corresponde
            descuentoDia+= descuentoService.calculoDescuentoDia(reparacion.getId_regrepair());
        }

        montoTotalDescuento =montoTotalReparaciones*(descuentoReparacion+descuentoBono)+descuentoDia;

        return montoTotalDescuento;
    }

    public double calcularMontoSubTotal(String patente, LocalDate fechaReparacion, LocalDate fechaCliente) {
        double sumaReparaciones = calcularMontoTotalReparaciones(patente);
        double montoRecargos = calcularMontoRecargos(patente, fechaReparacion, fechaCliente);
        double montoDescuentos = calcularMontoTotalDescuento(patente);

        // Calcular el montoSubTotal
        double montoSubTotal = sumaReparaciones + montoRecargos - montoDescuentos;

        return montoSubTotal;
    }

    public double calcularMontoIVA(String patente, LocalDate fechaReparacion, LocalDate fechaCliente) {
        // Calcular el monto subtotal antes de aplicar el IVA
        double montoSubTotal = calcularMontoSubTotal(patente, fechaReparacion, fechaCliente);

        // Calcular el monto del IVA
        double montoIVA = montoSubTotal*0.19;

        return montoIVA;
    }

    public double calcularCostoTotal(String patente, LocalDate fechaReparacion, LocalDate fechaCliente){
        double costoTotal = 0.0;
        double montoSubTotal = calcularMontoSubTotal(patente, fechaReparacion, fechaCliente);

        // Calcular el monto del IVA
        double montoIVA = montoSubTotal*0.19;

        costoTotal = montoSubTotal + montoIVA;
        return costoTotal;
    }

    public BoletaEntity generarBoleta(String patente, LocalDate fechaReparacion, LocalDate fechaCliente, LocalTime horaReparacion, LocalTime horaCliente) {
        VehiculoModel vehiculo = getVehiculo(patente);
        // Calcular los valores necesarios
        int montoTotalReparaciones = calcularMontoTotalReparaciones(patente);
        double montoRecargos = calcularMontoRecargos(patente, fechaReparacion, fechaCliente);
        double montoDescuentos = calcularMontoTotalDescuento(patente);
        double montoSubTotal = calcularMontoSubTotal(patente, fechaReparacion, fechaCliente);
        double montoIVA = calcularMontoIVA(patente, fechaReparacion, fechaCliente);
        double costoTotal = calcularCostoTotal(patente, fechaReparacion, fechaCliente);

        // Crear y guardar la boleta
        BoletaEntity boleta = new BoletaEntity();
        boleta.setPatente(vehiculo.getPatente());
        boleta.setMonto_total(montoTotalReparaciones);
        boleta.setRecargo(montoRecargos);
        boleta.setDescuento(montoDescuentos);
        boleta.setIva(montoIVA);
        boleta.setCosto_total(costoTotal);
        boleta.setFecha_salida(fechaReparacion);
        boleta.setHora_salida(horaReparacion);
        boleta.setFecha_cliente(fechaCliente);
        boleta.setHora_cliente(horaCliente);

        return boletaRepository.save(boleta);
    }


}
