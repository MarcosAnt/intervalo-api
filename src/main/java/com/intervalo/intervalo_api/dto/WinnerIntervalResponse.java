package com.intervalo.intervalo_api.dto;

import com.intervalo.intervalo_api.model.Winner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WinnerIntervalResponse {

    private List<Winner> min;
    private List<Winner> max;
}
