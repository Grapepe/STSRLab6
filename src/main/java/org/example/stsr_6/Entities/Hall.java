package org.example.stsr_6.Entities;

import lombok.*;
import jakarta.persistence.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer capacity;
    private String description;
}