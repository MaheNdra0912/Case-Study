package com.medication.compliance.controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medication.compliance.services.MedicationService;

@RestController
@RequestMapping("/api/v1/compliance")
class ComplianceController {
	
    @Autowired
    private MedicationService medicationService;
    
    @GetMapping("/rate")
    public ResponseEntity<Map<String, Object>> getComplianceRate(@RequestParam Long userId, @RequestParam Long medicationId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        Map<String, Object> complianceRate = medicationService.calculateCompliance(userId, medicationId, startDate, endDate);
        return ResponseEntity.ok(complianceRate);
    }
}
