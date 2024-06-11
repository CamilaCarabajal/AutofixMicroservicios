import React from 'react';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeAutofix from './components/HomeAutofix';
import RegistroVehiculo from './components/RegistroVehiculo';
import RegistroReparacion from './components/RegistroReparacion';
import VehiculoReparacion from './components/VehiculoReparacion';
import ListaReparaciones from './components/ListaReparaciones';
import ListaVehiculos from './components/ListaVehiculos'

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomeAutofix/>} />
          <Route path="/guardar" element={<RegistroVehiculo/>} />
          <Route path="/guardarreparacion" element={<RegistroReparacion/>} />
          <Route path="/vehiculo-reparacion" component={VehiculoReparacion} />
          <Route path="/lista-reparaciones" component={ListaReparaciones} />
          <Route path="/lista-vehiculos" element={<ListaVehiculos/>} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
