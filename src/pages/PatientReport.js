import React, { useEffect, useState } from 'react';
import axios from 'axios';
import API_BASE_URL from "../api";

function PatientReport() {

  const [patients, setPatients] = useState([]);
  const [selectedPatientId, setSelectedPatientId] = useState('');
  const [report, setReport] = useState(null);

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

  const generateReport = async () => {

    if (!selectedPatientId) {
      alert('Please select a patient');
      return;
    }

    try {
      const response = await axios.get(`${API_BASE_URL}/patients/${selectedPatientId}/report`);

      setReport(response.data);

    } catch (error) {
      console.error(error);
      alert('Failed to generate patient report');
    }
  };

  return (
    <div className="container mt-4">

      <div className="page-header mb-4">
        <div>
          <h2 className="mb-1">
            Patient Report
          </h2>

          <p className="text-muted mb-0">
            Generate a complete patient history report
          </p>
        </div>
      </div>

      <div className="row mb-4 patient-report-controls">

        <div className="col-md-6">

          <label className="form-label">
            Select Patient
          </label>

          <select
            className="form-select"
            value={selectedPatientId}
            onChange={(event) => {
              setSelectedPatientId(event.target.value);
              setReport(null);
            }}
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

        <div className="col-md-3 d-flex align-items-end">

          <button
            className="btn btn-primary mt-3 report-generate-btn"
            onClick={generateReport}
          >
            Generate Report
          </button>

        </div>

      </div>

      {report && (

        <div>

          <hr />

          <div className="report-section-title mt-4 mb-3">
            <h3 className="mb-0">
              Patient Details
            </h3>
          </div>

          <div className="card mb-4 patient-details-card">

            <div className="card-body">

              <p>
                <strong>Patient ID:</strong>{' '}
                {report.patient.patientId}
              </p>

              <p>
                <strong>Name:</strong>{' '}
                {report.patient.name}
              </p>

              <p>
                <strong>Age:</strong>{' '}
                {report.patient.age}
              </p>

              <p>
                <strong>Gender:</strong>{' '}
                {report.patient.gender}
              </p>

              <p>
                <strong>Phone:</strong>{' '}
                {report.patient.phone}
              </p>

              <p>
                <strong>Email:</strong>{' '}
                {report.patient.email}
              </p>

              <p>
                <strong>Blood Group:</strong>{' '}
                {report.patient.bloodGroup}
              </p>

              <p>
                <strong>Address:</strong>{' '}
                {report.patient.address}
              </p>

            </div>

          </div>

          <div className="report-section-title mb-3">
            <h3 className="mb-0">
              Appointment History
            </h3>
          </div>

          <div className="table-responsive mb-5">

            <table className="table report-appointment-table">

              <thead>

                <tr>
                  <th>ID</th>
                  <th>Doctor</th>
                  <th>Specialization</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Status</th>
                </tr>

              </thead>

              <tbody>

                {report.appointments.length > 0 ? (

                  report.appointments.map((a) => (

                    <tr key={a.appointmentId}>

                      <td>
                        {a.appointmentId}
                      </td>

                      <td>
                        {a.doctor?.name}
                      </td>

                      <td>
                        {a.doctor?.specialization}
                      </td>

                      <td>
                        {a.appointmentDate}
                      </td>

                      <td>
                        {a.appointmentTime}
                      </td>

                      <td>
                        {a.status}
                      </td>

                    </tr>

                  ))

                ) : (

                  <tr>

                    <td
                      colSpan="6"
                      className="text-center"
                    >
                      No appointments found
                    </td>

                  </tr>

                )}

              </tbody>

            </table>

          </div>

          <div className="report-section-title mb-3">
            <h3 className="mb-0">
              Medical History
            </h3>
          </div>

          <div className="table-responsive mb-5">

            <table className="table report-medical-table">

              <thead>

                <tr>
                  <th>ID</th>
                  <th>Doctor</th>
                  <th>Diagnosis</th>
                  <th>Treatment</th>
                  <th>Prescription</th>
                  <th>Visit Date</th>
                </tr>

              </thead>

              <tbody>

                {report.medicalRecords.length > 0 ? (

                  report.medicalRecords.map((r) => (

                    <tr key={r.recordId}>

                      <td>
                        {r.recordId}
                      </td>

                      <td>
                        {r.doctor?.name}
                      </td>

                      <td>
                        {r.diagnosis}
                      </td>

                      <td>
                        {r.treatment}
                      </td>

                      <td>
                        {r.prescription}
                      </td>

                      <td>
                        {r.visitDate}
                      </td>

                    </tr>

                  ))

                ) : (

                  <tr>

                    <td
                      colSpan="6"
                      className="text-center"
                    >
                      No medical records found
                    </td>

                  </tr>

                )}

              </tbody>

            </table>

          </div>

        </div>

      )}

    </div>
  );
}

export default PatientReport;