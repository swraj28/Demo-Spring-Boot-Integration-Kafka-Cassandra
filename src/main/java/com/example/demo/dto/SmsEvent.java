package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SmsEvent {

    @JsonProperty("cId")
    private String customerId;

    @JsonProperty("smsDateTime")
    private String smsDateTime;

    @JsonProperty("smsOperator")
    private String smsOperator;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("transaction_month")
    private String transactionMonth;

    @JsonProperty("is_refund")
    private Boolean isRefund;

    @JsonProperty("merchant_name")
    private String merchantName;

    @JsonProperty("credit_card_details")
    private CreditCardDetails creditCardDetails;

}