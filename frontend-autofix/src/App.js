import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HomeAutofix from './components/HomeAutofix'; 
import RegistroVehiculo from './components/RegistroVehiculo';
import RegistroReparacion from './components/RegistroReparacion';
import VehiculoReparacion from './components/VehiculoReparacion';
import ListaReparaciones from './components/ListaReparaciones';
import ListaVehiculos from './components/ListaVehiculos';
import ListRepairVehicle from './components/ListRepairVehicle';
import Boleta from './components/Boleta';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomeAutofix/>} />
          <Route path="/guardar" element={<RegistroVehiculo/>} />
          <Route path="/guardarreparacion" element={<RegistroReparacion/>} />
          <Route path="/vehiculo-reparacion/:patente" element={<VehiculoReparacion/>} />
          <Route path="/vehiculo-reparaciones/:patente" element={<ListRepairVehicle/>} />
          <Route path="/lista-reparaciones" element={<ListaReparaciones/>} />
          <Route path="/lista-vehiculos" element={<ListaVehiculos/>} />
          <Route path="/boleta/:id" element={<Boleta />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
