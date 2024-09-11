package com.example.demo.listeners;

import com.example.demo.commons.configurations.property.smsParsingCCTransactionsKafkaConfig;
import com.example.demo.dto.SmsEvent;
import com.example.demo.models.CcSmsTransctionKey.CcSmsTransactionKey;
import com.example.demo.models.ccSmsTransactionModel;
import com.example.demo.repository.ccSmsTransaction;
import com.example.demo.utils.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.example.demo.dto.smsParsingCCTransactionsModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
@DependsOn("smsParsingCCTransactionsKafkaConfig")
public class smsParsingCCTransactions {

    private final Logger logger = LogManager.getLogger(smsParsingCCTransactions.class);

    @Autowired
    private ccSmsTransaction ccSmsTransactionRepository;

    @Autowired
    private smsParsingCCTransactionsKafkaConfig kafkaConfig;

    @KafkaListener(topics = "#{smsParsingCCTransactionsKafkaConfig.getTopicNames()}",
            groupId = "#{smsParsingCCTransactionsKafkaConfig.getConsumerGroup()}",
            containerFactory = "smsParsingCCTransactionsContainerFactory"
    )
    public void listen(@Payload List<String> messages, Acknowledgment acknowledgment) {
        for (String message : messages) {
            try {
                smsParsingCCTransactionsModel smsParsingCCTransactionsModel = null;
                logger.info("[smsParsingCCTransactions.listen] Received message: {}", message);
                // Add your business logic here

                if(Objects.nonNull(message)){
                    smsParsingCCTransactionsModel =  JsonUtils.parseJson(message, smsParsingCCTransactionsModel.class);
                    logger.info("[smsParsingCCTransactions.listen] smsParsingCCTransactionsModel: {}", smsParsingCCTransactionsModel);

                    insertDataIntoCassandra(smsParsingCCTransactionsModel);
                }

            } catch (Exception e){
                logger.error("[smsParsingCCTransactions.listen] Exception occurred for message {}, Exception {}", message, e);
            }
        }
        acknowledgment.acknowledge();
    }

    private void insertDataIntoCassandra(smsParsingCCTransactionsModel smsParsingCCTransactionsModel) {
        try{
            logger.info("[smsParsingCCTransactions.insertDataIntoCassandra] Inserting data into Cassandra for smsParsingCCTransactionsModel: {}", smsParsingCCTransactionsModel);
            if(smsParsingCCTransactionsModel == null || smsParsingCCTransactionsModel.getData() == null) {
                logger.error("[smsParsingCCTransactions.insertDataIntoCassandra] smsParsingCCTransactionsModel or smsParsingCCTransactionsModel.getData() is null");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Parse the data into ccSmsTransactionsModel and insert into Cassandra
            for(SmsEvent smsEvent : smsParsingCCTransactionsModel.getData()){

                LocalDate txnDate = LocalDate.parse(smsEvent.getCreditCardDetails().getTransactionDate(), formatter);
                String txnMonth = txnDate.getYear() + "-" + String.format("%02d", txnDate.getMonthValue());
                smsEvent.setTransactionMonth(txnMonth);

                // Create and set the primary key
                CcSmsTransactionKey key = getCcSmsTransactionKey(smsEvent);

                // Create and set the transaction model
                ccSmsTransactionModel ccSmsTransactionEntity = new ccSmsTransactionModel();
                ccSmsTransactionEntity.setKey(key);
                ccSmsTransactionEntity.setIsRefund(smsEvent.getIsRefund() != null ? smsEvent.getIsRefund() : false);
                ccSmsTransactionEntity.setMerchantName(smsEvent.getMerchantName() != null ? smsEvent.getMerchantName() : "");
                ccSmsTransactionEntity.setTxnType(smsEvent.getCreditCardDetails().getTransactionType());
                if (ccSmsTransactionEntity.getCreatedAt() == null) {
                    ccSmsTransactionEntity.setCreatedAt(new Date());
                }
                ccSmsTransactionEntity.setUpdatedAt(new Date());

                // Insert the data into Cassandra
                insertTransaction(ccSmsTransactionEntity);
            }

        }catch (Exception e){
            logger.error("[smsParsingCCTransactions.insertDataIntoCassandra] Exception occurred for smsParsingCCTransactionsModel {}, Exception {}", smsParsingCCTransactionsModel, e);
        }
    }

    private static CcSmsTransactionKey getCcSmsTransactionKey(SmsEvent smsEvent) {
        CcSmsTransactionKey key = new CcSmsTransactionKey();
        key.setCustId(smsEvent.getCustomerId());
        key.setCard(smsEvent.getCreditCardDetails().getCardNumber());
        key.setTxnMonth(smsEvent.getTransactionMonth());
        key.setAmount(smsEvent.getCreditCardDetails().getTransactionAmount());
        key.setTxnDate(smsEvent.getCreditCardDetails().getTransactionDate());
        key.setSmsDateTime(smsEvent.getSmsDateTime());
        return key;
    }

    public void insertTransaction(ccSmsTransactionModel ccSmsTransactionEntity) {
        ccSmsTransactionRepository.insertTransaction(
                ccSmsTransactionEntity.getKey().getCustId(),
                ccSmsTransactionEntity.getKey().getCard(),
                ccSmsTransactionEntity.getKey().getTxnMonth(),
                ccSmsTransactionEntity.getKey().getAmount(),
                ccSmsTransactionEntity.getKey().getTxnDate(),
                ccSmsTransactionEntity.getKey().getSmsDateTime(),
                ccSmsTransactionEntity.getIsRefund(),
                ccSmsTransactionEntity.getMerchantName(),
                ccSmsTransactionEntity.getTxnType(),
                ccSmsTransactionEntity.getCreatedAt()
        );
    }
}