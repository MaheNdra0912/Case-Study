package com.medication.compliance.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medication.compliance.models.Schedule;

public interface ScheduleRepo extends JpaRepository<Schedule, Long> {

	List<Schedule> findByUserIdAndMedicationId(Long userId, Long medicationId);
}