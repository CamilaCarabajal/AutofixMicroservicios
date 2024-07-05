import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import NavbarComponent from './NavbarComponent';
import reportesService from '../services/ReportesService';

const ReporteDos = () => {
    const [resultados, setResultados] = useState([]);
    const [error, setError] = useState('');
    const [mesParam, setMesParam] = useState('');
    const [anoParam, setAnoParam] = useState('');
    const { search } = useLocation();
    const navigate = useNavigate();

    // Obtener mes y año de la URL o establecer valores iniciales
    useEffect(() => {
        const searchParams = new URLSearchParams(search);
        const mesInicial = searchParams.get('mes') || '1'; // Valor inicial si no hay mes en la URL
        const anoInicial = searchParams.get('ano') || '2024'; // Valor inicial si no hay año en la URL
        setMesParam(mesInicial);
        setAnoParam(anoInicial);
    }, [search]);

    // Función para obtener el nombre de un mes dado su número
    const getNombreMes = (mes) => {
        const meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
        return meses[(mes - 1 + 12) % 12]; // Manejar casos donde el mes sea negativo o mayor que 12
    };

    // Función para calcular el mes anterior
    const calcularMesAnterior = (mes, decremento) => {
        return (mes - decremento + 12 - 1) % 12 + 1;
    };

    // Función para calcular reparaciones al cargar o al cambiar mes/ano
    useEffect(() => {
        const calcularReparaciones = async () => {
            try {
                const response = await reportesService.calcularReparaciones(mesParam, anoParam);
                console.log(response.data); // Verificar datos recibidos en consola
                setResultados(response.data);
                setError('');
            } catch (err) {
                setError('Error al obtener los resultados.');
                setResultados([]);
            }
        };

        if (mesParam && anoParam) {
            calcularReparaciones();
        }
    }, [mesParam, anoParam]);

    // Estilos para la tabla y otros elementos
    const styles = {
        container: {
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            minHeight: '100vh',
            fontFamily: '"Courier New", Courier, monospace',
            backgroundColor: '#f4f4f4',
            padding: '20px'
        },
        table: {
            width: '80%',
            borderCollapse: 'collapse',
            margin: '20px 0',
            boxShadow: '0 0 20px rgba(0, 0, 0, 0.15)',
            backgroundColor: '#fff',
        },
        th: {
            backgroundColor: '#333', // Mismo color que el botón Ok
            color: '#ffffff',
            textAlign: 'center',
            padding: '10px',
            border: '1px solid #dddddd',
        },
        td: {
            textAlign: 'center',
            padding: '8px',
            border: '1px solid #dddddd',
            width: '80px', // Ajustado el ancho de las celdas
        },
        tdTipo: {
            textAlign: 'center',
            padding: '8px',
            border: '1px solid #dddddd',
            width: '180px', // Ajustado el ancho de la celda de tipos de reparación
        },
        h2: {
            color: '#333',
        },
        buttonOk: {
            marginTop: '20px',
            padding: '10px 20px',
            backgroundColor: '#333', // Color del botón Ok
            color: '#fff',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer',
        },
        select: {
            margin: '10px',
            padding: '8px',
            fontSize: '1rem', // Tamaño de fuente ajustado
            borderRadius: '5px',
            border: '1px solid #ccc',
        },
        label: {
            marginRight: '10px',
        },
        inputAno: {
            padding: '8px',
            fontSize: '1rem', // Tamaño de fuente ajustado
            borderRadius: '5px',
            border: '1px solid #ccc',
        }
    };

    // Tipos de reparación a mostrar en la tabla
    const tiposReparacion = [
        "Reparaciones del Sistema de Frenos",
        "Servicio del Sistema de Refrigeración",
        "Reparaciones del Motor",
        "Reparaciones de la Transmisión",
        "Reparación del Sistema Eléctrico",
        "Reparaciones del Sistema de Escape",
        "Reparación de Neumáticos y Ruedas",
        "Reparaciones de la Suspensión y la Dirección",
        "Reparación del Sistema de Aire Acondicionado y Calefacción",
        "Reparaciones del Sistema de Combustible",
        "Reparación y Reemplazo del Parabrisas y Cristales"
    ];

    // Manejar cambio de mes y año
    const handleMesChange = (event) => {
        setMesParam(event.target.value);
    };

    const handleAnoChange = (event) => {
        setAnoParam(event.target.value);
    };

    // Manejar el click en el botón "Ok"
    const handleOkClick = () => {
        navigate('/');
    };

    return (
        <div>
            <NavbarComponent />
            <div style={styles.container}>
                <h2 style={styles.h2}>Calcular Reparaciones por Mes</h2>
                <div>
                    <label style={styles.label}>Mes:</label>
                    <select style={styles.select} value={mesParam} onChange={handleMesChange}>
                        {Array.from({ length: 12 }, (_, i) => i + 1).map((mes) => (
                            <option key={mes} value={mes}>{getNombreMes(mes)}</option>
                        ))}
                    </select>
                    <label style={styles.label}>Año:</label>
                    <input type="text" style={styles.inputAno} value={anoParam} onChange={handleAnoChange} />
                </div>
                <h3 style={styles.h2}>Resultados:</h3>
                {error && <p>{error}</p>}
                {resultados.length > 0 ? (
                    <table style={styles.table}>
                        <thead>
                            <tr>
                                <th style={styles.th}>Tipo de Reparación</th>
                                <th style={styles.th}>{getNombreMes(mesParam)}</th>
                                <th style={styles.th}>% Variación</th>
                                <th style={styles.th}>{getNombreMes(calcularMesAnterior(parseInt(mesParam), 1))}</th>
                                <th style={styles.th}>% Variación</th>
                                <th style={styles.th}>{getNombreMes(calcularMesAnterior(parseInt(mesParam), 2))}</th>
                                <th style={styles.th}>% Variación</th>
                            </tr>
                        </thead>
                        <tbody>
                            {tiposReparacion.map((tipo, index) => {
                                const tipoReparacionIndex = index + 1;
                                const resultadoActual = resultados.find(r => r.tipo_reparacion === tipoReparacionIndex && r.mes === parseInt(mesParam));
                                const resultadoMesAnterior = resultados.find(r => r.tipo_reparacion === tipoReparacionIndex && r.mes === calcularMesAnterior(parseInt(mesParam), 1));
                                const resultadoDosMesesAnterior = resultados.find(r => r.tipo_reparacion === tipoReparacionIndex && r.mes === calcularMesAnterior(parseInt(mesParam), 2));

                                return (
                                    <React.Fragment key={index}>
                                        <tr>
                                            <td style={styles.tdTipo} rowSpan="2">{tipo}</td>
                                            <td style={styles.td}>{resultadoActual ? resultadoActual.cantidad_reparaciones : 0}</td>
                                            <td style={styles.td}>{resultadoActual ? resultadoActual.variacion_cantidad + '%' : '0%'}</td>
                                            <td style={styles.td}>{resultadoMesAnterior ? resultadoMesAnterior.cantidad_reparaciones : 0}</td>
                                            <td style={styles.td}>{resultadoMesAnterior ? resultadoMesAnterior.variacion_cantidad + '%' : '0%'}</td>
                                            <td style={styles.td}>{resultadoDosMesesAnterior ? resultadoDosMesesAnterior.cantidad_reparaciones : 0}</td>
                                            <td style={styles.td}>{resultadoDosMesesAnterior ? resultadoDosMesesAnterior.variacion_cantidad + '%' : '0%'}</td>
                                        </tr>
                                        <tr>
                                            <td style={styles.td}>{resultadoActual ? resultadoActual.monto_total_reparaciones : 0}</td>
                                            <td style={styles.td}>{resultadoActual ? resultadoActual.variacion_monto + '%' : '0%'}</td>
                                            <td style={styles.td}>{resultadoMesAnterior ? resultadoMesAnterior.monto_total_reparaciones : 0}</td>
                                            <td style={styles.td}>{resultadoMesAnterior ? resultadoMesAnterior.variacion_monto + '%' : '0%'}</td>
                                            <td style={styles.td}>{resultadoDosMesesAnterior ? resultadoDosMesesAnterior.monto_total_reparaciones : 0}</td>
                                            <td style={styles.td}>{resultadoDosMesesAnterior ? resultadoDosMesesAnterior.variacion_monto + '%' : '0%'}</td>
                                        </tr>
                                    </React.Fragment>
                                );
                            })}
                        </tbody>
                    </table>
                ) : (
                    <p>No hay resultados para mostrar.</p>
                )}

                <button style={styles.buttonOk} onClick={handleOkClick}>Ok</button>
            </div>
        </div>
    );
};

export default ReporteDos;
