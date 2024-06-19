import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import vehiculoService from '../services/VehiculoService';
import NavbarComponent from './NavbarComponent';
import 'bootstrap/dist/css/bootstrap.min.css';

const ListaVehiculos = () => {
  const [vehiculos, setVehiculos] = useState([]);

  useEffect(() => {
    vehiculoService.getVehiculos()
      .then(response => {
        setVehiculos(response.data);
      })
      .catch(error => {
        console.error("Hubo un error al obtener la lista de vehículos:", error);
      });
  }, []);

  return (
    <div>
      <NavbarComponent />
      <br />
      <div className="container">
        <h1 className="text-center">Lista de Vehículos</h1>
        <br />
        <table className="table table-striped">
          <thead>
            <tr>
              <th>Patente</th>
              <th>Marca</th>
              <th>Modelo</th>
              <th>Año de Fabricación</th>
              <th>Motor</th>
              <th>Asientos</th>
              <th>Kilometraje</th>
              <th>Acciones</th>
              <th>Reparaciones</th>
            </tr>
          </thead>
          <tbody>
            {vehiculos.length > 0 ? (
              vehiculos.map(vehiculo => (
                <tr key={vehiculo.patente}>
                  <td>{vehiculo.patente}</td>
                  <td>{vehiculo.marca}</td>
                  <td>{vehiculo.modelo}</td>
                  <td>{vehiculo.ano_fabricacion}</td>
                  <td>{vehiculo.motor}</td>
                  <td>{vehiculo.asientos}</td>
                  <td>{vehiculo.kilometraje}</td>
                  <td>
                    <Link to={`/vehiculo-reparacion/${vehiculo.patente}`}>Registrar Reparación</Link>
                  </td>
                  <td>
                    <Link to={`/vehiculo-reparaciones/${vehiculo.patente}`}>Ver Reparaciones</Link>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="9" className="text-center">No hay vehículos registrados.</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ListaVehiculos;
