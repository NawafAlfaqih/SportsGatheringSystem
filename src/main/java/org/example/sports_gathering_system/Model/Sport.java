package org.example.sports_gathering_system.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "sport name cannot be blank.")
    @Size(min = 3, message = "sport name length must be at least 3.")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String name;

    @NotBlank(message = "environment cannot be blank.")
    @Pattern(regexp = "^(indoor|outdoor|both)$",
            message = "environment must be 'indoor', 'outdoor' or 'both'.")
    @Column(columnDefinition = "varchar(10) not null")
    private String environment;
}
