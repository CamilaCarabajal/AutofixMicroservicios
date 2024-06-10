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


    public int calculoMontoReparacion(ReparacionEntity reparacion) {
        int costoReparacion = 0;
        int tipoReparacion = reparacion.getTipo_reparacion();
        String motor = reparacion.getTipo_motor();

        switch(tipoReparacion) {
            case 1:
                if ("Gasolina".equals(motor) || "Diesel".equals(motor)) {
                    costoReparacion = 120000;
                } else if ("Hibrido".equals(motor)) {
                    costoReparacion = 180000;
                } else if ("Electrico".equals(motor)) {
                    costoReparacion = 220000;
                }
                break;
            case 2:
                if ("Gasolina".equals(motor) || "Diesel".equals(motor)) {
                    costoReparacion = 130000;
                } else if ("Hibrido".equals(motor)) {
                    costoReparacion = 190000;
                } else if ("Electrico".equals(motor)) {
                    costoReparacion = 230000;
                }
                break;
            case 3:
                if ("Gasolina".equals(motor)) {
                    costoReparacion = 350000;
                } else if ("Diesel".equals(motor)) {
                    costoReparacion = 450000;
                } else if ("Hibrido".equals(motor)) {
                    costoReparacion = 700000;
                } else if ("Electrico".equals(motor)) {
                    costoReparacion = 800000;
                }
                break;
            case 4:
                if ("Gasolina".equals(motor) || "Diesel".equals(motor)) {
                    costoReparacion = 210000;
                } else if ("Hibrido".equals(motor) || "Electrico".equals(motor)) {
                    costoReparacion = 300000;
                }
                break;
            case 5:
                if ("Gasolina".equals(motor) || "Diesel".equals(motor)) {
                    costoReparacion = 150000;
                } else if ("Hibrido".equals(motor)) {
                    costoReparacion = 200000;
                } else if ("Electrico".equals(motor)) {
                    costoReparacion = 250000;
                }
                break;
            case 6:
                if ("Gasolina".equals(motor)) {
                    costoReparacion = 100000;
                } else if ("Diesel".equals(motor)) {
                    costoReparacion = 120000;
                } else if ("Hibrido".equals(motor)) {
                    costoReparacion = 450000;
                } else {
                    costoReparacion = 0;
                }
                break;
            case 7:
                if ("Gasolina".equals(motor) || "Diesel".equals(motor) || "Hibrido".equals(motor) || "Electrico".equals(motor)) {
                    costoReparacion = 100000;
                }
                break;
            case 8:
                if ("Gasolina".equals(motor) || "Diesel".equals(motor)) {
                    costoReparacion = 180000;
                } else if ("Hibrido".equals(motor)) {
                    costoReparacion = 210000;
                } else if ("Electrico".equals(motor)) {
                    costoReparacion = 250000;
                }
                break;
            case 9:
                if ("Gasolina".equals(motor) || "Diesel".equals(motor)) {
                    costoReparacion = 150000;
                } else if ("Hibrido".equals(motor) || "Electrico".equals(motor)) {
                    costoReparacion = 180000;
                }
                break;
            case 10:
                if ("Gasolina".equals(motor)) {
                    costoReparacion = 130000;
                } else if ("Diesel".equals(motor)) {
                    costoReparacion = 140000;
                } else if ("Hibrido".equals(motor)) {
                    costoReparacion = 220000;
                } else {
                    costoReparacion = 0;
                }
                break;
            case 11:
                if ("Gasolina".equals(motor) || "Diesel".equals(motor) || "Hibrido".equals(motor) || "Electrico".equals(motor)) {
                    costoReparacion = 80000;
                }
                break;
            default:
                costoReparacion = 0;
                break;
        }
        return costoReparacion;
    }

    public ReparacionEntity guardarReparacion(ReparacionEntity reparacion) {
        reparacion.setMonto_reparacion(calculoMontoReparacion(reparacion));
        return reparacionRepository.save(reparacion);
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
