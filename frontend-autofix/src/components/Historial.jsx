import React, { useEffect, useState } from 'react';
import repairvehicleService from '../services/RepairVehicleService';
import NavbarComponent from './NavbarComponent';
import 'bootstrap/dist/css/bootstrap.min.css';

const Historial = () => {
  const [historial, setHistorial] = useState([]); // Estado para almacenar el historial

  useEffect(() => {
    // Función para obtener el historial al cargar el componente
    const fetchHistorial = async () => {
      try {
        const response = await repairvehicleService.getHistorial(); // Obtener historial del servicio
        setHistorial(response.data); // Actualizar el estado con el historial obtenido
      } catch (error) {
        console.error("Hubo un error al obtener el historial:", error);
      }
    };

    fetchHistorial(); // Llamar a la función para obtener el historial
  }, []);

  return (
    <div>
      <NavbarComponent /> {/* Componente de navegación */}
      <div className="container">
        <h1 className="text-center">Historial de Vehículos</h1>
        <br />
        <table className="table table-striped">
          {/* Tabla de historial */}
          <thead>
            <tr>
              <th>Patente</th>
              <th>Marca</th>
              <th>Modelo</th>
              <th>Año Fabricación</th>
              <th>Motor</th>
              <th>Fecha Ingreso</th>
              <th>Hora Ingreso</th>
              <th>Monto Total</th>
              <th>Monto Recargo</th>
              <th>Monto Descuento</th>
              <th>Sub Total</th>
              <th>Monto IVA</th>
              <th>Costo Total</th>
              <th>Fecha Salida</th>
              <th>Hora Salida</th>
              <th>Fecha Cliente</th>
              <th>Hora Cliente</th>
            </tr>
          </thead>
          <tbody>
            {historial.length > 0 ? (
              historial.map(entry => (
                <tr key={entry.patente}>
                  <td>{entry.patente}</td>
                  <td>{entry.marca}</td>
                  <td>{entry.modelo}</td>
                  <td>{entry.ano_fabricacion}</td>
                  <td>{entry.motor}</td>
                  <td>{entry.fecha_ingreso}</td>
                  <td>{entry.hora_ingreso}</td>
                  <td>{entry.monto_total}</td>
                  <td>{entry.monto_recargo}</td>
                  <td>{entry.monto_descuento}</td>
                  <td>{entry.sub_total}</td> {/* Nuevo campo sub_total */}
                  <td>{entry.monto_iva}</td>
                  <td>{entry.costo_total}</td>
                  <td>{entry.fecha_salida}</td>
                  <td>{entry.hora_salida}</td>
                  <td>{entry.fecha_cliente}</td>
                  <td>{entry.hora_cliente}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="17" className="text-center">No hay historial disponible.</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Historial;
