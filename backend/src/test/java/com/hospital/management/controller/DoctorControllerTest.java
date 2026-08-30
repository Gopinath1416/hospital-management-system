package com.hospital.management.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hospital.management.entity.Doctor;
import com.hospital.management.exception.GlobalExceptionHandler;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.service.DoctorService;

public class DoctorControllerTest {

    private MockMvc mockMvc;

    private DoctorService doctorService;

    @BeforeEach
    void setUp() {

        doctorService = Mockito.mock(DoctorService.class);

        DoctorController doctorController =
                new DoctorController(doctorService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(doctorController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // 1. GET all doctors
    @Test
    void testGetAllDoctors() throws Exception {

        mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk());
    }

    // 2. GET doctor by ID
    @Test
    void testGetDoctorById() throws Exception {

        Doctor doctor = new Doctor();

        doctor.setDoctorId(1);
        doctor.setName("Dr. Ravi");
        doctor.setSpecialization("Cardiology");
        doctor.setPhone("9876543210");
        doctor.setEmail("ravi@gmail.com");
        doctor.setExperience(5);

        when(doctorService.getDoctorById(1))
                .thenReturn(doctor);

        mockMvc.perform(get("/doctors/1"))
                .andExpect(status().isOk());
    }

    // 3. Doctor not found - 404
    @Test
    void testGetDoctorByIdNotFound() throws Exception {

        when(doctorService.getDoctorById(99))
                .thenThrow(new ResourceNotFoundException(
                        "Doctor not found with id: 99"));

        mockMvc.perform(get("/doctors/99"))
                .andExpect(status().isNotFound());
    }

    // 4. POST validation - 400
    @Test
    void testAddDoctorValidationError() throws Exception {

        String invalidDoctorJson = """
                {
                  "name": "",
                  "specialization": "Cardiology",
                  "phone": "9876543210",
                  "email": "ravi@gmail.com",
                  "experience": 5
                }
                """;

        mockMvc.perform(post("/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidDoctorJson))
                .andExpect(status().isBadRequest());
    }

    // 5. DELETE doctor
    @Test
    void testDeleteDoctor() throws Exception {

        mockMvc.perform(delete("/doctors/1"))
                .andExpect(status().isOk());
    }
}