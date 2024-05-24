package com.example.vehiculoservice.Service;

import com.example.vehiculoservice.Entity.VehiculoEntity;
import com.example.vehiculoservice.Repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoService {
    @Autowired
    VehiculoRepository vehiculoRepository;

    public VehiculoEntity crearVehiculo(VehiculoEntity vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public VehiculoEntity obtenerVehiculoPorPatente(String patente) {
        return vehiculoRepository.findByPatente(patente);
    }

    public VehiculoEntity actualizarVehiculo(String patente, VehiculoEntity vehiculoActualizado) {
        VehiculoEntity vehiculo = vehiculoRepository.findByPatente(patente);
        if (vehiculo != null) {
            vehiculo.setMarca(vehiculoActualizado.getMarca());
            vehiculo.setModelo(vehiculoActualizado.getModelo());
            vehiculo.setAno_fabricacion(vehiculoActualizado.getAno_fabricacion());
            vehiculo.setMotor(vehiculoActualizado.getMotor());
            vehiculo.setAsientos(vehiculoActualizado.getAsientos());
            return vehiculoRepository.save(vehiculo);
        }
        return null;
    }

    public void eliminarVehiculo(String patente) {
        vehiculoRepository.deleteByPatente(patente);
    }

    public List<VehiculoEntity> obtenerVehiculos() {
        return vehiculoRepository.findAll();
    }



}
