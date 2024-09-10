package com.example.demo.models.CcSmsTransctionKey;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;

@Data
public class CcSmsTransactionKey {

    @PrimaryKeyColumn(name = "customer_id", type = PrimaryKeyType.PARTITIONED)
    private String custId;

    @PrimaryKeyColumn(name = "last_cc", type = PrimaryKeyType.PARTITIONED)
    private String card;

    @PrimaryKeyColumn(name = "txn_month", type = PrimaryKeyType.CLUSTERED)
    private String txnMonth;

    @PrimaryKeyColumn(name = "amount", type = PrimaryKeyType.CLUSTERED)
    private double amount;

    @PrimaryKeyColumn(name = "txn_date", type = PrimaryKeyType.CLUSTERED)
    private String txnDate;

    @PrimaryKeyColumn(name = "sms_date_time" , type = PrimaryKeyType.CLUSTERED)
    private String smsDateTime;
}
