package com.demo.springboot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class IncomeMaterielInfo {
    private Long id;

    private String materielNo;

    private String materielName;

    private Long incomeNum;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date incomeDate;

    private String startTime;

    private String endTime;

}