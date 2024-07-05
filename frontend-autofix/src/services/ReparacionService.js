import axios from 'axios'; 

const REPARACION_API_URL = "http://localhost:8080/reparacion";

class ReparacionService {
    registroReparacion(data) {
        return axios.post(REPARACION_API_URL, data);
    }

    calcularCostoReparacion(reparacion) {
        return axios.post(`${REPARACION_API_URL}/calcularCostoReparacion`, reparacion);
    }
}

// Exporta una instancia de la clase VehiculoService
const reparacionService = new ReparacionService(); 
export default reparacionService;