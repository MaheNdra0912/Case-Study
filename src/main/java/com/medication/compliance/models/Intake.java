package com.medication.compliance.models;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "intakes")
public class Intake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long scheduleId;


    @NotNull
    @Pattern(regexp = "TAKEN|MISSED", message = "Status must be either TAKEN or MISSED")
    private String status; // "TAKEN" or "MISSED"

	private Timestamp scheduledFor;

    private Timestamp takenAt; // Should be NOT NULL if status = "TAKEN"

    @AssertTrue(message = "takenAt must be provided when status is TAKEN")
    public boolean isTakenAtValid() {
        return !"TAKEN".equalsIgnoreCase(status) || takenAt != null;
    }
}