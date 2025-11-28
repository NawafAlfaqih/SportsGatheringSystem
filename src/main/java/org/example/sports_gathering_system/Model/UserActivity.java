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
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "leaderId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer leaderId;   // user who created the activity - must check

    @NotBlank(message = "title cannot be blank.")
    @Size(min = 3, message = "title length must be at least 3.")
    @Column(columnDefinition = "varchar(40) not null")
    private String title;

    @NotBlank(message = "description cannot be blank.")
    @Size(min = 10, message = "description length must be at least 10.")
    @Column(columnDefinition = "varchar(200) not null")
    private String description;

    @NotBlank(message = "location cannot be blank.")
    @Size(min = 3, message = "location length must be at least 3.")
    @Column(columnDefinition = "varchar(40) not null")
    private String location; //validation for city

    @NotBlank(message = "participants gender cannot be blank.")
    @Pattern(regexp = "^(male|female)$",
            message = "participantsGender must be 'male', 'female'.")
    @Column(columnDefinition = "varchar(10) not null")
    private String participantsGender; //validation for user gender

    @NotNull(message = "maxParticipants cannot be null.")
    @Min(value = 2, message = "maxParticipants must be at least 2.")
    @Column(columnDefinition = "int not null")
    private Integer maxParticipants; //validation

    @NotNull(message = "sportId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer sportId; //filtering for search

    @NotNull(message = "dateTime cannot be null.")
    @FutureOrPresent(message = "dateTime cannot be in the past.")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime dateTime; //filter by datetime

    @NotNull(message = "isPrivate cannot be null.")
    @Column(columnDefinition = "boolean not null")
    private Boolean isPrivate; //check if private - validation with friends

    @Column(columnDefinition = "json")
    private String participantIds;   // list of user IDs - handle in service
}
