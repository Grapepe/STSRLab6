package org.example.stsr_6.Entities;

import lombok.*;
import jakarta.persistence.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity
public class CinemaUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Изменено

    @ManyToOne
    @JoinColumn(name = "cinemaid")
    private Cinema cinemas;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User users;

    private String statuscinema;
    private Integer ratinguser; // Изменено
}