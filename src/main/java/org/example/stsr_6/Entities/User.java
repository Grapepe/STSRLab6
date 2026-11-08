package org.example.stsr_6.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Изменено с int на Integer

    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private Set<CinemaUser> cinemaUsers;

    @JsonIgnore
    @OneToMany(mappedBy = "usersone")
    private Set<FriendRelationship> friendRelationships;

    @JsonIgnore
    @OneToMany(mappedBy = "userstwo")
    private Set<FriendRelationship> friendRelationships2;

    private String email;
    private String login;
    private String password;

    private Boolean showotherfilms; // Изменено с boolean на Boolean
    private Boolean showforaddfr;   // Изменено с boolean на Boolean
}