import axios from 'axios'; 

const REPAIRVEHICLE_API_URL = "http://localhost:8080/repairvehicle";

class RepairVehicleService {
    registroRepairVehicle(patente,data) {
        return axios.post(`${REPAIRVEHICLE_API_URL}/regrepair/vehiculo/${patente}`, data);
    }
    calcularMonto(patente, tipoReparacion) {
        return axios.get(`${REPAIRVEHICLE_API_URL}/regrepair/calcular-monto`, { params: { patente, tipoReparacion } });
    }
}

// Exporta una instancia de la clase VehiculoService
const repairvehicleService = new RepairVehicleService(); 
export default repairvehicleService;