package com.intervalo.intervalo_api.repository;

import com.intervalo.intervalo_api.dto.ProducerYearDTO;
import com.intervalo.intervalo_api.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT new com.intervalo.intervalo_api.dto.ProducerYearDTO(m.releaseYear, producer) " +
            "FROM Movie m JOIN m.producers producer " +
            "WHERE m.winner = TRUE")
    public List<ProducerYearDTO> findProducerWinnerYears();
}
