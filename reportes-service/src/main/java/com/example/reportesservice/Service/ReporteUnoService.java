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

import java.time.YearMonth;
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
    //Lo mismo con el monto de cada tipo reparacion

    public List<Integer> listaReparacionUno(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==1 && YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
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

    public List<Integer> listaMontoUno(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> montoReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==1 && YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
                if(vehiculo.getModelo().equals("Sedan")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                       montoReparacion.set(0,montoReparacion.get(0)+120000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(0,montoReparacion.get(0)+180000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(0,montoReparacion.get(0)+220000);
                    }
                }if(vehiculo.getModelo().equals("HatchBack")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(1,montoReparacion.get(1)+120000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(1,montoReparacion.get(1)+180000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(1,montoReparacion.get(1)+220000);
                    }
                }if(vehiculo.getModelo().equals("SUV")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(2,montoReparacion.get(2)+120000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(2,montoReparacion.get(2)+180000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(2,montoReparacion.get(2)+220000);
                    }
                }if(vehiculo.getModelo().equals("Pickup")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(3,montoReparacion.get(3)+120000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(3,montoReparacion.get(3)+180000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(3,montoReparacion.get(3)+220000);
                    }
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(4,montoReparacion.get(4)+120000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(4,montoReparacion.get(4)+180000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(4,montoReparacion.get(4)+220000);
                    }
                }
            }
        }
        return montoReparacion;
    }
    /*----------------------------------------------------------------------------------------------------------------*/
    public List<Integer> listaReparacionDos(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==2 || YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
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

    public List<Integer> listaMontoDos(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> montoReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==2 && YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
                if(vehiculo.getModelo().equals("Sedan")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(0,montoReparacion.get(0)+130000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(0,montoReparacion.get(0)+190000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(0,montoReparacion.get(0)+230000);
                    }
                }if(vehiculo.getModelo().equals("HatchBack")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(1,montoReparacion.get(1)+130000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(1,montoReparacion.get(1)+190000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(1,montoReparacion.get(1)+230000);
                    }
                }if(vehiculo.getModelo().equals("SUV")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(2,montoReparacion.get(2)+130000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(2,montoReparacion.get(2)+190000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(2,montoReparacion.get(2)+230000);
                    }
                }if(vehiculo.getModelo().equals("Pickup")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(3,montoReparacion.get(3)+130000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(3,montoReparacion.get(3)+190000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(3,montoReparacion.get(3)+230000);
                    }
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(4,montoReparacion.get(4)+130000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(4,montoReparacion.get(4)+190000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(4,montoReparacion.get(4)+230000);
                    }
                }
            }
        }
        return montoReparacion;
    }
    /*----------------------------------------------------------------------------------------------------------------*/

    public List<Integer> listaReparacionTres(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==3 || YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
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
    public List<Integer> listaMontoTres(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> montoReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==3 && YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
                if(vehiculo.getModelo().equals("Sedan")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(0,montoReparacion.get(0)+350000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(0,montoReparacion.get(0)+450000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(0,montoReparacion.get(0)+700000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(0,montoReparacion.get(0)+800000);
                    }
                }if(vehiculo.getModelo().equals("HatchBack")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(1,montoReparacion.get(1)+350000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(1,montoReparacion.get(1)+450000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(1,montoReparacion.get(1)+700000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(1,montoReparacion.get(1)+800000);
                    }
                }if(vehiculo.getModelo().equals("SUV")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(2,montoReparacion.get(2)+350000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(2,montoReparacion.get(2)+450000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(2,montoReparacion.get(2)+700000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(2,montoReparacion.get(2)+800000);
                    }
                }if(vehiculo.getModelo().equals("Pickup")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(3,montoReparacion.get(3)+350000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(3,montoReparacion.get(3)+450000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(3,montoReparacion.get(3)+700000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(3,montoReparacion.get(3)+800000);
                    }
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(4,montoReparacion.get(4)+350000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(4,montoReparacion.get(4)+450000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(4,montoReparacion.get(4)+700000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(4,montoReparacion.get(4)+800000);
                    }
                }
            }
        }
        return montoReparacion;
    }
    /*----------------------------------------------------------------------------------------------------------------*/


    public List<Integer> listaReparacionCuatro(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==4 || YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
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

    public List<Integer> listaMontoCuatro(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> montoReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==4 && YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
                if(vehiculo.getModelo().equals("Sedan")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(0,montoReparacion.get(0)+210000);}
                    else if(vehiculo.getMotor().equals("Hibrido")|| vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(0,montoReparacion.get(0)+300000);
                    }
                }if(vehiculo.getModelo().equals("HatchBack")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(1,montoReparacion.get(1)+210000);}
                    else if(vehiculo.getMotor().equals("Hibrido")|| vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(1,montoReparacion.get(1)+300000);
                    }
                }if(vehiculo.getModelo().equals("SUV")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(2,montoReparacion.get(2)+210000);}
                    else if(vehiculo.getMotor().equals("Hibrido")|| vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(2,montoReparacion.get(2)+300000);
                    }
                }if(vehiculo.getModelo().equals("Pickup")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(3,montoReparacion.get(3)+210000);}
                    else if(vehiculo.getMotor().equals("Hibrido")|| vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(3,montoReparacion.get(3)+300000);
                    }
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(4,montoReparacion.get(4)+210000);}
                    else if(vehiculo.getMotor().equals("Hibrido")|| vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(4,montoReparacion.get(4)+300000);
                    }
                }
            }
        }
        return montoReparacion;
    }
   /*----------------------------------------------------------------------------------------------------------------*/



    public List<Integer> listaReparacionCinco(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==5 || YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
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

    public List<Integer> listaMontoCinco(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> montoReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==5 && YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
                if(vehiculo.getModelo().equals("Sedan")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(0,montoReparacion.get(0)+130000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(0,montoReparacion.get(0)+190000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(0,montoReparacion.get(0)+230000);
                    }
                }if(vehiculo.getModelo().equals("HatchBack")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(1,montoReparacion.get(1)+130000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(1,montoReparacion.get(1)+190000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(1,montoReparacion.get(1)+230000);
                    }
                }if(vehiculo.getModelo().equals("SUV")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(2,montoReparacion.get(2)+130000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(2,montoReparacion.get(2)+190000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(2,montoReparacion.get(2)+230000);
                    }
                }if(vehiculo.getModelo().equals("Pickup")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(3,montoReparacion.get(3)+130000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(3,montoReparacion.get(3)+190000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(3,montoReparacion.get(3)+230000);
                    }
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(4,montoReparacion.get(4)+130000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(4,montoReparacion.get(4)+190000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(4,montoReparacion.get(4)+230000);
                    }
                }
            }
        }
        return montoReparacion;
    }
    /*----------------------------------------------------------------------------------------------------------------*/


    public List<Integer> listaReparacionSeis(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==6 || YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
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
    public List<Integer> listaMontoSeis(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> montoReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==6 && YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
                if(vehiculo.getModelo().equals("Sedan")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(0,montoReparacion.get(0)+100000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(0,montoReparacion.get(0)+120000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(0,montoReparacion.get(0)+450000);
                    }else{
                        montoReparacion.set(0,montoReparacion.get(0)+0);
                    }
                }if(vehiculo.getModelo().equals("HatchBack")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(1,montoReparacion.get(1)+100000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(1,montoReparacion.get(1)+120000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(1,montoReparacion.get(1)+450000);
                    }else{
                        montoReparacion.set(1,montoReparacion.get(1)+0);
                    }
                }if(vehiculo.getModelo().equals("SUV")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(2,montoReparacion.get(2)+100000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(2,montoReparacion.get(2)+120000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(2,montoReparacion.get(2)+450000);
                    }else{
                        montoReparacion.set(2,montoReparacion.get(2)+0);
                    }
                }if(vehiculo.getModelo().equals("Pickup")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(3,montoReparacion.get(3)+100000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(3,montoReparacion.get(3)+120000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(3,montoReparacion.get(3)+450000);
                    }else{
                        montoReparacion.set(3,montoReparacion.get(3)+0);
                    }
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(4,montoReparacion.get(4)+100000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(4,montoReparacion.get(4)+120000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(4,montoReparacion.get(4)+450000);
                    }else{
                        montoReparacion.set(4,montoReparacion.get(4)+0);
                    }
                }
            }
        }
        return montoReparacion;
    }
    /*----------------------------------------------------------------------------------------------------------------*/

    public List<Integer> listaReparacionSiete(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==7 || YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
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

    public List<Integer> listaMontoSiete(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> montoReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==7 && YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
                if(vehiculo.getModelo().equals("Sedan")){
                    montoReparacion.set(0,montoReparacion.get(0)+100000);
                }if(vehiculo.getModelo().equals("HatchBack")){
                    montoReparacion.set(1,montoReparacion.get(1)+100000);
                }if(vehiculo.getModelo().equals("SUV")){
                    montoReparacion.set(2,montoReparacion.get(2)+100000);
                }if(vehiculo.getModelo().equals("Pickup")){
                    montoReparacion.set(3,montoReparacion.get(3)+100000);
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    montoReparacion.set(4,montoReparacion.get(4)+100000);
                }
            }
        }
        return montoReparacion;
    }
    /*----------------------------------------------------------------------------------------------------------------*/

    public List<Integer> listaReparacionOcho(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==8 || YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
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

    public List<Integer> listaMontoOcho(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> montoReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==8 && YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
                if(vehiculo.getModelo().equals("Sedan")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(0,montoReparacion.get(0)+180000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(0,montoReparacion.get(0)+210000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(0,montoReparacion.get(0)+250000);
                    }
                }if(vehiculo.getModelo().equals("HatchBack")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(1,montoReparacion.get(1)+180000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(1,montoReparacion.get(1)+210000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(1,montoReparacion.get(1)+250000);
                    }
                }if(vehiculo.getModelo().equals("SUV")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(2,montoReparacion.get(2)+180000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(2,montoReparacion.get(2)+210000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(2,montoReparacion.get(2)+250000);
                    }
                }if(vehiculo.getModelo().equals("Pickup")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(3,montoReparacion.get(3)+180000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(3,montoReparacion.get(3)+210000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(3,montoReparacion.get(3)+250000);
                    }
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(4,montoReparacion.get(4)+180000);}
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(4,montoReparacion.get(4)+210000);
                    }
                    else if(vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(4,montoReparacion.get(4)+250000);
                    }
                }
            }
        }
        return montoReparacion;
    }
    /*----------------------------------------------------------------------------------------------------------------*/


    public List<Integer> listaReparacionNueve(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==9 || YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
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
    public List<Integer> listaMontoNueve(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> montoReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==9 && YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
                if(vehiculo.getModelo().equals("Sedan")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(0,montoReparacion.get(0)+150000);}
                    else if(vehiculo.getMotor().equals("Hibrido")|| vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(0,montoReparacion.get(0)+180000);
                    }
                }if(vehiculo.getModelo().equals("HatchBack")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(1,montoReparacion.get(1)+150000);}
                    else if(vehiculo.getMotor().equals("Hibrido")|| vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(1,montoReparacion.get(1)+180000);
                    }
                }if(vehiculo.getModelo().equals("SUV")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(2,montoReparacion.get(2)+150000);}
                    else if(vehiculo.getMotor().equals("Hibrido")|| vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(2,montoReparacion.get(2)+180000);
                    }
                }if(vehiculo.getModelo().equals("Pickup")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(3,montoReparacion.get(3)+150000);}
                    else if(vehiculo.getMotor().equals("Hibrido")|| vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(3,montoReparacion.get(3)+180000);
                    }
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    if(vehiculo.getMotor().equals("Gasolina")|| vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(4,montoReparacion.get(4)+150000);}
                    else if(vehiculo.getMotor().equals("Hibrido")|| vehiculo.getMotor().equals("Electrico")){
                        montoReparacion.set(4,montoReparacion.get(4)+180000);
                    }
                }
            }
        }
        return montoReparacion;
    }
    /*----------------------------------------------------------------------------------------------------------------*/


    public List<Integer> listaReparacionDiez(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==10 || YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
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

    public List<Integer> listaMontoDiez(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> montoReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==10 && YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
                if(vehiculo.getModelo().equals("Sedan")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(0,montoReparacion.get(0)+130000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(0,montoReparacion.get(0)+140000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(0,montoReparacion.get(0)+220000);
                    }else{
                        montoReparacion.set(0,montoReparacion.get(0)+0);
                    }
                }if(vehiculo.getModelo().equals("HatchBack")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(1,montoReparacion.get(1)+130000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(1,montoReparacion.get(1)+140000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(1,montoReparacion.get(1)+220000);
                    }else{
                        montoReparacion.set(1,montoReparacion.get(1)+0);
                    }
                }if(vehiculo.getModelo().equals("SUV")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(2,montoReparacion.get(2)+130000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(2,montoReparacion.get(2)+140000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(2,montoReparacion.get(2)+220000);
                    }else{
                        montoReparacion.set(2,montoReparacion.get(2)+0);
                    }
                }if(vehiculo.getModelo().equals("Pickup")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(3,montoReparacion.get(3)+130000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(3,montoReparacion.get(3)+140000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(3,montoReparacion.get(3)+220000);
                    }else{
                        montoReparacion.set(3,montoReparacion.get(3)+0);
                    }
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    if(vehiculo.getMotor().equals("Gasolina")){
                        montoReparacion.set(4,montoReparacion.get(4)+130000);}
                    else if(vehiculo.getMotor().equals("Diesel")){
                        montoReparacion.set(4,montoReparacion.get(4)+140000);
                    }
                    else if(vehiculo.getMotor().equals("Hibrido")){
                        montoReparacion.set(4,montoReparacion.get(4)+220000);
                    }else{
                        montoReparacion.set(4,montoReparacion.get(4)+0);
                    }
                }
            }
        }
        return montoReparacion;
    }
    /*----------------------------------------------------------------------------------------------------------------*/


    public List<Integer> listaReparacionOnce(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> cantidadReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==11 || YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
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
    public List<Integer> listaMontoOnce(String patente, YearMonth fecha){
        VehiculoModel vehiculo = getVehiculo(patente);
        List<RegReparacionModel> regrepair = getReparaciones();
        List<Integer> montoReparacion = new ArrayList<>(Collections.nCopies(5, 0));
        for(RegReparacionModel reparacion : regrepair){
            if(reparacion.getTipo_reparacion()==11 && YearMonth.from(reparacion.getFecha_reparacion()).equals(fecha)){
                if(vehiculo.getModelo().equals("Sedan")){
                    montoReparacion.set(0,montoReparacion.get(0)+80000);
                }if(vehiculo.getModelo().equals("HatchBack")){
                    montoReparacion.set(1,montoReparacion.get(1)+80000);
                }if(vehiculo.getModelo().equals("SUV")){
                    montoReparacion.set(2,montoReparacion.get(2)+80000);
                }if(vehiculo.getModelo().equals("Pickup")){
                    montoReparacion.set(3,montoReparacion.get(3)+80000);
                }if(vehiculo.getModelo().equals("Furgoneta")){
                    montoReparacion.set(4,montoReparacion.get(4)+80000);
                }
            }
        }
        return montoReparacion;
    }
    /*----------------------------------------------------------------------------------------------------------------*/


}
