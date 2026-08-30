package com.hospital.management.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hospital.management.entity.Doctor;
import com.hospital.management.entity.MedicalRecord;
import com.hospital.management.entity.Patient;
import com.hospital.management.exception.GlobalExceptionHandler;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.service.MedicalRecordService;

public class MedicalRecordControllerTest {

    private MockMvc mockMvc;

    private MedicalRecordService medicalRecordService;

    @BeforeEach
    void setUp() {

        medicalRecordService = Mockito.mock(MedicalRecordService.class);

        MedicalRecordController medicalRecordController =
                new MedicalRecordController(medicalRecordService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(medicalRecordController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // 1. GET all medical records
    @Test
    void testGetAllMedicalRecords() throws Exception {

        mockMvc.perform(get("/medical-records"))
                .andExpect(status().isOk());
    }

    // 2. GET medical record by ID
    @Test
    void testGetMedicalRecordById() throws Exception {

        Patient patient = new Patient();
        patient.setPatientId(1);

        Doctor doctor = new Doctor();
        doctor.setDoctorId(1);

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setRecordId(1);
        medicalRecord.setPatient(patient);
        medicalRecord.setDoctor(doctor);
        medicalRecord.setDiagnosis("Viral Fever");
        medicalRecord.setTreatment("Rest and hydration");
        medicalRecord.setPrescription("Paracetamol 500mg");
        medicalRecord.setVisitDate(LocalDate.of(2026, 8, 28));

        when(medicalRecordService.getMedicalRecordById(1))
                .thenReturn(medicalRecord);

        mockMvc.perform(get("/medical-records/1"))
                .andExpect(status().isOk());
    }

    // 3. Medical record not found - 404
    @Test
    void testGetMedicalRecordByIdNotFound() throws Exception {

        when(medicalRecordService.getMedicalRecordById(99))
                .thenThrow(new ResourceNotFoundException(
                        "Medical record not found with id: 99"));

        mockMvc.perform(get("/medical-records/99"))
                .andExpect(status().isNotFound());
    }

    // 4. POST validation - 400
    @Test
    void testAddMedicalRecordValidationError() throws Exception {

        String invalidMedicalRecordJson = """
                {
                  "patient": {
                    "patientId": 1
                  },
                  "doctor": {
                    "doctorId": 1
                  },
                  "diagnosis": "",
                  "treatment": "Rest and hydration",
                  "prescription": "Paracetamol 500mg",
                  "visitDate": "2026-08-28"
                }
                """;

        mockMvc.perform(post("/medical-records")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidMedicalRecordJson))
                .andExpect(status().isBadRequest());
    }

    // 5. DELETE medical record
    @Test
    void testDeleteMedicalRecord() throws Exception {

        mockMvc.perform(delete("/medical-records/1"))
                .andExpect(status().isOk());
    }
}
