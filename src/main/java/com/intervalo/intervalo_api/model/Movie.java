package com.intervalo.intervalo_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer releaseYear;

    private String title;

    @ElementCollection
    @CollectionTable(name = "studios", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "studio")
    private List<String> studios;

    @ElementCollection
    @CollectionTable(name = "producers", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "producer")
    private List<String> producers;

    private boolean winner;
}
