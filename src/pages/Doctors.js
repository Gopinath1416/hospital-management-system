import React, { useEffect, useState } from 'react';
import axios from 'axios';
import API_BASE_URL from "../api";

function Doctors() {

  const [doctor, setDoctor] = useState({
    name: '',
    specialization: '',
    phone: '',
    email: '',
    experience: ''
  });

  const [doctors, setDoctors] = useState([]);

  const handleChange = (event) => {
    setDoctor({
      ...doctor,
      [event.target.name]: event.target.value
    });
  };

  const fetchDoctors = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/doctors`);

      setDoctors(response.data);

    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchDoctors();
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      await axios.post(`${API_BASE_URL}/doctors`,
        doctor
      );

      alert('Doctor registered successfully');

      setDoctor({
        name: '',
        specialization: '',
        phone: '',
        email: '',
        experience: ''
      });

      fetchDoctors();

    } catch (error) {
      console.error(error);
      alert('Failed to register doctor');
    }
  };

  const deleteDoctor = async (id) => {

    const confirmDelete = window.confirm(
      'Are you sure you want to delete this doctor?'
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await axios.delete(`${API_BASE_URL}/doctors/${id}`);

      alert('Doctor deleted successfully');

      fetchDoctors();

    } catch (error) {
      console.error(error);
      alert('Failed to delete doctor');
    }
  };

  return (
    <div className="container mt-4">

      <h2 className="mb-4">Doctor Registration</h2>

      <form onSubmit={handleSubmit}>

        <div className="row">

          <div className="col-md-6 mb-3">
            <label className="form-label">
              Doctor Name
            </label>

            <input
              type="text"
              className="form-control"
              name="name"
              value={doctor.name}
              onChange={handleChange}
              required
            />
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">
              Specialization
            </label>

            <input
              type="text"
              className="form-control"
              name="specialization"
              value={doctor.specialization}
              onChange={handleChange}
              required
            />
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">
              Phone
            </label>

            <input
              type="text"
              className="form-control"
              name="phone"
              value={doctor.phone}
              onChange={handleChange}
              required
            />
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">
              Email
            </label>

            <input
              type="email"
              className="form-control"
              name="email"
              value={doctor.email}
              onChange={handleChange}
            />
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">
              Experience
            </label>

            <input
              type="number"
              className="form-control"
              name="experience"
              value={doctor.experience}
              onChange={handleChange}
              required
            />
          </div>

        </div>

        <button
          type="submit"
          className="btn btn-primary"
        >
          Register Doctor
        </button>

      </form>

      <hr className="my-5" />

      <h2 className="mb-4">
        Registered Doctors
      </h2>

      <div className="table-responsive">

        <table className="table table-bordered table-striped">

          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Specialization</th>
              <th>Phone</th>
              <th>Email</th>
              <th>Experience</th>
              <th>Action</th>
            </tr>
          </thead>

          <tbody>

            {doctors.map((d) => (

              <tr key={d.doctorId}>

                <td>{d.doctorId}</td>
                <td>{d.name}</td>
                <td>{d.specialization}</td>
                <td>{d.phone}</td>
                <td>{d.email}</td>
                <td>{d.experience} Years</td>

                <td>
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() =>
                      deleteDoctor(d.doctorId)
                    }
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

export default Doctors;