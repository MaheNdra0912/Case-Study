package com.medication.compliance.repos;

import com.medication.compliance.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepo extends JpaRepository<Medication, Long> {
}