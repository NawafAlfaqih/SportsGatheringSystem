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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "username cannot be blank.")
    @Size(min = 3, message = "username's length must be at least '3'.")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String username; //search - add friend

    @NotBlank(message = "password cannot be blank.")
    @Size(min = 8, message = "password length must be at least '8'.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.,?!])(?=\\S+$).{8,20}$",
            message = "Password must contain at least one digit, one lowercase letter, " +
                    "one uppercase letter, one special character, and be 8-20 characters long.")
    @Column(columnDefinition = "varchar(20) not null")
    private String password; // check with email

    @NotBlank(message = "email cannot be blank.")
    @Email
    @Column(columnDefinition = "varchar(35) not null unique")
    private String email; // check with password

    @NotBlank(message = "phoneNumber cannot be blank.")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "phoneNumber must contain 10 to 15 digits.")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String phoneNumber; //search - add friend

    @NotBlank(message = "gender cannot be blank.")
    @Pattern(regexp = "^(male|female)$", message = "gender must be 'male', 'female'.")
    @Column(columnDefinition = "varchar(10) not null")
    private String gender; //filter for activities - validation for activities

    @NotNull(message = "age cannot be null.")
    @Min(value = 10, message = "age must be at least 10.")
    @Column(columnDefinition = "int not null")
    private Integer age; //ai recommendation

    @NotNull(message = "height cannot be null.")
    @Min(value = 100, message = "height must be at least 100 cm.")
    @Max(value = 250, message = "height must be at most 250 cm.")
    @Column(columnDefinition = "int not null")
    private Integer height; //ai recommendation

    @NotNull(message = "weight cannot be null.")
    @Min(value = 30, message = "weight must be at least 30 kg.")
    @Column(columnDefinition = "int not null")
    private Integer weight; //ai recommendation

    @NotBlank(message = "bio cannot be blank.")
    @Column(columnDefinition = "varchar(200) not null")
    private String bio; //ai recommendation

    @NotBlank(message = "city cannot be blank.")
    @Size(min = 2, message = "city name length must be between 2 and 30.")
    @Column(columnDefinition = "varchar(30) not null")
    private String city; //filter for activities - validation for activity

    @Column(columnDefinition = "json")
    private String sportIds;       // e.g., [1,3,5]
    //filter for search

    @Column(columnDefinition = "json")
    private String friendIds;      // e.g., [5,12,20]

    @Column(columnDefinition = "json")
    private String ratingIds;      // ratings made by this user
    //total rating - filter for search high/low

    @Column(columnDefinition = "int")
    private Integer currentActivityId; //activity validation

}