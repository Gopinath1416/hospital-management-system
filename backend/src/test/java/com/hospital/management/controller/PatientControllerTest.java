package com.hospital.management.controller;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import com.hospital.management.service.PatientService;
import com.hospital.management.entity.Patient;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.exception.GlobalExceptionHandler;
import com.hospital.management.dto.PatientReport;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


public class PatientControllerTest {

    private MockMvc mockMvc;

    private PatientService patientService;

    @BeforeEach
    void setUp() {

        patientService = Mockito.mock(PatientService.class);

        PatientController patientController =
                new PatientController(patientService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(patientController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
    
    @Test
    void testGetAllPatients() throws Exception {

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetPatientById() throws Exception {

        Patient patient = new Patient();

        patient.setPatientId(1);
        patient.setName("Arun Kumar");
        patient.setAge(28);
        patient.setGender("Male");
        patient.setPhone("9876543210");
        patient.setEmail("arun@gmail.com");
        patient.setAddress("Chennai");
        patient.setBloodGroup("O+");

        when(patientService.getPatientById(1))
                .thenReturn(patient);

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetPatientByIdNotFound() throws Exception {

        when(patientService.getPatientById(99))
                .thenThrow(new ResourceNotFoundException(
                        "Patient not found with id: 99"));

        mockMvc.perform(get("/patients/99"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void testAddPatientValidationError() throws Exception {

        String invalidPatientJson = """
                {
                  "name": "",
                  "age": 28,
                  "gender": "Male",
                  "phone": "9876543210",
                  "email": "arun@gmail.com",
                  "address": "Chennai",
                  "bloodGroup": "O+"
                }
                """;

        mockMvc.perform(post("/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidPatientJson))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testDeletePatient() throws Exception {

        mockMvc.perform(delete("/patients/1"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetPatientReport() throws Exception {

        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setName("Arun Kumar");

        PatientReport report = new PatientReport(
                patient,
                new ArrayList<>(),
                new ArrayList<>()
        );

        when(patientService.generatePatientReport(1))
                .thenReturn(report);

        mockMvc.perform(get("/patients/1/report"))
                .andExpect(status().isOk());
    }
}