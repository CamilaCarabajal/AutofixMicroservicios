import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import NavbarComponent from './NavbarComponent';

function Reportes() {
  const [mes, setMes] = useState('');
  const [ano, setAno] = useState('');
  const navigate = useNavigate();

  const handleViewReporteUno = () => {
    if (mes && ano) {
      navigate(`/reporte-uno?mes=${mes}&ano=${ano}`);
    } else {
      alert("Por favor ingrese el mes y el año.");
    }
  };

  const handleViewReporteDos = () => {
    if (mes && ano) {
      navigate(`/reporte-dos?mes=${mes}&ano=${ano}`);
    } else {
      alert("Por favor ingrese el mes y el año.");
    }
  };

  const styles = {
    container: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
      height: '100vh',
      backgroundColor: '#f0f0f0',
      fontFamily: '"Courier New", Courier, monospace',
    },
    form: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      gap: '10px',
      marginBottom: '20px',
    },
    input: {
      padding: '10px',
      fontSize: '16px',
      borderRadius: '5px',
      border: '1px solid #ccc',
    },
    button: {
      textDecoration: 'none',
      color: '#ffffff',
      backgroundColor: '#333',
      padding: '10px 20px',
      borderRadius: '5px',
      cursor: 'pointer',
      margin: '5px'
    },
  };

  return (
    <div>
      <NavbarComponent />
      <div style={styles.container}>
        <form style={styles.form}>
          <input 
            type="number" 
            placeholder="Mes" 
            value={mes}
            onChange={(e) => setMes(e.target.value)}
            style={styles.input}
          />
          <input 
            type="number" 
            placeholder="Año" 
            value={ano}
            onChange={(e) => setAno(e.target.value)}
            style={styles.input}
          />
        </form>
        <button onClick={handleViewReporteUno} style={styles.button}>Ver Reporte 1</button>
        <button onClick={handleViewReporteDos} style={styles.button}>Ver Reporte 2</button>
      </div>
    </div>
  );
}

export default Reportes;
