package com.intervalo.intervalo_api.service;

import com.intervalo.intervalo_api.dto.ProducerYearDTO;
import com.intervalo.intervalo_api.dto.WinnerIntervalResponse;
import com.intervalo.intervalo_api.exception.WinnersNotFoundException;
import com.intervalo.intervalo_api.model.Winner;
import com.intervalo.intervalo_api.repository.MovieRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WinnerIntervalService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MovieRepository movieRepository;

    public WinnerIntervalResponse getMinMaxInterval() throws WinnersNotFoundException{
        LOGGER.info("Iniciando WinnerIntervalService - getMinMaxInterval");

        // Buscando os anos de vitória dos produtores
        List<ProducerYearDTO> producerYears = getProducerWinnerYears();
        LOGGER.info("getMinMaxInterval - agrupando dados dos Producers");
        Map<String, List<Integer>> ganhadores = producerYears.stream()
                .collect(Collectors.groupingBy(
                        ProducerYearDTO::getProducer,
                        Collectors.mapping(ProducerYearDTO::getReleaseYear, Collectors.toList())
                ));

        // getMinMaxInterval - calcula os intervalos
        List<Winner> ganhadoresIntervals = getIntervalos(ganhadores);

        LOGGER.info("getMinMaxInterval - Encontra menor intervalo");
        Optional<Winner> minIntervalOpt = ganhadoresIntervals.stream()
                .min(Comparator.comparing(Winner::getInterval));

        LOGGER.info("getMinMaxInterval - Encontra maior intervalo");
        Optional<Winner> maxIntervalOpt = ganhadoresIntervals.stream()
                .max(Comparator.comparing(Winner::getInterval));

        LOGGER.info("getMinMaxInterval - Inclui os produtores com o mesmo intervalo MÍNIMO");
        List<Winner> minList = getProdutoresInterval(minIntervalOpt.get(), ganhadoresIntervals);

        LOGGER.info("getMinMaxInterval - Inclui os produtores com o mesmo intervalo MÁXIMO");
        List<Winner> maxList = getProdutoresInterval(maxIntervalOpt.get(), ganhadoresIntervals);

        LOGGER.info("getMinMaxInterval - Mapeia Resultado para o Controller");
        WinnerIntervalResponse response = new WinnerIntervalResponse(minList, maxList);
        LOGGER.info("getMinMaxInterval - Retornando os resultados");
        return response;
    }

    private List<ProducerYearDTO> getProducerWinnerYears() {
        LOGGER.info("Iniciando WinnerIntervalService - getProducerWinnerYears");
        List<ProducerYearDTO> producerYears = movieRepository.findProducerWinnerYears();
        LOGGER.info("getProducerWinnerYears - retornando os dados");
        return producerYears;
    }

    private List<Winner> getIntervalos(Map<String, List<Integer>> ganhadores) {
        LOGGER.info("Iniciando WinnerIntervalService - getIntervalos");
        List<Winner> intervals = new ArrayList<>();
        ganhadores.forEach((producer, years) -> {
            // Ordena os anos para garantir o cálculo do intervalo
            years.sort(Integer::compareTo);

            // Necessita de pelo menos 2 vitórias para ter um intervalo
            if (years.size() >= 2) {

                for (int i = 1; i < years.size(); i++) {
                    int previousWin = years.get(i - 1);
                    int followingWin = years.get(i);
                    int interval = followingWin - previousWin;

                    Winner winner = new Winner();
                    winner.setProducer(producer);
                    winner.setInterval(interval);
                    winner.setPreviousWin(previousWin);
                    winner.setFollowingWin(followingWin);
                    intervals.add(winner);
                }
            }
        });
        LOGGER.info("getIntervalos - retornando os intervalos");
        return intervals;
    }

    private List<Winner> getProdutoresInterval(Winner interval, List<Winner> producers) {
        LOGGER.info("Iniciando WinnerIntervalService - getProdutoresMinInterval");
        Optional<Winner> itvOpt = Optional.of(interval);
        return itvOpt.map(itv -> producers.stream()
                        .filter(p -> p.getInterval() == itv.getInterval())
                        .collect(Collectors.toList()))
                .orElseGet(Collections::emptyList);
    }

}
