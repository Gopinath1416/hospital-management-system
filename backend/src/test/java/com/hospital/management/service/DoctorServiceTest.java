package com.hospital.management.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hospital.management.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hospital.management.entity.Doctor;
import com.hospital.management.repository.DoctorRepository;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    @Test
    void testGetDoctorById() {

        Doctor doctor = new Doctor();

        doctor.setDoctorId(1);
        doctor.setName("Dr. Karthik");
        doctor.setSpecialization("Cardiologist");
        doctor.setExperience(5);

        when(doctorRepository.findById(1))
                .thenReturn(Optional.of(doctor));

        Doctor result = doctorService.getDoctorById(1);

        assertNotNull(result);
        assertEquals("Dr. Karthik", result.getName());
        assertEquals("Cardiologist", result.getSpecialization());
        assertEquals(5, result.getExperience());
    }
    
    @Test
    void testAddDoctor() {

        Doctor doctor = new Doctor();

        doctor.setDoctorId(2);
        doctor.setName("Dr. Meena Priya");
        doctor.setSpecialization("Dermatologist");
        doctor.setExperience(4);

        when(doctorRepository.save(doctor))
                .thenReturn(doctor);

        Doctor result = doctorService.addDoctor(doctor);

        assertNotNull(result);
        assertEquals(2, result.getDoctorId());
        assertEquals("Dr. Meena Priya", result.getName());
        assertEquals("Dermatologist", result.getSpecialization());
        assertEquals(4, result.getExperience());
    }
    
    @Test
    void testGetAllDoctors() {

        Doctor doctor1 = new Doctor();
        doctor1.setDoctorId(1);
        doctor1.setName("Dr. Karthik");

        Doctor doctor2 = new Doctor();
        doctor2.setDoctorId(2);
        doctor2.setName("Dr. Meena Priya");

        List<Doctor> doctorList = Arrays.asList(doctor1, doctor2);

        when(doctorRepository.findAll())
                .thenReturn(doctorList);

        List<Doctor> result = doctorService.getAllDoctors();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Dr. Karthik", result.get(0).getName());
        assertEquals("Dr. Meena Priya", result.get(1).getName());
    }
    
    @Test
    void testDeleteDoctor() {

        doctorService.deleteDoctor(1);

        verify(doctorRepository).deleteById(1);
    }
    
    @Test
    void testGetDoctorById_NotFound() {

        when(doctorRepository.findById(99))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> doctorService.getDoctorById(99)
                );

        assertEquals(
                "Doctor not found with id: 99",
                exception.getMessage()
        );
    }
}