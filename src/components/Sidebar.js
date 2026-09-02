import React from 'react';
import { NavLink } from 'react-router-dom';

import {
  FaHome,
  FaUserInjured,
  FaUserMd,
  FaCalendarCheck,
  FaNotesMedical,
  FaFileMedicalAlt
} from 'react-icons/fa';

function Sidebar({ sidebarOpen, setSidebarOpen }) {

  return (
    <div className={`sidebar ${sidebarOpen ? 'sidebar-open' : ''}`}>

      <div className="sidebar-title">
        MENU
      </div>

      <NavLink
        to="/"
        className="sidebar-link"
        onClick={() => setSidebarOpen(false)}
      >
        <FaHome />
        <span>Dashboard</span>
      </NavLink>

      <NavLink
        to="/patients"
        className="sidebar-link"
        onClick={() => setSidebarOpen(false)}
      >
        <FaUserInjured />
        <span>Patients</span>
      </NavLink>

      <NavLink
        to="/doctors"
        className="sidebar-link"
        onClick={() => setSidebarOpen(false)}
      >
        <FaUserMd />
        <span>Doctors</span>
      </NavLink>

      <NavLink
        to="/appointments"
        className="sidebar-link"
        onClick={() => setSidebarOpen(false)}
      >
        <FaCalendarCheck />
        <span>Appointments</span>
      </NavLink>

      <NavLink
        to="/medical-records"
        className="sidebar-link"
        onClick={() => setSidebarOpen(false)}
      >
        <FaNotesMedical />
        <span>Medical Records</span>
      </NavLink>

      <NavLink
        to="/patient-report"
        className="sidebar-link"
        onClick={() => setSidebarOpen(false)}
      >
        <FaFileMedicalAlt />
        <span>Patient Report</span>
      </NavLink>

    </div>
  );
}

export default Sidebar;