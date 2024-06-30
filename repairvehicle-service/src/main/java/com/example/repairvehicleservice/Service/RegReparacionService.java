package com.example.repairvehicleservice.Service;

import com.example.repairvehicleservice.Entity.RegReparacionEntity;
import com.example.repairvehicleservice.Entity.ReparacionEntity;
import com.example.repairvehicleservice.Entity.ResultadoEntity;
import com.example.repairvehicleservice.Entity.ResultadosDosEntity;
import com.example.repairvehicleservice.Model.VehiculoModel;
import com.example.repairvehicleservice.Repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RegReparacionService {

    @Autowired
    RegReparacionRepository regReparacionRepository;
    @Autowired
    ReparacionRepository reparacionRepository;
    @Autowired
    ResultadosDosRepository resultadosDosRepository;
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
    @Autowired
    ResultadoRepository resultadoRepository;
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

    public List<VehiculoModel> getVehiculos() {
        try {
            ResponseEntity<VehiculoModel[]> responseEntity = restTemplate.exchange(
                    "http://vehiculo-service/vehiculo/lista",
                    HttpMethod.GET,
                    null,
                    VehiculoModel[].class
            );
            VehiculoModel[] vehiculosArray = responseEntity.getBody();
            if (vehiculosArray != null) {
                return Arrays.asList(vehiculosArray);
            } else {
                return new ArrayList<>();
            }
        } catch (RestClientException e) {
            // Maneja el error de red o respuesta no exitosa
            return new ArrayList<>();
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
        ReparacionEntity auxiliar = new ReparacionEntity();
        if (vehiculo != null) {
            int monto_reparacion = calcularCostoReparacion(patente, reparacion);
            reparacion.setPatente(vehiculo.getPatente());
            int nuevaCantidadReparaciones = vehiculo.getCantidad_reparaciones() + 1;
            vehiculo.setCantidad_reparaciones(nuevaCantidadReparaciones);
            String vehiculoUpdateUrl = "http://vehiculo-service" + "/vehiculo/" + patente;
            restTemplate.put(vehiculoUpdateUrl, vehiculo);
            reparacion.setMonto_reparacion(monto_reparacion);

            //Se agrega un auxiliar para que mantenga las reparaciones que se registraron.
            auxiliar.setId_reparacion(reparacion.getId_regrepair());
            auxiliar.setPatente(vehiculo.getPatente());
            auxiliar.setTipo_reparacion(reparacion.getTipo_reparacion());
            auxiliar.setFecha_reparacion(reparacion.getFecha_reparacion());
            auxiliar.setHora_reparacion(reparacion.getHora_reparacion());
            auxiliar.setMonto_reparacion(reparacion.getMonto_reparacion());
            reparacionRepository.save(auxiliar);
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

/*----------------------------------------RESULTADOS PARA REPORTE UNO-----------------------------------------------*/

    public List<ResultadoEntity> calcularCostosReparacion(int mes, int ano) {
        List<ReparacionEntity> reparaciones = listaReparacion();
        List<ResultadoEntity> resultados = new ArrayList<>();

        for (ReparacionEntity reparacion : reparaciones) {
            if (reparacion.getFecha_reparacion().getMonthValue() == mes && reparacion.getFecha_reparacion().getYear() == ano) {
                VehiculoModel vehiculo = getVehiculo(reparacion.getPatente());
                if (vehiculo == null) {
                    System.err.println("Vehículo no encontrado para la patente: " + reparacion.getPatente());
                    continue;
                }
                int costoReparacion = calcularCostoReparaciones(vehiculo.getPatente(), reparacion);
                ResultadoEntity resultadoExistente = findResultadoByModeloAndTipoReparacion(resultados, vehiculo.getModelo(), reparacion.getTipo_reparacion());

                if (resultadoExistente != null) {
                    resultadoExistente.setCosto_reparacion(resultadoExistente.getCosto_reparacion() + costoReparacion);
                    resultadoExistente.setConteo_reparaciones(resultadoExistente.getConteo_reparaciones() + 1);
                } else {
                    ResultadoEntity nuevoResultado = new ResultadoEntity(null, vehiculo.getPatente(), vehiculo.getModelo(), reparacion.getTipo_reparacion(), costoReparacion, 1);
                    resultados.add(nuevoResultado);
                }
            }
        }

        List<ResultadoEntity> resultadosFinales = new ArrayList<>();
        for (ResultadoEntity resultado : resultados) {
            ResultadoEntity resultadoExistente = findResultadoByModeloAndTipoReparacion(resultadosFinales, resultado.getModelo(), resultado.getTipo_reparacion());

            if (resultadoExistente != null) {
                resultadoExistente.setCosto_reparacion(resultadoExistente.getCosto_reparacion() + resultado.getCosto_reparacion());
                resultadoExistente.setConteo_reparaciones(resultadoExistente.getConteo_reparaciones() + resultado.getConteo_reparaciones());
            } else {
                resultadosFinales.add(new ResultadoEntity(null, "", resultado.getModelo(), resultado.getTipo_reparacion(), resultado.getCosto_reparacion(), resultado.getConteo_reparaciones()));
            }
        }

        try {
            // Guardar resultados en la base de datos
            resultadoRepository.saveAll(resultadosFinales);
        } catch (DataAccessException e) {
            // Manejar errores al guardar en la base de datos
            System.err.println("Error al guardar resultados: " + e.getMessage());
            return new ArrayList<>();
        }

        return resultadosFinales;
    }



    private ResultadoEntity findResultadoByModeloAndTipoReparacion(List<ResultadoEntity> resultados, String modelo, int tipoReparacion) {
        for (ResultadoEntity resultado : resultados) {
            if (resultado.getModelo().equals(modelo) && resultado.getTipo_reparacion() == tipoReparacion) {
                return resultado;
            }
        }
        return null;
    }

    public int calcularCostoReparaciones(String patente, ReparacionEntity reparacion){
        int costoReparacion = 0;
        VehiculoModel vehiculo = getVehiculo(patente);
        String tipoMotor = vehiculo.getMotor();
        int tipoReparacion = reparacion.getTipo_reparacion();

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

    public List<ReparacionEntity> listaReparacion() {
        return reparacionRepository.findAll();
    }

    /*-------------------------------------------RESULTADO REPORTE DOS ---------------------------------------*/
    public List<ResultadosDosEntity> calcularReparacionesPorMes(int mes, int ano) {
        List<ReparacionEntity> reparaciones = listaReparacion(); // Obtener la lista de reparaciones
        List<ResultadosDosEntity> resultados = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int mesActual = mes - i;
            int anoActual = ano;
            if (mesActual <= 0) {
                mesActual += 12;
                anoActual--;
            }

            for (ReparacionEntity reparacion : reparaciones) {
                LocalDate fechaReparacion = reparacion.getFecha_reparacion();
                if (fechaReparacion.getMonthValue() == mesActual && fechaReparacion.getYear() == anoActual) {
                    int tipoReparacion = reparacion.getTipo_reparacion();
                    int costoReparacion = calcularCostoReparaciones(reparacion.getPatente(), reparacion);

                    ResultadosDosEntity resultadoExistente = findResultadoPorTipo(resultados, tipoReparacion, mesActual, anoActual);
                    if (resultadoExistente != null) {
                        resultadoExistente.setCantidad_reparaciones(resultadoExistente.getCantidad_reparaciones() + 1);
                        resultadoExistente.setMonto_total_reparaciones(resultadoExistente.getMonto_total_reparaciones() + costoReparacion);
                    } else {
                        ResultadosDosEntity nuevoResultado = new ResultadosDosEntity(null, tipoReparacion, 1, costoReparacion, mesActual, anoActual, 0.0, 0.0);
                        resultados.add(nuevoResultado);
                    }
                }
            }
        }

        calcularVariaciones(resultados);

        try {
            // Guardar los resultados en la base de datos
            resultadosDosRepository.saveAll(resultados);
        } catch (DataAccessException e) {
            // Manejar errores al guardar en la base de datos
            System.err.println("Error al guardar resultados: " + e.getMessage());
        }

        return resultados;
    }

    private ResultadosDosEntity findResultadoPorTipo(List<ResultadosDosEntity> resultados, int tipoReparacion, int mes, int ano) {
        for (ResultadosDosEntity resultado : resultados) {
            if (resultado.getTipo_reparacion() == tipoReparacion && resultado.getMes() == mes && resultado.getAno() == ano) {
                return resultado;
            }
        }
        return null;
    }

    private void calcularVariaciones(List<ResultadosDosEntity> resultados) {
        for (ResultadosDosEntity actual : resultados) {
            for (ResultadosDosEntity previo : resultados) {
                if (actual.getTipo_reparacion() == previo.getTipo_reparacion() &&
                        actual.getMes() == previo.getMes() - 1 && actual.getAno() == previo.getAno()) {

                    double variacionCantidad = ((double) (actual.getCantidad_reparaciones() - previo.getCantidad_reparaciones()) / previo.getCantidad_reparaciones()) * 100;
                    double variacionMonto = ((double) (actual.getMonto_total_reparaciones() - previo.getMonto_total_reparaciones()) / previo.getMonto_total_reparaciones()) * 100;

                    actual.setVariacion_cantidad(variacionCantidad);
                    actual.setVariacion_monto(variacionMonto);
                }
            }
        }
    }

}
