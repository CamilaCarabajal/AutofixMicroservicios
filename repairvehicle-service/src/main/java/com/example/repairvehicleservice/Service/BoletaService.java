package com.example.repairvehicleservice.Service;

import com.example.repairvehicleservice.Entity.BoletaEntity;
import com.example.repairvehicleservice.Entity.HistorialEntity;
import com.example.repairvehicleservice.Entity.RegReparacionEntity;
import com.example.repairvehicleservice.Model.VehiculoModel;
import com.example.repairvehicleservice.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
    HistorialRepository historialRepository;
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

        // Calcular los descuentos
        double descuentoReparacion = descuentoService.calcularDescuentoReparacion(patente);
        double descuentoBono = descuentoService.calcularDescuentoBono(patente);

        // Acumulador para el descuento por día
        double descuentoDiaTotal = 0.0;

        // Aplicar el descuento por día de atención para cada reparación
        for (RegReparacionEntity reparacion : reparaciones) {
            descuentoDiaTotal += descuentoService.calculoDescuentoDia(reparacion.getId_regrepair());
        }

        // Calcular el descuento total
        double montoDescuentoReparacionYBono = (descuentoReparacion * montoTotalReparaciones) + descuentoBono;
        double descuentoTotal = montoDescuentoReparacionYBono + descuentoDiaTotal;

        return descuentoTotal;
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
        try {
            VehiculoModel vehiculo = getVehiculo(patente);

            if (vehiculo != null) {
                // Obtener la primera reparación
                RegReparacionEntity primeraReparacion = regReparacionService.getPrimeraReparacionPorPatente(patente);
                if (primeraReparacion == null) {
                    // Si no hay reparaciones, manejar adecuadamente (puede que quieras lanzar una excepción o devolver null)
                    return null;
                }

                // Calcular los valores necesarios
                int montoTotalReparaciones = calcularMontoTotalReparaciones(vehiculo.getPatente());
                double montoRecargos = calcularMontoRecargos(vehiculo.getPatente(), fechaReparacion, fechaCliente);
                double montoDescuentos = calcularMontoTotalDescuento(vehiculo.getPatente());
                double montoSubTotal = calcularMontoSubTotal(vehiculo.getPatente(), fechaReparacion, fechaCliente);
                double montoIVA = calcularMontoIVA(vehiculo.getPatente(), fechaReparacion, fechaCliente);
                double costoTotal = calcularCostoTotal(vehiculo.getPatente(), fechaReparacion, fechaCliente);

                // Crear y guardar la boleta
                BoletaEntity boleta = new BoletaEntity();
                boleta.setPatente(vehiculo.getPatente());
                boleta.setMonto_total(montoTotalReparaciones);
                boleta.setRecargo(montoRecargos);
                boleta.setDescuento(montoDescuentos);
                boleta.setSub_total(montoSubTotal);
                boleta.setIva(montoIVA);
                boleta.setCosto_total(costoTotal);
                boleta.setFecha_salida(fechaReparacion);
                boleta.setHora_salida(horaReparacion);
                boleta.setFecha_cliente(fechaCliente);
                boleta.setHora_cliente(horaCliente);

                BoletaEntity savedBoleta = boletaRepository.save(boleta);

                // Crear y guardar el historial
                HistorialEntity historial = new HistorialEntity();
                historial.setId_historial(savedBoleta.getId_boleta()); // Asumiendo que id_historial es el mismo que id_boleta
                historial.setPatente(vehiculo.getPatente());
                historial.setMarca(vehiculo.getMarca());
                historial.setModelo(vehiculo.getModelo());
                historial.setAno_fabricacion(vehiculo.getAno_fabricacion());
                historial.setMotor(vehiculo.getMotor());
                historial.setFecha_ingreso(primeraReparacion.getFecha_reparacion()); // Usando la fecha de la primera reparación
                historial.setHora_ingreso(primeraReparacion.getHora_reparacion()); // Usando la hora de la primera reparación
                historial.setMonto_total(montoTotalReparaciones);
                historial.setMonto_recargo(montoRecargos);
                historial.setMonto_descuento(montoDescuentos);
                historial.setSub_total(montoSubTotal);
                historial.setMonto_iva(montoIVA);
                historial.setCosto_total(costoTotal);
                historial.setFecha_salida(fechaReparacion);
                historial.setHora_salida(horaReparacion);
                historial.setFecha_cliente(fechaCliente);
                historial.setHora_cliente(horaCliente);

                historialRepository.save(historial);

                return savedBoleta;
            } else {
                return null;
            }
        } catch (HttpClientErrorException e) {
            // Manejar la excepción cuando el servicio de vehículos no está disponible o retorna un error
            e.printStackTrace();
            return null;
        }
    }

    public boolean eliminarBoleta(Long id) {
        if (boletaRepository.existsById(id)) {
            boletaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public BoletaEntity obtenerBoletaPorId(Long id) {
        return boletaRepository.findById(id).orElse(null);
    }


}
