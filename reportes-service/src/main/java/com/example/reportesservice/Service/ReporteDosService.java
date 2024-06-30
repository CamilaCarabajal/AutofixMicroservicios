package com.example.reportesservice.Service;

import com.example.reportesservice.Entity.ReporteDosEntity;
import com.example.reportesservice.Repository.ReporteDosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class ReporteDosService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    ReporteDosRepository reporteDosRepository;

    public List<ReporteDosEntity> calcularReparacionesPorMes(int mes, int ano) {
        String url = "http://localhost:8080/repairvehicle-service/regrepair/calcular-reparaciones?mes=" + mes + "&ano=" + ano;

        // Usa RestTemplate para hacer una solicitud POST y maneja posibles excepciones
        try {
            ResponseEntity<ReporteDosEntity[]> responseEntity = restTemplate.postForEntity(url, null, ReporteDosEntity[].class);
            ReporteDosEntity[] resultadosArray = responseEntity.getBody();

            List<ReporteDosEntity> resultados = Arrays.asList(resultadosArray != null ? resultadosArray : new ReporteDosEntity[0]);

            // Guardar los resultados en la base de datos
            if (!resultados.isEmpty()) {
                reporteDosRepository.saveAll(resultados);
            }

            return resultados;
        } catch (ResourceAccessException e) {
            // Manejar errores de acceso al recurso
            System.err.println("Error de acceso al recurso: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            // Manejar otros errores
            System.err.println("Error al realizar la solicitud: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
