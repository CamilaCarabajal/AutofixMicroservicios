package com.example.reparacionservice.Service;

import com.example.reparacionservice.Entity.ReparacionEntity;
import com.example.reparacionservice.Repository.ReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReparacionService {
    @Autowired
    ReparacionRepository reparacionRepository;
    @Autowired
    private RestTemplate restTemplate;


    public int calculoMontoReparacion(ReparacionEntity reparacion, String motor){
        int costoReparacion= 0;
        int tipoReparacion = reparacion.getTipo_reparacion();

        switch(tipoReparacion) {
            case 1:
                if(motor == "Gasolina" || motor == "Diesel") {
                    costoReparacion = 120000;
                } else if (motor == "Hibrido") {
                    costoReparacion = 180000;

                } else if(motor == "Electrico"){
                    costoReparacion = 220000;
                }
                break;
            case 2:
                if(motor == "Gasolina" || motor == "Diesel") {
                    costoReparacion = 130000;
                } else if (motor == "Hibrido") {
                    costoReparacion = 190000;

                } else if(motor == "Electrico"){
                    costoReparacion = 230000;
                }
                break;
            case 3:
                if(motor == "Gasolina"){
                    costoReparacion = 350000;
                }else if(motor == "Diesel"){
                    costoReparacion = 450000;
                } else if (motor == "Hibrido") {
                    costoReparacion = 700000;
                } else if(motor == "Electrico"){
                    costoReparacion = 800000;
                }
                break;
            case 4:
                if(motor == "Gasolina" || motor == "Diesel") {
                    costoReparacion = 210000;
                } else if (motor == "Hibrido" || motor == "Electrico") {
                    costoReparacion = 300000;}
                break;
            case 5:
                if(motor == "Gasolina" || motor == "Diesel") {
                    costoReparacion = 150000;
                } else if (motor == "Hibrido") {
                    costoReparacion = 200000;

                } else if(motor == "Electrico"){
                    costoReparacion = 250000;
                }
                break;
            case 6:
                if(motor == "Gasolina"){
                    costoReparacion = 100000;
                }else if(motor == "Diesel"){
                    costoReparacion = 120000;
                } else if (motor == "Hibrido") {
                    costoReparacion = 450000;
                } else {
                    costoReparacion = 0;
                }
                break;
            case 7:
                if(motor == "Gasolina" || motor == "Diesel" || motor == "Hibrido" || motor == "Electrico"){
                    costoReparacion = 100000;
                }
                break;
            case 8:
                if(motor == "Gasolina" || motor == "Diesel") {
                    costoReparacion = 180000;
                } else if (motor == "Hibrido") {
                    costoReparacion = 210000;

                } else if(motor == "Electrico"){
                    costoReparacion = 250000;
                }
                break;
            case 9:
                if(motor == "Gasolina" || motor == "Diesel") {
                    costoReparacion = 150000;
                } else if (motor == "Hibrido" || motor == "Electrico") {
                    costoReparacion = 180000;}
                break;
            case 10:
                if(motor == "Gasolina"){
                    costoReparacion = 130000;
                }else if(motor == "Diesel"){
                    costoReparacion = 140000;
                } else if (motor == "Hibrido") {
                    costoReparacion = 220000;
                } else {
                    costoReparacion = 0;
                }
                break;
            case 11:
                if(motor == "Gasolina" || motor == "Diesel" || motor == "Hibrido" || motor == "Electrico"){
                    costoReparacion = 80000;
                }
                break;
            default:
                costoReparacion=0;
                break;
        }

        return costoReparacion;


    }
    public ReparacionEntity guardarReparacion(ReparacionEntity reparacion, String motor) {
        ReparacionEntity nuevaReparacion = new ReparacionEntity();
        nuevaReparacion.setId_reparacion(reparacion.getId_reparacion());
        nuevaReparacion.setPatente(reparacion.getPatente());
        nuevaReparacion.setTipo_reparacion(reparacion.getTipo_reparacion());
        nuevaReparacion.setMonto_reparacion(calculoMontoReparacion(reparacion,motor));
        return reparacionRepository.save(nuevaReparacion);
    }
    public ReparacionEntity obtenerReparacionPorId(Long id_reparacion) {
        return reparacionRepository.findById(id_reparacion).orElse(null);
    }

    public ReparacionEntity actualizarReparacion(ReparacionEntity reparacion) {
        return reparacionRepository.save(reparacion);
    }

    public void eliminarReparacion(Long id_reparacion) {
        reparacionRepository.deleteById(id_reparacion);
    }

}
