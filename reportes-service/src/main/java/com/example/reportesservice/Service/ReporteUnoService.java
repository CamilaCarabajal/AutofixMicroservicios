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

    public Map<String, Map<String, Object>> getReparacionesPorMesYAño(int mes, int año) {
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
}