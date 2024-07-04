import axios from 'axios';

const REPORTES_API_URL = "http://localhost:8080/reportes";

class ReportesService {
    calcularCostosReparacion(mes, ano) {
        return axios.post(`${REPORTES_API_URL}/calcular-costos-reparacion`, null, {
            params: {
                mes: mes,
                ano: ano
            }
        });
    }

    calcularReparaciones(mes, ano) {
        return axios.post(`${REPORTES_API_URL}/calcular-reparaciones`, null, {
            params: {
                mes: mes,
                ano: ano
            }
        });
    }
}

const reportesService = new ReportesService();
export default reportesService;

