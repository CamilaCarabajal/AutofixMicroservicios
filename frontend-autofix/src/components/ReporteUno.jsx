import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import NavbarComponent from './NavbarComponent';
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

        tiposReparacion.forEach((tipo, index) => {
            datos[index + 1] = {};
            modelos.forEach(modelo => {
                datos[index + 1][modelo] = { conteo: 0, costo: 0 };
            });
            datos[index + 1].total = { conteo: 0, costo: 0 };
        });

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

    const meses = [
        { value: '01', label: 'Enero' },
        { value: '02', label: 'Febrero' },
        { value: '03', label: 'Marzo' },
        { value: '04', label: 'Abril' },
        { value: '05', label: 'Mayo' },
        { value: '06', label: 'Junio' },
        { value: '07', label: 'Julio' },
        { value: '08', label: 'Agosto' },
        { value: '09', label: 'Septiembre' },
        { value: '10', label: 'Octubre' },
        { value: '11', label: 'Noviembre' },
        { value: '12', label: 'Diciembre' }
    ];

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
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'center',
            justifyContent: 'center',
            marginBottom: '20px',
        },
        inputContainer: {
            margin: '0 10px',
        },
        select: {
            padding: '8px',
            fontSize: '16px',
            border: '1px solid #ddd',
            borderRadius: '4px',
            textAlign: 'center',
        },
        yearInput: {
            width: '100px', // Ancho ajustado para mostrar el año completo
            padding: '8px',
            fontSize: '16px',
            border: '1px solid #ddd',
            borderRadius: '4px',
            textAlign: 'center',
        },
        table: {
            width: '80%',
            borderCollapse: 'collapse',
            margin: '20px 0',
            boxShadow: '0 0 20px rgba(0, 0, 0, 0.15)',
            backgroundColor: '#fff',
        },
        th: {
            backgroundColor: '#333',
            color: '#ffffff',
            textAlign: 'center',
            padding: '10px',
            border: '1px solid #dddddd',
        },
        td: {
            textAlign: 'center',
            padding: '8px',
            border: '1px solid #dddddd',
            width: '80px', // Ancho ajustado para las celdas de conteo y costo
        },
        tdTipo: {
            textAlign: 'center',
            padding: '8px',
            border: '1px solid #dddddd',
            width: '180px', // Ancho ajustado para la celda de tipos de reparación
        },
        h2: {
            color: '#333',
        },
        buttonOk: {
            backgroundColor: '#333', // Color del encabezado de la tabla
            color: '#ffffff',
            padding: '10px 20px',
            border: 'none',
            cursor: 'pointer',
            fontSize: '16px',
            marginTop: '20px',
        }
    };

    const handleOkClick = () => {
        window.location.href = '/'; // Redirigir a la página de inicio
    };

    return (
        <div>
            <NavbarComponent />
            <div style={styles.container}>
                <h2 style={styles.h2}>Calcular Costos de Reparación</h2>
                <form style={styles.form}>
                    <div style={styles.inputContainer}>
                        <label>Mes:</label>
                        <select
                            value={mes}
                            onChange={(e) => setMes(e.target.value)}
                            style={styles.select}
                            required
                        >
                            <option value="">Seleccione un mes</option>
                            {meses.map((m) => (
                                <option key={m.value} value={m.value}>{m.label}</option>
                            ))}
                        </select>
                    </div>
                    <div style={styles.inputContainer}>
                        <label>Año:</label>
                        <input
                            type="number"
                            value={ano}
                            onChange={(e) => setAno(e.target.value)}
                            style={styles.yearInput}
                            placeholder="Año"
                            required
                        />
                    </div>
                </form>

                {error && <p>{error}</p>}

                <h3 style={styles.h2}>Resultados:</h3>
                {resultados.length > 0 ? (
                    <table style={styles.table}>
                        <thead>
                            <tr>
                                <th style={styles.th}>Tipo de Reparación</th>
                                {modelos.map((modelo) => (
                                    <th key={modelo} style={styles.th}>{modelo}</th>
                                ))}
                                <th style={styles.th}>Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            {Object.entries(datosPorTipoYModelo).map(([tipo, datos]) => (
                                <React.Fragment key={tipo}>
                                    <tr>
                                        <td style={styles.tdTipo} rowSpan="2">{tiposReparacion[tipo - 1]}</td>
                                        {modelos.map((modelo) => (
                                            <td key={modelo} style={styles.td}>
                                                <div>{datos[modelo].conteo}</div>
                                            </td>
                                        ))}
                                        <td style={styles.td}>
                                            <div>{datos.total.conteo}</div>
                                        </td>
                                    </tr>
                                    <tr>
                                        {modelos.map((modelo) => (
                                            <td key={modelo} style={styles.td}>
                                                <div>${datos[modelo].costo}</div>
                                            </td>
                                        ))}
                                        <td style={styles.td}>
                                            <div>${datos.total.costo}</div>
                                        </td>
                                    </tr>
                                </React.Fragment>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p>No hay resultados para el mes y año seleccionados.</p>
                )}

                <div style={{ textAlign: 'center' }}>
                    <button style={styles.buttonOk} onClick={handleOkClick}>Ok</button>
                </div>
            </div>
        </div>
    );
};

export default ReporteUno;
