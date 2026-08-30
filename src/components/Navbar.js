import React from 'react';
import { NavLink } from 'react-router-dom';
import { FaHospital, FaUserShield } from 'react-icons/fa';

function Navbar() {
  return (
    <nav className="navbar navbar-dark bg-primary shadow-sm">
      <div className="container-fluid px-4">

        <NavLink
          className="navbar-brand fw-bold d-flex align-items-center"
          to="/"
        >
          <FaHospital className="me-2" />
          MediCare HMS
        </NavLink>

        <div className="text-white d-flex align-items-center">
          <FaUserShield className="me-2" />
          <span className="fw-semibold">
            Hospital Admin
          </span>
        </div>

      </div>
    </nav>
  );
}

export default Navbar;