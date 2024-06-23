package com.example.repairvehicleservice.Service;

import com.example.repairvehicleservice.Entity.HistorialEntity;
import com.example.repairvehicleservice.Model.VehiculoModel;
import com.example.repairvehicleservice.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    RegReparacionService regReparacionService;
    @Autowired
    BoletaRepository boletaRepository;
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




    public List<HistorialEntity> listaHistorial(){
        return historialRepository.findAll();
    }

    public void eliminarHistorial(Long idHistorial) {
        historialRepository.deleteById(idHistorial);
    }








}
