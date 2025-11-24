package com.intervalo.intervalo_api.service;

import com.intervalo.intervalo_api.model.Movie;
import com.intervalo.intervalo_api.repository.MovieRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieListLoaderService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MovieRepository movieRepository;

    public void loadMovieList() {
        LOGGER.info("Iniciando MovieListLoaderService - Iniciando carregamento dos dados do CSV");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/static/movielist.csv")))) {

            String line;
            br.readLine(); // Pula a primeira linha
            List<Movie> movies = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                try {
                    this.validaDadosLinha(data);
                    List<String> studiosList = Arrays.stream(data[2].split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());

                    List<String> producersList = getProducersList(data[3]);

                    // Cria a entidade
                    Movie movie = new Movie();
                    movie.setReleaseYear(Integer.parseInt(data[0].trim()));
                    movie.setTitle(data[1].trim());
                    movie.setStudios(studiosList);
                    movie.setProducers(producersList);
                    if(data.length == 5) {
                        movie.setWinner("yes".equals(data[4].trim()));
                    }

                    movies.add(movie);

                } catch (NumberFormatException e) {
                    LOGGER.error("loadMovieList - Linha ignorada por formato inválido: " + line);
                }
            }

            movieRepository.saveAll(movies);
            LOGGER.info("loadMovieList - Fim do carregamento: " + movies.size() + " registros carregados do CSV para o H2.");

        } catch (Exception e) {
            LOGGER.error("loadMovieList - Erro ao inicializar dados do CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<String> getProducersList(String rawProducers) {
        List<String> producersListRaw = Arrays.stream(rawProducers.split(","))
                .toList();

        List<String> producersList = new ArrayList<>();
        // Trata os nomes que estão separados por "and"
        for (String producers : producersListRaw) {
            if (producers.contains("and")) {
                List<String> splitProducers = Arrays.stream(producers.split("\\s+and\\s+"))
                        .map(String::trim)
                        .toList();
                producersList.addAll(splitProducers.stream().filter(p -> !p.isEmpty()).toList());
            } else {
                producersList.add(producers.trim());
            }
        }
        return producersList;
    }

    /**
     * Se não houver pelo menos 4 atributos
     * ou
     * Se não houver os atributos studios ou producers
     * Então lança a exceção
     * @param data String array
     */
    private void validaDadosLinha(String[] data) {

        boolean invalido = false;
        if (data.length < 4 || data.length > 5) {
            invalido = true;
        }
        else if (
                data[0].trim().isEmpty() ||
                data[1].trim().isEmpty() ||
                data[2].trim().isEmpty() ||
                data[3].trim().isEmpty() ||
                "yes".equals(data[2]) ||
                "yes".equals(data[3])) {
            invalido = true;
        }

        if (invalido) throw new NumberFormatException();
    }
}
