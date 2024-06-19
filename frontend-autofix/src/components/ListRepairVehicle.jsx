import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import repairvehicleService from '../services/RepairVehicleService';
import NavbarComponent from './NavbarComponent';
import 'bootstrap/dist/css/bootstrap.min.css';

const ListRepairVehicle = () => {
  const { patente } = useParams();
  const [reparaciones, setReparaciones] = useState([]);

  useEffect(() => {
    repairvehicleService.getReparaciones(patente)
      .then(response => {
        setReparaciones(response.data);
      })
      .catch(error => {
        console.error("Hubo un error al obtener la lista de reparaciones:", error);
      });
  }, [patente]);

  return (
    <div>
      <NavbarComponent />
      <br />
      <div className="container">
        <h1 className="text-center">Lista de Reparaciones para {patente}</h1>
        <br />
        <table className="table table-striped">
          <thead>
            <tr>
              <th>ID</th>
              <th>Patente</th>
              <th>Tipo de Reparación</th>
              <th>Fecha de Reparación</th>
              <th>Hora de Reparación</th>
              <th>Monto de Reparación</th>
            </tr>
          </thead>
          <tbody>
            {reparaciones.length > 0 ? (
              reparaciones.map(reparacion => (
                <tr key={reparacion.id_regrepair}>
                  <td>{reparacion.id_regrepair}</td>
                  <td>{reparacion.patente}</td>
                  <td>{reparacion.tipo_reparacion}</td>
                  <td>{reparacion.fecha_reparacion}</td>
                  <td>{reparacion.hora_reparacion}</td>
                  <td>{reparacion.monto_reparacion}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="6" className="text-center">No hay reparaciones registradas.</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ListRepairVehicle;
