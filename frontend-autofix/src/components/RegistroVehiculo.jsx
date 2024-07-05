import React, { useState } from "react";
import VehiculoService from "../services/VehiculoService.js";
import { useNavigate } from "react-router-dom";
import NavbarComponent from "./NavbarComponent.jsx";
import 'bootstrap/dist/css/bootstrap.min.css';

const RegistroVehiculo = () => {
  const [vehiculo, setVehiculo] = useState({
    patente: '',
    marca: '',
    modelo: '',
    ano_fabricacion: 0,
    motor: '',
    asientos: 0,
    kilometraje: 0
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setVehiculo({
      ...vehiculo,
      [e.target.name]: e.target.value,
    });
  };

  const saveVehiculo = (e) => {
    e.preventDefault();
    const vehiculoToSave = {
      ...vehiculo,
      cantidad_reparaciones: 0 // Añadir cantidad_reparaciones como 0 al objeto de vehículo a guardar
    };
    VehiculoService.registroVehiculo(vehiculoToSave).then(() => {
      navigate("/");
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
            <h1 className="text-center">Registro de vehículos</h1>
            <div className="card-body">
              <form>
                <div className="form-group">
                  <label>Patente:</label>
                  <input
                    type="text"
                    name="patente"
                    className="form-control"
                    placeholder="Patente del vehículo"
                    required
                    value={vehiculo.patente}
                    onChange={handleChange}
                  />
                </div>
                <br />
                <div className="form-group">
                  <label>Marca:</label>
                  <select
                    name="marca"
                    className="form-control"
                    value={vehiculo.marca}
                    onChange={handleChange}
                    required
                  >
                    <option value="">Seleccionar marca</option>
                    <option value="Toyota">Toyota</option>
                    <option value="Kia">Kia</option>
                    <option value="Honda">Honda</option>
                    <option value="Ford">Ford</option>
                    <option value="Chevrolet">Chevrolet</option>
                    <option value="Hyundai">Hyundai</option>
                  </select>
                </div>
                <br />
                <div className="form-group">
                  <label>Modelo:</label>
                  <select
                    name="modelo"
                    className="form-control"
                    value={vehiculo.modelo}
                    onChange={handleChange}
                    required
                  >
                    <option value="">Seleccionar modelo</option>
                    <option value="Sedan">Sedan</option>
                    <option value="Hatchback">Hatchback</option>
                    <option value="SUV">SUV</option>
                    <option value="Pickup">Pickup</option>
                    <option value="Furgoneta">Furgoneta</option>
                  </select>
                </div>
                <br />
                <div className="form-group">
                  <label>Año de fabricación:</label>
                  <input
                    type="number"
                    name="ano_fabricacion"
                    className="form-control"
                    placeholder="Año de fabricación del vehículo"
                    required
                    value={vehiculo.ano_fabricacion}
                    onChange={handleChange}
                  />
                </div>
                <br />
                <div className="form-group">
                  <label>Motor:</label>
                  <select
                    name="motor"
                    className="form-control"
                    value={vehiculo.motor}
                    onChange={handleChange}
                    required
                  >
                    <option value="">Seleccionar motor</option>
                    <option value="Gasolina">Gasolina</option>
                    <option value="Diesel">Diesel</option>
                    <option value="Hibrido">Hibrido</option>
                    <option value="Electrico">Electrico</option>
                  </select>
                </div>
                <br />
                <div className="form-group">
                  <label>Asientos:</label>
                  <input
                    type="number"
                    name="asientos"
                    className="form-control"
                    placeholder="Número de asientos del vehículo"
                    required
                    value={vehiculo.asientos}
                    onChange={handleChange}
                  />
                </div>
                <br />
                <div className="form-group">
                  <label>Kilometraje:</label>
                  <input
                    type="number"
                    name="kilometraje"
                    className="form-control"
                    placeholder="Kilometraje del vehículo"
                    required
                    value={vehiculo.kilometraje}
                    onChange={handleChange}
                  />
                </div>
                <br />
                {/* El campo cantidad_reparaciones no se muestra */}
                <div className="box-footer">
                  <button className="btn btn-success" onClick={saveVehiculo}>
                    Guardar vehículo
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

export default RegistroVehiculo;
