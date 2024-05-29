package com.example.reportesservice.Service;

import com.example.reportesservice.Model.RegReparacionModel;
import com.example.reportesservice.Model.VehiculoModel;
import com.example.reportesservice.Repository.ReporteUnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReporteUnoService {
    @Autowired
    ReporteUnoRepository reporteUnoRepository;
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

    public RegReparacionModel getRegReparacion(Long id_regrepair) {
        ResponseEntity<RegReparacionModel> responseEntity = restTemplate.exchange(
                "http://repairvehicle-service/regrepair/" + id_regrepair,
                HttpMethod.GET,
                null,
                RegReparacionModel.class
        );
        return responseEntity.getBody();
    }


}
