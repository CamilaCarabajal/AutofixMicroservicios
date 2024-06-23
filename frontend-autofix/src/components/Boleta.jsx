import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import repairvehicleService from '../services/RepairVehicleService';
import NavbarComponent from './NavbarComponent';
import styled from 'styled-components';

const BoletaContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 80vh; /* Ajustar el alto según sea necesario */
  background-color: #f8f9fa;
`;

const BoletaBox = styled.div`
  border: 2px solid #000;
  padding: 20px;
  background-color: #fff;
  width: calc(80% - 10px); /* Reducir el ancho en 10px */
  max-width: 590px; /* Ajustar para mantener el tamaño máximo deseado */
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

const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 20px;
`;

const Button = styled(Link)`
  text-decoration: none;
  background-color: #28a745;
  color: #fff;
  padding: 10px 20px;
  border-radius: 5px;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #218838;
  }
`;

const Boleta = () => {
  const { id } = useParams();
  const [boleta, setBoleta] = useState(null);

  useEffect(() => {
    const fetchBoleta = async () => {
      try {
        const response = await repairvehicleService.getBoleta(id);
        setBoleta(response.data);
      } catch (error) {
        console.error("Hubo un error al obtener los detalles de la boleta:", error);
      }
    };

    fetchBoleta();
  }, [id]);

  if (!boleta) {
    return <div>Cargando...</div>;
  }

  return (
    <div>
      <NavbarComponent />
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
          <ButtonContainer>
            <Button to="/">Ok</Button>
          </ButtonContainer>
        </BoletaBox>
      </BoletaContainer>
    </div>
  );
};

export default Boleta;

