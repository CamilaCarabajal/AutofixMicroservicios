import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import repairvehicleService from '../services/RepairVehicleService';
import NavbarComponent from './NavbarComponent';
import 'bootstrap/dist/css/bootstrap.min.css';

const ListRepairVehicle = () => {
  const { patente } = useParams(); // Obtener el parámetro patente desde la URL
  const [reparaciones, setReparaciones] = useState([]); // Estado para almacenar las reparaciones
  const [fechaReparacion, setFechaReparacion] = useState(''); // Estado para la fecha de reparación
  const [fechaCliente, setFechaCliente] = useState(''); // Estado para la fecha del cliente
  const [horaReparacion, setHoraReparacion] = useState(''); // Estado para la hora de reparación
  const [horaCliente, setHoraCliente] = useState(''); // Estado para la hora del cliente

  useEffect(() => {
    // Función para obtener las reparaciones al cargar el componente
    const fetchReparaciones = async () => {
      try {
        const response = await repairvehicleService.getReparaciones(patente); // Obtener reparaciones del servicio
        setReparaciones(response.data); // Actualizar el estado con las reparaciones obtenidas
      } catch (error) {
        console.error("Hubo un error al obtener la lista de reparaciones:", error);
      }
    };

    fetchReparaciones(); // Llamar a la función para obtener las reparaciones
  }, [patente]); // Dependencia: patente, para que se actualice cuando cambie

  // Función para manejar el clic en el botón Generar Boleta
  const handleGenerarBoletaClick = async () => {
    // Validar que se hayan ingresado la fecha y hora de reparación, y la fecha y hora del cliente
    if (!fechaReparacion || !fechaCliente || !horaReparacion || !horaCliente) {
      alert("Por favor ingresa la fecha y hora de reparación, y la fecha y hora del cliente.");
      return;
    }

    try {
      const response = await repairvehicleService.generarBoleta(patente, fechaReparacion, fechaCliente, horaReparacion, horaCliente);
      console.log(response.data);
      
      // Eliminar reparaciones después de generar la boleta
      await repairvehicleService.deleteReparaciones(patente);

      // Redirigir a la página de boleta con el id de la boleta generada
      window.location.href = `/boleta/${response.data.id_boleta}`;
    } catch (error) {
      console.error("Hubo un error al generar la boleta:", error);
      console.log(error.response.data);
      // Manejo de errores en el frontend
    }
  };

  return (
    <div>
      <NavbarComponent /> {/* Componente de navegación */}
      <div className="container">
        <h1 className="text-center">Lista de Reparaciones para {patente}</h1>
        <br />
        <table className="table table-striped">
          {/* Tabla de reparaciones */}
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

        {/* Sección para ingresar fechas y horas */}
        <div className="text-center mt-5">
          <div className="row justify-content-center align-items-center">
            <div className="col-auto">
              <label>Fecha de reparación:</label>
              <input type="date" className="form-control" value={fechaReparacion} onChange={(e) => setFechaReparacion(e.target.value)} />
            </div>
            <div className="col-auto">
              <label>Hora de reparación:</label>
              <input type="time" className="form-control" value={horaReparacion} onChange={(e) => setHoraReparacion(e.target.value)} />
            </div>
            <div className="col-auto">
              <label>Fecha del cliente:</label>
              <input type="date" className="form-control" value={fechaCliente} onChange={(e) => setFechaCliente(e.target.value)} />
            </div>
            <div className="col-auto">
              <label>Hora del cliente:</label>
              <input type="time" className="form-control" value={horaCliente} onChange={(e) => setHoraCliente(e.target.value)} />
            </div>
            <div className="col-auto mt-4">
              <button className="btn btn-primary" onClick={handleGenerarBoletaClick}>Generar Boleta</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ListRepairVehicle;
