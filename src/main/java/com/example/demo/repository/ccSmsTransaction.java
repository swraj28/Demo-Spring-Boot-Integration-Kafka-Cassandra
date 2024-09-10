package com.example.demo.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.models.ccSmsTransactionModel;

import java.util.Date;

@Repository("ccSmsTransaction")
public interface ccSmsTransaction extends CassandraRepository<ccSmsTransactionModel, String> {

    @Query("INSERT INTO cc_sms_transaction (customer_id, last_cc, txn_month, amount, txn_date, sms_date_time, is_refund, merchant_name, txn_type, created_at, updated_at) VALUES (?0, ?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, toTimestamp(now()))")
    void insertTransaction(String customerId, String lastCc, String txnMonth, double amount, String txnDate, String smsDateTime, boolean isRefund, String merchantName, String txnType, Date createdAt);
}