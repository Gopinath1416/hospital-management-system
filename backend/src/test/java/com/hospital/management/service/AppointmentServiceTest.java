package com.hospital.management.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hospital.management.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hospital.management.entity.Appointment;
import com.hospital.management.entity.Doctor;
import com.hospital.management.entity.Patient;
import com.hospital.management.repository.AppointmentRepository;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void testGetAppointmentById() {

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

        when(appointmentRepository.findById(1))
                .thenReturn(Optional.of(appointment));

        Appointment result = appointmentService.getAppointmentById(1);

        assertNotNull(result);
        assertEquals(1, result.getAppointmentId());
        assertEquals("Arun Kumar", result.getPatient().getName());
        assertEquals("Dr. Karthik", result.getDoctor().getName());
        assertEquals("Scheduled", result.getStatus());
    }
    
    @Test
    void testAddAppointment() {

        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setName("Arun Kumar");

        Doctor doctor = new Doctor();
        doctor.setDoctorId(1);
        doctor.setName("Dr. Karthik");

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(2);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(LocalDate.of(2026, 8, 31));
        appointment.setAppointmentTime(LocalTime.of(11, 0));
        appointment.setStatus("Scheduled");

        when(appointmentRepository.save(appointment))
                .thenReturn(appointment);

        Appointment result = appointmentService.addAppointment(appointment);

        assertNotNull(result);
        assertEquals(2, result.getAppointmentId());
        assertEquals("Arun Kumar", result.getPatient().getName());
        assertEquals("Dr. Karthik", result.getDoctor().getName());
        assertEquals("Scheduled", result.getStatus());
    }
    
    @Test
    void testGetAllAppointments() {

        Appointment appointment1 = new Appointment();
        appointment1.setAppointmentId(1);
        appointment1.setStatus("Scheduled");

        Appointment appointment2 = new Appointment();
        appointment2.setAppointmentId(2);
        appointment2.setStatus("Completed");

        List<Appointment> appointmentList =
                Arrays.asList(appointment1, appointment2);

        when(appointmentRepository.findAll())
                .thenReturn(appointmentList);

        List<Appointment> result =
                appointmentService.getAllAppointments();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Scheduled", result.get(0).getStatus());
        assertEquals("Completed", result.get(1).getStatus());
    }
    
    @Test
    void testDeleteAppointment() {

        appointmentService.deleteAppointment(1);

        verify(appointmentRepository).deleteById(1);
    }
    
    @Test
    void testGetAppointmentById_NotFound() {

        when(appointmentRepository.findById(99))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> appointmentService.getAppointmentById(99)
                );

        assertEquals(
                "Appointment not found with id: 99",
                exception.getMessage()
        );
    }
}
