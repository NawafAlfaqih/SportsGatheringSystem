package org.example.sports_gathering_system.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class CoachRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "raterId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer raterId;   // user giving the rating

    @NotNull(message = "coachId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer coachId;   // coach being rated

    @NotNull(message = "activityId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer activityId;  // the coach activity

    @NotNull(message = "ratingValue cannot be null.")
    @Min(value = 1, message = "ratingValue must be at least 1.")
    @Max(value = 5, message = "ratingValue cannot be more than 5.")
    @Column(columnDefinition = "int not null")
    private Integer ratingValue;

    @Size(max = 200, message = "comment cannot exceed 200 characters.")
    @Column(columnDefinition = "varchar(200)")
    private String comment;
}
