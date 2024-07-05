import React, { useState } from 'react';
import ReparacionService from '../services/ReparacionService';
import { useNavigate } from 'react-router-dom';
import NavbarComponent from './NavbarComponent';
import 'bootstrap/dist/css/bootstrap.min.css';

const RegistroReparacion = () => {
    const [reparacion, setReparacion] = useState({
        tipo_reparacion: '',
        tipo_motor: '',
        monto_reparacion: 0 // Este campo será calculado en el backend
    });
    const [successMessage, setSuccessMessage] = useState(''); // Estado para el mensaje de éxito
    const navigate = useNavigate();

    const handleChange = async (e) => {
        const { name, value } = e.target;
        setReparacion({
            ...reparacion,
            [name]: value,
        });

        if (name === 'tipo_reparacion' && value !== '') {
            // Llamar al servicio para calcular el costo de reparación
            try {
                const response = await ReparacionService.calcularCostoReparacion({
                    tipo_reparacion: value,
                    tipo_motor: reparacion.tipo_motor,
                });
                setReparacion({
                    ...reparacion,
                    monto_reparacion: response.data,
                    tipo_reparacion: value, // Mantener el tipo de reparación seleccionado
                });
            } catch (error) {
                console.error('Error al calcular el costo de reparación: ', error);
            }
        }
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
                                {/* ID Reparación no se muestra ya que será generado automáticamente */}
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
                                        <option value="1">Reparaciones del Sistema de Frenos</option>
                                        <option value="2">Servicio del Sistema de Refrigeración</option>
                                        <option value="3">Reparaciones del Motor</option>
                                        <option value="4">Reparaciones de la Transmisión</option>
                                        <option value="5">Reparación del Sistema Eléctrico</option>
                                        <option value="6">Reparaciones del Sistema de Escape</option>
                                        <option value="7">Reparación de Neumáticos y Ruedas</option>
                                        <option value="8">Reparaciones de la Suspensión y la Dirección</option>
                                        <option value="9">Reparación del Sistema de Aire Acondicionado y Calefacción</option>
                                        <option value="10">Reparaciones del Sistema de Combustible</option>
                                        <option value="11">Reparación y Reemplazo del Parabrisas y Cristales</option>
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