package com.example.repairvehicleservice.Service;

import com.example.repairvehicleservice.Entity.RegReparacionEntity;
import com.example.repairvehicleservice.Model.VehiculoModel;
import com.example.repairvehicleservice.Repository.BoletaRepository;
import com.example.repairvehicleservice.Repository.RecargoRepository;
import com.example.repairvehicleservice.Repository.RegReparacionRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RegReparacionService {

    @Autowired
    RegReparacionRepository regReparacionRepository;
    @Autowired
    BoletaRepository boletaRepository;
    @Autowired
    RecargoRepository recargoRepository;
    @Autowired
    RecargoService recargoService;
    @Autowired
    DescuentoService descuentoService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EntityManager entityManager;
    public VehiculoModel getVehiculo(String patente) {
        try {
            ResponseEntity<VehiculoModel> responseEntity = restTemplate.exchange(
                    "http://vehiculo-service/vehiculo/" + patente,
                    HttpMethod.GET,
                    null,
                    VehiculoModel.class
            );
            return responseEntity.getBody();
        } catch (RestClientException e) {
            // Maneja el error de red o respuesta no exitosa
            return null;
        }
    }
/*----------------------------------Historia de Usuario 3------------------------------------------*/
    public int calcularCostoReparacion(String patente, RegReparacionEntity regRepair){
        int costoReparacion = 0;
        VehiculoModel vehiculo = getVehiculo(patente);
        String tipoMotor = vehiculo.getMotor();
        int tipoReparacion = regRepair.getTipo_reparacion();

        if(tipoMotor.equals("Gasolina")){
            if(tipoReparacion == 1){
                costoReparacion = 120000;
            } else if (tipoReparacion == 2) {
                costoReparacion =130000;

            } else if (tipoReparacion == 3) {
                costoReparacion = 350000;
            } else if (tipoReparacion == 4) {
                costoReparacion = 210000;

            } else if (tipoReparacion == 5) {
                costoReparacion = 150000;

            } else if (tipoReparacion == 6) {
                costoReparacion = 100000;

            } else if (tipoReparacion == 7) {
                costoReparacion = 100000;

            } else if (tipoReparacion == 8) {
                costoReparacion = 180000;

            } else if (tipoReparacion == 9) {
                costoReparacion = 150000;

            } else if (tipoReparacion == 10) {
                costoReparacion = 130000;

            } else {
                costoReparacion = 80000;

            }

        } else if (tipoMotor.equals("Diesel")) {
            if(tipoReparacion == 1){
                costoReparacion = 120000;
            } else if (tipoReparacion == 2) {
                costoReparacion =130000;

            } else if (tipoReparacion == 3) {
                costoReparacion = 450000;
            } else if (tipoReparacion == 4) {
                costoReparacion = 210000;

            } else if (tipoReparacion == 5) {
                costoReparacion = 150000;

            } else if (tipoReparacion == 6) {
                costoReparacion = 120000;

            } else if (tipoReparacion == 7) {
                costoReparacion = 100000;

            } else if (tipoReparacion == 8) {
                costoReparacion = 180000;

            } else if (tipoReparacion == 9) {
                costoReparacion = 150000;

            } else if (tipoReparacion == 10) {
                costoReparacion = 140000;

            } else {
                costoReparacion = 80000;

            }

        } else if (tipoMotor.equals("Hibrido")) {
            if(tipoReparacion == 1){
                costoReparacion = 180000;
            } else if (tipoReparacion == 2) {
                costoReparacion =190000;

            } else if (tipoReparacion == 3) {
                costoReparacion = 700000;
            } else if (tipoReparacion == 4) {
                costoReparacion = 300000;

            } else if (tipoReparacion == 5) {
                costoReparacion = 200000;

            } else if (tipoReparacion == 6) {
                costoReparacion = 450000;

            } else if (tipoReparacion == 7) {
                costoReparacion = 100000;

            } else if (tipoReparacion == 8) {
                costoReparacion = 210000;

            } else if (tipoReparacion == 9) {
                costoReparacion = 180000;

            } else if (tipoReparacion == 10) {
                costoReparacion = 220000;

            } else {
                costoReparacion = 80000;

            }

        } else if (tipoMotor.equals("Electrico")) {
            if(tipoReparacion == 1){
                costoReparacion = 220000;
            } else if (tipoReparacion == 2) {
                costoReparacion =230000;

            } else if (tipoReparacion == 3) {
                costoReparacion = 800000;
            } else if (tipoReparacion == 4) {
                costoReparacion = 300000;

            } else if (tipoReparacion == 5) {
                costoReparacion = 250000;

            } else if (tipoReparacion == 6) {
                costoReparacion = 0;

            } else if (tipoReparacion == 7) {
                costoReparacion = 100000;

            } else if (tipoReparacion == 8) {
                costoReparacion = 250000;

            } else if (tipoReparacion == 9) {
                costoReparacion = 180000;

            } else if (tipoReparacion == 10) {
                costoReparacion = 0;

            } else {
                costoReparacion = 80000;

            }

        }
        return costoReparacion;
    }
    public RegReparacionEntity crearReparacion(RegReparacionEntity reparacion){
        return regReparacionRepository.save(reparacion);
    }

    public RegReparacionEntity crearReparacionVehiculo(String patente, RegReparacionEntity reparacion) {
        VehiculoModel vehiculo = getVehiculo(patente);
        if (vehiculo != null) {
            int monto_reparacion = calcularCostoReparacion(patente, reparacion);
            reparacion.setPatente(vehiculo.getPatente());
            int nuevaCantidadReparaciones = vehiculo.getCantidad_reparaciones() + 1;
            vehiculo.setCantidad_reparaciones(nuevaCantidadReparaciones);
            String vehiculoUpdateUrl = "http://vehiculo-service" + "/vehiculo/" + patente;
            restTemplate.put(vehiculoUpdateUrl, vehiculo);
            reparacion.setMonto_reparacion(monto_reparacion);
            return regReparacionRepository.save(reparacion);
        } else {
            return null;
        }
    }

    public List<RegReparacionEntity> getReparacionesPorPatente(String patente) {
        return regReparacionRepository.findListByPatente(patente);
    }
/*--------------------------------------BOLETA CON REPARACIONES--------------------------------------------------------------*/
    public RegReparacionEntity getPrimeraReparacionPorPatente(String patente) {
        List<RegReparacionEntity> reparaciones = regReparacionRepository.findListByPatente(patente);
        return reparaciones.isEmpty() ? null : reparaciones.get(0);

    }

    public int calcularMontoTotalReparaciones(String patente) {
        List<RegReparacionEntity> reparaciones = getReparacionesPorPatente(patente);
        int montoTotal = 0;

        for (RegReparacionEntity reparacion : reparaciones) {
            montoTotal += calcularCostoReparacion(patente,reparacion);
        }

        return montoTotal;
    }

    public void eliminarReparacionesPorPatente(String patente) {
        regReparacionRepository.deleteByPatente(patente);
    }
/*----------------------------------------------CRUD----------------------------------------*/
    public RegReparacionEntity obtenerReparacionPorId(Long id_reparacion) {
        return regReparacionRepository.findById(id_reparacion).orElse(null);
}

    public RegReparacionEntity actualizarReparacion(Long id_reparacion, RegReparacionEntity reparacionActualizada) {
        RegReparacionEntity reparacion = regReparacionRepository.findById(id_reparacion).orElse(null);
        if (reparacion != null) {
            reparacion.setPatente(reparacionActualizada.getPatente());
            reparacion.setTipo_reparacion(reparacionActualizada.getTipo_reparacion());
            reparacion.setFecha_reparacion(reparacionActualizada.getFecha_reparacion());
            reparacion.setHora_reparacion(reparacionActualizada.getHora_reparacion());
            reparacion.setMonto_reparacion(reparacionActualizada.getMonto_reparacion());
            return regReparacionRepository.save(reparacion);
        }
        return null;
    }

    public void eliminarReparacion(Long id_reparacion) {
        regReparacionRepository.deleteById(id_reparacion);
    }

    public List<RegReparacionEntity> listarReparaciones() {
        return regReparacionRepository.findAll();
    }

    public List<RegReparacionEntity> obtenerReparacionesPatente(String patente) {
        return regReparacionRepository.findListByPatente(patente);
    }





}
