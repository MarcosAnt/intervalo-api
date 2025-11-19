package com.intervalo.intervalo_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Winner {

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;

}
