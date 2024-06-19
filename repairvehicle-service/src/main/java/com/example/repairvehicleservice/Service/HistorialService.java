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



    public HistorialEntity guardarHistorial(String patente) {
        // Obtener vehículo asociado a la patente
        VehiculoModel vehiculo = getVehiculo(patente);
        if (vehiculo == null) {
            // Manejar caso de vehículo no encontrado
            return null;
        }

        // Obtener la primera reparación asociada a la patente
        RegReparacionEntity primeraReparacion = regReparacionService.getPrimeraReparacionPorPatente(patente);
        if (primeraReparacion == null) {
            // Manejar caso de reparación no encontrada
            return null;
        }

        // Obtener la última boleta asociada a la patente
        BoletaEntity boleta = boletaRepository.findTopByPatenteOrderByIdBoletaDesc(patente);
        if (boleta == null) {
            // Manejar caso de boleta no encontrada
            return null;
        }

        // Crear un nuevo objeto HistorialEntity
        HistorialEntity nuevoHistorial = new HistorialEntity();

        // Configurar los atributos del nuevo historial
        nuevoHistorial.setPatente(patente);
        nuevoHistorial.setMarca(vehiculo.getMarca());
        nuevoHistorial.setModelo(vehiculo.getModelo());
        nuevoHistorial.setAno_fabricacion(vehiculo.getAno_fabricacion());
        nuevoHistorial.setMotor(vehiculo.getMotor());
        nuevoHistorial.setFecha_ingreso(primeraReparacion.getFecha_reparacion());
        nuevoHistorial.setHora_ingreso(primeraReparacion.getHora_reparacion());
        nuevoHistorial.setMonto_total(boleta.getMonto_total());
        nuevoHistorial.setMonto_recargo(boleta.getRecargo());
        nuevoHistorial.setMonto_descuento(boleta.getDescuento());
        nuevoHistorial.setMonto_iva(boleta.getIva());
        nuevoHistorial.setCosto_total(boleta.getCosto_total());
        nuevoHistorial.setFecha_salida(boleta.getFecha_salida());
        nuevoHistorial.setHora_salida(boleta.getHora_salida());
        nuevoHistorial.setFecha_cliente(boleta.getFecha_cliente());
        nuevoHistorial.setHora_cliente(boleta.getHora_cliente());

        // Guardar el nuevo historial en la base de datos
        return historialRepository.save(nuevoHistorial);
    }


    public List<HistorialEntity> listaHistorial(){
        return historialRepository.findAll();
    }









}
