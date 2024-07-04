import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HomeAutofix from './components/HomeAutofix'; 
import RegistroVehiculo from './components/RegistroVehiculo';
import RegistroReparacion from './components/RegistroReparacion';
import VehiculoReparacion from './components/VehiculoReparacion';
import ListaVehiculos from './components/ListaVehiculos';
import ListRepairVehicle from './components/ListRepairVehicle';
import Boleta from './components/Boleta';
import Historial from './components/Historial';
import ReporteUno from './components/ReporteUno'; // Importar el componente
import ReporteDos from './components/ReporteDos'
import Reportes from './components/Reportes'; // Importar el nuevo componente

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
          <Route path="/historial" element={<Historial/>} />
          <Route path="/lista-vehiculos" element={<ListaVehiculos/>} />
          <Route path="/boleta/:id" element={<Boleta />} />
          <Route path="/reporte-uno" element={<ReporteUno />} /> 
          <Route path="/reporte-dos" element={<ReporteDos />} /> 
          <Route path="/reportes" element={<Reportes />} /> {/* Nueva ruta */}
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
