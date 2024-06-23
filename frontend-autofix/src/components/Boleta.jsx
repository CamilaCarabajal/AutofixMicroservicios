import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import repairvehicleService from '../services/RepairVehicleService';
import NavbarComponent from './NavbarComponent';
import styled from 'styled-components';
import 'bootstrap/dist/css/bootstrap.min.css';

const BoletaContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f8f9fa;
`;

const BoletaBox = styled.div`
  border: 2px solid #000;
  padding: 20px;
  background-color: #fff;
  width: 80%;
  max-width: 600px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
`;

const BoletaRow = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 10px;
  margin-bottom: 10px;
  border: 2px solid #eb9191;
  background-color: #fff;
`;

const Label = styled.span`
  font-weight: bold;
`;

const Boleta = () => {
  const { id } = useParams(); // Obtener el parámetro id desde la URL
  const [boleta, setBoleta] = useState(null); // Estado para almacenar los datos de la boleta

  useEffect(() => {
    // Función para obtener los detalles de la boleta al cargar el componente
    const fetchBoleta = async () => {
      try {
        const response = await repairvehicleService.getBoleta(id); // Obtener boleta del servicio
        setBoleta(response.data); // Actualizar el estado con los datos de la boleta obtenidos
      } catch (error) {
        console.error("Hubo un error al obtener los detalles de la boleta:", error);
      }
    };

    fetchBoleta(); // Llamar a la función para obtener la boleta
  }, [id]); // Dependencia: id, para que se actualice cuando cambie

  if (!boleta) {
    return <div>Cargando...</div>;
  }

  return (
    <div>
      <NavbarComponent /> {/* Componente de navegación */}
      <BoletaContainer>
        <BoletaBox>
          <h2 className="text-center">Detalles de la Boleta</h2>
          <BoletaRow>
            <Label>ID Boleta:</Label> <span>{boleta.id_boleta}</span>
          </BoletaRow>
          <BoletaRow>
            <Label>Patente:</Label> <span>{boleta.patente}</span>
          </BoletaRow>
          <BoletaRow>
            <Label>Monto Total:</Label> <span>{boleta.monto_total}</span>
          </BoletaRow>
          <BoletaRow>
            <Label>Descuento:</Label> <span>{boleta.descuento}</span>
          </BoletaRow>
          <BoletaRow>
            <Label>Recargo:</Label> <span>{boleta.recargo}</span>
          </BoletaRow>
          <BoletaRow>
            <Label>IVA:</Label> <span>{boleta.iva}</span>
          </BoletaRow>
          <BoletaRow>
            <Label>Costo Total:</Label> <span>{boleta.costo_total}</span>
          </BoletaRow>
          <BoletaRow>
            <Label>Fecha de Salida:</Label> <span>{boleta.fecha_salida}</span>
          </BoletaRow>
          <BoletaRow>
            <Label>Hora de Salida:</Label> <span>{boleta.hora_salida}</span>
          </BoletaRow>
          <BoletaRow>
            <Label>Fecha Cliente:</Label> <span>{boleta.fecha_cliente}</span>
          </BoletaRow>
          <BoletaRow>
            <Label>Hora Cliente:</Label> <span>{boleta.hora_cliente}</span>
          </BoletaRow>
        </BoletaBox>
      </BoletaContainer>
    </div>
  );
};

export default Boleta;
