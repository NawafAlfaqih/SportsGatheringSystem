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
public class UserParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "activityId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer activityId;

    @NotNull(message = "leaderId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer leaderId;

    @NotNull(message = "participantId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer participantId;
}
