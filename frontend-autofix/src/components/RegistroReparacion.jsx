import React, { useState } from 'react';
import ReparacionService from '../services/ReparacionService';
import { useNavigate } from 'react-router-dom';
import NavbarComponent from './NavbarComponent';
import 'bootstrap/dist/css/bootstrap.min.css';

const RegistroReparacion = () => {
    const [reparacion, setReparacion] = useState({
        id_reparacion: '',
        tipo_reparacion: '',
        tipo_motor: '',
        monto_reparacion: 0 // Este campo será calculado en el backend
    });
    const [successMessage, setSuccessMessage] = useState(''); // Estado para el mensaje de éxito
    const navigate = useNavigate();

    const handleChange = (e) => {
        setReparacion({
            ...reparacion,
            [e.target.name]: e.target.value,
        });
    };

    const saveReparacion = (e) => {
        e.preventDefault();
        ReparacionService.registroReparacion(reparacion).then(() => {
            setSuccessMessage('La reparación se registró correctamente');
            setTimeout(() => {
                setSuccessMessage('');
                navigate("/");
            }, 2000); // Navega a la página principal después de 2 segundos
        }).catch(err => {
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
                        <h1 className="text-center">Registro de Reparaciones</h1>
                        <div className="card-body">
                            {successMessage && <div className="alert alert-success">{successMessage}</div>}
                            <form>
                                <div className="form-group">
                                    <label>ID Reparación:</label>
                                    <input
                                        type="text"
                                        name="id_reparacion"
                                        className="form-control"
                                        placeholder="ID de la reparación"
                                        required
                                        value={reparacion.id_reparacion}
                                        onChange={handleChange}
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
                                        <option value="1">Tipo 1</option>
                                        <option value="2">Tipo 2</option>
                                        <option value="3">Tipo 3</option>
                                        <option value="4">Tipo 4</option>
                                        <option value="5">Tipo 5</option>
                                        <option value="6">Tipo 6</option>
                                        <option value="7">Tipo 7</option>
                                        <option value="8">Tipo 8</option>
                                        <option value="9">Tipo 9</option>
                                        <option value="10">Tipo 10</option>
                                        <option value="11">Tipo 11</option>
                                    </select>
                                </div>
                                <br />
                                <div className="form-group">
                                    <label>Tipo de Motor:</label>
                                    <select
                                        name="tipo_motor"
                                        className="form-control"
                                        value={reparacion.tipo_motor}
                                        onChange={handleChange}
                                        required
                                    >
                                        <option value="">Seleccionar tipo de motor</option>
                                        <option value="Gasolina">Gasolina</option>
                                        <option value="Diesel">Diesel</option>
                                        <option value="Hibrido">Hibrido</option>
                                        <option value="Electrico">Electrico</option>
                                    </select>
                                </div>
                                <br />
                                <div className="form-group">
                                    <label>Monto de Reparación:</label>
                                    <input
                                        type="number"
                                        name="monto_reparacion"
                                        className="form-control"
                                        placeholder="Monto de la reparación"
                                        required
                                        value={reparacion.monto_reparacion}
                                        onChange={handleChange}
                                        disabled // Deshabilitado ya que se calculará automáticamente en el backend
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

export default RegistroReparacion;
