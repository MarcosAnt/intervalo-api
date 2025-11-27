package com.intervalo.intervalo_api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class WinnerIntervalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRetornarStatusOk() throws Exception {
        mockMvc.perform(get("/api/intervals"))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarIntervaloCorreto() throws Exception {
        mockMvc.perform(get("/api/intervals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min").exists())
                .andExpect(jsonPath("$.max").exists());
    }

    @Test
    void deveRetornarValoresCorretosParaIntervalo() throws Exception {
        mockMvc.perform(get("/api/intervals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min[0].producer").value("Produtor A"))
                .andExpect(jsonPath("$.min[0].interval").value(1))
                .andExpect(jsonPath("$.min[0].previousWin").value(2000))
                .andExpect(jsonPath("$.min[0].followingWin").value(2001))
                .andExpect(jsonPath("$.max[0].producer").value("Produtor B"))
                .andExpect(jsonPath("$.max[0].interval").value(10))
                .andExpect(jsonPath("$.max[0].previousWin").value(1990))
                .andExpect(jsonPath("$.max[0].followingWin").value(2000));
    }


}
