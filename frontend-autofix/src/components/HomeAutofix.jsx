import React from 'react';
import { Link } from 'react-router-dom';

function HomeAutofix() {
  const styles = {
    container: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
      height: '100vh',
      backgroundColor: '#217b97',
      fontFamily: '"Courier New", Courier, monospace',
    },
    box: {
      backgroundColor: '#eb9196',
      padding: '20px',
      borderRadius: '10px',
      textAlign: 'center',
    },
    title: {
      fontSize: '5em', // Doble de grande que antes
      margin: '0.5em 0',
      fontWeight: '100', // Thin
    },
    linksContainer: {
      display: 'flex',
      justifyContent: 'center',
      gap: '15px',
    },
    link: {
      textDecoration: 'none',
      color: '#ffffff',
      backgroundColor: '#333',
      padding: '10px 20px',
      borderRadius: '5px',
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.box}>
        <h1 style={styles.title}>Reparaciones Autofix</h1>
        <div style={styles.linksContainer}>
          <Link to="/guardar" style={styles.link}>Registro Vehículo</Link>
          <Link to="/guardarreparacion" style={styles.link}>Registro Reparación</Link>
          <Link to="/lista-vehiculos" style={styles.link}>Lista Vehículos</Link> 
          <Link to="/historial" style={styles.link}>Historial de Vehiculos</Link>
        </div>
      </div>
    </div>
  );
}

export default HomeAutofix;
