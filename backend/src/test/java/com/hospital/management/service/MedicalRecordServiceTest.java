package com.hospital.management.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hospital.management.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hospital.management.entity.Doctor;
import com.hospital.management.entity.MedicalRecord;
import com.hospital.management.entity.Patient;
import com.hospital.management.repository.MedicalRecordRepository;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Test
    void testGetMedicalRecordById() {

        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setName("Arun Kumar");

        Doctor doctor = new Doctor();
        doctor.setDoctorId(1);
        doctor.setName("Dr. Karthik");

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setRecordId(1);
        medicalRecord.setPatient(patient);
        medicalRecord.setDoctor(doctor);
        medicalRecord.setDiagnosis("Viral Fever");
        medicalRecord.setTreatment("Rest and hydration");
        medicalRecord.setPrescription("Paracetamol 500mg");
        medicalRecord.setVisitDate(LocalDate.of(2026, 8, 28));

        when(medicalRecordRepository.findById(1))
                .thenReturn(Optional.of(medicalRecord));

        MedicalRecord result = medicalRecordService.getMedicalRecordById(1);

        assertNotNull(result);
        assertEquals(1, result.getRecordId());
        assertEquals("Arun Kumar", result.getPatient().getName());
        assertEquals("Dr. Karthik", result.getDoctor().getName());
        assertEquals("Viral Fever", result.getDiagnosis());
        assertEquals("Paracetamol 500mg", result.getPrescription());
    }
    
    @Test
    void testAddMedicalRecord() {

        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setName("Arun Kumar");

        Doctor doctor = new Doctor();
        doctor.setDoctorId(1);
        doctor.setName("Dr. Karthik");

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setRecordId(2);
        medicalRecord.setPatient(patient);
        medicalRecord.setDoctor(doctor);
        medicalRecord.setDiagnosis("Skin Allergy");
        medicalRecord.setTreatment("Keep skin moisturized");
        medicalRecord.setPrescription("Cetirizine 10mg");
        medicalRecord.setVisitDate(LocalDate.of(2026, 8, 28));

        when(medicalRecordRepository.save(medicalRecord))
                .thenReturn(medicalRecord);

        MedicalRecord result =
                medicalRecordService.addMedicalRecord(medicalRecord);

        assertNotNull(result);
        assertEquals(2, result.getRecordId());
        assertEquals("Arun Kumar", result.getPatient().getName());
        assertEquals("Dr. Karthik", result.getDoctor().getName());
        assertEquals("Skin Allergy", result.getDiagnosis());
        assertEquals("Cetirizine 10mg", result.getPrescription());
    }
    
    @Test
    void testGetAllMedicalRecords() {

        MedicalRecord record1 = new MedicalRecord();
        record1.setRecordId(1);
        record1.setDiagnosis("Viral Fever");

        MedicalRecord record2 = new MedicalRecord();
        record2.setRecordId(2);
        record2.setDiagnosis("Skin Allergy");

        List<MedicalRecord> medicalRecordList =
                Arrays.asList(record1, record2);

        when(medicalRecordRepository.findAll())
                .thenReturn(medicalRecordList);

        List<MedicalRecord> result =
                medicalRecordService.getAllMedicalRecords();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Viral Fever", result.get(0).getDiagnosis());
        assertEquals("Skin Allergy", result.get(1).getDiagnosis());
    }
    
    @Test
    void testDeleteMedicalRecord() {

        medicalRecordService.deleteMedicalRecord(1);

        verify(medicalRecordRepository).deleteById(1);
    }
    
    @Test
    void testGetMedicalRecordById_NotFound() {

        when(medicalRecordRepository.findById(99))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> medicalRecordService.getMedicalRecordById(99)
                );

        assertEquals(
                "Medical record not found with id: 99",
                exception.getMessage()
        );
    }
}
