package com.medication.compliance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.medication.compliance.models.Intake;
import com.medication.compliance.models.Schedule;
import com.medication.compliance.repos.IntakeRepo;
import com.medication.compliance.repos.ScheduleRepo;
import com.medication.compliance.services.MedicationService;

class MedicationServiceTest {

    @Mock
    private ScheduleRepo scheduleRepo;

    @Mock
    private IntakeRepo intakeRepo;

    @InjectMocks
    private MedicationService medicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateCompliance_WithValidData() {
        Long userId = 101L;
        Long medicationId = 1L;
        LocalDate startDate = LocalDate.of(2024, 2, 17);
        LocalDate endDate = LocalDate.of(2024, 2, 23);

        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setDaysOfWeek(new Integer[]{1, 2, 3, 4, 5});

        when(scheduleRepo.findByUserIdAndMedicationId(userId, medicationId))
            .thenReturn(Collections.singletonList(schedule));

        when(intakeRepo.countByStatusTakenAndScheduledForBetween(anyList(), any(), any()))
            .thenReturn(5L);

        Map<String, Object> compliance = medicationService.calculateCompliance(userId, medicationId, startDate, endDate);

        assertNotNull(compliance);
        assertEquals(71.42, (double) compliance.get("compliance_rate"), 0.1); // Approximate check
        assertEquals(2L, compliance.get("missed_count"));
    }

    @Test
    void testCalculateCompliance_NoSchedules() {
        Long userId = 102L;
        Long medicationId = 2L;
        LocalDate startDate = LocalDate.of(2024, 2, 17);
        LocalDate endDate = LocalDate.of(2024, 2, 23);

        when(scheduleRepo.findByUserIdAndMedicationId(userId, medicationId))
            .thenReturn(Collections.emptyList());

        Map<String, Object> compliance = medicationService.calculateCompliance(userId, medicationId, startDate, endDate);

        assertNotNull(compliance);
        assertEquals(0.0, compliance.get("compliance_rate"));
        assertEquals(0L, compliance.get("missed_count"));
    }

    @Test
    void testCalculateSMA() {
        Long userId = 101L;
        Long medicationId = 1L;
        int days = 7;

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days - 1);

        Map<String, Object> mockComplianceData = Map.of(
            "compliance_rate", 75.0,
            "missed_count", 2L
        );

        // Mock calculateCompliance() to return compliance rate for the period
        when(medicationService.calculateCompliance(userId, medicationId, startDate, endDate))
            .thenReturn(mockComplianceData);

        Map<String, Object> smaData = medicationService.calculateSMA(userId, medicationId, days);

        assertNotNull(smaData);
        assertTrue(smaData.containsKey("dates"));
        assertTrue(smaData.containsKey("values"));
    }

    @Test
    void testSaveIntake() {
        Intake intake = new Intake();
        intake.setId(1L);
        intake.setStatus("TAKEN");

        when(intakeRepo.save(any(Intake.class))).thenReturn(intake);

        Intake savedIntake = medicationService.saveIntake(intake);

        assertNotNull(savedIntake);
        assertEquals("TAKEN", savedIntake.getStatus());
    }

    @Test
    void testGetScheduleById_Found() {
        Schedule schedule = new Schedule();
        schedule.setId(1L);

        when(scheduleRepo.findById(1L)).thenReturn(Optional.of(schedule));

        Optional<Schedule> foundSchedule = medicationService.getScheduleById(1L);

        assertTrue(foundSchedule.isPresent());
        assertEquals(1L, foundSchedule.get().getId());
    }

    @Test
    void testGetScheduleById_NotFound() {
        when(scheduleRepo.findById(99L)).thenReturn(Optional.empty());

        Optional<Schedule> foundSchedule = medicationService.getScheduleById(99L);

        assertFalse(foundSchedule.isPresent());
    }
}