package org.example.sports_gathering_system.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class CoachParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "activityId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer activityId;

    @NotNull(message = "coachId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer coachId;

    @NotNull(message = "participantId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer participantId;
}
