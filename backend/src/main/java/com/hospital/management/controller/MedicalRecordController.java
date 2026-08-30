package com.hospital.management.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hospital.management.entity.MedicalRecord;
import com.hospital.management.service.MedicalRecordService;

@CrossOrigin(origins = "${FRONTEND_URL:http://localhost:3000}")
@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public MedicalRecord addMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.addMedicalRecord(medicalRecord);
    }

    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    @GetMapping("/{id}")
    public MedicalRecord getMedicalRecordById(@PathVariable Integer id) {
        return medicalRecordService.getMedicalRecordById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteMedicalRecord(@PathVariable Integer id) {
        medicalRecordService.deleteMedicalRecord(id);
        return "Medical record deleted successfully";
    }
}
