package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreditCardDetails {
    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("transaction_amount")
    private double transactionAmount;

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("transaction_date")
    private String transactionDate;

    @JsonProperty("bank_name")
    private String bankName;
}