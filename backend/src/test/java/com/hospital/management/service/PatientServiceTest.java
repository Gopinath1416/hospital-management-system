package com.hospital.management.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hospital.management.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hospital.management.entity.Patient;
import com.hospital.management.repository.PatientRepository;
import com.hospital.management.dto.PatientReport;
import com.hospital.management.entity.Appointment;
import com.hospital.management.entity.Doctor;
import com.hospital.management.entity.MedicalRecord;
import com.hospital.management.repository.AppointmentRepository;
import com.hospital.management.repository.MedicalRecordRepository;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;
    
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    void testGetPatientById() {

        Patient patient = new Patient();

        patient.setPatientId(1);
        patient.setName("Arun Kumar");
        patient.setAge(28);
        patient.setGender("Male");

        when(patientRepository.findById(1))
                .thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientById(1);

        assertNotNull(result);
        assertEquals("Arun Kumar", result.getName());
        assertEquals(28, result.getAge());
        assertEquals("Male", result.getGender());
    }
    
    @Test
    void testAddPatient() {

        Patient patient = new Patient();

        patient.setPatientId(2);
        patient.setName("Priya Devi");
        patient.setAge(29);
        patient.setGender("Female");

        when(patientRepository.save(patient))
                .thenReturn(patient);

        Patient result = patientService.addPatient(patient);

        assertNotNull(result);
        assertEquals(2, result.getPatientId());
        assertEquals("Priya Devi", result.getName());
        assertEquals(29, result.getAge());
        assertEquals("Female", result.getGender());
    }
    
    @Test
    void testGetAllPatients() {

        Patient patient1 = new Patient();
        patient1.setPatientId(1);
        patient1.setName("Arun Kumar");

        Patient patient2 = new Patient();
        patient2.setPatientId(2);
        patient2.setName("Priya Devi");

        List<Patient> patientList = Arrays.asList(patient1, patient2);

        when(patientRepository.findAll())
                .thenReturn(patientList);

        List<Patient> result = patientService.getAllPatients();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Arun Kumar", result.get(0).getName());
        assertEquals("Priya Devi", result.get(1).getName());
    }
    
    @Test
    void testDeletePatient() {

        patientService.deletePatient(1);

        verify(patientRepository).deleteById(1);
    }
    
    @Test
    void testGetPatientById_NotFound() {

        when(patientRepository.findById(99))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> patientService.getPatientById(99)
                );

        assertEquals(
                "Patient not found with id: 99",
                exception.getMessage()
        );
    }
    
    @Test
    void testGeneratePatientReport() {

        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setName("Arun Kumar");

        Doctor doctor = new Doctor();
        doctor.setDoctorId(1);
        doctor.setName("Dr. Karthik");

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(LocalDate.of(2026, 8, 30));
        appointment.setAppointmentTime(LocalTime.of(10, 30));
        appointment.setStatus("Scheduled");

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setRecordId(1);
        medicalRecord.setPatient(patient);
        medicalRecord.setDoctor(doctor);
        medicalRecord.setDiagnosis("Viral Fever");
        medicalRecord.setTreatment("Rest and hydration");
        medicalRecord.setPrescription("Paracetamol 500mg");
        medicalRecord.setVisitDate(LocalDate.of(2026, 8, 28));

        when(patientRepository.findById(1))
                .thenReturn(Optional.of(patient));

        when(appointmentRepository.findByPatientPatientId(1))
                .thenReturn(Arrays.asList(appointment));

        when(medicalRecordRepository.findByPatientPatientId(1))
                .thenReturn(Arrays.asList(medicalRecord));

        PatientReport result =
                patientService.generatePatientReport(1);

        assertNotNull(result);

        assertEquals("Arun Kumar",
                result.getPatient().getName());

        assertEquals(1,
                result.getAppointments().size());

        assertEquals("Scheduled",
                result.getAppointments().get(0).getStatus());

        assertEquals(1,
                result.getMedicalRecords().size());

        assertEquals("Viral Fever",
                result.getMedicalRecords().get(0).getDiagnosis());
    }
    
    @Test
    void testGeneratePatientReport_PatientNotFound() {

        when(patientRepository.findById(99))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> patientService.generatePatientReport(99)
                );

        assertEquals(
                "Patient not found with id: 99",
                exception.getMessage()
        );
    }
}