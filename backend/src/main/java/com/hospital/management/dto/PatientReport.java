package com.hospital.management.dto;

import java.util.List;

import com.hospital.management.entity.Appointment;
import com.hospital.management.entity.MedicalRecord;
import com.hospital.management.entity.Patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientReport {

    private Patient patient;

    private List<Appointment> appointments;

    private List<MedicalRecord> medicalRecords;
}