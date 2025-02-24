package com.medication.compliance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medication.compliance.models.Intake;
import com.medication.compliance.models.Schedule;
import com.medication.compliance.services.MedicationService;

@RestController
@RequestMapping("/api/v1/intakes")
class IntakeController {
	
    @Autowired
    private MedicationService medicationService;

    @PostMapping
    public ResponseEntity<Intake> recordIntake(@RequestBody Intake intake) {
        Intake savedIntake = medicationService.saveIntake(intake);
        return ResponseEntity.ok(savedIntake);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<Schedule> getIntakesBySchedule(@PathVariable Long scheduleId) {
    	Schedule savedSchedule = medicationService.getScheduleById(scheduleId)
    	        .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + scheduleId));
        return ResponseEntity.ok(savedSchedule);
    }
}
