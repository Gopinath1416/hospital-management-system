import React, { useEffect, useState } from 'react';
import axios from 'axios';
import API_BASE_URL from "../api";

function Appointments() {

  const [appointment, setAppointment] = useState({
    patientId: '',
    doctorId: '',
    appointmentDate: '',
    appointmentTime: '',
    status: 'Scheduled'
  });

  const [patients, setPatients] = useState([]);
  const [doctors, setDoctors] = useState([]);
  const [appointments, setAppointments] = useState([]);

  const fetchPatients = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/patients`);
      setPatients(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchDoctors = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/doctors`);
      setDoctors(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchAppointments = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/appointments`);
      setAppointments(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchPatients();
    fetchDoctors();
    fetchAppointments();
  }, []);

  const handleChange = (event) => {
    setAppointment({
      ...appointment,
      [event.target.name]: event.target.value
    });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const appointmentData = {
      patient: {
        patientId: Number(appointment.patientId)
      },
      doctor: {
        doctorId: Number(appointment.doctorId)
      },
      appointmentDate: appointment.appointmentDate,
      appointmentTime: appointment.appointmentTime,
      status: appointment.status
    };

    try {
      await axios.post(`${API_BASE_URL}/appointments`,appointmentData
      );

      alert('Appointment booked successfully');

      setAppointment({
        patientId: '',
        doctorId: '',
        appointmentDate: '',
        appointmentTime: '',
        status: 'Scheduled'
      });

      fetchAppointments();

    } catch (error) {
      console.error(error);
      alert('Failed to book appointment');
    }
  };

  const deleteAppointment = async (id) => {

    const confirmDelete = window.confirm(
      'Are you sure you want to delete this appointment?'
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await axios.delete(`${API_BASE_URL}/appointments/${id}`);

      alert('Appointment deleted successfully');

      fetchAppointments();

    } catch (error) {
      console.error(error);
      alert('Failed to delete appointment');
    }
  };

  return (
    <div className="container mt-4">

      <div className="page-header mb-4">
        <div>
          <h2 className="mb-1">Book Appointment</h2>
          <p className="text-muted mb-0">
            Schedule and manage patient appointments
          </p>
        </div>
      </div>

<form onSubmit={handleSubmit} className="appointment-form">

        <div className="row">

          <div className="col-md-6 mb-3">

            <label className="form-label">
              Patient
            </label>

            <select
              className="form-select"
              name="patientId"
              value={appointment.patientId}
              onChange={handleChange}
              required
            >

              <option value="">
                Select Patient
              </option>

              {patients.map((p) => (
                <option
                  key={p.patientId}
                  value={p.patientId}
                >
                  {p.name}
                </option>
              ))}

            </select>

          </div>

          <div className="col-md-6 mb-3">

            <label className="form-label">
              Doctor
            </label>

            <select
              className="form-select"
              name="doctorId"
              value={appointment.doctorId}
              onChange={handleChange}
              required
            >

              <option value="">
                Select Doctor
              </option>

              {doctors.map((d) => (
                <option
                  key={d.doctorId}
                  value={d.doctorId}
                >
                  {d.name} - {d.specialization}
                </option>
              ))}

            </select>

          </div>

          <div className="col-md-6 mb-3">

            <label className="form-label">
              Appointment Date
            </label>

            <input
              type="date"
              className="form-control"
              name="appointmentDate"
              value={appointment.appointmentDate}
              onChange={handleChange}
              required
            />

          </div>

          <div className="col-md-6 mb-3">

            <label className="form-label">
              Appointment Time
            </label>

            <input
              type="time"
              className="form-control"
              name="appointmentTime"
              value={appointment.appointmentTime}
              onChange={handleChange}
              required
            />

          </div>

          <div className="col-md-6 mb-3">

            <label className="form-label">
              Status
            </label>

            <select
              className="form-select"
              name="status"
              value={appointment.status}
              onChange={handleChange}
            >

              <option value="Scheduled">
                Scheduled
              </option>

              <option value="Completed">
                Completed
              </option>

              <option value="Cancelled">
                Cancelled
              </option>

            </select>

          </div>

        </div>

        <button
          type="submit"
          className="btn btn-primary appointment-submit-btn"
        >
          Book Appointment
        </button>

      </form>

      <hr className="my-5" />

      <div className="section-header mb-3">

        <div>
          <h2 className="mb-1">
            Appointment List
          </h2>

          <p className="text-muted mb-0">
            View and manage all scheduled appointments
          </p>
        </div>

        <span className="appointment-count-badge">
          {appointments.length} Appointments
        </span>

      </div>

      <div className="table-responsive">

        <table className="table appointment-table">

          <thead>
            <tr>
              <th>ID</th>
              <th>Patient</th>
              <th>Doctor</th>
              <th>Date</th>
              <th>Time</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>

          <tbody>

            {appointments.map((a) => (

              <tr key={a.appointmentId}>

                <td>{a.appointmentId}</td>

                <td>
                  {a.patient?.name}
                </td>

                <td>
                  {a.doctor?.name}
                </td>

                <td>
                  {a.appointmentDate}
                </td>

                <td>
                  {a.appointmentTime}
                </td>

                <td>
                  <span
                    className={`appointment-status ${a.status?.toLowerCase()}`}
                  >
                    {a.status}
                  </span>
                </td>

                <td>
                  <button
                    className="btn btn-danger btn-sm appointment-delete-btn"
                    onClick={() =>
                      deleteAppointment(a.appointmentId)
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

export default Appointments;