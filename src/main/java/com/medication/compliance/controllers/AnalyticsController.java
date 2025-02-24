package com.medication.compliance.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medication.compliance.services.MedicationService;



@RestController
@RequestMapping("/api/v1/analytics")
class AnalyticsController {
	
    @Autowired
    private MedicationService medicationService;

    @GetMapping("/sma")
    public ResponseEntity<Map<String, Object>> getMovingAverage(@RequestParam Long userId, @RequestParam Long medicationId, @RequestParam int days) {
        Map<String, Object> smaData = medicationService.calculateSMA(userId, medicationId, days);
        return ResponseEntity.ok(smaData);
    }
}
