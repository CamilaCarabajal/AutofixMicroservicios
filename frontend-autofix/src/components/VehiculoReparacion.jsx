import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import RepairVehicleService from '../services/RepairVehicleService';
import NavbarComponent from './NavbarComponent';
import 'bootstrap/dist/css/bootstrap.min.css';

const VehiculoReparacion = () => {
  const { patente } = useParams();
  const [reparacion, setReparacion] = useState({
    tipo_reparacion: '',
    fecha_reparacion: '',
    hora_reparacion: '',
    monto_reparacion: 0 // monto inicial de 0
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setReparacion(prevState => ({
      ...prevState,
      [name]: value,
    }));

    if (name === 'tipo_reparacion') {
      RepairVehicleService.calcularMonto(patente, value)
        .then(response => {
          setReparacion(prevState => ({
            ...prevState,
            monto_reparacion: response.data
          }));
        })
        .catch(err => {
          console.error("Error al calcular el monto: ", err);
        });
    }
  };

  const saveReparacion = (e) => {
    e.preventDefault();
    RepairVehicleService.registroRepairVehicle(patente, reparacion)
      .then(response => {
        console.log("Reparación registrada con éxito: ", response.data);
        navigate("/");
      })
      .catch(err => {
        console.error("Error al guardar la reparación: ", err);
      });
  };

  return (
    <div>
      <NavbarComponent />
      <br />
      <div className="container">
        <div className="row">
          <div className="col-lg-10 col-md-10 col-sm-10 container justify-content-center card">
            <br />
            <h1 className="text-center">Registrar Reparación para {patente}</h1>
            <div className="card-body">
              <form>
                <div className="form-group">
                  <label>Patente:</label>
                  <input
                    type="text"
                    name="patente"
                    className="form-control"
                    value={patente}
                    readOnly
                  />
                </div>
                <br />
                <div className="form-group">
                  <label>Tipo de Reparación:</label>
                  <select
                    name="tipo_reparacion"
                    className="form-control"
                    value={reparacion.tipo_reparacion}
                    onChange={handleChange}
                    required
                  >
                    <option value="">Seleccionar tipo de reparación</option>
                    {[...Array(11).keys()].map(i => (
                      <option key={i + 1} value={i + 1}>Tipo {i + 1}</option>
                    ))}
                  </select>
                </div>
                <br />
                <div className="form-group">
                  <label>Fecha de Reparación:</label>
                  <input
                    type="date"
                    name="fecha_reparacion"
                    className="form-control"
                    required
                    value={reparacion.fecha_reparacion}
                    onChange={handleChange}
                  />
                </div>
                <br />
                <div className="form-group">
                  <label>Hora de Reparación:</label>
                  <input
                    type="time"
                    name="hora_reparacion"
                    className="form-control"
                    required
                    value={reparacion.hora_reparacion}
                    onChange={handleChange}
                  />
                </div>
                <br />
                <div className="form-group">
                  <label>Monto de Reparación:</label>
                  <input
                    type="number"
                    name="monto_reparacion"
                    className="form-control"
                    value={reparacion.monto_reparacion}
                    readOnly
                  />
                </div>
                <br />
                <div className="box-footer">
                  <button className="btn btn-success" onClick={saveReparacion}>
                    Guardar Reparación
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default VehiculoReparacion;
