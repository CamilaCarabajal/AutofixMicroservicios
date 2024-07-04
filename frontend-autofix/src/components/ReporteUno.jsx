import React, { useState, useEffect } from 'react';
import { useSearchParams, Link } from 'react-router-dom';
import reportesService from '../services/ReportesService';

const ReporteUno = () => {
    const [searchParams] = useSearchParams();
    const [mes, setMes] = useState(searchParams.get('mes') || '');
    const [ano, setAno] = useState(searchParams.get('ano') || '');
    const [resultados, setResultados] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchData = async () => {
            if (mes && ano) {
                try {
                    const response = await reportesService.calcularCostosReparacion(mes, ano);
                    setResultados(response.data);
                    setError('');
                } catch (err) {
                    setError('Error al obtener los resultados.');
                    setResultados([]);
                }
            }
        };

        fetchData();
    }, [mes, ano]);

    const modelos = ["Sedan", "Hatchback", "SUV", "Pickup", "Furgoneta"];
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

    const agregarDatosPorTipoYModelo = (resultados) => {
        let datos = {};

        // Inicializar el objeto datos
        tiposReparacion.forEach((tipo, index) => {
            datos[index + 1] = {};
            modelos.forEach(modelo => {
                datos[index + 1][modelo] = { conteo: 0, costo: 0 };
            });
            datos[index + 1].total = { conteo: 0, costo: 0 };
        });

        // Rellenar el objeto datos con los resultados
        resultados.forEach(resultado => {
            let tipo = resultado.tipo_reparacion;
            let modelo = resultado.modelo;

            if (tipo && datos[tipo][modelo]) {
                datos[tipo][modelo].conteo += resultado.conteo_reparaciones;
                datos[tipo][modelo].costo += resultado.costo_reparacion;
                datos[tipo].total.conteo += resultado.conteo_reparaciones;
                datos[tipo].total.costo += resultado.costo_reparacion;
            }
        });

        return datos;
    };

    const datosPorTipoYModelo = agregarDatosPorTipoYModelo(resultados);

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
        },
        button: {
            backgroundColor: '#009879',
            color: '#ffffff',
            padding: '10px 20px',
            border: 'none',
            cursor: 'pointer',
            fontSize: '16px',
            marginTop: '20px',
        }
    };

    return (
        <div style={styles.container}>
            <h2 style={styles.h2}>Calcular Costos de Reparación</h2>
            <form style={styles.form}>
                <div>
                    <label>Mes:</label>
                    <input type="number" value={mes} onChange={(e) => setMes(e.target.value)} required />
                </div>
                <div>
                    <label>Año:</label>
                    <input type="number" value={ano} onChange={(e) => setAno(e.target.value)} required />
                </div>
            </form>

            {error && <p>{error}</p>}

            <h3 style={styles.h2}>Resultados:</h3>
            {resultados.length > 0 ? (
                <table style={styles.table}>
                    <thead>
                        <tr>
                            <th style={styles.th} rowSpan="2">Lista de reparaciones</th>
                            {modelos.map((modelo, index) => (
                                <th key={index} style={styles.th} colSpan="2">{modelo}</th>
                            ))}
                            <th style={styles.th} rowSpan="2">TOTAL</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tiposReparacion.map((tipo, index) => (
                            <React.Fragment key={index}>
                                <tr>
                                    <td style={styles.td} rowSpan="2">{tipo}</td>
                                    {modelos.map((modelo, idx) => (
                                        <React.Fragment key={idx}>
                                            <td style={styles.td}>{datosPorTipoYModelo[index + 1][modelo].conteo}</td>
                                            <td style={styles.td}>{datosPorTipoYModelo[index + 1][modelo].costo}</td>
                                        </React.Fragment>
                                    ))}
                                    <td style={styles.td} rowSpan="2">{datosPorTipoYModelo[index + 1].total.conteo}</td>
                                </tr>
                                <tr>
                                    {modelos.map((modelo, idx) => (
                                        <React.Fragment key={idx}>
                                            <td style={styles.td}></td>
                                            <td style={styles.td}></td>
                                        </React.Fragment>
                                    ))}
                                </tr>
                            </React.Fragment>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p>No hay resultados para mostrar.</p>
            )}

            <Link to="/">
                <button style={styles.button}>Ok</button>
            </Link>
        </div>
    );
};

export default ReporteUno;
