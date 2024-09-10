package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class smsParsingCCTransactionsModel {
    @JsonProperty("data")
    private List<SmsEvent> data;

    @JsonProperty("kafka_topic")
    private List<String> kafkaTopic;
}