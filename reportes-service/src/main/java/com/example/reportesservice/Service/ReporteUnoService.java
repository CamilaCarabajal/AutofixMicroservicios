package com.example.reportesservice.Service;

import com.example.reportesservice.Entity.ReporteUnoEntity;
import com.example.reportesservice.Repository.ReporteUnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ReporteUnoService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    ReporteUnoRepository reporteUnoRepository;

    public List<ReporteUnoEntity> calcularCostosReparacion(int mes, int ano) {
        String url = "http://localhost:8080/repairvehicle/regrepair/calcularCostos?mes=" + mes + "&ano=" + ano;
        ResponseEntity<ReporteUnoEntity[]> responseEntity = restTemplate.postForEntity(url, null, ReporteUnoEntity[].class);

        ReporteUnoEntity[] resultados = responseEntity.getBody();
        if (resultados != null) {
            reporteUnoRepository.saveAll(Arrays.asList(resultados));
        }
        return Arrays.asList(resultados);
    }
}


