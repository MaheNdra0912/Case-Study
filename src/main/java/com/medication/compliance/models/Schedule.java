package com.medication.compliance.models;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long medicationId;

    @NotNull
    private Long userId;

    @NotNull
    private LocalTime scheduledTime;

    @NotNull
    @Size(min = 1, message = "At least one day must be selected")
    @Column(columnDefinition = "integer[]")
    private Integer[] daysOfWeek;
    
}