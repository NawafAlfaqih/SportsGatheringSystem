package org.example.sports_gathering_system.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class CoachJoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "userId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer userId; // user requesting to join

    @NotNull(message = "activityId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer activityId; //must check

    @Pattern(regexp = "^(Pending|Accepted|Rejected)$",
            message = "status must be 'Pending', 'Accepted', or 'Rejected'.")
    @Column(columnDefinition = "varchar(20) not null default 'Pending'")
    private String status;

    @Column(columnDefinition = "varchar(150)")
    private String note;     // message from coach to leader
}
