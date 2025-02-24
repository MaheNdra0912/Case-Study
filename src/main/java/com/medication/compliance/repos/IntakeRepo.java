package com.medication.compliance.repos;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medication.compliance.models.Intake;

public interface IntakeRepo extends JpaRepository<Intake, Long> {
	
	@Query("SELECT COUNT(i) FROM Intake i WHERE i.status = 'TAKEN' AND i.scheduleId IN :scheduleIds AND i.scheduledFor BETWEEN :startDate AND :endDate")
	long countByStatusTakenAndScheduledForBetween(@Param("scheduleIds") List<Long> scheduleIds, @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);


}