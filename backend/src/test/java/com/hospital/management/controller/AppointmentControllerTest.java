package com.hospital.management.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hospital.management.entity.Appointment;
import com.hospital.management.entity.Doctor;
import com.hospital.management.entity.Patient;
import com.hospital.management.exception.GlobalExceptionHandler;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.service.AppointmentService;

public class AppointmentControllerTest {

    private MockMvc mockMvc;

    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {

        appointmentService = Mockito.mock(AppointmentService.class);

        AppointmentController appointmentController =
                new AppointmentController(appointmentService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(appointmentController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // 1. GET all appointments
    @Test
    void testGetAllAppointments() throws Exception {

        mockMvc.perform(get("/appointments"))
                .andExpect(status().isOk());
    }

    // 2. GET appointment by ID
    @Test
    void testGetAppointmentById() throws Exception {

        Patient patient = new Patient();
        patient.setPatientId(1);

        Doctor doctor = new Doctor();
        doctor.setDoctorId(1);

        Appointment appointment = new Appointment();

        appointment.setAppointmentId(1);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(LocalDate.of(2026, 8, 31));
        appointment.setAppointmentTime(LocalTime.of(11, 0));
        appointment.setStatus("Scheduled");

        when(appointmentService.getAppointmentById(1))
                .thenReturn(appointment);

        mockMvc.perform(get("/appointments/1"))
                .andExpect(status().isOk());
    }

    // 3. Appointment not found - 404
    @Test
    void testGetAppointmentByIdNotFound() throws Exception {

        when(appointmentService.getAppointmentById(99))
                .thenThrow(new ResourceNotFoundException(
                        "Appointment not found with id: 99"));

        mockMvc.perform(get("/appointments/99"))
                .andExpect(status().isNotFound());
    }

    // 4. POST validation - 400
    @Test
    void testAddAppointmentValidationError() throws Exception {

        String invalidAppointmentJson = """
                {
                  "patient": {
                    "patientId": 1
                  },
                  "doctor": {
                    "doctorId": 1
                  },
                  "appointmentDate": "2026-08-31",
                  "appointmentTime": "11:00:00",
                  "status": ""
                }
                """;

        mockMvc.perform(post("/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidAppointmentJson))
                .andExpect(status().isBadRequest());
    }

    // 5. DELETE appointment
    @Test
    void testDeleteAppointment() throws Exception {

        mockMvc.perform(delete("/appointments/1"))
                .andExpect(status().isOk());
    }
}