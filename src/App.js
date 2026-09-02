import { useState } from 'react';
import './App.css';

import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Navbar from './components/Navbar';
import Sidebar from './components/Sidebar';

import Dashboard from './pages/Dashboard';
import Patients from './pages/Patients';
import Doctors from './pages/Doctors';
import Appointments from './pages/Appointments';
import MedicalRecords from './pages/MedicalRecords';
import PatientReport from './pages/PatientReport';

function App() {

  const [sidebarOpen, setSidebarOpen] = useState(false);

  return (
    <BrowserRouter>

      <Navbar
        sidebarOpen={sidebarOpen}
        setSidebarOpen={setSidebarOpen}
      />

      <div className="app-layout">

        <Sidebar
          sidebarOpen={sidebarOpen}
          setSidebarOpen={setSidebarOpen}
        />

        <main className="main-content">

          <Routes>

            <Route
              path="/"
              element={<Dashboard />}
            />

            <Route
              path="/patients"
              element={<Patients />}
            />

            <Route
              path="/doctors"
              element={<Doctors />}
            />

            <Route
              path="/appointments"
              element={<Appointments />}
            />

            <Route
              path="/medical-records"
              element={<MedicalRecords />}
            />

            <Route
              path="/patient-report"
              element={<PatientReport />}
            />

          </Routes>

        </main>

      </div>

    </BrowserRouter>
  );
}

export default App;