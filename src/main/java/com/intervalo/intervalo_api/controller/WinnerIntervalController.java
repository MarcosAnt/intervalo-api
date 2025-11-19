package com.intervalo.intervalo_api.controller;

import com.intervalo.intervalo_api.dto.WinnerIntervalResponse;
import com.intervalo.intervalo_api.exception.WinnersNotFoundException;
import com.intervalo.intervalo_api.service.WinnerIntervalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WinnerIntervalController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private WinnerIntervalService winnerIntervalService;

    @Operation(summary = "Obter intervalo de premiação",
            description = "Obtem o intervalo mínimo e máximo de premiação")
    @ApiResponse(responseCode = "200", description = "Dados das premiações.")
    @ApiResponse(responseCode = "204", description = "Nenhum dado de premiação encontrado.")
    @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    @ApiResponse(responseCode = "500", description = "Erro no fluxo.")
    @GetMapping("/intervals")
    public ResponseEntity<WinnerIntervalResponse> getWinnerInterval() {
        LOGGER.info("Iniciando WinnerIntervalController - getWinnerInterval");

        WinnerIntervalResponse response;
        try {
            response = winnerIntervalService.getMinMaxInterval();
        }
        catch (WinnersNotFoundException e) {
            LOGGER.warn("getWinnerInterval - Nenhum filme encontrado");
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            LOGGER.error("getWinnerInterval - Erro no fluxo");
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().body(response);
    }

}
