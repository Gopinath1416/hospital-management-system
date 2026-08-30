import React, { useEffect, useState } from 'react';
import axios from 'axios';
import API_BASE_URL from "../api";

function MedicalRecords() {

  const [record, setRecord] = useState({
    patientId: '',
    doctorId: '',
    diagnosis: '',
    treatment: '',
    prescription: '',
    visitDate: ''
  });

  const [patients, setPatients] = useState([]);
  const [doctors, setDoctors] = useState([]);
  const [records, setRecords] = useState([]);

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

  const fetchRecords = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/medical-records`);
      setRecords(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchPatients();
    fetchDoctors();
    fetchRecords();
  }, []);

  const handleChange = (event) => {
    setRecord({
      ...record,
      [event.target.name]: event.target.value
    });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const recordData = {
      patient: {
        patientId: Number(record.patientId)
      },
      doctor: {
        doctorId: Number(record.doctorId)
      },
      diagnosis: record.diagnosis,
      treatment: record.treatment,
      prescription: record.prescription,
      visitDate: record.visitDate
    };

    try {
      await axios.post(`${API_BASE_URL}/medical-records`,
        recordData
      );

      alert('Medical record added successfully');

      setRecord({
        patientId: '',
        doctorId: '',
        diagnosis: '',
        treatment: '',
        prescription: '',
        visitDate: ''
      });

      fetchRecords();

    } catch (error) {
      console.error(error);
      alert('Failed to add medical record');
    }
  };

  const deleteRecord = async (id) => {

    const confirmDelete = window.confirm(
      'Are you sure you want to delete this medical record?'
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await axios.delete(`${API_BASE_URL}/medical-records/${id}`);

      alert('Medical record deleted successfully');

      fetchRecords();

    } catch (error) {
      console.error(error);
      alert('Failed to delete medical record');
    }
  };

  return (
    <div className="container mt-4">

      <h2 className="mb-4">Add Medical Record</h2>

      <form onSubmit={handleSubmit}>

        <div className="row">

          <div className="col-md-6 mb-3">

            <label className="form-label">
              Patient
            </label>

            <select
              className="form-select"
              name="patientId"
              value={record.patientId}
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
              value={record.doctorId}
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
              Diagnosis
            </label>

            <input
              type="text"
              className="form-control"
              name="diagnosis"
              value={record.diagnosis}
              onChange={handleChange}
              required
            />

          </div>

          <div className="col-md-6 mb-3">

            <label className="form-label">
              Visit Date
            </label>

            <input
              type="date"
              className="form-control"
              name="visitDate"
              value={record.visitDate}
              onChange={handleChange}
              required
            />

          </div>

          <div className="col-md-6 mb-3">

            <label className="form-label">
              Treatment
            </label>

            <textarea
              className="form-control"
              name="treatment"
              value={record.treatment}
              onChange={handleChange}
              rows="3"
              required
            />

          </div>

          <div className="col-md-6 mb-3">

            <label className="form-label">
              Prescription
            </label>

            <textarea
              className="form-control"
              name="prescription"
              value={record.prescription}
              onChange={handleChange}
              rows="3"
              required
            />

          </div>

        </div>

        <button
          type="submit"
          className="btn btn-primary"
        >
          Add Medical Record
        </button>

      </form>

      <hr className="my-5" />

      <h2 className="mb-4">
        Medical Records
      </h2>

      <div className="table-responsive">

        <table className="table table-bordered table-striped">

          <thead>
            <tr>
              <th>ID</th>
              <th>Patient</th>
              <th>Doctor</th>
              <th>Diagnosis</th>
              <th>Treatment</th>
              <th>Prescription</th>
              <th>Visit Date</th>
              <th>Action</th>
            </tr>
          </thead>

          <tbody>

            {records.map((r) => (

              <tr key={r.recordId}>

                <td>{r.recordId}</td>

                <td>
                  {r.patient?.name}
                </td>

                <td>
                  {r.doctor?.name}
                </td>

                <td>{r.diagnosis}</td>
                <td>{r.treatment}</td>
                <td>{r.prescription}</td>
                <td>{r.visitDate}</td>

                <td>
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() =>
                      deleteRecord(r.recordId)
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

export default MedicalRecords;