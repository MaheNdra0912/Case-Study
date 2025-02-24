package com.medication.compliance.services;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medication.compliance.models.Intake;
import com.medication.compliance.models.Schedule;
import com.medication.compliance.repos.IntakeRepo;
import com.medication.compliance.repos.ScheduleRepo;

@Service
public class MedicationService {
    @Autowired
    private IntakeRepo intakeRepository;
    
    @Autowired
    private ScheduleRepo scheduleRepository;

    public Intake saveIntake(Intake intake) {
        return intakeRepository.save(intake);
    }
    
	public Optional<Schedule> getScheduleById(Long scheduleId) {
		return scheduleRepository.findById(scheduleId);
	}
	
	public Map<String, Object> calculateCompliance(Long userId, Long medicationId, LocalDate startDate, LocalDate endDate) {
		 List<Schedule> schedules = scheduleRepository.findByUserIdAndMedicationId(userId, medicationId);
		    if (schedules.isEmpty()) {
		        return Map.of("compliance_rate", 0.0, "missed_count", 0);
		    }

		    long totalScheduledDoses = 0;
		    List<Long> validScheduleIds = new ArrayList<>();

		    for (Schedule schedule : schedules) {
		        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
		            int dayOfWeek = date.getDayOfWeek().getValue();
		            if (isDayScheduled(schedule.getDaysOfWeek(), dayOfWeek)) {
		                totalScheduledDoses++;
		                if (!validScheduleIds.contains(schedule.getId())) {
		                    validScheduleIds.add(schedule.getId());
		                }
		            }
		        }
		    }
		    System.out.println("Valid Schedule IDs: " + validScheduleIds);

		    if (totalScheduledDoses == 0) {
		        return Map.of("compliance_rate", 0.0, "missed_count", 0);
		    }
		    
		    Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
		    Timestamp endTimestamp = Timestamp.valueOf(endDate.atTime(23, 59, 59));

		    long takenDoses = intakeRepository.countByStatusTakenAndScheduledForBetween(validScheduleIds, startTimestamp, endTimestamp);
		    System.out.println("Taken Doses: " + takenDoses);
		    long missedDoses = totalScheduledDoses - takenDoses;
		    double complianceRate = (double) takenDoses / totalScheduledDoses * 100;

		    return Map.of("compliance_rate", complianceRate, "missed_count", missedDoses);
	}

	private boolean isDayScheduled(Integer[] daysOfWeek, int day) {
	    return Arrays.asList(daysOfWeek).contains(day);
	}

	public Map<String, Object> calculateSMA(Long userId, Long medicationId, int days) {
	    LocalDate endDate = LocalDate.now();
	    LocalDate startDate = endDate.minusDays(days - 1);

	    Map<String, Object> complianceData = calculateCompliance(userId, medicationId, startDate, endDate);


	    double complianceRate = complianceData.containsKey("compliance_rate")
	            ? (double) ((Number) complianceData.getOrDefault("compliance_rate", 0.0))
	            : 0.0;

	    return Map.of(
	        "dates", Arrays.asList(startDate.toString(), endDate.toString()),
	        "values", Arrays.asList(complianceRate)
	    );
	}

}