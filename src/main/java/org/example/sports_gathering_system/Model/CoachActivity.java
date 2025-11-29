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
public class CoachActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "coachId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer coachId;   // coach who created the activity

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
    private String location;

    @NotBlank(message = "participants gender cannot be blank.")
    @Pattern(regexp = "^(male|female)$",
            message = "participantsGender must be 'male' or 'female'.")
    @Column(columnDefinition = "varchar(10) not null")
    private String participantsGender;

    @NotNull(message = "maxParticipants cannot be null.")
    @Min(value = 2, message = "maxParticipants must be at least 2.")
    @Column(columnDefinition = "int not null")
    private Integer maxParticipants;

    @NotNull(message = "sportId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer sportId;

    @NotNull(message = "dateTime cannot be null.")
    @FutureOrPresent(message = "dateTime cannot be in the past.")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime dateTime; //filter only new dates - no old

    @NotNull(message = "price cannot be null.")
    @Min(value = 0, message = "price must be 0 or greater.")
    @Column(columnDefinition = "double not null")
    private Double price;   // filter by price

    @Column(columnDefinition = "json")
    private String participantIds;   // list of user IDs

}
