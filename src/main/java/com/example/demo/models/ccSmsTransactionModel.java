package com.example.demo.models;

import com.example.demo.models.CcSmsTransctionKey.CcSmsTransactionKey;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;

import java.util.Date;

@Table("cc_sms_transaction")
@Data
@ToString
public class ccSmsTransactionModel {

    @PrimaryKey
    private CcSmsTransactionKey key;

    @Column("is_refund")
    private Boolean isRefund;

    @Column("merchant_name")
    private String merchantName;

    @Column("txn_type")
    private String txnType;

    @CreatedDate
    @Column("created_at")
    private Date createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private Date updatedAt;

}