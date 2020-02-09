package com.nordson.materiel.domain;

import lombok.Data;

@Data
public class StockInfo {
    private Long id;

    private String materielNo;

    private String materielName;

    private Long stockNum;

    private Long total;
}