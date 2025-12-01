package org.example.sports_gathering_system.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "trainingSportId cannot be null.")
    @Column(columnDefinition = "int not null")
    private Integer trainingSportId; //filter for search

    @Column(columnDefinition = "int")
    private Integer currentActivityId; //activity validation

    @NotBlank(message = "username cannot be blank.")
    @Size(min = 3, message = "username length must be at least 3.")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String username; //search

    @NotBlank(message = "password cannot be blank.")
    @Size(min = 8, message = "password length must be at least '8'.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.,?!])(?=\\S+$).{8,20}$",
            message = "Password must contain at least one digit, one lowercase letter, " +
                    "one uppercase letter, one special character, and be 8-20 characters long.")
    @Column(columnDefinition = "varchar(20) not null")
    private String password; // check with email

    @NotBlank(message = "email cannot be blank.")
    @Email(message = "invalid email format.")
    @Column(columnDefinition = "varchar(35) not null unique")
    private String email; // check with password

    @NotBlank(message = "phoneNumber cannot be blank.")
    @Pattern(regexp = "^[0-9]{10,15}$",
            message = "phoneNumber must contain 10 to 15 digits.")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String phoneNumber; //search

    @NotBlank(message = "gender cannot be blank.")
    @Pattern(regexp = "^(male|female)$", message = "gender must be 'male', 'female'.")
    @Column(columnDefinition = "varchar(10) not null")
    private String gender; //filter for activities - validation for activities

    @NotNull(message = "age cannot be null.")
    @Min(value = 18, message = "age must be at least 18.")
    @Column(columnDefinition = "int not null")
    private Integer age; //ai recommendation

    @NotNull(message = "height cannot be null.")
    @Min(value = 120, message = "height must be at least 120 cm.")
    @Column(columnDefinition = "int not null")
    private Integer height; //ai recommendation

    @NotNull(message = "weight cannot be null.")
    @Min(value = 40, message = "weight must be at least 40 kg.")
    @Column(columnDefinition = "int not null")
    private Integer weight; //ai recommendation

    @NotBlank(message = "bio cannot be blank.")
    @Column(columnDefinition = "varchar(200) not null")
    private String bio; //ai recommendation

    @NotBlank(message = "city cannot be blank.")
    @Size(min = 2, message = "city length must be at least 2.")
    @Column(columnDefinition = "varchar(30) not null")
    private String city; //filter for activities - validation for activity

    @NotNull(message = "yearsOfExperience cannot be null.")
    @Min(value = 0, message = "yearsOfExperience cannot be negative.")
    @Column(columnDefinition = "int not null")
    private Integer yearsOfExperience; //filter for search

    @NotBlank(message = "Certificate Level cannot be blank.")
    @Pattern(regexp = "^(Pro|Mid|Junior)$",
            message = "Certificate Level must be 'Junior', 'Mid' or 'Pro'.")
    @Column(columnDefinition = "varchar(30) not null")
    private String certificateLevel; //filter for search

    @Pattern(regexp = "^(Pending|Rejected|Accepted)$",
            message = "status must be 'Pending', 'Accepted' or 'Rejected'.")
    @Column(columnDefinition = "varchar(20) not null default 'Pending'")
    private String status; //admin
}