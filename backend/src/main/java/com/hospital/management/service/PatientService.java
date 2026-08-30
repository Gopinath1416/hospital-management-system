package com.hospital.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.dto.PatientReport;
import com.hospital.management.entity.Appointment;
import com.hospital.management.entity.MedicalRecord;
import com.hospital.management.entity.Patient;
import com.hospital.management.repository.AppointmentRepository;
import com.hospital.management.repository.MedicalRecordRepository;
import com.hospital.management.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public PatientService(
            PatientRepository patientRepository,
            AppointmentRepository appointmentRepository,
            MedicalRecordRepository medicalRecordRepository) {

        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Integer id) {
        return patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id: " + id));
    }

    public void deletePatient(Integer id) {
        patientRepository.deleteById(id);
    }

    public PatientReport generatePatientReport(Integer patientId) {

    	Patient patient = patientRepository.findById(patientId)
    	        .orElseThrow(() ->
    	                new ResourceNotFoundException(
    	                        "Patient not found with id: " + patientId));

        List<Appointment> appointments =
                appointmentRepository.findByPatientPatientId(patientId);

        List<MedicalRecord> medicalRecords =
                medicalRecordRepository.findByPatientPatientId(patientId);

        return new PatientReport(patient, appointments, medicalRecords);
    }
}