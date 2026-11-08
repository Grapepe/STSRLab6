package org.example.stsr_6.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @OneToMany(mappedBy = "cinemas")
    private Set<CinemaUser> cinemaUsers;

    // Указываем, что картинка может быть длинной (например, base64 или очень длинный URL)
    @Column(columnDefinition = "TEXT")
    private String urlimage;

    private String headname;
    private String director;
    private String genre;

    // Указываем, что описание может быть очень длинным
    @Column(columnDefinition = "TEXT")
    private String aboutis;

    private Integer year;
    private Integer marks;
    private Float rating;
}