package com.example.reportesservice.Service;

import com.example.reportesservice.Model.RegReparacionModel;
import com.example.reportesservice.Model.VehiculoModel;
import com.example.reportesservice.Repository.ReporteUnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public List<RegReparacionModel> getReparaciones() {
        ResponseEntity<List<RegReparacionModel>> responseEntity = restTemplate.exchange(
                "http://repairvehicle-service/regrepair/lista",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RegReparacionModel>>() {}
        );
        List<RegReparacionModel> reparacionList = responseEntity.getBody();

        return reparacionList;
    }

    //CALCULO CANTIDAD REPARACIONES DEPENDIENDO DEL TIPO DE VEHICULO
    //Se haran listas de cada tipo de raparacion con las cantidades de reparaciones que se hicieron a cada tipo de vehiculo

    public List<Integer> listaReparacionUno(String patente){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==1){
                if(vehiculo.getModelo().equals("Sedan")){
                    cantidadReparacion.set(0,cantidadReparacion.get(0)+1);
                }if(vehiculo.getModelo().equals("HatchBack")){
                    cantidadReparacion.set(1,cantidadReparacion.get(1)+1);
                }if(vehiculo.getModelo().equals("SUV")){
                    cantidadReparacion.set(2,cantidadReparacion.get(2)+1);
                }if(vehiculo.getModelo().equals("Pickup")){
                    cantidadReparacion.set(3,cantidadReparacion.get(3)+1);
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    cantidadReparacion.set(4,cantidadReparacion.get(4)+1);
                }
            }
        }
        return cantidadReparacion;
    }
    public List<Integer> listaReparacionDos(String patente){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==2){
                if(vehiculo.getModelo().equals("Sedan")){
                    cantidadReparacion.set(0,cantidadReparacion.get(0)+1);
                }if(vehiculo.getModelo().equals("HatchBack")){
                    cantidadReparacion.set(1,cantidadReparacion.get(1)+1);
                }if(vehiculo.getModelo().equals("SUV")){
                    cantidadReparacion.set(2,cantidadReparacion.get(2)+1);
                }if(vehiculo.getModelo().equals("Pickup")){
                    cantidadReparacion.set(3,cantidadReparacion.get(3)+1);
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    cantidadReparacion.set(4,cantidadReparacion.get(4)+1);
                }
            }
        }
        return cantidadReparacion;
    }

    public List<Integer> listaReparacionTres(String patente){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==3){
                if(vehiculo.getModelo().equals("Sedan")){
                    cantidadReparacion.set(0,cantidadReparacion.get(0)+1);
                }if(vehiculo.getModelo().equals("HatchBack")){
                    cantidadReparacion.set(1,cantidadReparacion.get(1)+1);
                }if(vehiculo.getModelo().equals("SUV")){
                    cantidadReparacion.set(2,cantidadReparacion.get(2)+1);
                }if(vehiculo.getModelo().equals("Pickup")){
                    cantidadReparacion.set(3,cantidadReparacion.get(3)+1);
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    cantidadReparacion.set(4,cantidadReparacion.get(4)+1);
                }
            }
        }
        return cantidadReparacion;
    }

    public List<Integer> listaReparacionCuatro(String patente){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==4){
                if(vehiculo.getModelo().equals("Sedan")){
                    cantidadReparacion.set(0,cantidadReparacion.get(0)+1);
                }if(vehiculo.getModelo().equals("HatchBack")){
                    cantidadReparacion.set(1,cantidadReparacion.get(1)+1);
                }if(vehiculo.getModelo().equals("SUV")){
                    cantidadReparacion.set(2,cantidadReparacion.get(2)+1);
                }if(vehiculo.getModelo().equals("Pickup")){
                    cantidadReparacion.set(3,cantidadReparacion.get(3)+1);
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    cantidadReparacion.set(4,cantidadReparacion.get(4)+1);
                }
            }
        }
        return cantidadReparacion;
    }

    public List<Integer> listaReparacionCinco(String patente){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==5){
                if(vehiculo.getModelo().equals("Sedan")){
                    cantidadReparacion.set(0,cantidadReparacion.get(0)+1);
                }if(vehiculo.getModelo().equals("HatchBack")){
                    cantidadReparacion.set(1,cantidadReparacion.get(1)+1);
                }if(vehiculo.getModelo().equals("SUV")){
                    cantidadReparacion.set(2,cantidadReparacion.get(2)+1);
                }if(vehiculo.getModelo().equals("Pickup")){
                    cantidadReparacion.set(3,cantidadReparacion.get(3)+1);
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    cantidadReparacion.set(4,cantidadReparacion.get(4)+1);
                }
            }
        }
        return cantidadReparacion;
    }

    public List<Integer> listaReparacionSeis(String patente){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==6){
                if(vehiculo.getModelo().equals("Sedan")){
                    cantidadReparacion.set(0,cantidadReparacion.get(0)+1);
                }if(vehiculo.getModelo().equals("HatchBack")){
                    cantidadReparacion.set(1,cantidadReparacion.get(1)+1);
                }if(vehiculo.getModelo().equals("SUV")){
                    cantidadReparacion.set(2,cantidadReparacion.get(2)+1);
                }if(vehiculo.getModelo().equals("Pickup")){
                    cantidadReparacion.set(3,cantidadReparacion.get(3)+1);
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    cantidadReparacion.set(4,cantidadReparacion.get(4)+1);
                }
            }
        }
        return cantidadReparacion;
    }

    public List<Integer> listaReparacionSiete(String patente){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==7){
                if(vehiculo.getModelo().equals("Sedan")){
                    cantidadReparacion.set(0,cantidadReparacion.get(0)+1);
                }if(vehiculo.getModelo().equals("HatchBack")){
                    cantidadReparacion.set(1,cantidadReparacion.get(1)+1);
                }if(vehiculo.getModelo().equals("SUV")){
                    cantidadReparacion.set(2,cantidadReparacion.get(2)+1);
                }if(vehiculo.getModelo().equals("Pickup")){
                    cantidadReparacion.set(3,cantidadReparacion.get(3)+1);
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    cantidadReparacion.set(4,cantidadReparacion.get(4)+1);
                }
            }
        }
        return cantidadReparacion;
    }

    public List<Integer> listaReparacionOcho(String patente){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==8){
                if(vehiculo.getModelo().equals("Sedan")){
                    cantidadReparacion.set(0,cantidadReparacion.get(0)+1);
                }if(vehiculo.getModelo().equals("HatchBack")){
                    cantidadReparacion.set(1,cantidadReparacion.get(1)+1);
                }if(vehiculo.getModelo().equals("SUV")){
                    cantidadReparacion.set(2,cantidadReparacion.get(2)+1);
                }if(vehiculo.getModelo().equals("Pickup")){
                    cantidadReparacion.set(3,cantidadReparacion.get(3)+1);
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    cantidadReparacion.set(4,cantidadReparacion.get(4)+1);
                }
            }
        }
        return cantidadReparacion;
    }

    public List<Integer> listaReparacionNueve(String patente){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==9){
                if(vehiculo.getModelo().equals("Sedan")){
                    cantidadReparacion.set(0,cantidadReparacion.get(0)+1);
                }if(vehiculo.getModelo().equals("HatchBack")){
                    cantidadReparacion.set(1,cantidadReparacion.get(1)+1);
                }if(vehiculo.getModelo().equals("SUV")){
                    cantidadReparacion.set(2,cantidadReparacion.get(2)+1);
                }if(vehiculo.getModelo().equals("Pickup")){
                    cantidadReparacion.set(3,cantidadReparacion.get(3)+1);
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    cantidadReparacion.set(4,cantidadReparacion.get(4)+1);
                }
            }
        }
        return cantidadReparacion;
    }

    public List<Integer> listaReparacionDiez(String patente){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==10){
                if(vehiculo.getModelo().equals("Sedan")){
                    cantidadReparacion.set(0,cantidadReparacion.get(0)+1);
                }if(vehiculo.getModelo().equals("HatchBack")){
                    cantidadReparacion.set(1,cantidadReparacion.get(1)+1);
                }if(vehiculo.getModelo().equals("SUV")){
                    cantidadReparacion.set(2,cantidadReparacion.get(2)+1);
                }if(vehiculo.getModelo().equals("Pickup")){
                    cantidadReparacion.set(3,cantidadReparacion.get(3)+1);
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    cantidadReparacion.set(4,cantidadReparacion.get(4)+1);
                }
            }
        }
        return cantidadReparacion;
    }

    public List<Integer> listaReparacionOnce(String patente){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==11){
                if(vehiculo.getModelo().equals("Sedan")){
                    cantidadReparacion.set(0,cantidadReparacion.get(0)+1);
                }if(vehiculo.getModelo().equals("HatchBack")){
                    cantidadReparacion.set(1,cantidadReparacion.get(1)+1);
                }if(vehiculo.getModelo().equals("SUV")){
                    cantidadReparacion.set(2,cantidadReparacion.get(2)+1);
                }if(vehiculo.getModelo().equals("Pickup")){
                    cantidadReparacion.set(3,cantidadReparacion.get(3)+1);
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    cantidadReparacion.set(4,cantidadReparacion.get(4)+1);
                }
            }
        }
        return cantidadReparacion;
    }


}
