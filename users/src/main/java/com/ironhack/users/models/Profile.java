package com.ironhack.users.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@EnableAutoConfiguration
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    private String photo;
    private Date birthdate;
    private String address;
    private String postalCode;
    @NotNull
    private String phoneNumber;
    @Lob
    private String presentationText;
    @ManyToOne
    private User user;
    @NotNull
    private Long requestedProfessionalExperienceId;
    @NotNull
    private Long yearsOfExperienceId;
    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date updatedDate;
}
