package com.example.repairvehicleservice.Service;

import com.example.repairvehicleservice.Entity.DescuentoEntity;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

    /*-----------------------RECARGOS-------------------------*/
    public double calcularRecargoKilometraje(String patente){
        double recargoKilometraje = 0.0;
        VehiculoModel vehiculo = getVehiculo(patente);
        int kilometraje = vehiculo.getKilometraje();
        String modelo = vehiculo.getModelo();

        if (kilometraje>=0 && kilometraje <=5000){
            recargoKilometraje = 0.0;
        } else if (kilometraje>=5001 && kilometraje<=12000) {
            if(modelo == "Sedan" || modelo == "Hatchback"){
                recargoKilometraje = 0.03;
            } else if (modelo == "SUV" || modelo == "Pickup" || modelo == "Furgoneta") {
                recargoKilometraje = 0.05;
            }
        } else if (kilometraje>=12001 && kilometraje<=25000) {
            if(modelo == "Sedan" || modelo == "Hatchback"){
                recargoKilometraje = 0.07;
            } else if (modelo == "SUV" || modelo == "Pickup" || modelo == "Furgoneta") {
                recargoKilometraje = 0.09;
            }

        } else if (kilometraje>=25001 && kilometraje<=40000) {
            recargoKilometraje = 0.12;

        } else if (kilometraje>=40001) {
            recargoKilometraje = 0.2;
        }
        return recargoKilometraje;
    }

    public double calculoRecargoAntiguedad(String patente){
        double recargoAntiguedad = 0.0;
        int anoActual = 2024;
        VehiculoModel vehiculo = getVehiculo(patente);
        int anoFabricacion = vehiculo.getAno_fabricacion();
        int antiguedad = anoActual - anoFabricacion;
        String modelo = vehiculo.getModelo();

        if(antiguedad >=0 && antiguedad<=5){
            recargoAntiguedad = 0.0;
        } else if (antiguedad>=6 && antiguedad<=10) {
            if(modelo == "Sedan" || modelo == "Hatchback"){
                recargoAntiguedad = 0.05;
            } else if (modelo == "SUV" || modelo == "Pickup" || modelo == "Furgoneta") {
                recargoAntiguedad = 0.07;
            }
        } else if (antiguedad>=11 && antiguedad<=15) {
            if(modelo == "Sedan" || modelo == "Hatchback"){
                recargoAntiguedad = 0.09;
            } else if (modelo == "SUV" || modelo == "Pickup" || modelo == "Furgoneta") {
                recargoAntiguedad = 0.11;
            }
        } else if (antiguedad>=16) {
            if(modelo == "Sedan" || modelo == "Hatchback"){
                recargoAntiguedad = 0.15;
            } else if (modelo == "SUV" || modelo == "Pickup" || modelo == "Furgoneta") {
                recargoAntiguedad = 0.2;
            }
        }
        return recargoAntiguedad;
    }
    public double calcularRecargoPorRetraso(Long idReparacion, LocalDate fecha_reparacion, LocalDate fecha_cliente) {
        RegReparacionEntity reparacion = regReparacionRepository.findById(idReparacion).orElse(null);
        if (reparacion == null) {
            // Manejo de error si no se encuentra la reparación
            return 0.0;
        }

        // Calcula la cantidad de días de retraso
        long diasRetraso = ChronoUnit.DAYS.between(fecha_reparacion, fecha_cliente);

        if (diasRetraso > 0) {
            // Calcula el recargo por retraso (5% por cada día de retraso)
            double recargo = reparacion.getMonto_reparacion() * 0.05 * diasRetraso;

            return recargo;
        } else {
            // No hay retraso, no se aplica recargo
            return 0.0;
        }
    }


    public double calcularMontoRecargos(String patente, LocalDate fechaReparacion, LocalDate fechaCliente) {
        List<RegReparacionEntity> reparaciones = getReparacionesPorPatente(patente);


        double montoTotal = calcularMontoTotalReparaciones(patente);

        double recargoPorRetraso = 0.0;
        double recargoPorKilometraje = recargoService.calcularRecargoKilometraje(patente);
        double recargoPorAntiguedad = recargoService.calculoRecargoAntiguedad(patente);

        for (RegReparacionEntity reparacion : reparaciones) {
            recargoPorRetraso += recargoService.calcularRecargoPorRetraso(reparacion.getId_regrepair(), fechaReparacion, fechaCliente);
        }

        double montoRecargos = montoTotal * (recargoPorKilometraje + recargoPorAntiguedad) + recargoPorRetraso;

        return montoRecargos;
    }
    /*-----------------------RECARGOS-------------------------*/

    /*------------------------------DESCUENTOS-------------------------*/
    public double calcularDescuentoReparacion(String patente){
        double descuentoReparacion = 0.0;
        VehiculoModel vehiculo = getVehiculo(patente);
        int cantReparaciones = vehiculo.getCantidad_reparaciones();
        String tipoMotor = vehiculo.getMotor();

        if(tipoMotor == "Gasolina") {
            if(cantReparaciones>=1 && cantReparaciones<=2){
                descuentoReparacion = 0.05;
            } else if (cantReparaciones>=3 && cantReparaciones<=5) {
                descuentoReparacion = 0.1;
            } else if (cantReparaciones>=6 && cantReparaciones<=9) {
                descuentoReparacion = 0.15;
            } else if (cantReparaciones>=10) {
                descuentoReparacion = 0.2;
            }
        } else if (tipoMotor == "Diesel") {
            if(cantReparaciones>=1 && cantReparaciones<=2){
                descuentoReparacion = 0.07;
            } else if (cantReparaciones>=3 && cantReparaciones<=5) {
                descuentoReparacion = 0.12;
            } else if (cantReparaciones>=6 && cantReparaciones<=9) {
                descuentoReparacion = 0.17;
            } else if (cantReparaciones>=10) {
                descuentoReparacion = 0.22;
            }

        } else if (tipoMotor == "Hibrido") {
            if(cantReparaciones>=1 && cantReparaciones<=2){
                descuentoReparacion = 0.1;
            } else if (cantReparaciones>=3 && cantReparaciones<=5) {
                descuentoReparacion = 0.15;
            } else if (cantReparaciones>=6 && cantReparaciones<=9) {
                descuentoReparacion = 0.2;
            } else if (cantReparaciones>=10) {
                descuentoReparacion = 0.25;
            }

        } else if(tipoMotor == "Electrico"){
            if(cantReparaciones>=1 && cantReparaciones<=2){
                descuentoReparacion = 0.08;
            } else if (cantReparaciones>=3 && cantReparaciones<=5) {
                descuentoReparacion = 0.13;
            } else if (cantReparaciones>=6 && cantReparaciones<=9) {
                descuentoReparacion = 0.18;
            } else if (cantReparaciones>=10) {
                descuentoReparacion = 0.23;
            }
        }
        return descuentoReparacion;
    }

    public double calcularDescuentoBono(String patente){
        double descuentoBono = 0.0;
        VehiculoModel vehiculo = getVehiculo(patente);
        String marcaVehiculo = vehiculo.getMarca();
        List<Integer> precioBono = List.of(70000,50000,30000,40000);
        List<Integer> cantBonos = new ArrayList<>(List.of(5,2,1,7));

        if(cantBonos.get(0)>=1 && marcaVehiculo == "Toyota"){
            descuentoBono = precioBono.get(0);
            cantBonos.set(0,cantBonos.get(0)-1);
        } else if (cantBonos.get(1)>=1 && marcaVehiculo == "Ford"){
            descuentoBono = precioBono.get(1);
            cantBonos.set(1,cantBonos.get(1)-1);
        }else if (cantBonos.get(2)>=1 && marcaVehiculo == "Hyundai"){
            descuentoBono = precioBono.get(2);
            cantBonos.set(2,cantBonos.get(2)-1);
        }else if (cantBonos.get(3)>=1 && marcaVehiculo == "Honda"){
            descuentoBono = precioBono.get(3);
            cantBonos.set(3,cantBonos.get(3)-1);
        }else {
            descuentoBono = 0.0;
        }
        return descuentoBono;
    }

    public double calculoDescuentoDia(Long id_reparacion) {
        // Obtener la reparación por su id
        RegReparacionEntity reparacion = new RegReparacionEntity();

        if (reparacion != null) {
            if (reparacion.getFecha_reparacion().getDayOfWeek().equals(DayOfWeek.MONDAY) || reparacion.getFecha_reparacion().getDayOfWeek().equals(DayOfWeek.THURSDAY)) {
                LocalTime horaReparacion = reparacion.getHora_reparacion();
                if (horaReparacion.isAfter(LocalTime.of(9, 0)) && horaReparacion.isBefore(LocalTime.of(12, 0))) {
                    // Calcular el monto con el descuento del 10%
                    double montoConDescuento = reparacion.getMonto_reparacion() * 0.9;

                    return reparacion.getMonto_reparacion() - montoConDescuento; // Retornar el monto del descuento realizado
                }
            }
        }

        return 0; // Retornar 0 si no se realizó ningún descuento
    }

    public double calcularMontoTotalDescuento(String patente) {
        // Obtener la lista de reparaciones por patente
        List<RegReparacionEntity> reparaciones = getReparacionesPorPatente(patente);


        // Calcular el monto total de reparaciones
        double montoTotalReparaciones = calcularMontoTotalReparaciones(patente);

        // Calcular el monto total de descuento sumando los descuentos de reparación, bono y por día de atención para cada reparación
        double montoTotalDescuento = 0.0;
        double descuentoDia = 0.0;
        double descuentoReparacion = descuentoService.calcularDescuentoReparacion(patente);
        double descuentoBono = descuentoService.calcularDescuentoBono(patente);

        for (RegReparacionEntity reparacion : reparaciones) {

            // Aplicar el descuento por día de atención si corresponde
            descuentoDia+= descuentoService.calculoDescuentoDia(reparacion.getId_regrepair());
        }

        montoTotalDescuento =montoTotalReparaciones*(descuentoReparacion+descuentoBono)+descuentoDia;

        return montoTotalDescuento;
    }
    /*------------------------------DESCUENTOS-------------------------*/
    public double calcularMontoSubTotal(String patente, LocalDate fechaReparacion, LocalDate fechaCliente) {
        double sumaReparaciones = calcularMontoTotalReparaciones(patente);
        double montoRecargos = calcularMontoRecargos(patente, fechaReparacion, fechaCliente);
        double montoDescuentos = calcularMontoTotalDescuento(patente);

        // Calcular el montoSubTotal
        double montoSubTotal = sumaReparaciones + montoRecargos - montoDescuentos;

        return montoSubTotal;
    }
    public double calcularMontoIVA(String patente, LocalDate fechaReparacion, LocalDate fechaCliente) {
        // Calcular el monto subtotal antes de aplicar el IVA
        double montoSubTotal = calcularMontoSubTotal(patente, fechaReparacion, fechaCliente);

        // Calcular el monto del IVA
        double montoIVA = montoSubTotal*0.19;

        return montoIVA;
    }
    public double calcularCostoTotal(String patente, LocalDate fechaReparacion, LocalDate fechaCliente){
        double costoTotal = 0.0;
        double montoSubTotal = calcularMontoSubTotal(patente, fechaReparacion, fechaCliente);

        // Calcular el monto del IVA
        double montoIVA = montoSubTotal*0.19;

        costoTotal = montoSubTotal + montoIVA;
        return costoTotal;
    }

    public BoletaEntity registrarBoleta(String patente, BoletaEntity boleta, LocalDate fechaCliente, LocalTime horaCliente, LocalDate fechaSalida, LocalTime horaSalida) {
        // Calcular los montos necesarios
        int montoTotal = calcularMontoTotalReparaciones(patente);
        double montoDescuentos = calcularMontoTotalDescuento(patente);
        double montoRecargos = calcularMontoRecargos(patente, fechaSalida, fechaCliente);
        double montoIVA = calcularMontoIVA(patente, fechaSalida, fechaCliente);
        double costoTotal = calcularCostoTotal(patente, fechaSalida, fechaCliente);

        // Crear la entidad Boleta
        boleta.setPatente(patente);
        boleta.setMonto_total(montoTotal);
        boleta.setDescuento(montoDescuentos);
        boleta.setRecargo(montoRecargos);
        boleta.setIva(montoIVA);
        boleta.setCosto_total(costoTotal);
        boleta.setFecha_salida(fechaSalida);
        boleta.setHora_salida(horaSalida);
        boleta.setFecha_cliente(fechaCliente);
        boleta.setHora_cliente(horaCliente);

        // Guardar la boleta en el repositorio
        return boletaRepository.save(boleta);
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
