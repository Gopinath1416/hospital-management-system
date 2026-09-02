import React, { useEffect, useState } from 'react';
import axios from 'axios';
import API_BASE_URL from "../api";
import { useNavigate } from 'react-router-dom';

import {
  FaUserInjured,
  FaUserMd,
  FaCalendarCheck,
  FaNotesMedical
} from 'react-icons/fa';

function Dashboard() {

  const navigate = useNavigate();

  const [patientCount, setPatientCount] = useState(0);
  const [doctorCount, setDoctorCount] = useState(0);
  const [appointmentCount, setAppointmentCount] = useState(0);
  const [recordCount, setRecordCount] = useState(0);

  const fetchDashboardData = async () => {

    try {

      const patients = await axios.get(`${API_BASE_URL}/patients`);

      const doctors = await axios.get(`${API_BASE_URL}/doctors`);

      const appointments = await axios.get(`${API_BASE_URL}/appointments`);

      const records = await axios.get(`${API_BASE_URL}/medical-records`);

      setPatientCount(patients.data.length);
      setDoctorCount(doctors.data.length);
      setAppointmentCount(appointments.data.length);
      setRecordCount(records.data.length);

    } catch (error) {
      console.error('Failed to load dashboard data', error);
    }
  };

  useEffect(() => {
    fetchDashboardData();
  }, []);

  return (

    <div className="container mt-4">

      <div className="mb-4">
        <h2 className="page-title">
          Hospital Dashboard
        </h2>

        <p className="text-muted">
          Overview of hospital management activities
        </p>
      </div>

      <div className="row">

        <div className="col-lg-3 col-md-6 mb-4">

         <div
          className="card dashboard-card patient-card shadow-sm h-100"
          onClick={() => navigate('/patients')}
          style={{ cursor: 'pointer' }}
         >

            <div className="card-body d-flex align-items-center">

             <div className="dashboard-icon patient me-3">
                <FaUserInjured />
             </div>

              <div>
                <h6 className="text-muted">
                  Total Patients
                </h6>

                <h2 className="mb-0">
                  {patientCount}
                </h2>
              </div>

            </div>

          </div>

        </div>

        <div className="col-lg-3 col-md-6 mb-4">

          <div
            className="card dashboard-card doctor-card shadow-sm h-100"
            onClick={() => navigate('/doctors')}
            style={{ cursor: 'pointer' }}
          >

            <div className="card-body d-flex align-items-center">

              <div className="dashboard-icon doctor me-3">
                <FaUserMd />
              </div>

              <div>
                <h6 className="text-muted">
                  Total Doctors
                </h6>

                <h2 className="mb-0">
                  {doctorCount}
                </h2>
              </div>

            </div>

          </div>

        </div>

        <div className="col-lg-3 col-md-6 mb-4">

          <div
            className="card dashboard-card appointment-card shadow-sm h-100"
            onClick={() => navigate('/appointments')}
            style={{ cursor: 'pointer' }}
          >

            <div className="card-body d-flex align-items-center">

              <div className="dashboard-icon appointment me-3">
                <FaCalendarCheck />
              </div>

              <div>
                <h6 className="text-muted">
                  Appointments
                </h6>

                <h2 className="mb-0">
                  {appointmentCount}
                </h2>
              </div>

            </div>

          </div>

        </div>

        <div className="col-lg-3 col-md-6 mb-4">

          <div
            className="card dashboard-card record-card shadow-sm h-100"
            onClick={() => navigate('/medical-records')}
            style={{ cursor: 'pointer' }}
          >

            <div className="card-body d-flex align-items-center">

              <div className="dashboard-icon record me-3">
                <FaNotesMedical />
              </div>

              <div>
                <h6 className="text-muted">
                  Medical Records
                </h6>

                <h2 className="mb-0">
                  {recordCount}
                </h2>
              </div>

            </div>

          </div>

        </div>

      </div>

      <div className="welcome-card mt-3">

        <div>
          <h4>Welcome to Hospital Management System</h4>

          <p className="mb-0">
            Manage patients, doctors, appointments, medical records,
            and patient reports from one place.
          </p>
        </div>

        <div className="welcome-badge">
          HMS
        </div>

      </div>

    </div>
  );
}

export default Dashboard;