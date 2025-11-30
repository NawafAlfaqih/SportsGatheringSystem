package org.example.sports_gathering_system.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class UserRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "raterId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer raterId;     // user giving the rating - must check

    @NotNull(message = "targetUserId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer targetUserId;   // user being rated - must check

    @NotNull(message = "activityId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer activityId;  // the activity where the rating happened - must check

    @NotNull(message = "score cannot be null.")
    @Min(value = 1, message = "score must be at least 1.")
    @Max(value = 5, message = "score must be at most 5.")
    @Column(columnDefinition = "int not null")
    private Integer score; //calculate total rating - sort by rate

    @NotBlank(message = "comment cannot be blank.")
    @Size(min = 3, message = "comment length must be at least 3.")
    @Column(columnDefinition = "varchar(200)")
    private String comment;

    @PastOrPresent(message = "date must be in the past or present.")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime date; //sort by date newest/oldest
}
