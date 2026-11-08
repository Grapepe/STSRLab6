package org.example.stsr_6.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity
public class FriendRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "usersone")
    private User usersone;

    @ManyToOne
    @JoinColumn(name = "userstwo")
    private User userstwo;
}