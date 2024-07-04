import React, { useState, useEffect } from 'react';
import reportesService from '../services/ReportesService';
import { useLocation } from 'react-router-dom';

const ReporteDos = () => {
    const [mes, setMes] = useState('');
    const [ano, setAno] = useState('');
    const [resultados, setResultados] = useState([]);
    const [error, setError] = useState('');
    const location = useLocation();

    // Actualizar mes y año basado en la ubicación (URL)
    useEffect(() => {
        const searchParams = new URLSearchParams(location.search);
        const mesParam = searchParams.get('mes');
        const anoParam = searchParams.get('ano');
        setMes(mesParam);
        setAno(anoParam);
    }, [location.search]);

    // Manejar el envío del formulario para calcular reparaciones
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await reportesService.calcularReparaciones(mes, ano);
            console.log(response.data); // Verificar datos recibidos en consola
            setResultados(response.data);
            setError('');
        } catch (err) {
            setError('Error al obtener los resultados.');
            setResultados([]);
        }
    };

    // Función para obtener el nombre de un mes dado su número
    const getNombreMes = (mes) => {
        const meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
        return meses[(mes - 1 + 12) % 12]; // Manejar casos donde el mes sea negativo o mayor que 12
    };

    // Función para calcular el mes anterior
    const calcularMesAnterior = (mes, decremento) => {
        return (mes - decremento + 12 - 1) % 12 + 1;
    };

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
        form: {
            marginBottom: '20px',
        },
        table: {
            width: '80%',
            borderCollapse: 'collapse',
            margin: '20px 0',
            boxShadow: '0 0 20px rgba(0, 0, 0, 0.15)',
            backgroundColor: '#fff',
        },
        th: {
            backgroundColor: '#009879',
            color: '#ffffff',
            textAlign: 'center',
            padding: '10px',
            border: '1px solid #dddddd',
        },
        td: {
            textAlign: 'center',
            padding: '8px',
            border: '1px solid #dddddd',
        },
        h2: {
            color: '#333',
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

    return (
        <div style={styles.container}>
            <h2 style={styles.h2}>Calcular Reparaciones por Mes</h2>
            <form style={styles.form} onSubmit={handleSubmit}>
                <div>
                    <label>Mes:</label>
                    <input type="number" value={mes} onChange={(e) => setMes(e.target.value)} required />
                </div>
                <div>
                    <label>Año:</label>
                    <input type="number" value={ano} onChange={(e) => setAno(e.target.value)} required />
                </div>
                <button type="submit">Calcular</button>
            </form>

            {error && <p>{error}</p>}

            <h3 style={styles.h2}>Resultados:</h3>
            {resultados.length > 0 ? (
                <table style={styles.table}>
                    <thead>
                        <tr>
                            <th style={styles.th}>Tipo de Reparación</th>
                            <th style={styles.th}>{getNombreMes(mes)}</th>
                            <th style={styles.th}>% Variación</th>
                            <th style={styles.th}>{getNombreMes(calcularMesAnterior(parseInt(mes), 1))}</th>
                            <th style={styles.th}>% Variación</th>
                            <th style={styles.th}>{getNombreMes(calcularMesAnterior(parseInt(mes), 2))}</th>
                            <th style={styles.th}>% Variación</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tiposReparacion.map((tipo, index) => {
                            const tipoReparacionIndex = index + 1;
                            const resultadoActual = resultados.find(r => r.tipo_reparacion === tipoReparacionIndex && r.mes === parseInt(mes));
                            const resultadoMesAnterior = resultados.find(r => r.tipo_reparacion === tipoReparacionIndex && r.mes === calcularMesAnterior(parseInt(mes), 1));
                            const resultadoDosMesesAnterior = resultados.find(r => r.tipo_reparacion === tipoReparacionIndex && r.mes === calcularMesAnterior(parseInt(mes), 2));

                            console.log(`Tipo: ${tipo}, Mes: ${mes}, Resultado Actual:`, resultadoActual);

                            return (
                                <React.Fragment key={index}>
                                    <tr>
                                        <td style={styles.td}>{tipo}</td>
                                        <td style={styles.td}>{resultadoActual ? resultadoActual.cantidad_reparaciones : 0}</td>
                                        <td style={styles.td}>{resultadoActual ? resultadoActual.variacion_cantidad + '%' : '0%'}</td>
                                        <td style={styles.td}>{resultadoMesAnterior ? resultadoMesAnterior.cantidad_reparaciones : 0}</td>
                                        <td style={styles.td}>{resultadoMesAnterior ? resultadoMesAnterior.variacion_cantidad + '%' : '0%'}</td>
                                        <td style={styles.td}>{resultadoDosMesesAnterior ? resultadoDosMesesAnterior.cantidad_reparaciones : 0}</td>
                                        <td style={styles.td}>{resultadoDosMesesAnterior ? resultadoDosMesesAnterior.variacion_cantidad + '%' : '0%'}</td>
                                    </tr>
                                    <tr>
                                        <td style={styles.td}></td>
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
        </div>
    );
};

export default ReporteDos;

