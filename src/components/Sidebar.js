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

function Sidebar() {

  return (
    <div className="sidebar">

      <div className="sidebar-title">
        MENU
      </div>

      <NavLink to="/" className="sidebar-link">
        <FaHome />
        <span>Dashboard</span>
      </NavLink>

      <NavLink to="/patients" className="sidebar-link">
        <FaUserInjured />
        <span>Patients</span>
      </NavLink>

      <NavLink to="/doctors" className="sidebar-link">
        <FaUserMd />
        <span>Doctors</span>
      </NavLink>

      <NavLink to="/appointments" className="sidebar-link">
        <FaCalendarCheck />
        <span>Appointments</span>
      </NavLink>

      <NavLink to="/medical-records" className="sidebar-link">
        <FaNotesMedical />
        <span>Medical Records</span>
      </NavLink>

      <NavLink to="/patient-report" className="sidebar-link">
        <FaFileMedicalAlt />
        <span>Patient Report</span>
      </NavLink>

    </div>
  );
}

export default Sidebar;