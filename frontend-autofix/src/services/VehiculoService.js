import axios from 'axios'; 

const VEHICULO_API_URL = "http://localhost:8080/vehiculo";

class VehiculoService {
    registroVehiculo(data) {
        return axios.post(VEHICULO_API_URL, data);
    }
    getVehiculos() {
        return axios.get(`${VEHICULO_API_URL}/lista`);
    }
}

// Exporta una instancia de la clase VehiculoService
const vehiculoService = new VehiculoService(); 
export default vehiculoService;