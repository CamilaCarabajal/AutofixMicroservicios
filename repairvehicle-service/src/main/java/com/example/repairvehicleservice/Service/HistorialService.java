package com.example.repairvehicleservice.Service;

import com.example.repairvehicleservice.Entity.HistorialEntity;
import com.example.repairvehicleservice.Entity.RegReparacionEntity;
import com.example.repairvehicleservice.Model.VehiculoModel;
import com.example.repairvehicleservice.Repository.DescuentoRepository;
import com.example.repairvehicleservice.Repository.HistorialRepository;
import com.example.repairvehicleservice.Repository.RecargoRepository;
import com.example.repairvehicleservice.Repository.RegReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class HistorialService {
    @Autowired
    HistorialRepository historialRepository;
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

    public List<RegReparacionEntity> listarReparacionesPorPatente(String patente) {
        List<RegReparacionEntity> reparaciones = regReparacionRepository.findByPatente(patente);
        return reparaciones;
    }

    public double calcularMontoTotalReparaciones(List<RegReparacionEntity> reparaciones) {
        double montoTotal = reparaciones.stream()
                .mapToDouble(RegReparacionEntity::getMonto_reparacion)
                .sum();
        return montoTotal;
    }
    public double calcularMontoRecargos(String patente, LocalDate fechaReparacion, LocalDate fechaCliente) {
        List<RegReparacionEntity> reparaciones = listarReparacionesPorPatente(patente);

        double montoTotal = calcularMontoTotalReparaciones(reparaciones);

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
        List<RegReparacionEntity> reparaciones = listarReparacionesPorPatente(patente);

        // Calcular el monto total de reparaciones
        double montoTotalReparaciones = calcularMontoTotalReparaciones(reparaciones);

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
        List<RegReparacionEntity> reparaciones = listarReparacionesPorPatente(patente);
        double sumaReparaciones = calcularMontoTotalReparaciones(reparaciones);
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

    public HistorialEntity guardarHistorial(HistorialEntity historial) {
        HistorialEntity nuevoHistorial = new HistorialEntity();
        nuevoHistorial.setId_historial(historial.getId_historial());
        nuevoHistorial.setId_reparacion(historial.getId_reparacion());
        nuevoHistorial.setPatente(historial.getPatente());
        nuevoHistorial.setId_descuento(historial.getId_descuento());
        nuevoHistorial.setId_recargo(historial.getId_recargo());
        nuevoHistorial.setFecha_ingreso(LocalDate.now());
        nuevoHistorial.setHora_ingreso(LocalTime.now());
        nuevoHistorial.setMonto_total(historial.getMonto_total());
        nuevoHistorial.setMonto_recargo(historial.getMonto_recargo());
        nuevoHistorial.setMonto_descuento(historial.getMonto_descuento());
        nuevoHistorial.setMonto_iva(calcularMontoIVA()); // Si necesitas calcular el IVA
        nuevoHistorial.setCosto_total(calcularCostoTotal(historial.getPatente(), historial.getFecha_salida(),historial.getFecha_cliente())); // Podrías implementar un método para calcular el costo total

        // Guardar el historial en la base de datos
        return historialRepository.save(nuevoHistorial);
    }

    public List<HistorialEntity> listaHistorial(){
        return historialRepository.findAll();
    }









}
