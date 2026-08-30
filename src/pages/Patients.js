import React, { useEffect, useState } from 'react';
import axios from 'axios';
import API_BASE_URL from "../api";

function Patients() {

  const [patient, setPatient] = useState({
    name: '',
    age: '',
    gender: '',
    phone: '',
    email: '',
    address: '',
    bloodGroup: ''
  });

  const [patients, setPatients] = useState([]);

  const fetchPatients = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/patients`);

    setPatients(response.data);

  } catch (error) {
    console.error(error);
  }
};

useEffect(() => {
  fetchPatients();
}, []);

  const handleChange = (event) => {
    setPatient({
      ...patient,
      [event.target.name]: event.target.value
    });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      await axios.post(`${API_BASE_URL}/patients`, patient);

      alert('Patient registered successfully');
      fetchPatients();

      setPatient({
        name: '',
        age: '',
        gender: '',
        phone: '',
        email: '',
        address: '',
        bloodGroup: ''
      });

    } catch (error) {
      console.error(error);
      alert('Failed to register patient');
    }
  };

  const deletePatient = async (id) => {
  const confirmDelete = window.confirm(
    'Are you sure you want to delete this patient?'
  );

  if (!confirmDelete) {
    return;
  }

  try {
    await axios.delete(`${API_BASE_URL}/patients/${id}`);

    alert('Patient deleted successfully');

    fetchPatients();

  } catch (error) {
    console.error(error);
    alert('Failed to delete patient');
  }
};

  return (
    <div className="container mt-4">

      <h2 className="mb-4">Patient Registration</h2>

      <form onSubmit={handleSubmit}>

        <div className="row">

          <div className="col-md-6 mb-3">
            <label className="form-label">Patient Name</label>
            <input
              type="text"
              className="form-control"
              name="name"
              value={patient.name}
              onChange={handleChange}
              required
            />
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">Age</label>
            <input
              type="number"
              className="form-control"
              name="age"
              value={patient.age}
              onChange={handleChange}
              required
            />
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">Gender</label>

            <select
              className="form-select"
              name="gender"
              value={patient.gender}
              onChange={handleChange}
              required
            >
              <option value="">Select Gender</option>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
              <option value="Other">Other</option>
            </select>
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">Phone</label>
            <input
              type="text"
              className="form-control"
              name="phone"
              value={patient.phone}
              onChange={handleChange}
              required
            />
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">Email</label>
            <input
              type="email"
              className="form-control"
              name="email"
              value={patient.email}
              onChange={handleChange}
            />
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">Blood Group</label>

            <select
              className="form-select"
              name="bloodGroup"
              value={patient.bloodGroup}
              onChange={handleChange}
              required
            >
              <option value="">Select Blood Group</option>
              <option value="A+">A+</option>
              <option value="A-">A-</option>
              <option value="B+">B+</option>
              <option value="B-">B-</option>
              <option value="AB+">AB+</option>
              <option value="AB-">AB-</option>
              <option value="O+">O+</option>
              <option value="O-">O-</option>
            </select>
          </div>

          <div className="col-md-12 mb-3">
            <label className="form-label">Address</label>

            <textarea
              className="form-control"
              name="address"
              value={patient.address}
              onChange={handleChange}
              rows="3"
              required
            />
          </div>

        </div>

        <button type="submit" className="btn btn-primary">
          Register Patient
        </button>

      </form>

      <hr className="my-5" />

<h2 className="mb-4">Registered Patients</h2>

<div className="table-responsive">

  <table className="table table-bordered table-striped">

    <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Age</th>
        <th>Gender</th>
        <th>Phone</th>
        <th>Email</th>
        <th>Blood Group</th>
        <th>Address</th>
        <th>Action</th>
      </tr>
    </thead>

    <tbody>

      {patients.map((p) => (

        <tr key={p.patientId}>

          <td>{p.patientId}</td>
          <td>{p.name}</td>
          <td>{p.age}</td>
          <td>{p.gender}</td>
          <td>{p.phone}</td>
          <td>{p.email}</td>
          <td>{p.bloodGroup}</td>
          <td>{p.address}</td>
          
          <td>
            <button
                className="btn btn-danger btn-sm"
                onClick={() => deletePatient(p.patientId)}
            >
                Delete
            </button>
         </td>

        </tr>

      ))}

    </tbody>

  </table>

</div>

    </div>
  );
}

export default Patients;