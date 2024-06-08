package com.example.reportesservice.Service;

import com.example.reportesservice.Model.RegReparacionModel;
import com.example.reportesservice.Model.VehiculoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteUnoService {

    @Autowired
    private RestTemplate restTemplate;

    public List<VehiculoModel> getVehiculos() {
        ResponseEntity<List<VehiculoModel>> responseEntity = restTemplate.exchange(
                "http://vehiculo-service/vehiculo/lista",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<VehiculoModel>>() {}
        );
        return responseEntity.getBody();
    }

    public List<RegReparacionModel> getReparaciones() {
        ResponseEntity<List<RegReparacionModel>> responseEntity = restTemplate.exchange(
                "http://repairvehicle-service/regrepair/lista",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RegReparacionModel>>() {}
        );
        return responseEntity.getBody();
    }

    public Map<String, Map<String, Object>> getReporte(int mes, int año) {
        if (mes < 1 || mes > 12 || año <= 0) {
            throw new IllegalArgumentException("Mes o año inválido.");
        }

        List<VehiculoModel> vehiculos;
        List<RegReparacionModel> reparaciones;

        try {
            vehiculos = getVehiculos();
            reparaciones = getReparaciones();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener datos de servicios externos", e);
        }

        if (vehiculos == null || reparaciones == null) {
            throw new RuntimeException("Datos de vehículos o reparaciones son nulos");
        }

        LocalDate startDate = LocalDate.of(año, mes, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        Map<String, VehiculoModel> vehiculoMap = vehiculos.stream()
                .collect(Collectors.toMap(VehiculoModel::getPatente, vehiculo -> vehiculo));

        Map<String, Map<String, Object>> reporte = new HashMap<>();

        for (RegReparacionModel reparacion : reparaciones) {
            if (reparacion.getFecha_reparacion().isBefore(startDate) || reparacion.getFecha_reparacion().isAfter(endDate)) {
                continue;
            }

            VehiculoModel vehiculo = vehiculoMap.get(reparacion.getPatente());
            if (vehiculo == null) continue;

            String tipoVehiculo = vehiculo.getModelo();
            String tipoReparacion = String.valueOf(reparacion.getTipo_reparacion());

            reporte.putIfAbsent(tipoVehiculo, new HashMap<>());
            Map<String, Object> reparacionData = reporte.get(tipoVehiculo);

            reparacionData.putIfAbsent(tipoReparacion, 0);
            reparacionData.put(tipoReparacion, (Integer) reparacionData.get(tipoReparacion) + 1);

            reparacionData.putIfAbsent(tipoReparacion + "_monto", 0.0);
            reparacionData.put(tipoReparacion + "_monto", (Double) reparacionData.get(tipoReparacion + "_monto") + reparacion.getMonto_reparacion());
        }

        return reporte;
    }

    public Map<String, Map<String, Object>> getComparativoReparaciones(int mes, int año) {
        if (mes < 1 || mes > 12 || año <= 0) {
            throw new IllegalArgumentException("Mes o año inválido.");
        }

        YearMonth currentMonth = YearMonth.of(año, mes);
        YearMonth previousMonth1 = currentMonth.minusMonths(1);
        YearMonth previousMonth2 = currentMonth.minusMonths(2);

        Map<String, Map<String, Object>> currentMonthData = getReporte(currentMonth.getMonthValue(), currentMonth.getYear());
        Map<String, Map<String, Object>> previousMonth1Data = getReporte(previousMonth1.getMonthValue(), previousMonth1.getYear());
        Map<String, Map<String, Object>> previousMonth2Data = getReporte(previousMonth2.getMonthValue(), previousMonth2.getYear());

        Map<String, Map<String, Object>> comparativoReporte = new HashMap<>();

        // Lógica para combinar y comparar los datos de los tres meses
        for (String tipoVehiculo : currentMonthData.keySet()) {
            Map<String, Object> currentData = currentMonthData.get(tipoVehiculo);
            Map<String, Object> prevData1 = previousMonth1Data.getOrDefault(tipoVehiculo, new HashMap<>());
            Map<String, Object> prevData2 = previousMonth2Data.getOrDefault(tipoVehiculo, new HashMap<>());

            Map<String, Object> comparativoData = new HashMap<>();

            for (String tipoReparacion : currentData.keySet()) {
                if (tipoReparacion.endsWith("_monto")) continue;

                int currentCantidad = (Integer) currentData.getOrDefault(tipoReparacion, 0);
                int prev1Cantidad = (Integer) prevData1.getOrDefault(tipoReparacion, 0);
                int prev2Cantidad = (Integer) prevData2.getOrDefault(tipoReparacion, 0);

                double currentMonto = (Double) currentData.getOrDefault(tipoReparacion + "_monto", 0.0);
                double prev1Monto = (Double) prevData1.getOrDefault(tipoReparacion + "_monto", 0.0);
                double prev2Monto = (Double) prevData2.getOrDefault(tipoReparacion + "_monto", 0.0);

                comparativoData.put(tipoReparacion, currentCantidad);
                comparativoData.put(tipoReparacion + "_prev1", prev1Cantidad);
                comparativoData.put(tipoReparacion + "_prev2", prev2Cantidad);

                comparativoData.put(tipoReparacion + "_monto", currentMonto);
                comparativoData.put(tipoReparacion + "_monto_prev1", prev1Monto);
                comparativoData.put(tipoReparacion + "_monto_prev2", prev2Monto);
            }

            comparativoReporte.put(tipoVehiculo, comparativoData);
        }

        return comparativoReporte;
    }
}