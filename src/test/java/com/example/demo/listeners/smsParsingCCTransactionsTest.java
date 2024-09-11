package com.example.demo.listeners;

import com.example.demo.commons.configurations.property.smsParsingCCTransactionsKafkaConfig;
import com.example.demo.dto.SmsEvent;
import com.example.demo.dto.smsParsingCCTransactionsModel;
import com.example.demo.models.CcSmsTransctionKey.CcSmsTransactionKey;
import com.example.demo.models.ccSmsTransactionModel;
import com.example.demo.repository.ccSmsTransaction;
import com.example.demo.utils.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.Acknowledgment;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class smsParsingCCTransactionsTest {

    @Mock
    private ccSmsTransaction ccSmsTransactionRepository;

    @Mock
    private smsParsingCCTransactionsKafkaConfig kafkaConfig;

    @Mock
    private Acknowledgment acknowledgment;

    @InjectMocks
    private smsParsingCCTransactions smsParsingCCTransactions;

    @BeforeEach
    public void setUp() {
        // Initialize mocks if needed
    }

    @Test
    public void testListenDummy() {
        // Given
        String message = "{\"data\":[{\"appCount\":1,\"appVersion\":\"9.6.2\",\"clientId\":\"Unknown\",\"netWorkType\":\"Unknown\",\"latitude\":0,\"preference\":[{\"prefCat\":\"permission\",\"prefKeys\":\"ocl.permission.creditcard.sms_read_consent\",\"prefSubCat\":\"sms consent\"}],\"mId\":\"Unknown\",\"eventType\":\"smsEvent\",\"uploadTime\":1620903472035,\"deviceDateTime\":1620903472035,\"osVersion\":\"28\",\"osType\":\"android\",\"event_name\":\"sms\",\"batteryPercentage\":100,\"model\":\"Unknown\",\"stag_userId\":\"user_c16d599c-0395-4aa-8c7a-0af390df3cf4\",\"brand\":\"CDP-ML\",\"cId\":123456,\"longitude\":0,\"smsUUID\":\"69959a28-b62a-4566-b0af-7bdfc749f60d\",\"smsDateTime\":1658300777249,\"feature\":\"parser_btp\",\"smsSenderID\":\"VM-SBICRD\",\"smsBody\":\"Rs. 199 has been credited to your SBI Credit Card xxxx5524, towards reversal/cashback from AMAZON PAY INDIA PRIVA Bangalore IN for trxn. dated 09/08/2024\",\"sn\":1,\"smsReceiver\":\"\",\"smsOperator\":\"\",\"timestamp\":1724654150426,\"credit_card_details\":{\"card_number\":\"5524\",\"transaction_amount\":\"199.0\",\"transaction_type\":\"cashback_reverse\",\"transaction_date\":\"2024-08-21\",\"bank_name\":\"HDFC\"},\"producerTime\":1724654150}],\"kafka_topic\":[\"SMS_PARSER_CREDIT_CARD_EXTRACTOR\"]}";
        List<String> messages = Collections.singletonList(message);

        // When
        smsParsingCCTransactions.listen(messages, acknowledgment);

        // Then
        verify(ccSmsTransactionRepository, times(1)).insertTransaction(any(), anyString(), any(), any(), any(), any(), any(), any(), any(), any());
        verify(acknowledgment, times(1)).acknowledge();
    }
}