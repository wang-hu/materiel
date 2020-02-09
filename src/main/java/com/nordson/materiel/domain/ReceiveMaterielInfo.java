package com.nordson.materiel.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ReceiveMaterielInfo {
    private Long id;

    private String receiver;

    private String materielNo;

    private String materielName;

    private Long receiveNum;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date receiveDate;

    private String startTime;

    private String endTime;

}